package com.chinatower.product.legalms.modules.dispute.service.legislation.impl;


import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.Table;

import com.chinatower.product.legalms.common.*;
import com.chinatower.product.legalms.modules.flow.entity.common.BusinessLogVO;
import com.chinatower.product.legalms.modules.flow.service.common.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestBody;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;

import com.chinatower.product.legalms.modules.dispute.entity.LegislationVO;
import com.chinatower.product.legalms.modules.dispute.mapper.issue.TIssueGuideMapper;
import com.chinatower.product.legalms.modules.dispute.mapper.legislation.LegislationMapper;
import com.chinatower.product.legalms.modules.dispute.service.flow.TFlowService;
import com.chinatower.product.legalms.modules.dispute.service.issue.TIssueGuideService;
import com.chinatower.product.legalms.modules.dispute.service.legislation.LegislationService;
import com.chinatower.product.legalms.modules.dispute.vo.legislation.LegislationParam;
import com.chinatower.product.legalms.modules.dispute.vo.legislation.PageParam;
import com.chinatower.product.legalms.modules.dispute.vo.querylist.TLegislationListVO;
import com.chinatower.product.legalms.modules.flow.entity.common.CaseMainVO;
import com.chinatower.product.legalms.modules.flow.entity.common.DraftsVO;
import com.chinatower.product.legalms.modules.flow.entity.common.OrgBeanVO;
import com.chinatower.product.legalms.modules.flow.entity.flow.FlowStartVO;
import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowLog;
import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowMain;
import com.chinatower.product.legalms.modules.flow.mapper.common.DraftsVOMapper;
import com.chinatower.product.legalms.modules.flow.mapper.flow.TFlowLogMapper;
import com.chinatower.product.legalms.modules.flow.mapper.flow.TFlowMainMapper;
import com.chinatower.product.legalms.modules.flow.mapper.version.FlowVersionMapper;
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
import com.eos.workflow.data.WFActivityInst;
import com.eos.workflow.data.WFProcessInst;
import com.eos.workflow.data.WFWorkItem;
import com.eos.workflow.omservice.WFParticipant;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("tCaseLegislation")
public class LegislationServiceImpl implements LegislationService {

    private static final Logger log = LoggerFactory.getLogger(DisputeConstClass.LOGGER_NAME);

    @Autowired
    TFlowService tFlowService;

    @Autowired
    TFlowLogMapper tFlowLogMapper;

    @Autowired
    LegislationMapper legislationMapper;

    @Autowired
    FileShareService fileShareService;

    @Autowired
    TFlowMainMapper tFlowMainMapper;

    @Autowired
    TIssueGuideService tIssueGuideService;
    @Autowired
    private DraftsVOMapper draftsVOMapper;
    @Autowired
    CaseMainService caseMainService;
    @Autowired
    TIssueGuideMapper tIssueGuideMapper;
    @Autowired
    OrgBeanService orgBeanService;
    @Autowired
    OrgDepLeaderService orgDepLeaderService;
    @Autowired
    TFlowCommonService commonService;
    @Autowired
    FlowUtil flowUtil;
    @Autowired
    FlowActivityConfigService flowActivityConfigService;
    @Autowired
    FlowVersionMapper flowVersionService;
    @Autowired
    BusinessLogService businessLogService;
    @Autowired
    private OrgBeanService orgBean;
    /*法律转办单列表查询*/
    @Override
    public ProcessResult selectAll(PageParam param) {
        List<LegislationVO> result = null;//查询结果
        PageInfo<LegislationVO> pageInfo = null;// 分页对象
        UserInfo info = RequestHolder.getUserInfo();// 获取当前登录人信息
        if ( StringUtils.isNotBlank(param.getExecuteUnitId())) {// 判断执行单位id不为空
            List<String> orgCodeList = orgBean.selectOrgListByOrgCode(param.getExecuteUnitId());
            if(!orgCodeList.isEmpty()){
                param.setOrgCodeList(orgCodeList);
            }else {
                param.setOrgCodeList(Arrays.asList(param.getExecuteUnitId()));
            }
        }
        PageHelper.startPage(param.getPageNum(), param.getPageSize());// 分页对象  分页查询
        result = legislationMapper.selectAll(param,info.getLoginAcct());
        pageInfo = new PageInfo<>(result);
        return new ProcessResult(ProcessResult.SUCCESS, "SUCCESS", pageInfo);
    }

    /*暂存法律文书*/
    @Transactional
    public int tempLegislation(AddFlowVO<LegislationVO> vo,UserInfo info,LegislationVO legislationVO) {
        DraftsVO record = new DraftsVO(); // 创建草稿箱对象
        record.setApproveItemType(DisputeConstClass.T_CASE_LEGISLATION)
                .setApproveItemName(DisputeConstClass.ID)
                .setApproveItemId(legislationVO.getId())
                .setModuleName(vo.getModuleName())
                .setApplyType("6")
                .setLawCaseTitle(legislationVO.getTitle())
                .setUserCode(info.getLoginAcct());
        int i = 0;// 数据更新记录数
        legislationVO.setState("10");// 设置状态为未提交（数据字典值10）
        legislationVO.setLoginAcct(info.getLoginAcct());
        legislationVO.setCreationTime(new Date());
        if (null!=vo.getAddFlag() && 1==vo.getAddFlag()) {
            if (StringUtils.isBlank(legislationVO.getId())) {
                legislationVO.setId(StringUtil.getId());
            }
            record.setCreateTime(new Date());
            record.setLawCaseCode(legislationVO.getOdd());
            i = i + legislationMapper.insertSelective(legislationVO);
            i = i + draftsVOMapper.insert(record);
        } else {
            record.setUpdateTime(new Date());
            i = i + legislationMapper.updateByPrimaryKey(legislationVO);
            i = i + draftsVOMapper.updateDrafts(record);
        }
        return i;
    }

    /*法律文书转办详情*/
    @Override
    public LegislationParam findOneLegislation(String id, String actId,BusinessLogVO businessLogVO) {
        List<LegislationParam> legislationVOList = legislationMapper.findOneLegislation(id);
        //如果查询出来的条数为空，或者条数大于一
        if (null == legislationVOList || legislationVOList.isEmpty() || legislationVOList.size() > 1) {
            log.info("数据错误");
            throw new MyOwnRuntimeException("数据错误");
        }
        LegislationParam vo = legislationVOList.get(0);
        List<TFlowLogVO> logs = vo.getMian().getLogs();
        TFlowLogVO tFlowLogVO;
        for (int i = 0; i < logs.size(); i++) {
            tFlowLogVO = logs.get(i);
            tFlowLogVO.setGroupingSubLogs(tFlowLogVO.getSubLogs().stream().collect(Collectors.groupingBy(SubTFlowLogVO::getDeptName)));
        }
        // 查询案件信息
        if (StringUtils.isNotBlank(vo.getCaseFile())) {
            String[] caseId = vo.getCaseFile().split(",");
            List<CaseMainVO> caseMainVO = caseMainService.selectCase(caseId);
            vo.setCaseMainVO(caseMainVO == null ? null : caseMainVO.get(0));
        }
        //查询历史反馈记录
        List<BusinessLogVO> list = businessLogService.selectBusinessLog2(businessLogVO);
        if (list != null && !list.isEmpty()) {
            vo.setFeedBackMap(list.stream().filter(x -> vo.getExecuteUnitId().equals(x.getOrgId())).collect(Collectors.toList()));
        }
        List<Map<String, Object>> maps = tIssueGuideMapper.selectFile(vo.getId());
        vo.setFiles(maps);
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

    /*批量删除*/
    @Override
    public int deleteLegislation(List<String> ids) {
        DraftsVO draftsVO=new DraftsVO(DisputeConstClass.T_CASE_LEGISLATION, ids);
        int i =draftsVOMapper.deleteBatchDrafts(draftsVO);
        int j = legislationMapper.deleteLegislation(ids);
        return i+j;
    }
    public int muitRelationship(LegislationVO legislationVO, String moduleName, int addFlag) {
        int k=0;
        String caseId = legislationVO.getCaseFile();
        if(StringUtil.isEmpty(caseId)) {
            return k;
        }
        RelationshipVO relationshipVO =new RelationshipVO(DisputeConstClass.T_CASE_LEGISLATION,caseId,legislationVO.getId(),
                legislationVO.getTitle(),legislationVO.getOdd(),moduleName);
        if(addFlag==1) {
            k=tFlowMainMapper.addRelationship(relationshipVO);
        }else if(addFlag==0){
            k=tFlowMainMapper.updateRelationship(relationshipVO);
        }
        if(addFlag==0 && k==0) {
            k=tFlowMainMapper.addRelationship(relationshipVO);
        }
        return k;

    }
    /*起草*/
    @Override
    @Transactional
    public int addLegislationInfo(@RequestBody AddFlowVO<LegislationVO> vo, FlowUtil flowUtil, UserInfo info,LegislationVO legislationVO) {
        legislationVO.setState("20");
        legislationVO.setCreationTime(new Date());
        legislationVO.setLoginAcct(info.getLoginAcct());
        legislationVO.setDeptId(info.getDeptId());
        legislationVO.setMobile(info.getMobile());
        legislationVO.setDeptName(info.getDeptName());
        legislationVO.setOrgId(info.getOrgId());
        legislationVO.setOrgName(info.getOrgName());
        legislationVO.setLoginName(info.getLoginName());
        int i = 0;
        if (vo.getAddFlag() == null || 1!=vo.getAddFlag()) {// 有编号（数据库中已有）（编号，id此时都存在）进行更新
            i = legislationMapper.updateByPrimaryKey(legislationVO);// 草稿箱中提交 更新案件协办表
            DraftsVO draftsVO = new DraftsVO(DisputeConstClass.T_CASE_LEGISLATION,legislationVO.getId());
            draftsVOMapper.delDraftsByItem(draftsVO);
            int k = muitRelationship(legislationVO, vo.getModuleName(), 0);
            log.info("法律文书修改关联卷宗：" + k);
        } else if(StringUtil.isEmpty(legislationVO.getId())){
            legislationVO.setId(StringUtil.getId());
            i = legislationMapper.insertSelective(legislationVO);// 插入案件协办单数据
            int k = muitRelationship(legislationVO, vo.getModuleName(), 1);
            log.info("法律文书新增关联卷宗：" + k);
        }else {
            i = legislationMapper.insertSelective(legislationVO);
            int k = muitRelationship(legislationVO, vo.getModuleName(), 1);
            log.info("法律文书新增关联卷宗：" + k);
        }
        if (i < 1) {
            throw new MyOwnRuntimeException("起草失败");
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
        businessFields.setBusinessTitle(legislationVO.getTitle())
                .setBusinessCode(legislationVO.getOdd())
                .setBusinessType("法律文书办理")
                .setBusinessTableName(DisputeConstClass.T_CASE_LEGISLATION)
                .setBusinessId(legislationVO.getId())
                .setBusinessIdName(DisputeConstClass.ID)
                .setCurState("审批中")
                .setAccountId(flowUtil.getUserId())
                .setModuleName(vo.getModuleName());

        Map<String, Object> bizInfo = BusinessFiledUtil.setBusinessFields(businessFields);
        FlowStartVO flowStartVO = new FlowStartVO();
        flowStartVO.setDefName(vo.getFlowName())
                .setInstName(legislationVO.getTitle())
                .setInstDesc(vo.getModel().getTitle())
                .setBizInfo(bizInfo)
                .setMap(map)
                .setTableName("LMSBIZINFO")
                .setTransactionSplit(false)
                .setParams(new Object[0]);
        long flowId = flowUtil.getFlowIdWithBizInfo(flowStartVO);
        flowUtil.setFlowId(flowId);
        return i;
    }

    /*起草记录流程信息*/
    @Transactional
    public int addFlow(AddFlowVO<LegislationVO> vo, FlowUtil flowUtil, UserInfo info,TFlowLog log) {
        LegislationVO model = vo.getModel();
        TFlowMain flow = new TFlowMain();
        flow.setFlowName(flowUtil.getFlowName())
                .setCreateUserId(flowUtil.getUserId())
                .setApproveItemName(DisputeConstClass.ID)
                .setCreateUserName(flowUtil.getUserName())
                .setApproveItemId(model.getId().toString())
                .setFlowId(flowUtil.getFlowId())
                .setCreateTime(new Date())
                .setApproveItemType(model.getClass().getAnnotationsByType(Table.class)[0].name())
                .setModuleName(vo.getModuleName())
                .setFlowName(vo.getFlowName());
        flow.setVersionId(flowVersionService.selectLatestVersion(vo.getFlowName()).getVersionId() + "");
        WFWorkItem work = flowUtil.finishFirstWork(flowUtil.getFlowId(), false);
        flowUtil.setActivityInstID(work.getActivityInstID());
        if ("1".equals(vo.getModel().getSignDept())) {
            flowUtil.setRelativeData(work.getProcessInstID(), "parentActInstId", work.getActivityInstID());
        }
        log.setFlowLogId(StringUtil.getId())
                .setActivityDefId(work.getActivityDefID())
                .setActivityDefName(work.getWorkItemName())
                .setNextActivityDefId(vo.getActDefParam())
                .setNextActivityDefName(vo.getActDefName())
                .setStatus(flowUtil.getWorkStatus(work.getCurrentState()))
                .setUserId(flowUtil.getUserId()).setActivityInstId(work.getActivityInstID())
                .setApproveItemId(model.getId())
                .setApproveItemType(model.getClass().getAnnotationsByType(Table.class)[0].name())
                .setFlowDefName(vo.getFlowName()).setWorkType("1");
        log.setVersionId(vo.getVersionId());
		log.setToerId(info.getLoginAcct());
		log.setToerName(info.getLoginName());
        log.setFlowId(flowUtil.getFlowId());
        List<OrgParticipantVO> orgs = vo.getOrgs();
        BusinessFiledUtil.setReceiverInfo(log, orgs, info);
        int i = tFlowMainMapper.insertSelective(flow);
        i = i + tFlowLogMapper.insertSelective(log);
        return i;
    }

    /*根据主键进行修改*/
    @Override
    public int updateByPrimaryKey(LegislationVO legislationVO) {
        if(StringUtil.isEmpty(legislationVO.getId())) {
            return 0;
        }
        return legislationMapper.updateByPrimaryKey(legislationVO);
    }

    /*法律文书列表综合查询*/
    @Override
    public ProcessResult findLegislationList(TLegislationListVO param) {
        List<LegislationVO> result = null;//查询结果
        PageInfo<LegislationVO> pageInfo = null;// 分页对象
        UserInfo info = RequestHolder.getUserInfo();// 获取当前登录人信息
        List<Object> roleCodeList = info.getRoleCodeList();// 获取当前人的角色列表
        String roleLevel = tFlowService.getRoleLevel(roleCodeList);// 判断并获取当前人角色权限
        List<String> preOrgIds = new ArrayList<>(2);
        param.setPreOrgId(preOrgIds);
        setFixedFields(param, info, preOrgIds);
        if ( StringUtils.isNotBlank(param.getId())) {// 所属公司
            OrgBeanVO orgBeanVO = orgBeanService.selectOrgInfoByOrgCode(param.getId());
            param.setOrgPath(orgBeanVO.getOrgPath() + "%");
        }
        if (StringUtils.isNotBlank(param.getExecuteUnitId())) { // 执行单位
            OrgBeanVO orgBeanVO = orgBeanService.selectOrgInfoByOrgCode(param.getExecuteUnitId());
            param.setUnitIdPath(orgBeanVO.getOrgPath() + "%");
        }
        switch (roleLevel) {
            case "whole":
            case "all":
                break;
            case "bmry":
                param.setDeptId(info.getDeptId());
                break;
            case "mr":
                param.setUserId(info.getLoginAcct());
                break;
            default:
                return new ProcessResult(ProcessResult.ERROR, "ERROR");
        }
        PageHelper.startPage(param.getPageNum(), param.getPageSize());// 分页对象  分页查询
        result = legislationMapper.selectWholeData(param);
        pageInfo = new PageInfo<>(result);
        return new ProcessResult(ProcessResult.SUCCESS, "SUCCESS", pageInfo);
    }

    private void setFixedFields(TLegislationListVO param, UserInfo info, List<String> preOrgIds) {
        if ("01".equals(info.getOrgLevel())) {
            param.setExecUnitId(null);
            preOrgIds .isEmpty();
        } else if ("02".equals(info.getOrgLevel()) || "03".equals(info.getOrgLevel())) {
            setPreOrgIds(preOrgIds, info);
            param.setExecUnitId(getPath(info) + "%");
        }
        param.setCreatorUnitId(getPath(info) + "%");
    }

    private String getPath(UserInfo info) {
        OrgBeanVO orgBeanVO = orgBeanService.selectOrgInfoByOrgCode(info.getOrgId());
        String path = orgBeanVO.getOrgPath();
        if ("01".equals(info.getOrgLevel()) || "02".equals(info.getOrgLevel())) {
            path = path.substring(0, orgBeanVO.getOrgPath().length() - 1);
            path = path.substring(0, path.lastIndexOf(','));// 解析出省份公司组织树
        }
        return path;
    }

    private void setPreOrgIds(List<String> preOrgIds, UserInfo info) {
        String headOrgCode = null;
        switch (info.getOrgLevel()) {
            case "02":
                // 省分
                headOrgCode = legislationMapper.getHeadOrgCode(getEnterpType(info.getUnitCode()));
                preOrgIds.add(headOrgCode);
                break;
            case "03":
                // 地市
                String provinceCode = RequestHolder.getProvinceCode(info.getDeptId());
                preOrgIds.add(provinceCode);
                headOrgCode = legislationMapper.getHeadOrgCode(getEnterpType(info.getUnitCode()));
                preOrgIds.add(headOrgCode);
                break;
            default:
                return;
        }
    }

    private String getEnterpType(String unitCode) {
        switch (unitCode) {
            case "CT":
                return "01";
            case "TE":
                return "02";
            case "TZ":
                return "03";
            default:
                return null;
        }
    }

    @Override
    public String selectCode(String unitCode, String orgCode) {
        return legislationMapper.selectOdd(unitCode,unitCode);
    }


    public int updateByPrimaryKeySelective(LegislationVO legislationVO) {
        if(StringUtil.isEmpty(legislationVO.getId())) {
            return 0;
        }
        muitRelationship(legislationVO, legislationVO.getModuleName(), 0);
        return legislationMapper.updateByPrimaryKeySelective(legislationVO);
    }

    @Override
    @Transactional
    public ProcessResult addTFlowLog(AddFlowLogVO vo) {
        Map<String, Object> bizInfo = new HashMap<>(); // 业务冗余字段

        UserInfo info = RequestHolder.getUserInfo();// 获取当前登录人信息
        TFlowLog record = vo.gettFlowLog();// 日志对象
        record.setOptionType(vo.getOptionType());
        List<OrgParticipantVO> orgs = vo.getOrgs();
        boolean finishFlag=false;
        long flowId = (record.getFlowPid() == null || record.getFlowPid() == 0) ? record.getFlowId() : record.getFlowPid();
        WFProcessInst processInst2=null;
        try {
            // 流程操作对象
            flowUtil.init(info.getLoginAcct(), info.getLoginName(), DisputeConstClass.TENANLID, null, true);
            // 查询当前活动实例
            WFActivityInst wfActivityInst = flowUtil.queryActivityDetail(record.getActivityInstId());
            //commonService.setApprovalAccountId(vo.getOrgs(), participant);// redis记录审批人

            WFProcessInst processInst = flowUtil.queryProcessInstDetail(record.getFlowId());// 查询流程详情
            record.setFlowPid(processInst.getParentProcID()); // 设置父流程ID
            record.setFlowDefName(flowUtil.getFlowDefName(record.getFlowId()));// 设置流程定义名称
            // 设置 参与者 及 判断条件 并 更新 流程相关数据
            BusinessFiledUtil.setRelativeData(record, vo.getSignDept(), vo.getOrgs(), flowUtil, wfActivityInst);// 设置 参与者 及 判断条件
            processInst2 = flowUtil.queryProcessInstDetail(flowId);// 查询流程详情
            // 审批最后一步
            if ("finishActivity".equals(vo.gettFlowLog().getNextActivityDefId())
                    && processInst.getParentProcID() == 0) {
                bizInfo.put(DisputeConstClass.BUSINESS_TABLE_CURWORK, "已办结");// 设置业务冗余数据-当前状态-已办结
                finishFlag=true;
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
                updateByPrimaryKeySelective(StringUtil.getModel(LegislationVO.class,(HashMap<String, Object>) vo.getBusinessMap()));
                vo.getBusinessMap().put("approveItemType", DisputeConstClass.T_CASE_LEGISLATION);
                vo.getBusinessMap().put("actName", record.getActivityDefName());
                vo.getBusinessMap().put("actInstId", record.getActivityInstId());
                vo.getBusinessMap().put("versionId", record.getVersionId());
                vo.getBusinessMap().put("businessAdvice", (String) vo.getBusinessMap().get("feedBack"));
                vo.getBusinessMap().put("deptId", info.getDeptId());
                vo.getBusinessMap().put("actParentInstId", record.getActPid() + "");
                vo.getBusinessMap().put("flowId", record.getFlowId() + "");
                vo.getBusinessMap().put("flowPid", record.getFlowPid() + "");
                log.info("插入反馈内容1");
                tFlowService.insertBusinessLog2(info, vo.gettFlowLog().getApproveItemId(), (String) vo.getBusinessMap().get("file_feedBack"),
                        record.getActivityDefId(), vo.getBusinessMap(), processInst2);
            }
            flowUtil.clientCommit(true);// 提交流程
        } catch (Exception e) {
            if (flowUtil != null) {
                flowUtil.clientBack(true);// 流程回退
            }
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚数据
            log.info(DisputeConstClass.OPERATION_EXCEPTION ,e);
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
            if (flowUtil != null) {
                flowUtil.clientBack(true);// 流程回退
            }
            log.info("法律文书审批错误",e);
            return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.NEXT_FLOW_ERROR);
        }
        if(finishFlag) {
            AutoView view=new AutoView();
            tFlowService.finishActivity(vo, flowUtil, info, record, processInst2, flowId,view);
        }
        return new ProcessResult(ProcessResult.SUCCESS, DisputeConstClass.NEXT_FLOW_SUCCESS);
    }

    @Override
    public List<Map<String, Object>> getExcelDate(String loginAcct) {
        List<Map<String, String>> list = legislationMapper.getExcelDate(loginAcct);
        Map<String, String> resultMap = getDictMap("sys_flow_status");
        List<Map<String, Object>> excels = new ArrayList<>(list.size());
        for (Map<String, String> map : list) {
            Map<String, Object> map1 = new LinkedHashMap<>();
            map1.put("标题", map.get("title"));
            map1.put("编号", map.get("odd"));
            map1.put("关联案件", map.get("case_title"));
            map1.put("执行部门", map.get("executive_arm"));
            map1.put("执行金额(元)", map.get("execution_amount"));
            map1.put("办理发起时间", map.get("creation_time"));
            String str1 = map.get("state");
            String state=null==str1?"":resultMap.get(str1);
            map1.put("状态", state);
            excels.add(map1);
        }
        return excels;
    }

    private Map<String, String> getDictMap(String dictType) {
        List<Map<String, String>> dictMaps = legislationMapper.dictMaps(dictType);
        Map<String, String> hashMap = new HashMap<>(dictMaps.size());
        for (Map<String, String> map : dictMaps) {
            hashMap.put(map.get("dict_value"), map.get("dict_cabel"));
        }
        return hashMap;
    }


}
