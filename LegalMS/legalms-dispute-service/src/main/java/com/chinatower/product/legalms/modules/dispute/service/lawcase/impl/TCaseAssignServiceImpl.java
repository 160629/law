package com.chinatower.product.legalms.modules.dispute.service.lawcase.impl;

import java.util.Date;
import java.util.HashMap;
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
import com.chinatower.product.legalms.common.ScheduledProperties;
import com.chinatower.product.legalms.common.StringUtil;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.dispute.entity.lawcase.TCaseAssign;
import com.chinatower.product.legalms.modules.dispute.mapper.issue.TIssueGuideMapper;
import com.chinatower.product.legalms.modules.dispute.mapper.lawcase.TCaseAssignMapper;
import com.chinatower.product.legalms.modules.dispute.service.flow.TFlowService;
import com.chinatower.product.legalms.modules.dispute.service.lawcase.TCaseAssignService;
import com.chinatower.product.legalms.modules.dispute.vo.issue.ListParam;
import com.chinatower.product.legalms.modules.dispute.vo.issue.TCaseAssignVO;
import com.chinatower.product.legalms.modules.dispute.vo.push.AutoCase;
import com.chinatower.product.legalms.modules.dispute.vo.querylist.TCaseAssignListVO;
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
import com.chinatower.product.legalms.modules.flow.vo.common.RelationshipVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowLogVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.BusinessFields;
import com.chinatower.product.legalms.modules.flow.vo.flow.OrgParticipantVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.SubTFlowLogVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.TFlowLogVO;
import com.chinatower.product.legalms.modules.flow.vo.unview.AutoView;
import com.chinatower.provider.call.tcase.CaseServiceClient;
import com.eos.workflow.data.WFActivityInst;
import com.eos.workflow.data.WFProcessInst;
import com.eos.workflow.data.WFWorkItem;
import com.eos.workflow.omservice.WFParticipant;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("tCaseAssign")
public class TCaseAssignServiceImpl implements TCaseAssignService {
	@Autowired
	TCaseAssignMapper mapper;
	@Autowired
	TFlowMainMapper tFlowMainMapper;
	@Autowired
	TFlowLogMapper tFlowLogMapper;
	@Autowired
	private DraftsVOMapper draftsVOMapper;
	@Autowired
	TIssueGuideMapper tIssueGuideMapper;
	@Autowired
	ScheduledProperties properties;
	@Autowired
	OrgBeanService orgBeanService;
	@Autowired
	OrgDepLeaderService orgDepLeaderService;
	@Autowired
	TFlowService tFlowService;
	@Autowired
	CaseServiceClient caseService;
	@Autowired
	FlowUtil flowUtil;
	@Autowired
	TFlowCommonService commonService;
	@Autowired
	FlowActivityConfigService flowActivityConfigService;
	@Autowired
	FlowVersionMapper flowVersionService;
	private static final String CLASS_CATCH = "TCaseAssignServiceImpl类捕捉异常";
	private static final Logger logger = LoggerFactory.getLogger(TCaseAssignServiceImpl.class);

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
			WFActivityInst wfActivityInst = flowUtil.queryActivityDetail(record.getActivityInstId());
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
				TCaseAssign model = StringUtil.getModel(TCaseAssign.class, (HashMap<String, Object>) vo.getBusinessMap());
				updateByPrimaryKeySelectivePre(model,record.getNextActivityDefId());
				vo.getBusinessMap().put("approveItemType", DisputeConstClass.T_CASE_ASSIGN);
				vo.getBusinessMap().put("actName", record.getActivityDefName());
				vo.getBusinessMap().put("actInstId", record.getActivityInstId());
				vo.getBusinessMap().put("versionId", record.getVersionId());
				vo.getBusinessMap().put("businessAdvice", (String) vo.getBusinessMap().get("feedBack"));
				vo.getBusinessMap().put("deptId", info.getDeptId());
				vo.getBusinessMap().put("actParentInstId", record.getActPid() + "");
				vo.getBusinessMap().put("flowId", record.getFlowId() + "");
				vo.getBusinessMap().put("flowPid", record.getFlowPid() + "");
				tFlowService.insertBusinessLog2(info, vo.gettFlowLog().getApproveItemId(), vo.getFileBusinessAdvice(),
						record.getActivityDefId(), vo.getBusinessMap(), processInst2);
			}
			flowUtil.clientCommit(true);// 提交流程
		} catch (Exception e) {
			logger.info("案件交办审批错误" , e);
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
			logger.info("案件交办审批错误" , e);
			if (flowUtil != null) {
				flowUtil.clientBack(true);// 流程回退
			}
			return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.NEXT_FLOW_ERROR);
		}
		if (finishFlag) {
			TCaseAssign assign = mapper.selectByPrimaryKey(record.getApproveItemId());
			AutoView view = new AutoView();
			view.setAssignOrgName(assign.getAssignOrg()).setSeedOrgName(assign.getMainSeedOrg())
					.setLogin(new WFParticipant(info.getLoginAcct(), info.getLoginName(), "emp"))
					.setInvolvedDeptId(assign.getInvolvedDeptId())
					.setInvolvedAccountId(assign.getInvolvedAccountId())
					.setInvolvedAccountName(assign.getInvolvedAccountName());
			tFlowService.finishActivity(vo, flowUtil, info, record, processInst2, flowId, view);
		}
		return new ProcessResult(ProcessResult.SUCCESS, DisputeConstClass.NEXT_FLOW_SUCCESS);
	}

	@Override
	@Transactional
	public int addFlow(AddFlowVO<TCaseAssign> vo, FlowUtil flowUtil, UserInfo info, TFlowLog log) {
		TFlowMain flow = new TFlowMain();
		flow.setFlowName(vo.getFlowName()).setCreateUserId(info.getLoginAcct()).setCreateUserName(info.getLoginName())
				.setApproveItemName(DisputeConstClass.ASSIGN_ID).setApproveItemId(vo.getModel().getAssignId())
				.setFlowId(flowUtil.getFlowId()).setCreateTime(new Date())
				.setApproveItemType(vo.getModel().getClass().getAnnotationsByType(Table.class)[0].name())
				.setModuleName(vo.getModuleName());
		flow.setVersionId(flowVersionService.selectLatestVersion(vo.getFlowName()).getVersionId() + "");
		WFWorkItem work = flowUtil.finishFirstWork(flowUtil.getFlowId(), false);
		flowUtil.setActivityInstID(work.getActivityInstID());
		if ("1".equals(vo.getModel().getSignDept())) {
			flowUtil.setRelativeData(work.getProcessInstID(), "parentActInstId", work.getActivityInstID());
		}
		List<WFWorkItem> works = flowUtil.queryNextWorkItemsByActivityInstID(work.getActivityInstID(), false);
		log.setFlowLogId(StringUtil.getId()).setActivityDefId(work.getActivityDefID())
				.setActivityDefName(work.getWorkItemName()).setNextActivityDefId(vo.getActDefParam())
				.setNextActivityDefName(vo.getActDefName()).setStatus(flowUtil.getWorkStatus(work.getCurrentState()))
				.setUserId(flowUtil.getUserId()).setActivityInstId(work.getActivityInstID())
				.setApproveItemId(vo.getModel().getAssignId())
				.setApproveItemType(vo.getModel().getClass().getAnnotationsByType(Table.class)[0].name())
				.setFlowDefName(vo.getFlowName()).setWorkType("1");
		log.setVersionId(vo.getVersionId());
		log.setToerId(info.getLoginAcct());
		log.setToerName(info.getLoginName());
		log.setFlowId(flowUtil.getFlowId());
		for (WFWorkItem wfWorkItem : works) {
			log.setFlowId(work.getProcessInstID()).setNextActivityDefId(wfWorkItem.getActivityDefID())
					.setNextActivityDefName(wfWorkItem.getWorkItemName());
		}
		List<OrgParticipantVO> orgs = vo.getOrgs();
		BusinessFiledUtil.setReceiverInfo(log, orgs, info);
		int i = tFlowMainMapper.insertSelective(flow);
		i = i + tFlowLogMapper.insertSelective(log);
		return i;
	}

	@Override
	@Transactional
	public int addTCaseAssign(AddFlowVO<TCaseAssign> vo, FlowUtil flowUtil, UserInfo info) {
		TCaseAssign assign = vo.getModel();
		assign.setAssignStatus("20");
		int i = 0;
		if (StringUtil.isEmpty(assign.getAssignId())) {
			assign.setAssignId(StringUtil.getId());
		}
		if (vo.getAddFlag() == null || 1 != vo.getAddFlag()) {
			i = mapper.updateByPrimaryKey(assign);
			DraftsVO draftsVO = new DraftsVO(DisputeConstClass.T_CASE_ASSIGN, assign.getAssignId());
			int j = draftsVOMapper.delDraftsByItem(draftsVO);
			logger.info("案件交办删除草稿：" + j);
		} else {
			i = mapper.insertSelective(assign);
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
			map.put(vo.getCurActDefParam() + vo.getActDefParam(), 1);

		} else {
			map.put(vo.getActDefParam() + "P", orgs.get(0).getDepaInterPersonP());
		}
		map.put("next", vo.getCurActDefParam() + vo.getActDefParam());
		map.put(vo.getCurActDefParam() + "P", new WFParticipant(info.getLoginAcct(), info.getLoginName(), "emp"));
		map.put("unView", 0);
		BusinessFields businessFields = new BusinessFields();
		businessFields.setBusinessTitle(assign.getAssignTitle()).setBusinessCode(assign.getAssignCode())
				.setBusinessType("案件交办").setBusinessTableName(DisputeConstClass.T_CASE_ASSIGN)
				.setBusinessId(assign.getAssignId()).setBusinessIdName(DisputeConstClass.ASSIGN_ID).setCurState("审批中")
				.setAccountId(flowUtil.getUserId()).setModuleName(vo.getModuleName());
		Map<String, Object> bizInfo = BusinessFiledUtil.setBusinessFields(businessFields);
		FlowStartVO flowStartVO = new FlowStartVO();
		flowStartVO.setDefName(vo.getFlowName()).setInstName(assign.getAssignTitle()).setInstDesc(assign.getAssignTitle())
				.setBizInfo(bizInfo).setMap(map).setTableName("LMSBIZINFO").setTransactionSplit(true)
				.setParams(new Object[0]);
		long flowId = flowUtil.getFlowIdWithBizInfo(flowStartVO);
		flowUtil.setFlowId(flowId);
		return i;
	}

	@Override
	public List<TCaseAssign> selectAll(ListParam param, String unitId) {
		return mapper.selectAll(param, unitId);
	}

	@Override
	@Transactional
	public int tempTCaseAssign(AddFlowVO<TCaseAssign> vo, UserInfo info) {
		TCaseAssign model = vo.getModel();
		model.setAssignStatus("10");
		DraftsVO record = new DraftsVO();
		record.setApproveItemType(DisputeConstClass.T_CASE_ASSIGN).setApproveItemName(DisputeConstClass.ASSIGN_ID)
				.setApproveItemId(model.getAssignId()).setModuleName(vo.getModuleName()).setApplyType("4")
				.setLawCaseTitle(model.getAssignTitle()).setUserCode(info.getLoginAcct());
		int i = 0;
		if (null != vo.getAddFlag() && 1 == vo.getAddFlag()) {
			if (StringUtils.isBlank(model.getAssignId())) {
				model.setAssignId(StringUtil.getId());
			}
			record.setCreateTime(new Date());
			record.setLawCaseCode(model.getAssignCode());
			i = i + mapper.insertSelectiveTemp(model);
			i = i + draftsVOMapper.insert(record);
		} else {
			record.setUpdateTime(new Date());
			i = i + mapper.updateByPrimaryKey(model);
			i = i + draftsVOMapper.updateDrafts(record);
		}
		return i;
	}

	@Override
	public int deleteTCaseAssign(List<String> assignIds) {
		for (String string : assignIds) {
			DraftsVO draftsVO = new DraftsVO(DisputeConstClass.T_CASE_ASSIGN,string);
			int j = draftsVOMapper.delDraftsByItem(draftsVO);
			logger.info("案件交办删除草稿：" + j);
		}
		return mapper.deleteTCaseAssign(assignIds);
	}

	@Override
	public TCaseAssignVO findOne(String assignId, String actId) {
		List<TCaseAssignVO> selectOne = mapper.selectOne(assignId);
		if (null == selectOne || selectOne.isEmpty() || selectOne.size() > 1) {
			logger.info("数据错误 :" + selectOne);
			throw new MyOwnRuntimeException("数据错误");
		}
		TCaseAssignVO vo = selectOne.get(0);
		List<TFlowLogVO> logs = vo.getMian().getLogs();
		TFlowLogVO tFlowLogVO;
		for (int i = 0; i < logs.size(); i++) {
			tFlowLogVO = logs.get(i);
			tFlowLogVO.setGroupingSubLogs(
					tFlowLogVO.getSubLogs().stream().collect(Collectors.groupingBy(SubTFlowLogVO::getDeptName)));
		}
		List<Map<String, Object>> map = tIssueGuideMapper.selectFile(assignId);
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
	public int updateByPrimaryKey(TCaseAssign assign) {
		if (StringUtil.isEmpty(assign.getAssignId())) {
			return 0;
		}
		return mapper.updateByPrimaryKey(assign);
	}

	@Override
	public int updateByPrimaryKeySelective(TCaseAssign assign) {
		updateRelationship(assign);
		return mapper.updateByPrimaryKeySelective(assign);
	}
	public int updateByPrimaryKeySelectivePre(TCaseAssign assign,String nextActivityDefId) {
		if (StringUtil.isEmpty(assign.getAssignId())) {
			return 0;
		}else if ("finishActivity".equals(nextActivityDefId)) {
			return updateByPrimaryKeySelective(assign);
		}else {
			return updateByPrimaryKeySelective(assign);
		}
	}
	@Override
	public int autoSaveCase(String approveItemType, String id, String pid, UserInfo userInfo) {
		logger.info("TCaseAssignServiceImpl生成案件{}方法参数=====id:{},pid:{},userInfo:{}", approveItemType, id, pid,
				userInfo);
		TCaseAssignVO vo = mapper.selectAssignAndFlow(id);
		Integer t = vo.getCreateCaseType();
		if(null==t || t!=0) {
			logger.info("交办autoSaveCase结束:{}", approveItemType);	
			return 0;
		}
		List<Map<String, Object>> map = tIssueGuideMapper.selectFile(id);
		List<String> fileIds = map.stream().map(x -> x.get("file_id") + "").collect(Collectors.toList());
		String join = StringUtils.join(fileIds, "-");
		AutoCase autoCase = new AutoCase(StringUtil.getId(),vo.getInvolvedAccountId(),vo.getInvolvedAccountName(),
				vo.getInvolvedDeptId(),vo.getInvolvedDeptName(),vo.getInvolvedOrgId(),vo.getInvolvedOrgName());
		autoCase.setCaseTitle(vo.getAssignTitle() + "卷宗").setCaseSpecialLine(vo.getCaseLine())
				.setModuleName(vo.getModuleName()).setCaseReason(vo.getCaseCause())
				.setLargeLawsuitMark(vo.getLawsuitSize()).setDeputeType(vo.getCaseType())
				.setCaseDeputeMoney(vo.getLawsuitMoney() + "").setOurLawsuitBody(vo.getOurLawsuitBody())
				.setOurLawsuitBodyName(vo.getOurLawsuitBodyName()).setOurLawsuitIdentity(vo.getOurLawsuitStatus())
				.setCaseTheThird(vo.getThirdPerson()).setCaseSamePlaintiff(vo.getPlaintiff())
				.setCaseSameDefendant(vo.getDefendant()).setCaseFile(join).setDrafterUnit(userInfo.getOrgId())
				.setCreatorDeptId(userInfo.getDeptId()).setCreatorDeptName(userInfo.getDeptName())
				.setCreatorUnitId(userInfo.getOrgId()).setCreatorAccountId(userInfo.getLoginAcct())
				.setCreatorAccountName(userInfo.getLoginName()).setCreatorCell(userInfo.getMobile()).setBusinessId(id)
				.setBusinessType(approveItemType).setCaseRecordTime(vo.getCaseCreateTime())
				.setInstitutionCaseCode(vo.getCaseCode()).setOtherLawsuitBody(vo.getTheyLawsuitBody())
				.setCreatorUnitName(userInfo.getOrgName()).setCode(vo.getAssignCode());
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String string = objectMapper.writeValueAsString(autoCase);
			Map<String, Object> hashMap = new HashMap<>();
			hashMap.put("pid", pid);
			logger.info("案件交办addAutoCaseMain参数string=={},pid=={}：", string, pid);
			ProcessResult main = caseService.addAutoCaseMain(string, pid);
			logger.info("addAutoCaseMain结果main=={}：", main);
			if (ProcessResult.SUCCESS.equals(main.getResultStat())) {
				TCaseAssign record=new TCaseAssign();
				record.setAssignId(vo.getAssignId());
				record.setCaseId(autoCase.getCaseId());
				record.setCaseTitle(autoCase.getCaseTitle());
				int i = mapper.updateByPrimaryKeySelective(record);
				return i +1;
			}
		} catch (Exception e) {
			logger.info(CLASS_CATCH , e);
		}
		return 0;
	}

	@Override
	public int deleteByPrimaryKey(String assignId) {
		return mapper.deleteByPrimaryKey(assignId);
	}

	@Override
	public ProcessResult selectAlles(TCaseAssignListVO param) {
		List<TCaseAssign> result = null;// 查询结果
		PageInfo<TCaseAssign> pageInfo = null;// 分页对象
		UserInfo info = RequestHolder.getUserInfo();// 获取当前登录人信息
		List<Object> roleCodeList = info.getRoleCodeList();// 获取当前人的角色列表
		String roleLevel = tFlowService.getRoleLevel(roleCodeList);// 判断并获取当前人角色权限
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
		case "bmld":
		case "bmry":
		case "mr":
			return new ProcessResult(ProcessResult.SUCCESS, ProcessResult.SUCCESS);
		default:
			return new ProcessResult(ProcessResult.ERROR, ProcessResult.ERROR);
		}
		PageHelper.startPage(param.getPageNum(), param.getPageSize());// 分页对象 分页查询
		result = mapper.selectWholeData(param);
		pageInfo = new PageInfo<>(result);
		return new ProcessResult(ProcessResult.SUCCESS, ProcessResult.SUCCESS, pageInfo);
	}
	public ProcessResult updateRelationship(TCaseAssign model) {
		String caseId = model.getCaseId();
		String assignId = model.getAssignId();
		TCaseAssign byPrimaryKey = mapper.selectByPrimaryKey(assignId);
		String oldCaseId = byPrimaryKey.getCaseId();
		RelationshipVO vo = new RelationshipVO("t_case_assign", caseId, assignId, byPrimaryKey.getAssignTitle(), byPrimaryKey.getAssignCode(),  byPrimaryKey.getModuleName());
		String optionType="2";  
		if (StringUtil.stringEquals(caseId,oldCaseId)) {
			return null;
		} else if(StringUtil.isEmpty(oldCaseId) && !StringUtil.isEmpty(caseId)) {
			optionType="2";
			vo.setIsDelete("1");
		} else if (!StringUtil.isEmpty(oldCaseId) && StringUtil.isEmpty(caseId)) {
			optionType="3";
		} else if (!StringUtil.isEmpty(oldCaseId) && !StringUtil.isEmpty(caseId)) {
			optionType="1";
			vo.setIsDelete("1");
		}
		vo.setOptionType(optionType);
		vo.setOldCaseId(oldCaseId);
		try {
			logger.info("案件交办修改关联案件开始:{}" , vo);
			ProcessResult result = caseService.updateRelationship(vo);
			logger.info("案件交办修改关联案件完成:{}" , result.getResultStat());
			return result;
		} catch (Exception e) {
			logger.info("案件交办修改关联案件异常:{}" , e);
		}
		logger.info("案件交办修改关联案件结束caseId:{}oldCaseId:{}" ,caseId, oldCaseId);
		return null;
	}
	@Override
	public ProcessResult test() {
		// TCaseAssignVO vo = mapper.selectAssignAndFlow("907980381403c26e2aa3");
		return null;
	}

}
