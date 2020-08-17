package com.chinatower.product.legalms.modules.dispute.service.flow.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.common.DisputeConstClass;
import com.chinatower.product.legalms.common.FlowUtil;
import com.chinatower.product.legalms.common.StringUtil;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.dispute.service.TaskService;
import com.chinatower.product.legalms.modules.dispute.service.flow.TFlowLogService;
import com.chinatower.product.legalms.modules.dispute.service.flow.TFlowService;
import com.chinatower.product.legalms.modules.flow.entity.common.DraftsVO;
import com.chinatower.product.legalms.modules.flow.entity.delegate.TFlowDelegate;
import com.chinatower.product.legalms.modules.flow.entity.flow.TBusinessEnd;
import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowLog;
import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowMain;
import com.chinatower.product.legalms.modules.flow.mapper.common.DraftsVOMapper;
import com.chinatower.product.legalms.modules.flow.mapper.flow.TFlowLogMapper;
import com.chinatower.product.legalms.modules.flow.mapper.flow.TFlowMainMapper;
import com.chinatower.product.legalms.modules.flow.service.common.BusinessLogService;
import com.chinatower.product.legalms.modules.flow.service.unview.TFlowCommonService;
import com.chinatower.product.legalms.modules.flow.vo.common.MyOwnRuntimeException;
import com.chinatower.product.legalms.modules.flow.vo.common.TaskVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowLogVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.OrgParticipantVO;
import com.chinatower.product.legalms.vo.CacheModel;
import com.chinatower.provider.call.oauth.RedisServiceClient;
import com.eos.workflow.api.IWFWorkItemDrawbackManager;
import com.eos.workflow.data.WFActivityInst;
import com.eos.workflow.data.WFProcessInst;
import com.eos.workflow.data.WFWorkItem;
import com.eos.workflow.omservice.WFParticipant;
import com.primeton.workflow.api.WFServiceException;

/**
 * <p>
 * 审批人员表 服务实现类
 * </p>
 *
 * @author 刘晓亮
 * @since 2019-09-30
 */
@Service
public class TFlowLogServiceImpl implements TFlowLogService {
	@Autowired
	TFlowLogMapper mapper;

	@Autowired
	TFlowMainMapper tFlowMainMapper;

	@Autowired
	TFlowService tFlowService;
	@Autowired
	TFlowCommonService commonService;
	@Autowired
	BusinessLogService businessLogService;
	// 流程工具
	@Autowired
	FlowUtil flowUtil;
	@Autowired
	private RedisServiceClient redisServiceClient;
	@Autowired
	private DraftsVOMapper draftsVOMapper;
	@Autowired
	TaskService taskService;

	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

	private static final Logger log = LoggerFactory.getLogger(DisputeConstClass.LOGGER_NAME);

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ProcessResult tempSelective(AddFlowLogVO addFlowLogVO) {
		UserInfo info = RequestHolder.getUserInfo();// 获取当前登录人信息
		try {
			flowUtil.init(info.getLoginAcct(), info.getLoginName(), DisputeConstClass.TENANLID, null, true);
			long parentProcID = flowUtil.queryProcessInstDetail(addFlowLogVO.gettFlowLog().getFlowId())
					.getParentProcID();
			if (addFlowLogVO.getBusinessMap() != null && addFlowLogVO.getBusinessMap().size() != 0) {// 编辑功能
				// 更新业务表数据
				tFlowService.updateByPrimaryKeySelective(addFlowLogVO.gettFlowLog().getApproveItemType(),
						(HashMap<String, Object>) addFlowLogVO.getBusinessMap());
			}
			if (!StringUtil.isEmpty(addFlowLogVO.getTitle())) {
				Map<String, Object> bizInfo = new HashMap<>(); // 业务冗余字段
				bizInfo.put(DisputeConstClass.BUSINESS_TABLE_TITLE, addFlowLogVO.getTitle());// 设置业务冗余数据-当前状态-已办结
				flowUtil.updateBizInfo(parentProcID == 0 ? addFlowLogVO.gettFlowLog().getFlowId() : parentProcID,
						bizInfo);// 更新主流程业务荣誉字段表
			}
			flowUtil.clientCommit(true);// 提交流程
		} catch (Exception e) {
			if (flowUtil != null) {
				flowUtil.clientBack(true);// 流程回退
			}
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚数据
			log.error(ProcessResult.ERROR,e);
			return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.NEXT_FLOW_SAVE_ERROR);
		}
		return new ProcessResult(ProcessResult.SUCCESS, DisputeConstClass.NEXT_FLOW_SAVE_SUCCESS);
	}

	@Override
	public ProcessResult selectCompleteFlow(TaskVO taskVO) {
		UserInfo info = RequestHolder.getUserInfo();
		TBusinessEnd tBusinessEnd = new TBusinessEnd();
		tBusinessEnd.setStartTime(taskVO.getStartTime()).setEndTime(taskVO.getEndTime())
				.setBusinessTitle(taskVO.getTitle()).setBusinessCode(taskVO.getCode());
		List<TBusinessEnd> list = mapper.selectCompleteFlow(info.getLoginAcct(), tBusinessEnd);
		return new ProcessResult(ProcessResult.SUCCESS, "SUCCESS", list);
	}

	@Override
	@Transactional
	public ProcessResult flowDrawBack(Long activityInstId) {
		UserInfo info = RequestHolder.getUserInfo();
		flowUtil.init(info.getLoginAcct(), info.getLoginName(), DisputeConstClass.TENANLID, null, false);
		IWFWorkItemDrawbackManager workItemDrawbackManager = flowUtil.getClient().getWorkItemDrawbackManager();
		WFWorkItem wfWorkItem = flowUtil.queryWorkItemByActInstId(activityInstId);
		WFActivityInst wfActivityInst = flowUtil.queryActivityDetail(activityInstId);
		try {
			if (null == wfWorkItem) {
				return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.FAILURE.FAILURE_MESS);
			}
			if (workItemDrawbackManager.isDrawbackEnable(wfWorkItem.getWorkItemID())) { // 允许撤回
				// 撤回变为未提交状态
				TFlowLog tFlowLog1 = mapper.selectActivityByFlowInstIdAndActivityInstId(wfActivityInst.getProcessInstID(), activityInstId);
				TFlowMain tFlowMain = tFlowMainMapper.selectByPrimaryKey(wfActivityInst.getProcessInstID());
				String approveItemId = tFlowLog1.getApproveItemId();
				String approveItemType = tFlowLog1.getApproveItemType();
				String businessStateField = getBusinessStateField(approveItemType);
				int i = mapper.updateBusinessTableState(approveItemId, approveItemType, businessStateField, tFlowMain.getApproveItemName());
				TaskVO taskVO = new TaskVO();
				taskVO.setProcessInstId(wfActivityInst.getProcessInstID() + "").setActivityInstId(activityInstId + "").setChooseType("2");
				Map<String, Object> result = (Map<String, Object>) taskService.queryPersonWorkItems(taskVO).getData();
				List<TaskVO> taskVOList = (List<TaskVO>) result.get("taskList");
				TaskVO taskVO1 = taskVOList.get(0);
				DraftsVO record = new DraftsVO();
				record.setApproveItemType(approveItemType)
						.setApproveItemId(approveItemId)
						.setModuleName(tFlowMain.getModuleName())
						.setLawCaseCode(taskVO1.getCode());
				int isExist = draftsVOMapper.isExist(record);
				record.setApproveItemId(approveItemId)
						.setApplyType("1")
						.setCreateTime(new Date())
						.setLawCaseTitle(taskVO1.getTitle())
						.setUserCode(tFlowMain.getCreateUserId());
				record.setStatus("1");
				if (isExist != 0) {
					i += draftsVOMapper.updateDrafts(record);
				} else {
					i += draftsVOMapper.insert(record);
				}
				// 删除流程日志，流程主表数据
				i += mapper.deleteByApproveItemId(approveItemId, approveItemType);
				i += tFlowMainMapper.deleteByPrimaryKey(wfActivityInst.getProcessInstID());
				// 删除流程实例
				i += flowUtil.getClient().getProcessInstManager().deleteProcessInstance(wfActivityInst.getProcessInstID());
				flowUtil.clientCommit(true);
				return new ProcessResult(ProcessResult.SUCCESS, DisputeConstClass.DRAWBACK_SUCCESS, i);
			} else { // 不允许撤回
				List<WFActivityInst> wfActivityDefines = flowUtil.getClient().getActivityInstManager().queryNextActInsts(wfActivityInst.getProcessInstID());
				String msg = getErrorMsg(flowUtil, wfActivityDefines);
				flowUtil.clientCommit(true);
				return new ProcessResult(ProcessResult.ERROR, msg);
			}
		} catch (WFServiceException e) {
			log.error(DisputeConstClass.FAILURE.FAILURE_MESS, e);
			if (flowUtil != null) {
				flowUtil.clientBack(true);// 流程回退
			}
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚数据
			return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.FAILURE.DRAWBACK_FLOW_FAIL);
		}
	}

	private String getBusinessStateField(String approveItemType) {
		switch (approveItemType) {
			case "t_issue_consult" :
			case "t_issue_assist" :
			case "t_case_legislation" :
				return "state";
			case "t_issue_guide" :
				return "guide_status";
			case "t_issue_jointly" :
				return "jointly_status";
			case "t_issue_lawsuit" :
				return "lawsuit_status";
			case "t_case_assign" :
				return "assign_status";
			default:
				throw new MyOwnRuntimeException(DisputeConstClass.FAILURE.NO_BUSINESS_STATE_FIELD);
		}
	}

	private String getErrorMsg(FlowUtil flowUtil, List<WFActivityInst> wfActivityDefines) {
		if (wfActivityDefines == null || wfActivityDefines.isEmpty()) {
			return DisputeConstClass.FAILURE.DRAWBACK_MEET_FAIL;
		} else if (wfActivityDefines.size() > 1 || flowUtil.getParentProcessInst(wfActivityDefines.get(0).getProcessInstID()).getProcessInstID() != 0) { // 送了会签
			return DisputeConstClass.FAILURE.DRAWBACK_MEET_FAIL;
		} else if (wfActivityDefines.get(0).getCurrentState() != 0) {
			return DisputeConstClass.FAILURE.DRAWBACK_FLOW_FINISH_FAIL;
		}
		return DisputeConstClass.FAILURE.DRAWBACK_FLOW_NULL_FAIL;
	}


	private void setWorkType(UserInfo info, TFlowLog record, String toerId) {
		if (toerId.equals(info.getLoginAcct())) {
			record.setWorkType("1");
		} else {
			record.setWorkType("2");
		}
	}

	private long getFlowId(TFlowLog record) {
		return record.getFlowPid() == 0 ? record.getFlowId() : record.getFlowPid();
	}

	private String getBusinessType(String approveItemType) {

		switch (approveItemType) {
			case "t_issue_guide":
				return "引诉纠纷";
			case "t_issue_jointly":
				return "案件协办";
			case "t_issue_lawsuit":
				return "纠纷处理";
			case "t_case_assign":
				return "案件交办";
			case "t_case_legislation":
				return "法律文书办理";
			case "t_issue_consult":
				return "法律支撑";
			default:
				return null;
		}
	}

	private void insertBusinessLog(UserInfo info, String approveItemId, String fileBusinessAdvice, Long flowId,
			String activityDefId, Map<String, Object> businessMap, FlowUtil flowUtil) {
		WFProcessInst wfProcessInst = flowUtil.queryProcessInstDetail(flowId);
		String defName = wfProcessInst.getProcessDefName();
		String fwcsrsh = "fwcsrsh";
		String fwshrsh = "fwshrsh";
		String sffwshrsh = "sffwshrsh";
		Map<String, String> hashMap = new HashMap<>();
		hashMap.put("com.tower.issue.guide.head", fwcsrsh);
		hashMap.put("com.tower.issue.guide.state", fwshrsh);
		hashMap.put("com.tower.issue.guide.city", sffwshrsh);
		hashMap.put("com.tower.issue.lawsuit.head", fwcsrsh);
		hashMap.put("com.tower.issue.lawsuit.state", fwshrsh);
		hashMap.put("com.tower.issue.lawsuit.city", sffwshrsh);
		String string = hashMap.get(defName);
		if (!StringUtil.isEmpty(string) && string.equals(activityDefId)) {
			businessMap.put("fileId", fileBusinessAdvice);
			businessMap.put(DisputeConstClass.APPROVEITEMID, approveItemId);
			businessLogService.insertBusinessLog(info, businessMap);
		}
	}

	/**
	 * 流程结束，生成待阅
	 *
	 * @param title
	 * @param record
	 * @param flowUtil
	 * @param info
	 */
	private void autoUnView(String title, TFlowLog record, FlowUtil flowUtil, UserInfo info) {
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
		commonService.autoUnView(flowDefName, title, record.getApproveItemId(), dqclr, toers);
	}

	/**
	 * 设置 参与者 及 判断条件 并 更新 流程相关数据
	 *
	 * @param map
	 * @param record
	 * @param signDept
	 * @param orgs
	 * @param flowUtil
	 * @param lastActivityInstByActivityID 当前活动实例
	 */
//	private void setRelativeData(Map<String, Object> map, TFlowLog record, String signDept, List<OrgParticipantVO> orgs,
//			FlowUtil flowUtil, WFActivityInst lastActivityInstByActivityID) {
//		if ("1".equals(signDept)) {// 1表示会签
//			List<Map<String, Object>> list = AddFlowVO.getObjectToMap(orgs);// 转换参与者对象
//			map.put("orgs", list);// 设置相关数据-会签-参与者
//			map.put("parentActInstId", lastActivityInstByActivityID.getActivityInstID());
//		} else if (!record.getNextActivityDefId().equals("finishActivity")) {
//			// 设置相关数据-下一步-参与者
//			map.put(record.getNextActivityDefId() + "P", orgs.get(0).getDepaInterPersonP());
//			if (record.getFlowPid() != 0) {
//				long parentActInstId = (Long) flowUtil.getRelativeData(record.getFlowPid(), "parentActInstId");
//				record.setActPid(parentActInstId);
//			}
//		}
//		map.put("next", record.getActivityDefId() + record.getNextActivityDefId());// 设置相关数据-流程路径判断条件
//		flowUtil.setRelativeDataBatch(record.getFlowId(), map);// 更新相关数据
//	}

	/**
	 * 向redis中设置当前审批人ID
	 *
	 * @param addFlowLogVO
	 * @param participant
	 */
//	private void setApprovalAccountId(AddFlowLogVO addFlowLogVO, WFParticipant participant) {
//		if (null != addFlowLogVO.getOrgs() && !addFlowLogVO.getOrgs().isEmpty()) {
//			addOrgsUndone(addFlowLogVO.getOrgs());
//		} else {
//			addUndone(participant);
//		}
//	}

	/**
	 * 作废流程
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ProcessResult nullifyFlow(Map<String, Object> businessMap) {
		try {
			// 开启事务
			UserInfo info = RequestHolder.getUserInfo();// 获取当前登录人信息
			flowUtil.init(info.getLoginAcct(), info.getLoginName(), DisputeConstClass.TENANLID, null, true);
			Map<String, Object> bizInfo = new HashMap<>();// 业务冗余字段
			bizInfo.put(DisputeConstClass.BUSINESS_TABLE_CURWORK, "已作废");// 设置当前工作状态-业务冗余字段
			Map<String, Object> map = (Map<String, Object>) businessMap.get("businessMap");
			String flowId = (String) map.get("flowId");
			flowUtil.updateBizInfo(Long.parseLong(flowId), bizInfo);// 更新业务荣誉字段表
			flowUtil.nullifyFlow(Long.parseLong(flowId));// 作废流程
			tFlowMainMapper.updateByPrimaryKey(// 更新流程主表
					new TFlowMain().setFlowId(Long.parseLong(flowId)).setFlowStatus("50"));
			// 更新业务表
			String approveItemType = "approveItemType";
			int i = tFlowService.updateByPrimaryKeySelective((String) map.get(approveItemType),
					(HashMap<String, Object>) map);
			// 生成办结
			TBusinessEnd tBusinessEnd = new TBusinessEnd();
			tBusinessEnd.setId(StringUtil.getId()).setApproveItemId((String) map.get(DisputeConstClass.APPROVEITEMID))
					.setApproveItemType((String) map.get(approveItemType)).setFlowStatus("已作废")
					.setFlowId(Long.valueOf(flowId)).setBusinessType(getBusinessType((String) map.get(approveItemType)))
					.setModuleName((String) map.get("moduleName")).setUpdateTime(simpleDateFormat.format(new Date()))
					.setBusinessTitle((String) map.get("busTitle")).setBusinessCode((String) map.get("busCode"));
			i += mapper.insertTBusinessEnd(tBusinessEnd);
			TFlowLog tFlowLog = new TFlowLog(); // 记录日志
			List<WFActivityInst> wfActivityInsts = flowUtil.getClient().getActivityInstManager().queryActivityInstsByProcessInstID(Long.parseLong(flowId), null);
			WFWorkItem wfWorkItem = flowUtil.queryWorkItemByActInstId(wfActivityInsts.get(wfActivityInsts.size() - 1).getActivityInstID());
			tFlowLog.setFlowLogId(StringUtil.getId())
					.setWorkType(info.getLoginAcct().equals(wfWorkItem.getParticipant()) ? "1" : "2")
					.setOptionType("3")
					.setActivityInstId(wfActivityInsts.get(wfActivityInsts.size() - 1).getActivityInstID())
					.setActivityDefId(wfActivityInsts.get(wfActivityInsts.size() - 1).getActivityDefID())
					.setActivityDefName(wfActivityInsts.get(wfActivityInsts.size() - 1).getActivityInstName())
					.setOrgId(info.getOrgId())
					.setOrgName(info.getOrgName())
					.setUserId(info.getLoginAcct())
					.setUserName(info.getLoginName())
					.setFlowId(Long.parseLong(flowId))
					.setApproveItemId((String) map.get(DisputeConstClass.APPROVEITEMID))
					.setApproveItemType(getBusinessType((String) map.get(approveItemType)))
					.setStatus("50")
					.setToerId(info.getLoginAcct())
					.setToerName(info.getLoginName())
					.setModifyTime(new Date());
			i += mapper.insert(tFlowLog);
			if (i < 2)
				throw new MyOwnRuntimeException("业务更新失败");
			flowUtil.clientCommit(true);// 提交流程
		} catch (Exception e) {
			if (flowUtil != null) {
				flowUtil.clientBack(true);// 流程回退
			}
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚数据
			log.error(ProcessResult.ERROR,e);
			return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.NEXT_FLOW_ERROR);
		}

		return new ProcessResult(ProcessResult.SUCCESS, DisputeConstClass.NEXT_FLOW_SUCCESS);
	}

	public void addUndone(WFParticipant participant) {
		CacheModel model = new CacheModel().setKey(DisputeConstClass.REDIS_SIGN_UNDONE);
		Map<String, Object> query = redisServiceClient.query(model);
		if (null == query) {
			log.info("query=" + query);
		} else {
			Object data = query.get("data");
			ArrayList<String> list = null == data ? new ArrayList<>() : (ArrayList) data;
			String id = participant.getId();
			if (!list.contains(id))
				list.add(id);
			model.setValue(list);
			Map<String, Object> result = null == data ? redisServiceClient.insert(model)
					: redisServiceClient.update(model);
			log.info(result.toString());
		}
	}

	public void addOrgsUndone(List<OrgParticipantVO> orgs) {
		CacheModel model = new CacheModel().setKey(DisputeConstClass.REDIS_SIGN_UNDONE);
		Map<String, Object> query = redisServiceClient.query(model);
		if (null == query) {
			log.info("query=" + query);
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
			log.info(result.toString());
		}

	}

	/**
	 * 判断委托待办
	 *
	 * @param lastActivityInstByActivityID
	 */
	private void delegateWorkItem(FlowUtil flowUtil, WFActivityInst lastActivityInstByActivityID) {
		List<WFWorkItem> wfWorkItems = flowUtil
				.queryNextWorkItemsByActivityInstID(lastActivityInstByActivityID.getActivityInstID(), false);
		for (int j = 0; wfWorkItems != null && j < wfWorkItems.size(); j++) {
			TFlowDelegate delegate = commonService.verifyDelegate(wfWorkItems.get(j).getParticipant());
			if (null != delegate) {
				flowUtil.init(wfWorkItems.get(j).getParticipant(), wfWorkItems.get(j).getPartiName(),
						DisputeConstClass.TENANLID, null, true);
				flowUtil.delegateWorkItem(delegate.getToerId(), delegate.getToerName(),
						wfWorkItems.get(j).getWorkItemID());
				flowUtil.clientCommit(true);
			}
		}
	}

	@Override
	public List<TFlowLog> selectAll() {
		return mapper.selectAll();
	}

	@Override
	public List<TFlowLog> selectMainFlowLog(String businessId, String businessType) {
		return mapper.selectMainFlowLog(businessId, businessType);
	}

	@Override
	public List<String> selectSubFlowIds(Long flowId) {
		return mapper.selectSubFlowLogs(flowId);
	}

	@Override
	public TFlowLog selectByPrimaryKey(String subProcessFlowLogId) {
		return mapper.selectByPrimaryKey(subProcessFlowLogId);
	}
}
