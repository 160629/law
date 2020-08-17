package com.chinatower.product.legalms.modules.consult.service.flow.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.common.BusinessFiledUtil;
import com.chinatower.product.legalms.common.ConsultConstClass;
import com.chinatower.product.legalms.common.CoreConstClass;
import com.chinatower.product.legalms.common.FlowUtil;
import com.chinatower.product.legalms.common.SpringContextUtil;
import com.chinatower.product.legalms.common.StringUtil;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.consult.service.flow.TFlowService;
import com.chinatower.product.legalms.modules.flow.entity.common.AccountLogic;
import com.chinatower.product.legalms.modules.flow.entity.common.FlowActivityConfigVO;
import com.chinatower.product.legalms.modules.flow.entity.delegate.TFlowDelegate;
import com.chinatower.product.legalms.modules.flow.entity.flow.TBusinessEnd;
import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowLog;
import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowMain;
import com.chinatower.product.legalms.modules.flow.mapper.flow.TFlowDelegateMapper;
import com.chinatower.product.legalms.modules.flow.mapper.flow.TFlowLogMapper;
import com.chinatower.product.legalms.modules.flow.mapper.flow.TFlowMainMapper;
import com.chinatower.product.legalms.modules.flow.mapper.flow.TFlowUnviewConfigMapper;
import com.chinatower.product.legalms.modules.flow.mapper.flow.TFlowUnviewMapper;
import com.chinatower.product.legalms.modules.flow.service.common.BusinessLogService;
import com.chinatower.product.legalms.modules.flow.service.common.FlowActivityConfigService;
import com.chinatower.product.legalms.modules.flow.service.common.UserInfoService;
import com.chinatower.product.legalms.modules.flow.service.unview.TFlowCommonService;
import com.chinatower.product.legalms.modules.flow.vo.common.MyOwnRuntimeException;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowLogVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.BusinessLogConfigVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.OrgParticipantVO;
import com.chinatower.product.legalms.modules.flow.vo.unview.AutoView;
import com.eos.workflow.api.IBPSServiceClient;
import com.eos.workflow.data.WFActivityInst;
import com.eos.workflow.data.WFProcessInst;
import com.eos.workflow.data.WFWorkItem;
import com.eos.workflow.omservice.WFParticipant;
import com.primeton.workflow.api.WFServiceException;

@Service
public class TFlowServiceImpl implements TFlowService {
	private static final String CLASS_CATCH = "TFlowServiceImpl类捕捉异常";
	private static final Logger logger = LoggerFactory.getLogger(TFlowServiceImpl.class);
	@Autowired
	TFlowUnviewMapper unviewMapper;
	@Autowired
	TFlowUnviewConfigMapper unviewConfigMapper;
	@Autowired
	UserInfoService userInfoService;
	@Autowired
	FlowUtil flowUtil;
	@Autowired
	TFlowMainMapper tFlowMainMapper;
	@Autowired
	BusinessLogService businessLogService;
	@Autowired
	TFlowLogMapper tFlowLogMapper;
	@Autowired
	TFlowCommonService commonService;
	@Autowired
	TFlowDelegateMapper tFlowDelegateMapper;
	@Autowired
	FlowActivityConfigService flowActivityConfigService;
	@Override
	public int updateByPrimaryKeySelective(String approveItemType, Map<String, Object> map) {
		logger.info("TFlowServiceImpl修改{}方法参数=====map:{}", approveItemType, map);
		Object bean = SpringContextUtil.getBean(StringUtil.lineToHump(approveItemType));
		if (null == bean || null == map) {
			return 0;
		}
		map = StringUtil.mapLineToHump(map);
		try {
			List<Method> methods = Arrays.asList(bean.getClass().getDeclaredMethods());
			List<Method> method1 = methods.stream().filter(x -> "updateByPrimaryKeySelective".equals(x.getName()))
					.collect(Collectors.toList());
			if (null == method1 || method1.isEmpty()) {
				logger.info("类方法错误或不存在");
				return 0;
			}
			Class<?> cla = method1.get(0).getParameterTypes()[0];
			Object instance = cla.newInstance();
			StringUtil.register();
			BeanUtils.populate(instance, map);
			Object invoke = method1.get(0).invoke(bean, instance);
			return (int) invoke;
		} catch (Exception e) {
			logger.info(CLASS_CATCH+approveItemType,e);
			throw new MyOwnRuntimeException(e);
		}
	}

	@Override
	public Object flowBack(Class clazz, String id, FlowUtil flowUtil) {
		try {
			// 获取类实例
			Object bean = SpringContextUtil.getBean(clazz);
			Method method = clazz.getDeclaredMethod("deleteByPrimaryKey", String.class);
			Object invoke = method.invoke(bean, id);
			IBPSServiceClient client = flowUtil.getClient();
			client.getProcessInstManager().terminateProcessInstance(flowUtil.getFlowId());
			flowUtil.clientBack(true);
			return invoke;
		} catch (Exception e) {
			logger.info(CLASS_CATCH+clazz.getName(),e);
			throw new MyOwnRuntimeException(e);
		}
	}

	// id 业务数据id
	@Override
	public int autoCase(String approveItemType, String id, String pid, Long flowId,String flowName) {
		logger.info("TFlowServiceImpl生成案件{}方法参数=====id:{},pid:{},flowId:{},flowName:{}", approveItemType, id, pid,flowId,flowName);
		if (StringUtil.isEmpty(approveItemType) || StringUtil.isEmpty(id) || StringUtil.isEmpty(pid)) {
			return 0;
		}
		Object bean = SpringContextUtil.getBean(StringUtil.lineToHump(approveItemType));
		if (null == bean) {
			logger.info("参数错误，参数bean:{}", bean);
			return 0;
		}
		List<Method> methods = Arrays.asList(bean.getClass().getDeclaredMethods());
		List<Method> method1 = methods.stream().filter(x -> "autoSaveCase".equals(x.getName()))
				.collect(Collectors.toList());
		UserInfo userInfo = getActivityParticipant(pid, flowId, flowName);
		if (null == method1 || method1.isEmpty() || null==userInfo) {
			logger.info("类方法错误或不存在,userInfo={}",userInfo);
			return 0;
		}

		try {
			Object invoke = method1.get(0).invoke(bean, approveItemType, id, pid,userInfo);
			int i = (int) invoke;
			logger.info("生成卷宗完成条数" + i);
			return i;
		} catch (Exception e) {
			logger.info(CLASS_CATCH+approveItemType,e);
		}
		return 0;
	}


	public UserInfo getActivityParticipant( String pid,Long flowId,String flowName){
		UserInfo info = RequestHolder.getUserInfo(pid);
		String activityDefId= null;
		if(StringUtil.isEmpty(flowName))
			return null;
		else if(flowName.endsWith("lawsuit.head"))
			activityDefId =unviewConfigMapper.selectActivityDefId(flowName, "法务初审人");
		else if(flowName.endsWith("lawsuit.state"))
			activityDefId =unviewConfigMapper.selectActivityDefId(flowName, "法务审核人");
		else if(flowName.endsWith("lawsuit.city"))
			activityDefId =unviewConfigMapper.selectActivityDefId(flowName, "法务接口人");
		else
			return info;
		flowUtil.init(info.getLoginAcct(), info.getLoginName(), ConsultConstClass.TENANLID, null, false);
		if(StringUtil.isEmpty(flowName) || StringUtil.isEmpty(activityDefId)) {
			return null;
		}
		WFWorkItem workItem = flowUtil.getWFWorkItemByFlowId(flowId, activityDefId);
		UserInfo info1 = new UserInfo();
		AccountLogic userInfo = userInfoService.selectUserInfo(workItem.getParticipant());
		info1.setLoginAcct(workItem.getParticipant());
		info1.setLoginName(workItem.getPartiName());
		info1.setDeptId(userInfo.getAccountOrgId());
		info1.setDeptName(userInfo.getDeptName());
		info1.setMobile(userInfo.getMobile());
		info1.setUnitCode(userInfo.getAccountUnitId());
		info1.setOrgCode(userInfo.getAccountOrgId());
		return info1;

	}


	public String setEnterpType(UserInfo info) {
		switch (info.getUnitCode()) {
			/*单位编码 例：CT 代表铁塔 TE 能源 TZ 智联*/
			case "CT":
				return "01";
			case "TE":
				return "02";
			case "TZ":
				return "03";
			default:
				throw new MyOwnRuntimeException("获取组织id失败");
		}
	}

	@Override
	public String getRoleLevel(List<Object> roleCodeList) {
		List<String> list = Arrays.asList(
				//法务初审人（法务专员）
				"CHNTLEGALMS_13",
				//法务审核人（法务人员）
				"CHNTLEGALMS_17",
				//法务部门负责人
				"CHNTLEGALMS_33",
				//董事长
				"CHNTLEGALMS_23",
				//公司总经理
				"CHNTLEGALMS_39",
				//系统管理员
				"CHNTLEGALMS_7",
				//法务部门分管领导
				"CHNTLEGALMS_36"
		);
		String role;
		if (roleCodeList.contains("CHNTLEGALMS_0")) {// 超级管理员
			return "whole";
		}
		for (int i = 0; i < roleCodeList.size(); i++) {
			role = (String) roleCodeList.get(i);
			if (list.contains(role)) {
				return "all";
			}
		}
//		if (roleCodeList.contains("CHNTLEGALMS_36")) {//分管领导
//			return "fgld";
//		}
		if (roleCodeList.contains("CHNTLEGALMS_43") || list.contains("CHNTLEGALMS_24")) {// 部门人员
			return "bmry";
		}
		return "mr";
	}
	@Override
	public void autoUnViewBefore(String title, TFlowLog record, FlowUtil flowUtil, UserInfo info,AutoView view) {
		TFlowMain tFlowMain = tFlowMainMapper
				.selectByPrimaryKey(record.getFlowPid() == 0 ? record.getFlowId() : record.getFlowPid());
		WFParticipant dqclr = new WFParticipant(info.getLoginAcct(), info.getLoginName(), "emp");
		WFParticipant qcr = new WFParticipant(tFlowMain.getCreateUserId(), tFlowMain.getCreateUserName(), "emp");
		List<WFParticipant> toers = new ArrayList<>();
		toers.add(qcr);
		String fwryStr = null;
		String flowDefName = flowUtil.getFlowDefName(record.getFlowId());
		switch (flowDefName) {
		case "com.tower.issue.lawsuit.head":
			fwryStr = "fwcsrsh";
			break;
		case "com.tower.issue.lawsuit.state":
			fwryStr = "fwshrsh";
			break;
		case "com.tower.issue.lawsuit.city":
			fwryStr = "dsfwjkrqr";
			break;
		default:
			break;
		}
		if (flowUtil.getFlowDefName(record.getFlowId()).startsWith("com.tower.issue.lawsuit")) {
			WFWorkItem fwryWFWorkItem = flowUtil.queryWorkItemByActInstId(
					flowUtil.findLastActivityInstByActivityID(record.getFlowId(), fwryStr).getActivityInstID());
			WFParticipant fwry = new WFParticipant(fwryWFWorkItem.getParticipant(), fwryWFWorkItem.getPartiName(),
					"emp");
			toers.add(fwry);
		}
		view.setShortFlowName(flowDefName).setTitle(title).setId( record.getApproveItemId())
		.setLogin(dqclr).setToers(toers);
		commonService.autoUnView(view,info);

	}

	@Override
	public void insertBusinessLog(UserInfo info, String approveItemId, String fileBusinessAdvice, String activityDefId,
			Map<String, Object> businessMap, WFProcessInst processInst2) {
		List<BusinessLogConfigVO> businessLogConfigVOS = businessLogService.selectConfigActs(processInst2.getProcessDefName(), (Integer)businessMap.get("versionId"));
		if (businessLogConfigVOS != null) {
			businessLogConfigVOS.forEach(x -> {
				if (x.getActivityDefName().contains(activityDefId)) {
					businessMap.put("fileId", fileBusinessAdvice);
					businessMap.put("approveItemId", approveItemId);
					businessMap.put("actId", activityDefId);
					businessLogService.insertBusinessLog(info, businessMap);
				}
			});
		}
	}

	@Override
	public void finishActivity(AddFlowLogVO vo, FlowUtil flowUtil, UserInfo info, TFlowLog record,WFProcessInst processInst2,
			long flowId,AutoView view) {
		try {
			// 更新流程主表-status--->30（已办结状态）
			tFlowMainMapper.updateByPrimaryKeySelective(new TFlowMain().setFlowStatus("30").setFlowId(flowId));
			// 生成办结
			TBusinessEnd tBusinessEnd = new TBusinessEnd();
			tBusinessEnd.setId(StringUtil.getId()).setApproveItemId(record.getApproveItemId())
					.setApproveItemType(record.getApproveItemType()).setBusinessTitle(vo.getTitle())
					.setFlowStatus("已办结").setFlowId(flowId).setBusinessCode(vo.getCode())
					.setBusinessType(BusinessFiledUtil.getBusinessType(record.getApproveItemType()))
					.setModuleName(vo.getModuleName()).setUpdateTime(StringUtil.longStrict());
			tFlowLogMapper.insertTBusinessEnd(tBusinessEnd);
			// 生成待阅
			autoUnViewBefore(vo.getTitle(), record, flowUtil, info,view);

			autoCase(record.getApproveItemType(), record.getApproveItemId(), RequestHolder.getPid(),
					flowId, processInst2.getProcessDefName());
		} catch (Exception e) {
			logger.info("审批生成待阅或生成案件报误",e);
		}

	}

	@Override
	public int isCurUser(Long flowId) {
		UserInfo userInfo = RequestHolder.getUserInfo();
		flowUtil.init(userInfo.getLoginAcct(), userInfo.getLoginName(), ConsultConstClass.TENANLID, null, false);
		int result = 0;
		try {
			List<WFActivityInst> wfActivityInsts = flowUtil.getClient().getActivityInstManager().queryActivityInstsByProcessInstID(flowId, null);
			if (wfActivityInsts == null || wfActivityInsts.isEmpty()) {
				return 1;
			}
			List<WFActivityInst> collect = wfActivityInsts.stream().filter(x -> x.getCurrentState() == 2).collect(Collectors.toList());
			for (WFActivityInst mainFlowActInst : collect) {
				if ("manual".equals(mainFlowActInst.getActivityType())) {
					// 主流程 人工活动节点
					// 获取 委托待办
					TFlowDelegate tFlowDelegate = new TFlowDelegate();
					tFlowDelegate.setLoginAcct(flowUtil.queryWorkItemsByActivityInstID(mainFlowActInst.getActivityInstID(), null).get(0).getParticipant());
					List<TFlowDelegate> tFlowDelegate1 = tFlowDelegateMapper.selectAll(tFlowDelegate);
					// 是否 当前人 在 待办环节 打开详情
					isSameUser(userInfo, mainFlowActInst, tFlowDelegate1, mainFlowActInst.getProcessInstID());
					if (flowUtil.getRelativeData(mainFlowActInst.getProcessInstID(), ConsultConstClass.UNVIEW) != null) {
						result += (int)flowUtil.getRelativeData(mainFlowActInst.getProcessInstID(), ConsultConstClass.UNVIEW);
					}
				} else if ("subflow".equals(mainFlowActInst.getActivityType())) {
					// 主流程 子流程活动节点
					long[] longs = flowUtil.getClient().getProcessInstManager().querySubProcessInstIDsByActivityInstID(mainFlowActInst.getActivityInstID());
					for (long l : longs) {
						result += isCurUser(l);
					}
					result += Arrays.stream(longs).map(x -> flowUtil.getRelativeData(x, ConsultConstClass.UNVIEW) == null ? 0 : (int) flowUtil.getRelativeData(x, ConsultConstClass.UNVIEW)).sum();
					return  result == 0 ? 0 : 1;
				}
			}
		} catch (Exception e) {
			logger.error("设置已读失败",e);
		}
		return result;
	}

	private void isSameUser(UserInfo userInfo, WFActivityInst wfActivityInst, List<TFlowDelegate> tFlowDelegate1, Long processInstId) {
		if ((StringUtil.listNotEmpty(tFlowDelegate1) && tFlowDelegate1.equals(userInfo.getLoginAcct())) || flowUtil.queryWorkItemsByActivityInstID(wfActivityInst.getActivityInstID(), null).get(0).getParticipant().equals(userInfo.getLoginAcct())){
			flowUtil.setRelativeData(processInstId, ConsultConstClass.UNVIEW, 1);
		}
	}

	public void setIsUnView(String signDept, UserInfo info, Long flowId) {
		flowUtil.init(info.getLoginAcct(), info.getLoginName(), ConsultConstClass.TENANLID, null, false);
		if ("1".equals(signDept)) {
			try {
				List<WFActivityInst> wfActivityInsts = flowUtil.getClient().getActivityInstManager().queryActivityInstsByProcessInstID(flowId, null);
				for (WFActivityInst w : wfActivityInsts) {
					if ("subflow".equals(w.getActivityType())) {
						long[] longs = flowUtil.getClient().getProcessInstManager().querySubProcessInstIDsByActivityInstID(w.getActivityInstID());
						for (int j = 0; j < longs.length; j++) {
							flowUtil.setRelativeData(longs[j], ConsultConstClass.UNVIEW, 0);
						}
					}
				}
			} catch (WFServiceException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	@Transactional
	public ProcessResult subFlowDrawBack(AddFlowLogVO vo) {
		try {
			logger.error("终止会签参数：" + vo.toString());
			TFlowLog record = vo.gettFlowLog();// 日志对象
			UserInfo info = RequestHolder.getUserInfo();// 获取当前登录人信息
			TFlowMain tFlowMain = tFlowMainMapper.selectByBusinessId(record.getApproveItemId());
			FlowActivityConfigVO flowActivityConfigVO = flowActivityConfigService.selActivityId(tFlowMain.getFlowName(),
					record.getActivityDefId(), record.getNextActivityDefId(), Integer.parseInt(tFlowMain.getVersionId()));
			if ("1".equals(flowActivityConfigVO.getIsSonReturn())) {
				flowUtil.init(info.getLoginAcct(), info.getLoginName(), CoreConstClass.TENANLID, null, true);
				WFActivityInst wfActivityInst1 = flowUtil.findLastActivityInstByActivityID(record.getFlowId(), record.getActivityDefId());
				long flowId = tFlowMain.getFlowId();
				record.setOptionType(vo.getOptionType());
				record.setFlowPid(flowId); // 设置父流程ID
				record.setFlowDefName(flowUtil.getFlowDefName(record.getFlowId()));// 设置流程定义名称
				WFWorkItem wfWorkItem = flowUtil.queryWorkItemByActInstId(wfActivityInst1.getActivityInstID());
				record.setToerId(wfWorkItem.getParticipant());
				record.setToerName(wfWorkItem.getPartiName());
				record.setWorkType(record.getToerId().equals(info.getLoginAcct()) ? "1" : "2");
				record.setActivityInstId(wfActivityInst1.getActivityInstID());// 日志表记录活动实例
				record.setFlowLogId(StringUtil.getId());// 生成日志表ID
				long parentActInstId = (long) flowUtil.getRelativeData(flowId, "parentActInstId");
				record.setActPid(parentActInstId);
				List<OrgParticipantVO> orgs = vo.getOrgs();
				BusinessFiledUtil.setReceiverInfo(record, orgs, info);// 设置日志表-下一步审批人及当前审批人信息
				tFlowLogMapper.insertSelective(record);// 生成审批日志记录
				List<WFActivityInst> wfActivityInsts = flowUtil.getClient().getActivityInstManager().queryActivityInstsByProcessInstID(flowId, null);
				List<WFActivityInst> collect = wfActivityInsts.stream().filter(x -> x.getCurrentState() == 2).collect(Collectors.toList());
				WFActivityInst wfActivityInst = collect.get(0);
				flowUtil.getClient().getActivityInstManager().terminateActivityInstance(wfActivityInst.getActivityInstID());
				flowUtil.getClient().getActivityInstManager().restartActivityInstance(parentActInstId);
				flowUtil.clientCommit(true);
				return new ProcessResult(ProcessResult.SUCCESS, "终止会签成功，已退回会签发起人");
			}
		} catch (Exception e) {
			logger.error("终止会签失败", e);
			if (flowUtil != null) {
				flowUtil.clientBack(true);// 流程回退
			}
			return new ProcessResult(ProcessResult.ERROR, "终止会签失败");
		}
		return null;
	}

}
