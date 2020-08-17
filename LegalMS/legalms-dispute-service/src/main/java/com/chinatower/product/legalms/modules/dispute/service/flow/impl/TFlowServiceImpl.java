package com.chinatower.product.legalms.modules.dispute.service.flow.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.common.BusinessFiledUtil;
import com.chinatower.product.legalms.common.DisputeConstClass;
import com.chinatower.product.legalms.common.FlowUtil;
import com.chinatower.product.legalms.common.SpringContextUtil;
import com.chinatower.product.legalms.common.StringUtil;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.dispute.entity.LegislationVO;
import com.chinatower.product.legalms.modules.dispute.entity.issue.TIssueJointly;
import com.chinatower.product.legalms.modules.dispute.entity.issue.TIssueLawsuit;
import com.chinatower.product.legalms.modules.dispute.mapper.issue.TIssueGuideMapper;
import com.chinatower.product.legalms.modules.dispute.mapper.issue.TIssueJointlyMapper;
import com.chinatower.product.legalms.modules.dispute.mapper.issue.TIssueLawsuitMapper;
import com.chinatower.product.legalms.modules.dispute.mapper.legislation.LegislationMapper;
import com.chinatower.product.legalms.modules.dispute.service.flow.TFlowDelegateService;
import com.chinatower.product.legalms.modules.dispute.service.flow.TFlowService;
import com.chinatower.product.legalms.modules.flow.entity.common.AccountLogic;
import com.chinatower.product.legalms.modules.flow.entity.delegate.TFlowDelegate;
import com.chinatower.product.legalms.modules.flow.entity.flow.TBusinessEnd;
import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowLog;
import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowMain;
import com.chinatower.product.legalms.modules.flow.entity.unview.TFlowUnviewConfig;
import com.chinatower.product.legalms.modules.flow.mapper.flow.TFlowDelegateMapper;
import com.chinatower.product.legalms.modules.flow.mapper.flow.TFlowLogMapper;
import com.chinatower.product.legalms.modules.flow.mapper.flow.TFlowMainMapper;
import com.chinatower.product.legalms.modules.flow.mapper.flow.TFlowUnviewConfigMapper;
import com.chinatower.product.legalms.modules.flow.mapper.flow.TFlowUnviewMapper;
import com.chinatower.product.legalms.modules.flow.service.common.BusinessLogService;
import com.chinatower.product.legalms.modules.flow.service.common.UserInfoService;
import com.chinatower.product.legalms.modules.flow.service.unview.TFlowCommonService;
import com.chinatower.product.legalms.modules.flow.vo.common.MyOwnRuntimeException;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowLogVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.BusinessLogConfigVO;
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
	TFlowDelegateService tFlowDelegateService;
	@Autowired
	TIssueGuideMapper guideMapper;
	@Autowired
	TIssueLawsuitMapper lawsuitMapper;
	@Autowired
	LegislationMapper legislationMapper;
	@Autowired
	TIssueJointlyMapper jointlyMapper;
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
			logger.info(CLASS_CATCH , e);
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
			logger.info(CLASS_CATCH + clazz.getName(), e);
			throw new MyOwnRuntimeException(e);
		}
	}

	// id 业务数据id
	@Override
	public int autoCase(String approveItemType, String id, String pid, Long flowId, String flowName) {
		logger.info("TFlowServiceImpl生成案件{}方法参数=====id:{},pid:{},flowId:{},flowName:{}", approveItemType, id, pid,
				flowId, flowName);
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
		if (null == method1 || method1.isEmpty() || null == userInfo) {
			logger.info("类方法错误或不存在,userInfo={}", userInfo);
			return 0;
		}

		try {
			Object invoke = method1.get(0).invoke(bean, approveItemType, id, pid, userInfo);
			int i = (int) invoke;
			logger.info("生成卷宗完成条数" + i);
			return i;
		} catch (Exception e) {
			logger.info(CLASS_CATCH + approveItemType, e);
		}
		return 0;
	}

	public Integer verifyAutoUnView(String approveItemType, String id, Map<String, Object> caseMap,
			List<TFlowUnviewConfig> list, List<WFParticipant> toers) {
		Integer lawsuitOrder = null;
		String caseId = null;
		if (approveItemType.equals(DisputeConstClass.T_ISSUE_LAWSUIT)) {
			TIssueLawsuit lawsuit = lawsuitMapper.selectByPrimaryKey(id);
			caseId = lawsuit.getCaseId();
		} else if (approveItemType.equals("t_case_legislation")) {
			LegislationVO legislationVO = legislationMapper.selectByPrimaryKey(id);
			caseId = legislationVO.getCaseFile();
		} else if (approveItemType.equals("t_issue_jointly")) {
			TIssueJointly issueJointly = jointlyMapper.selectByPrimaryKey(id);
			caseId = issueJointly.getJointlyCase();
		}
		if (approveItemType.equals(DisputeConstClass.T_ISSUE_LAWSUIT) && StringUtil.isEmpty(caseId)) {
			list.remove(list.size() - 1);
			lawsuitOrder = 2;
		} else if (approveItemType.equals(DisputeConstClass.T_ISSUE_LAWSUIT) && !StringUtil.isEmpty(caseId)) {
			list.remove(list.size() - 2);
			lawsuitOrder = 3;
			Map<String, Object> case1 = guideMapper.selectCase(caseId);
			caseMap.putAll(case1);
			WFParticipant part = toers.get(1);
			part.setId(caseMap.get("creator_account_id") + "");
			part.setName(caseMap.get("creator_account_name") + "");
		} else if (toers.size() > 1) {
			caseMap = guideMapper.selectCase(caseId);
			WFParticipant part = toers.get(1);
			part.setId(caseMap.get("creator_account_id") + "");
			part.setName(caseMap.get("creator_account_name") + "");
		}
		return lawsuitOrder;
	}

	@Override
	public TFlowDelegate verifyDelegate(String loginAcct) {
		logger.info("TFlowServiceImpl验证是否委托方法参数=====loginAcct:{}", loginAcct);
		if (StringUtil.isEmpty(loginAcct)) {
			return null;
		}
		TFlowDelegate tFlowDelegate = new TFlowDelegate(loginAcct);
		List<TFlowDelegate> tFlowDelegates = tFlowDelegateService.selectAll(tFlowDelegate);
		if (tFlowDelegates != null && !tFlowDelegates.isEmpty()) {
			return tFlowDelegates.get(0);
		}
		return null;
	}

	public UserInfo getActivityParticipant(String pid, Long flowId, String flowName) {
		UserInfo info = RequestHolder.getUserInfo(pid);
		String activityDefId = null;
		if (StringUtil.isEmpty(flowName))
			return null;
		else if (flowName.endsWith("lawsuit.mrg.city"))
			activityDefId = DisputeConstClass.SFFWSHRSH ;
		else if (flowName.endsWith("lawsuit.head"))
			activityDefId = DisputeConstClass.FWCSRSH;
		else if (flowName.startsWith(DisputeConstClass.FLOW_LAWSUIT))
			activityDefId = DisputeConstClass.FWSHRSH;
		else
			return info;
		flowUtil.init(info.getLoginAcct(), info.getLoginName(), DisputeConstClass.TENANLID, null, false);
		if (StringUtil.isEmpty(flowName) || StringUtil.isEmpty(activityDefId)) {
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
		info1.setOrgName(userInfo.getCompanyName());
		return info1;

	}


	public String setEnterpType(UserInfo info) {
		switch (info.getUnitCode()) {
		/* 单位编码 例：CT 代表铁塔 TE 能源 TZ 智联 */
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

	public String getRoleLevel(List<Object> roleCodeList) {
		String[] selectAllRoleList = { "CHNTLEGALMS_13", // 法务初审人（法务专员）
				"CHNTLEGALMS_17", // 法务审核人（法务人员）
				"CHNTLEGALMS_33", // 法务部门负责人
				"CHNTLEGALMS_23", // 董事长
				"CHNTLEGALMS_39", // 公司总经理
				"CHNTLEGALMS_7",// 系统管理员
				"CHNTLEGALMS_36"
		};
		List<String> list = Arrays.asList(selectAllRoleList);
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

//		if (roleCodeList.contains("CHNTLEGALMS_36")) {// 分管领导
//			return "fgld";
//		}
		if (roleCodeList.contains("CHNTLEGALMS_43") || list.contains("CHNTLEGALMS_24")) {// 部门人员
			return "bmry";
		}

		return "mr";
	}

	@Override
	public void autoUnViewBefore(String title, TFlowLog record, FlowUtil flowUtil, UserInfo info, AutoView view) {
		TFlowMain tFlowMain = tFlowMainMapper
				.selectByPrimaryKey(record.getFlowPid() == 0 ? record.getFlowId() : record.getFlowPid());
		WFParticipant dqclr = new WFParticipant(info.getLoginAcct(), info.getLoginName(), "emp");
		WFParticipant qcr = new WFParticipant(tFlowMain.getCreateUserId(), tFlowMain.getCreateUserName(), "emp");
		List<WFParticipant> toers = new ArrayList<>();
		toers.add(qcr);
		String flowDefName = flowUtil.getFlowDefName(record.getFlowId());
/*		String fwryStr = null;
		if (flowDefName.equals("com.tower.issue.lawsuit.mrg.city")) {
			fwryStr = DisputeConstClass.SFFWSHRSH;
		}else if (flowDefName.equals("com.tower.issue.lawsuit.head")) {
			fwryStr = DisputeConstClass.FWCSRSH;
		} else if (flowDefName.startsWith("com.tower.issue.lawsuit")) {
			fwryStr = "fwshrsh";
		}
		if (flowUtil.getFlowDefName(record.getFlowId()).startsWith(DisputeConstClass.FLOW_LAWSUIT)) {
			WFWorkItem fwryWFWorkItem = flowUtil.queryWorkItemByActInstId(
					flowUtil.findLastActivityInstByActivityID(record.getFlowId(), fwryStr).getActivityInstID());
			WFParticipant fwry = new WFParticipant(fwryWFWorkItem.getParticipant(), fwryWFWorkItem.getPartiName(),
					"emp");
			toers.add(fwry);
		}*/
		view.setShortFlowName(flowDefName).setTitle(title).setId(record.getApproveItemId()).setLogin(dqclr)
				.setToers(toers);
		commonService.autoUnView(view, info);

	}

	@Override
	public void insertBusinessLog(UserInfo info, String approveItemId, String fileBusinessAdvice, String activityDefId,
			Map<String, Object> businessMap, WFProcessInst processInst2) {
		String defName = processInst2.getProcessDefName();
		String lssh = "lssh";
		String sflssh = "sflssh";
//		String dslssh = "dslssh";
//		String dsfwjkrsh = "dsfwjkrsh";
		String fwcsrsh = "fwcsrsh";
		String fwshrsh = "fwshrsh";
		String sffwshrsh = "sffwshrsh";
		Map<String, String[]> hashMap = new HashMap<>();

//		hashMap.put("com.tower.issue.guide.head", new String[]{ConstClass.FWCSRSH, lssh});
//		hashMap.put("com.tower.issue.guide.state", new String[]{ConstClass.FWSHRSH, lssh});
//		hashMap.put("com.tower.issue.guide.city", new String[]{ConstClass.SFFWSHRSH, sflssh, dslssh, dsfwjkrsh});
		hashMap.put("com.tower.issue.lawsuit.head", new String[]{lssh, fwcsrsh, fwshrsh});
		hashMap.put("com.tower.issue.lawsuit.state", new String[]{lssh, fwshrsh});
		hashMap.put("com.tower.issue.lawsuit.city", new String[]{lssh, fwshrsh});
		hashMap.put("com.tower.issue.lawsuit.mrg.city", new String[]{sffwshrsh, sflssh});
		String[] string = hashMap.get(defName);
		if (string != null) {
			List<String> list = Arrays.asList(string);
			if (list.contains(activityDefId)) {
				businessMap.put("fileId", fileBusinessAdvice);
				businessMap.put("approveItemId", approveItemId);
				businessLogService.insertBusinessLog(info, businessMap);
			}
		}
	}

	@Override
	public void insertBusinessLog2(UserInfo info, String approveItemId, String fileBusinessAdvice, String activityDefId, Map<String, Object> businessMap, WFProcessInst processInst2) {
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
	public void finishActivity(AddFlowLogVO vo, FlowUtil flowUtil, UserInfo info, TFlowLog record,
			WFProcessInst processInst2, long flowId, AutoView view) {
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
			autoUnViewBefore(vo.getTitle(), record, flowUtil, info, view);
		} catch (Exception e) {
			logger.info("审批生成待阅报误", e);
		}
		try {
			autoCase(record.getApproveItemType(), record.getApproveItemId(), RequestHolder.getPid(), flowId,
					processInst2.getProcessDefName());
		} catch (Exception e) {
			logger.info("审批生成待阅或生成案件报误",e);
		}

	}
	@Override
	public int isCurUser2(Long flowId) {
		UserInfo userInfo = RequestHolder.getUserInfo();
		flowUtil.init(userInfo.getLoginAcct(), userInfo.getLoginName(), DisputeConstClass.TENANLID, null, false);
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
					if (flowUtil.getRelativeData(mainFlowActInst.getProcessInstID(), DisputeConstClass.UNVIEW) != null) {
						result += (int)flowUtil.getRelativeData(mainFlowActInst.getProcessInstID(), DisputeConstClass.UNVIEW);
					}
				} else if ("subflow".equals(mainFlowActInst.getActivityType())) {
					// 主流程 子流程活动节点
					long[] longs = flowUtil.getClient().getProcessInstManager().querySubProcessInstIDsByActivityInstID(mainFlowActInst.getActivityInstID());
					for (long l : longs) {
						result += isCurUser2(l);
					}
					result += Arrays.stream(longs).map(x -> flowUtil.getRelativeData(x, DisputeConstClass.UNVIEW) == null ? 0 : (int) flowUtil.getRelativeData(x, DisputeConstClass.UNVIEW)).sum();
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
			flowUtil.setRelativeData(processInstId, DisputeConstClass.UNVIEW, 1);
		}
	}

	public void setIsUnView(String signDept, UserInfo info, Long flowId) {
		flowUtil.init(info.getLoginAcct(), info.getLoginName(), DisputeConstClass.TENANLID, null, false);
		if ("1".equals(signDept)) {
			try {
				List<WFActivityInst> wfActivityInsts = flowUtil.getClient().getActivityInstManager().queryActivityInstsByProcessInstID(flowId, null);
				for (WFActivityInst w : wfActivityInsts) {
					if ("subflow".equals(w.getActivityType())) {
						long[] longs = flowUtil.getClient().getProcessInstManager().querySubProcessInstIDsByActivityInstID(w.getActivityInstID());
						for (int j = 0; j < longs.length; j++) {
							flowUtil.setRelativeData(longs[j], DisputeConstClass.UNVIEW, 0);
						}
					}
				}
			} catch (WFServiceException e) {
				e.printStackTrace();
			}
		}
	}
}
