package com.chinatower.product.legalms.modules.dispute.service.issue.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.common.BusinessFiledUtil;
import com.chinatower.product.legalms.common.DisputeConstClass;
import com.chinatower.product.legalms.common.FlowUtil;
import com.chinatower.product.legalms.common.SourceName;
import com.chinatower.product.legalms.common.StringUtil;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.dispute.entity.issue.TIssueGuide;
import com.chinatower.product.legalms.modules.dispute.mapper.issue.TIssueGuideMapper;
import com.chinatower.product.legalms.modules.dispute.service.flow.TFlowService;
import com.chinatower.product.legalms.modules.dispute.service.issue.TIssueGuideService;
import com.chinatower.product.legalms.modules.dispute.vo.issue.ListParam;
import com.chinatower.product.legalms.modules.dispute.vo.issue.TIssueGuideVO;
import com.chinatower.product.legalms.modules.dispute.vo.querylist.TIssueGuideListVO;
import com.chinatower.product.legalms.modules.flow.entity.common.DraftsVO;
import com.chinatower.product.legalms.modules.flow.entity.common.OrgBeanVO;
import com.chinatower.product.legalms.modules.flow.entity.flow.FlowStartVO;
import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowLog;
import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowMain;
import com.chinatower.product.legalms.modules.flow.mapper.common.DraftsVOMapper;
import com.chinatower.product.legalms.modules.flow.mapper.flow.TFlowLogMapper;
import com.chinatower.product.legalms.modules.flow.mapper.flow.TFlowMainMapper;
import com.chinatower.product.legalms.modules.flow.mapper.version.FlowVersionMapper;
import com.chinatower.product.legalms.modules.flow.service.common.FlowActivityConfigService;
import com.chinatower.product.legalms.modules.flow.service.common.OrgBeanService;
import com.chinatower.product.legalms.modules.flow.service.common.OrgDepLeaderService;
import com.chinatower.product.legalms.modules.flow.service.unview.TFlowCommonService;
import com.chinatower.product.legalms.modules.flow.vo.common.MyOwnRuntimeException;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowLogVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.BusinessFields;
import com.chinatower.product.legalms.modules.flow.vo.flow.OrgParticipantVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.SubTFlowLogVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.TFlowLogVO;
import com.chinatower.product.legalms.modules.flow.vo.unview.AutoView;
import com.chinatower.product.legalms.utils.HttpUtils;
import com.chinatower.product.legalms.vo.CacheModel;
import com.chinatower.provider.call.oauth.RedisServiceClient;
import com.eos.workflow.data.WFActivityInst;
import com.eos.workflow.data.WFProcessInst;
import com.eos.workflow.data.WFWorkItem;
import com.eos.workflow.omservice.WFParticipant;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 引诉信息表 服务实现类
 * </p>
 *
 * @author wangyong
 * @since 2019-09-30
 */
@Service("tIssueGuide")
public class TIssueGuideServiceImpl implements TIssueGuideService {
	@Autowired
	TIssueGuideMapper mapper;
	@Autowired
	TFlowMainMapper tFlowMainMapper;
	@Autowired
	TFlowLogMapper tFlowLogMapper;
	@Autowired
	private DraftsVOMapper draftsVOMapper;
	@Autowired
	private RedisServiceClient redisServiceClient;
	@Autowired
	TFlowCommonService commonService;
	@Autowired
	TFlowService tFlowService;


	private static final String CLASS_CATCH = "TIssueGuideServiceImpl类捕捉异常";

	@Autowired
	OrgBeanService orgBeanService;
	@Autowired
	OrgDepLeaderService orgDepLeaderService;
	@Autowired
	FlowUtil flowUtil;
	@ApiModelProperty
	private static final Logger logger = LoggerFactory.getLogger(TIssueGuideServiceImpl.class);
	@Autowired
	FlowVersionMapper flowVersionService;
	@Autowired
	FlowActivityConfigService flowActivityConfigService;


	@Override
	@Transactional
	public ProcessResult addTFlowLog(AddFlowLogVO vo) {
		Map<String, Object> bizInfo = new HashMap<>(); // 业务冗余字段

		UserInfo info = RequestHolder.getUserInfo();// 获取当前登录人信息
		TFlowLog record = vo.gettFlowLog();// 日志对象
		record.setOptionType(vo.getOptionType());
		List<OrgParticipantVO> orgs = vo.getOrgs();
		boolean finishFlag = false;
		long flowId = (record.getFlowPid() == null || record.getFlowPid() == 0) ? record.getFlowId()
				: record.getFlowPid();
		WFProcessInst processInst2 = null;
		try {
			// 流程操作对象
			flowUtil.init(info.getLoginAcct(), info.getLoginName(), DisputeConstClass.TENANLID, null, true);
			// 查询当前活动实例
			WFActivityInst wfActivityInst = flowUtil.findLastActivityInstByActivityID(record.getFlowId(),
					record.getActivityDefId());
			// commonService.setApprovalAccountId(vo.getOrgs(), participant);// redis记录审批人

			WFProcessInst processInst = flowUtil.queryProcessInstDetail(record.getFlowId());// 查询流程详情
			record.setFlowPid(processInst.getParentProcID()); // 设置父流程ID
			record.setFlowDefName(flowUtil.getFlowDefName(record.getFlowId()));// 设置流程定义名称
			// 设置 参与者 及 判断条件 并 更新 流程相关数据
			BusinessFiledUtil.setRelativeData(record, vo.getSignDept(), vo.getOrgs(), flowUtil, wfActivityInst);// 设置
																												// 参与者 及
																												// 判断条件
			processInst2 = flowUtil.queryProcessInstDetail(flowId);// 查询流程详情
			// 审批最后一步
			if ("finishActivity".equals(vo.gettFlowLog().getNextActivityDefId())
					&& processInst.getParentProcID() == 0) {
				bizInfo.put(DisputeConstClass.BUSINESS_TABLE_CURWORK, "已办结");// 设置业务冗余数据-当前状态-已办结
				finishFlag = true;
			}
			bizInfo.put(DisputeConstClass.BUSINESS_TABLE_TITLE, vo.getTitle());// 设置业务冗余数据-当前状态-已办结
			flowUtil.setProcessInstName(vo.getTitle(), flowId);
			// 设置委托人，委托类型
			WFWorkItem wfWorkItem = flowUtil.queryWorkItemByActInstId(wfActivityInst.getActivityInstID());
			flowUtil.setMainFlowBusinessFields(flowUtil, bizInfo, processInst2);// 更新主流程业务荣誉字段
			record.setToerId(wfWorkItem.getParticipant());
			record.setToerName(wfWorkItem.getPartiName());
			// 完成当前活动实例-工作项
			flowUtil.finishWorkItemByActInstId(wfActivityInst.getActivityInstID(), false);
			record.setActivityInstId(wfActivityInst.getActivityInstID());// 日志表记录活动实例
			record.setFlowLogId(StringUtil.getId());// 生成日志表ID
			BusinessFiledUtil.setReceiverInfo(record, orgs, info);// 设置日志表-下一步审批人及当前审批人信息
			if (!StringUtil.mapEmpty(vo.getBusinessMap())) {// 编辑功能
				// 更新业务表数据
				updateByPrimaryKeySelective(
						StringUtil.getModel(TIssueGuide.class, (HashMap<String, Object>) vo.getBusinessMap()));
				tFlowService.insertBusinessLog(info, vo.gettFlowLog().getApproveItemId(), vo.getFileBusinessAdvice(),
						record.getActivityDefId(), vo.getBusinessMap(), processInst2);
			}
			flowUtil.clientCommit(true);// 提交流程
		} catch (Exception e) {
			logger.info("引诉审批错误", e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚数据
			if (flowUtil != null) {
				flowUtil.clientBack(true);// 流程回退
			}
			return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.NEXT_FLOW_ERROR);
		}
		flowUtil.init(info.getLoginAcct(), info.getLoginName(), DisputeConstClass.TENANLID, null, false);

		try {
			WFActivityInst lastActivityInstByActivityID = flowUtil.findLastActivityInstByActivityID(record.getFlowId(),
					record.getActivityDefId());
			record.setWorkType(record.getToerId().equals(info.getLoginAcct()) ? "1" : "2");
			if (!StringUtil.listEmpty(orgs)) {
				commonService.delegateWorkItem(flowUtil, lastActivityInstByActivityID); // 委托人判断
			}
			tFlowLogMapper.insertSelective(record);// 生成审批日志记录
		} catch (Exception e) {
			logger.info("引诉审批错误", e);
			if (flowUtil != null) {
				flowUtil.clientBack(true);// 流程回退
			}
			return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.NEXT_FLOW_ERROR);
		}
		if (finishFlag) {
			finishActivity(vo, flowUtil, info, record, processInst2, flowId);
		}
		return new ProcessResult(ProcessResult.SUCCESS, DisputeConstClass.NEXT_FLOW_SUCCESS);
	}

	public void finishActivity(AddFlowLogVO vo, FlowUtil flowUtil, UserInfo info, TFlowLog record,
			WFProcessInst processInst2, long flowId) {
		TIssueGuide guide = mapper.selectByPrimaryKey(record.getApproveItemId());
		AutoView view = new AutoView();
		view.setDeptName(guide.getDeptName()).setPutLoginName(guide.getLoginName())
				.setLogin(new WFParticipant(info.getLoginAcct(), info.getLoginName(), "emp"))
				.setPutLoginDept(guide.getDeptId());
/*		String flowDefName = record.getFlowDefName();
		if (flowDefName.endsWith("city")) {
			String code2 = RequestHolder.getProvinceCode(guide.getDeptId());
			OrgBeanVO orgBeanVO = orgBeanService.selectOrgInfoByOrgCode(code2);
			view.setStateName(orgBeanVO.getOrgName()).setCityOrgName(guide.getOrgName());
			tFlowService.finishActivity(vo, flowUtil, info, record, processInst2, flowId, view);
		} else if (flowDefName.endsWith("state")) {
			view.setStateOrgName(guide.getOrgName());
			tFlowService.finishActivity(vo, flowUtil, info, record, processInst2, flowId, view);
		}*/

	}

	@Override
	public int insertSelective(TIssueGuide guide) {
		return mapper.insertSelective(guide);
	}

	@Override
	public List<TIssueGuide> selectAll(ListParam param) {
		return mapper.selectAll(param);
	}

	@Override
	@Transactional
	public int tempTIssueGuide(AddFlowVO<TIssueGuide> vo, UserInfo info) {
		TIssueGuide model = vo.getModel();
		model.setGuideStatus("10");
		DraftsVO record = new DraftsVO();
		record.setApproveItemType(DisputeConstClass.T_ISSUE_GUIDE).setApproveItemName(DisputeConstClass.GUIDE_ID)
				.setApproveItemId(model.getGuideId()).setModuleName(vo.getModuleName()).setApplyType("1")
				.setLawCaseTitle(model.getGuideTitle()).setUserCode(info.getLoginAcct());

		int i = 0;
		if (null != vo.getAddFlag() && 1 == vo.getAddFlag()) {
			if (StringUtil.isEmpty(model.getGuideId())) {
				model.setGuideId(StringUtil.getId());
			}
			record.setCreateTime(new Date());
			record.setLawCaseCode(model.getGuideCode());
			i = i + mapper.insertSelectiveTemp(model);
			i = i + draftsVOMapper.insert(record);
		} else {
			record.setUpdateTime(new Date());
			model.setModifyTime(new Date());
			i = i + mapper.updateByPrimaryKey(model);
			i = i + draftsVOMapper.updateDrafts(record);
		}
		return i;
	}

	@Override
	@Transactional
	public int addFlow(AddFlowVO<TIssueGuide> vo, FlowUtil flowUtil, UserInfo info, TFlowLog log) {
		TIssueGuide model = vo.getModel();
		TFlowMain flow = new TFlowMain();
		flow.setFlowName(vo.getFlowName()).setCreateUserId(flowUtil.getUserId()).setApproveItemName(DisputeConstClass.GUIDE_ID)
				.setCreateUserName(flowUtil.getUserName()).setApproveItemId(model.getGuideId().toString())
				.setFlowId(flowUtil.getFlowId()).setCreateTime(new Date())
				.setApproveItemType(model.getClass().getAnnotationsByType(Table.class)[0].name())
				.setModuleName(vo.getModuleName());
		flow.setVersionId(flowVersionService.selectLatestVersion(vo.getFlowName()).getVersionId() + "");
		WFWorkItem work = flowUtil.finishFirstWork(flowUtil.getFlowId(), false);
		flowUtil.setActivityInstID(work.getActivityInstID());
		if ("1".equals(model.getSignDept())) {
			flowUtil.setRelativeData(work.getProcessInstID(), "parentActInstId", work.getActivityInstID());
		}

		log.setFlowLogId(StringUtil.getId()).setActivityDefId(work.getActivityDefID())
				.setActivityDefName(work.getWorkItemName()).setNextActivityDefId(vo.getActDefParam())
				.setNextActivityDefName(vo.getActDefName()).setUserId(flowUtil.getUserId())
				.setActivityInstId(work.getActivityInstID()).setApproveItemId(model.getGuideId())
				.setApproveItemType(model.getClass().getAnnotationsByType(Table.class)[0].name())
				.setFlowDefName(vo.getFlowName()).setFlowId(flowUtil.getFlowId())
				.setNextActivityDefId(vo.getActDefParam()).setNextActivityDefName(vo.getActDefName()).setWorkType("1");
		log.setToerId(info.getLoginAcct());
		log.setToerName(info.getLoginName());
		List<OrgParticipantVO> orgs = vo.getOrgs();
		BusinessFiledUtil.setReceiverInfo(log, orgs, info);
		int i = tFlowMainMapper.insertSelective(flow);
		i = i + tFlowLogMapper.insertSelective(log);
		return i;
	}

	@Override
	@Transactional
	public int addTIssueGuide(AddFlowVO<TIssueGuide> vo, FlowUtil flowUtil, UserInfo info) {
		TIssueGuide guide = vo.getModel();
		guide.setGuideStatus("20");
		int i = 0;
		if (StringUtil.isEmpty(guide.getGuideId())) {
			guide.setGuideId(StringUtil.getId());
		}
		if (vo.getAddFlag() == null || 1 != vo.getAddFlag()) {
			guide.setModifyTime(new Date());
			i = mapper.updateByPrimaryKey(guide);
			DraftsVO draftsVO = new DraftsVO(DisputeConstClass.T_ISSUE_GUIDE, guide.getGuideId());
			int j = draftsVOMapper.delDraftsByItem(draftsVO);
			logger.info("引诉删除草稿：" + j);
		} else {
			i = mapper.insertSelective(guide);
		}
		if (i < 1) {
			throw new MyOwnRuntimeException(DisputeConstClass.INSERT_FAIL);
		}
		Map<String, Object> map = new HashMap<>();
		List<OrgParticipantVO> orgs = vo.getOrgs();
		// 走会签
		String signDept = vo.getModel().getSignDept();
		if ("1".equals(signDept)) {
			List<Map<String, Object>> list = AddFlowVO.getObjectToMap(vo.getOrgs());
			map.put("orgs", list);
		} else {
			map.put(vo.getActDefParam() + "P", orgs.get(0).getDepaInterPersonP());
		}
		map.put("next", vo.getCurActDefParam() + vo.getActDefParam());
		map.put(vo.getCurActDefParam() + "P", new WFParticipant(info.getLoginAcct(), info.getLoginName(), "emp"));
		map.put("unView", 0);
		BusinessFields businessFields = new BusinessFields();
		businessFields.setBusinessTitle(guide.getGuideTitle()).setBusinessCode(guide.getGuideCode())
				.setBusinessType("引诉纠纷").setBusinessTableName(DisputeConstClass.T_ISSUE_GUIDE)
				.setBusinessId(guide.getGuideId()).setBusinessIdName(DisputeConstClass.GUIDE_ID).setCurState("审批中")
				.setAccountId(flowUtil.getUserId()).setModuleName(vo.getModuleName());
		Map<String, Object> bizInfo = BusinessFiledUtil.setBusinessFields(businessFields);
		FlowStartVO flowStartVO = new FlowStartVO();
		flowStartVO.setDefName(vo.getFlowName()).setInstName(guide.getGuideTitle()).setInstDesc(guide.getGuideTitle())
				.setBizInfo(bizInfo).setMap(map).setTableName("LMSBIZINFO").setTransactionSplit(false)
				.setParams(new Object[0]);
		long flowId = flowUtil.getFlowIdWithBizInfo(flowStartVO);
		flowUtil.setFlowId(flowId);
		return i;
	}

	@Override
	public int deleteByPrimaryKey(String guideId) {
		return mapper.deleteByPrimaryKey(guideId);
	}

	@Override
	@SourceName(name = "second")
	public Map<String, Object> testLocal() {
		return mapper.selectCase("44064483733eb9623025");
	}

	@Override
	public Map<String, Object> selectByType(String tableName, String fieldName, String fieldValue) {
		return mapper.selectByType(tableName, fieldName, fieldValue);
	}

	public void addUndone(WFParticipant participant) {
		CacheModel model = new CacheModel().setKey(DisputeConstClass.REDIS_SIGN_UNDONE);
		Map<String, Object> query = redisServiceClient.query(model);
		if (null == query) {
			logger.info("query=" + query);
		} else {
			Object data = query.get("data");
			ArrayList<String> list = null == data ? new ArrayList<>() : (ArrayList) data;
			String id = participant.getId();
			if (!list.contains(id))
				list.add(id);
			model.setValue(list);
			Map<String, Object> result = null == data ? redisServiceClient.insert(model)
					: redisServiceClient.update(model);
			logger.info(result.toString());
		}
	}

	public void addOrgsUndone(List<OrgParticipantVO> orgs) {
		CacheModel model = new CacheModel().setKey(DisputeConstClass.REDIS_SIGN_UNDONE);
		Map<String, Object> query = redisServiceClient.query(model);
		if (null == query) {
			logger.info("query=" + query);
		} else {
			Object data = query.get("data");
			ArrayList<String> list = null == data ? new ArrayList<>() : (ArrayList) data;
			for (OrgParticipantVO pvo : orgs) {
				String id = pvo.getDepaInterPersonP().getId();
				if (!list.contains(id))
					list.add(pvo.getDepaInterPersonP().getId());
			}
			model.setValue(list);
			Map<String, Object> result = null == data ? redisServiceClient.insert(model)
					: redisServiceClient.update(model);
			logger.info(result.toString());
		}
	}

	@Override
	public TIssueGuideVO findOne(String guideId, String actId) {
		List<TIssueGuideVO> selectOne = mapper.selectOne(guideId);
		if (null == selectOne || selectOne.isEmpty() || selectOne.size() > 1) {
			logger.info("数据错误 :" + selectOne);
			return new TIssueGuideVO();
		}
		TIssueGuideVO vo = selectOne.get(0);
		List<TFlowLogVO> logs = vo.getMian().getLogs();
		TFlowLogVO tFlowLogVO;

		for (int i = 0; i < logs.size(); i++) {
			tFlowLogVO = logs.get(i);
			tFlowLogVO.setGroupingSubLogs(
					tFlowLogVO.getSubLogs().stream().collect(Collectors.groupingBy(SubTFlowLogVO::getOrgName)));
		}
		List<Map<String, Object>> map = mapper.selectFile(guideId);
		vo.setFiles(map);
		if (vo.getMian().getFlowId() == null) {
			vo.setIsUnView("1");
		} else {
			vo.setIsUnView(tFlowService.isCurUser2(vo.getMian().getFlowId()) + "");
		}
		if (StringUtils.isNotBlank(actId)) {
			String permissionJson = flowActivityConfigService.judgeFlow(vo.getMian().getFlowName(), actId, Integer.parseInt(vo.getMian().getVersionId()));
			if(StringUtils.isBlank(permissionJson)){
				throw new MyOwnRuntimeException(DisputeConstClass.MESSAGE_UNACTRIGHT);
			}
			vo.setPermissionJson(permissionJson);
		}
		return vo;
	}

	@Override
	public int deleteTIssueGuide(List<String> guideIds) {
		DraftsVO draftsVO = new DraftsVO(DisputeConstClass.T_ISSUE_GUIDE, guideIds);
		int i = draftsVOMapper.deleteBatchDrafts(draftsVO);
		int j = mapper.deleteTIssueGuide(guideIds);
		return i + j;
	}

	@Override
	public int updateByMap(Map<String, Object> map) {
		try {
			TIssueGuide map2JavaBean = (TIssueGuide) HttpUtils.map2JavaBean(TIssueGuide.class, map);
			return mapper.updateByPrimaryKey(map2JavaBean);
		} catch (Exception e) {
			logger.info(CLASS_CATCH , e);
			throw new MyOwnRuntimeException(e);
		}
	}

	@Override
	public List<TIssueGuideVO> selectAllByCase(ListParam param, String unitId) {
		return mapper.selectAllByCase(param, unitId);
	}

	@Override
	public int updateByPrimaryKey(TIssueGuide guide) {
		if (StringUtil.isEmpty(guide.getGuideId())) {
			return 0;
		}
		return mapper.updateByPrimaryKey(guide);
	}

	@Override
	public int updateByPrimaryKeySelective(TIssueGuide guide) {
		if (StringUtil.isEmpty(guide.getGuideId())) {
			return 0;
		}
		return mapper.updateByPrimaryKeySelective(guide);
	}

	@Override
	public int inserttest(String key) {
		/*
		 * ArrayList<String> arr = new ArrayList<>(); for (int i = 0; i < 5000; i++) {
		 * arr.add(key+i); } return mapper.inserttest(arr);
		 */
		// List<Map<String, String>> dictMaps = mapper.dictMaps("sys_issue_result");
		/*
		 * LawSum sum = mapper.getLawSum(); LawSum lawSum = null == sum ? new LawSum() :
		 * sum;
		 * 
		 * JSONObject jsonObject = (JSONObject) JSONObject.toJSON(lawSum);
		 * System.out.println(jsonObject.toString());
		 */
		return 1;

	}

	@Override
	public TIssueGuide selectByPrimaryKey(String guideId) {
		return mapper.selectByPrimaryKey(guideId);
	}

	@Override
	public Map<String, String> getDictMap(String dictType) {
		List<Map<String, String>> dictMaps = mapper.dictMaps(dictType);
		Map<String, String> hashMap = new HashMap<>(dictMaps.size());
		for (Map<String, String> map : dictMaps) {
			hashMap.put(map.get("dict_value"), map.get("dict_cabel"));
		}
		return hashMap;
	}

	@Override
	public List<Map<String, Object>> getExcelDate() {
		List<Map<String, String>> list = mapper.getExcelDate();
		Map<String, String> resultMap = getDictMap("sys_issue_result");
		Map<String, String> resultMap1 = getDictMap("sys_flow_status");
		List<Map<String, Object>> excels = new ArrayList<>(list.size());
		for (Map<String, String> map : list) {
			Map<String, Object> map1 = new LinkedHashMap<>();
			map1.put("标题", map.get("title"));
			map1.put("编号", map.get("code"));
			map1.put("对方争议主体", map.get("deputeBody"));
			map1.put("我方争议主体", map.get("ourLawsuitBody"));
			map1.put("争议金额(元)", map.get("implMoney"));
			map1.put("纠纷发生时间", map.get("happenTime"));
			String str = map.get("guideResult");
			String guideResult = null == str ? "" : resultMap.get(str);
			map1.put("启动诉讼程序", guideResult);
			String str1 = map.get("status");
			String status = null == str1 ? "" : resultMap1.get(str1);
			map1.put("状态", status);
			excels.add(map1);
		}
		return excels;
	}

	@Override
	/**
	 * 综合查询
	 */
	public ProcessResult findTIssueGuideList(TIssueGuideListVO param) {
		List<TIssueGuide> result = null;// 查询结果
		PageInfo<TIssueGuide> pageInfo = null;// 分页对象
		UserInfo info = RequestHolder.getUserInfo();// 获取当前登录人信息
		List<Object> roleCodeList = info.getRoleCodeList();// 获取当前人的角色列表
		String roleLevel = commonService.getRoleLevel(roleCodeList);// 判断并获取当前人角色权限
		if (StringUtils.isNotBlank(param.getId())) {
			OrgBeanVO orgBeanVO = orgBeanService.selectOrgInfoByOrgCode(param.getId());
			param.setOrgPath(orgBeanVO.getOrgPath() + "%");
		}
		switch (roleLevel) {
		case "whole":
			if (!StringUtils.isNotBlank(param.getId())) {
				OrgBeanVO orgBeanVO = orgBeanService.selectOrgInfoByOrgCode("000000");// 集团 最高级
				param.setOrgPath(orgBeanVO.getOrgPath() + "%");
			}
			break;
		case "all":
			if (!StringUtils.isNotBlank(param.getId())) {
				OrgBeanVO orgBeanVO = orgBeanService.selectOrgInfoByOrgCode(info.getOrgId());
				String path = orgBeanVO.getOrgPath();
				if ("01".equals(info.getOrgLevel()) || "02".equals(info.getOrgLevel())) {
					path = path.substring(0, orgBeanVO.getOrgPath().length() - 1);
					path = path.substring(0, path.lastIndexOf(','));// 解析出省份公司组织树
				}
				param.setOrgPath(path + "%");
			}
			break;
//		case "fgld":
//			List<OrgDepLeader> orgDepLeaders = orgDepLeaderService.selectDeptCodeByAccountId(info.getLoginAcct());
//			param.setOrgDepLeaders(orgDepLeaders);
//			break;
		case "bmry":
			param.setDeptId(info.getDeptId());
			break;
		case "mr":
			param.setDeptId(info.getDeptId());
			param.setUserId(info.getLoginAcct());
			break;
		default:
			return new ProcessResult(ProcessResult.ERROR, ProcessResult.ERROR);
		}
		PageHelper.startPage(param.getPageNum(), param.getPageSize());// 分页对象 分页查询
		result = mapper.selectWholeData(param);
		pageInfo = new PageInfo<>(result);
		return new ProcessResult(ProcessResult.SUCCESS, ProcessResult.SUCCESS, pageInfo);
	}

	@Override
	public String selectCode(String unitName, String moduleCode, String orgCode) {
		return mapper.selectCode(unitName, moduleCode, orgCode);
	}

}
