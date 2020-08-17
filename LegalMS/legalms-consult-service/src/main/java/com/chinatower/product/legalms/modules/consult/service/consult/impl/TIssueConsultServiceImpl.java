package com.chinatower.product.legalms.modules.consult.service.consult.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Table;

import com.chinatower.product.legalms.common.*;
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
import com.chinatower.product.legalms.modules.consult.entity.consult.TIssueConsult;
import com.chinatower.product.legalms.modules.consult.mapper.consult.TIssueConsultMapper;
import com.chinatower.product.legalms.modules.consult.service.TaskService;
import com.chinatower.product.legalms.modules.consult.service.consult.TIssueConsultService;
import com.chinatower.product.legalms.modules.consult.service.flow.TFlowService;
import com.chinatower.product.legalms.modules.consult.vo.issue.ConsultListParam;
import com.chinatower.product.legalms.modules.consult.vo.issue.TIssueConsultVO;
import com.chinatower.product.legalms.modules.consult.vo.querylist.TIssueConsultListVO;
import com.chinatower.product.legalms.modules.flow.entity.common.DraftsVO;
import com.chinatower.product.legalms.modules.flow.entity.common.OrgBeanVO;
import com.chinatower.product.legalms.modules.flow.entity.flow.FlowStartVO;
import com.chinatower.product.legalms.modules.flow.entity.flow.TBusinessEnd;
import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowLog;
import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowMain;
import com.chinatower.product.legalms.modules.flow.mapper.common.DraftsVOMapper;
import com.chinatower.product.legalms.modules.flow.mapper.flow.TFlowLogMapper;
import com.chinatower.product.legalms.modules.flow.mapper.flow.TFlowMainMapper;
import com.chinatower.product.legalms.modules.flow.service.common.FlowActivityConfigService;
import com.chinatower.product.legalms.modules.flow.service.common.OrgBeanService;
import com.chinatower.product.legalms.modules.flow.service.common.OrgDepLeaderService;
import com.chinatower.product.legalms.modules.flow.service.unview.TFlowCommonService;
import com.chinatower.product.legalms.modules.flow.service.version.FlowVersionService;
import com.chinatower.product.legalms.modules.flow.vo.common.MyOwnRuntimeException;
import com.chinatower.product.legalms.modules.flow.vo.common.TaskVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowLogVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.BusinessFields;
import com.chinatower.product.legalms.modules.flow.vo.flow.OrgParticipantVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.SubTFlowLogVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.TFlowLogVO;
import com.chinatower.product.legalms.modules.flow.vo.unview.AutoView;
import com.chinatower.product.legalms.vo.CacheModel;
import com.chinatower.provider.call.oauth.RedisServiceClient;
import com.eos.workflow.api.IWFWorkItemDrawbackManager;
import com.eos.workflow.data.WFActivityInst;
import com.eos.workflow.data.WFProcessInst;
import com.eos.workflow.data.WFWorkItem;
import com.eos.workflow.omservice.WFParticipant;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.primeton.workflow.api.WFServiceException;

@Service("tIssueConsult")
public class TIssueConsultServiceImpl implements TIssueConsultService {

    private static final Logger logger = LoggerFactory.getLogger(TIssueConsultServiceImpl.class);
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    @Autowired
    TIssueConsultMapper tIssueConsultMapper;
    @Autowired
    private DraftsVOMapper draftsVOMapper;
    @Autowired
    TaskService taskService;
    @Autowired
    private RedisServiceClient redisServiceClient;
    @Autowired
    TFlowMainMapper tFlowMainMapper;
    @Autowired
    TFlowLogMapper tFlowLogMapper;
    @Autowired
    TFlowService tFlowService;
    @Autowired
    OrgBeanService orgBeanService;
    @Autowired
    OrgDepLeaderService orgDepLeaderService;
    @Autowired
    FlowUtil flowUtil;
    @Autowired
    TFlowCommonService commonService;
    @Autowired
    FlowActivityConfigService flowActivityConfigService;
    @Autowired
    FlowVersionService flowVersionService;
    /**
     * 获取编号
     * @param unitName
     * @param moduleCode
     * @param orgCode
     * @return
     */
    @Override
    public String selectCode(String unitName, String moduleCode, String orgCode) {
        return tIssueConsultMapper.selectCode(unitName, moduleCode, orgCode);
    }

    /**
     * 起草
     * @param vo
     * @param flowUtil
     * @param info
     * @return
     */

    @Override
    @Transactional
    public int addTIssueConsult(AddFlowVO<TIssueConsult> vo, FlowUtil flowUtil, UserInfo info) {
        TIssueConsult consult = vo.getModel();
        consult.setState("20");
        int i = 0;
        if (StringUtil.isEmpty(consult.getId())) {
            consult.setId(StringUtil.getId());
        }
        if (vo.getAddFlag() == null || 1 != vo.getAddFlag()) {
            consult.setModifyTime(new Date());
            i = tIssueConsultMapper.updateByPrimaryKey(consult);
            DraftsVO draftsVO = new DraftsVO(CoreConstClass.T_ISSUE_CONSULT, consult.getId());
            int j = draftsVOMapper.delDraftsByItem(draftsVO);
            logger.info("法律支撑删除草稿：" + j);
        } else {
            i = tIssueConsultMapper.insertSelective(consult);
        }
        if (i < 1) {
            throw new MyOwnRuntimeException(CoreConstClass.INSERT_FAIL);
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
        businessFields.setBusinessTitle(consult.getTitle()).setBusinessCode(consult.getCode())
                .setBusinessType("法律支撑").setBusinessTableName(CoreConstClass.T_ISSUE_CONSULT)
                .setBusinessId(consult.getId()).setBusinessIdName(CoreConstClass.ID).setCurState("审批中")
                .setAccountId(flowUtil.getUserId()).setModuleName(vo.getModuleName());
        Map<String, Object> bizInfo = BusinessFiledUtil.setBusinessFields(businessFields);
        FlowStartVO flowStartVO = new FlowStartVO();
        flowStartVO.setDefName(vo.getFlowName()).setInstName(consult.getTitle()).setInstDesc(consult.getTitle())
                .setBizInfo(bizInfo).setMap(map).setTableName("LMSBIZINFO").setTransactionSplit(false)
                .setParams(new Object[0]);
        long flowId = flowUtil.getFlowIdWithBizInfo(flowStartVO);
        flowUtil.setFlowId(flowId);
        return i;
    }

    @Override
    public void addOrgsUndone(List<OrgParticipantVO> orgs) {
        CacheModel model = new CacheModel().setKey(CoreConstClass.REDIS_SIGN_UNDONE);
        Map<String, Object> query = redisServiceClient.query(model);
        if (null == query) {
            logger.info("query=" + query);
        } else {
            Object data = query.get("data");
            ArrayList<String> list = null == data ? new ArrayList<>() : (ArrayList) data;
            for (OrgParticipantVO pvo : orgs) {
                String id = pvo.getDepaInterPersonP().getId();
                if (!list.contains(id)) {
                    list.add(pvo.getDepaInterPersonP().getId());
                }
            }
            model.setValue(list);
            Map<String, Object> result = null == data ? redisServiceClient.insert(model)
                    : redisServiceClient.update(model);
            logger.info(result.toString());
        }
    }

    @Override
    public void addUndone(WFParticipant depaInterPersonP) {
        CacheModel model = new CacheModel().setKey(CoreConstClass.REDIS_SIGN_UNDONE);
        Map<String, Object> query = redisServiceClient.query(model);
        if (null == query) {
            logger.info("query=" + query);
        } else {
            Object data = query.get("data");
            ArrayList<String> list = null == data ? new ArrayList<>() : (ArrayList) data;
            String id = depaInterPersonP.getId();
            if (!list.contains(id)) {
                list.add(id);
            }
            model.setValue(list);
            Map<String, Object> result = null == data ? redisServiceClient.insert(model)
                    : redisServiceClient.update(model);
            logger.info(result.toString());
        }
    }

    @Override
    public int deleteByPrimaryKey(String id) {
        return tIssueConsultMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addFlow(AddFlowVO<TIssueConsult> vo, FlowUtil flowUtil, UserInfo info, TFlowLog log) {
        TIssueConsult model = vo.getModel();
        TFlowMain flow = new TFlowMain();
        flow.setFlowName(vo.getFlowName()).setCreateUserId(flowUtil.getUserId()).setApproveItemName(CoreConstClass.ID)
                .setCreateUserName(flowUtil.getUserName()).setApproveItemId(model.getId().toString())
                .setFlowId(flowUtil.getFlowId()).setCreateTime(new Date())
                .setApproveItemType(model.getClass().getAnnotationsByType(Table.class)[0].name())
                .setModuleName(vo.getModuleName());
        flow.setVersionId(vo.getVersionId() + "");
        WFWorkItem work = flowUtil.finishFirstWork(flowUtil.getFlowId(), false);
        flowUtil.setActivityInstID(work.getActivityInstID());
        if ("1".equals(model.getSignDept())) {
            flowUtil.setRelativeData(work.getProcessInstID(), "parentActInstId", work.getActivityInstID());
        }
        log.setFlowLogId(StringUtil.getId()).setActivityDefId(work.getActivityDefID())
                .setActivityDefName(work.getWorkItemName()).setNextActivityDefId(vo.getActDefParam())
                .setNextActivityDefName(vo.getActDefName()).setUserId(flowUtil.getUserId())
                .setActivityInstId(work.getActivityInstID()).setApproveItemId(model.getId())
                .setApproveItemType(model.getClass().getAnnotationsByType(Table.class)[0].name())
                .setFlowDefName(vo.getFlowName()).setFlowId(flowUtil.getFlowId())
                .setNextActivityDefId(vo.getActDefParam()).setNextActivityDefName(vo.getActDefName()).setWorkType("1");
        log.setVersionId(vo.getVersionId());
        log.setToerId(info.getLoginAcct());
        log.setToerName(info.getLoginName());
        List<OrgParticipantVO> orgs = vo.getOrgs();
        BusinessFiledUtil.setReceiverInfo(log, orgs, info);
        int i = tFlowMainMapper.insertSelective(flow);
        i = i + tFlowLogMapper.insertSelective(log);
        return i;
    }
    @Override
    public ProcessResult findIssueConsultList(TIssueConsultListVO param) {
        UserInfo info = RequestHolder.getUserInfo();// 获取当前登录人信息
        if (StringUtils.isNotBlank(param.getId())) {
            OrgBeanVO orgBeanVO = orgBeanService.selectOrgInfoByOrgCode(param.getId());
            param.setOrgPath(orgBeanVO.getOrgPath() + "%");
        } else {
            param.setEnterpType(info.getEnterType());
        }
        PageHelper.startPage(param.getPageNum(), param.getPageSize());// 分页对象 分页查询

        List<TIssueConsult> result = tIssueConsultMapper.selectAllDatas(param);
        PageInfo<TIssueConsult> pageInfo = new PageInfo<>(result);
        return new ProcessResult(ProcessResult.SUCCESS, ProcessResult.SUCCESS, pageInfo);
        /**
        List<TIssueConsult> result = null;// 查询结果
        PageInfo<TIssueConsult> pageInfo = null;// 分页对象
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
        result = tIssueConsultMapper.selectWholeData(param);
        pageInfo = new PageInfo<>(result);
        return new ProcessResult(ProcessResult.SUCCESS, ProcessResult.SUCCESS, pageInfo);
         */
    }

    @Override
    @Transactional
    public int tempTIssueConsult(AddFlowVO<TIssueConsult> vo, UserInfo info) {
        TIssueConsult model = vo.getModel();
        model.setState("10");
        DraftsVO record = new DraftsVO();
        record.setApproveItemType(CoreConstClass.T_ISSUE_CONSULT).setApproveItemName(CoreConstClass.ID)
                .setApproveItemId(model.getId()).setModuleName(vo.getModuleName()).setApplyType("1")
                .setLawCaseTitle(model.getTitle()).setUserCode(info.getLoginAcct());

        int i = 0;
        if (null != vo.getAddFlag() && 1 == vo.getAddFlag()) {
            if (StringUtil.isEmpty(model.getId())) {
                model.setId(StringUtil.getId());
            }
            record.setCreateTime(new Date());
            record.setLawCaseCode(model.getCode());
            i = i + tIssueConsultMapper.insertSelective(model);
            i = i + draftsVOMapper.insert(record);
        } else {
            record.setUpdateTime(new Date());
            model.setModifyTime(new Date());
            i = i + tIssueConsultMapper.updateByPrimaryKey(model);
            i = i + draftsVOMapper.updateDrafts(record);
        }
        return i;
    }

    @Override
    public TIssueConsultVO findOne(String id, String actId) {
        List<TIssueConsultVO> selectOne = tIssueConsultMapper.selectOne(id);
        if (null == selectOne || selectOne.isEmpty() || selectOne.size() > 1) {
            logger.info("数据错误 :" + selectOne);
            return new TIssueConsultVO();
        }
        TIssueConsultVO vo = selectOne.get(0);
        List<TFlowLogVO> logs = vo.getMian().getLogs();
        TFlowLogVO tFlowLogVO;

        for (int i = 0; i < logs.size(); i++) {
            tFlowLogVO = logs.get(i);
            List<SubTFlowLogVO> subLogs = tFlowLogVO.getSubLogs();
            if(!StringUtil.listEmpty(subLogs)) {
                Map<String, List<SubTFlowLogVO>> collect = subLogs.stream().sorted(Comparator.comparing(SubTFlowLogVO::getModifyTime)).collect(Collectors.groupingBy(SubTFlowLogVO::getDeptName));
                tFlowLogVO.setGroupingSubLogs(collect);
            }

        }
        List<Map<String, Object>> map = tIssueConsultMapper.selectFile(id);
        vo.setFiles(map);
        if (vo.getMian().getFlowId() == null) {
            vo.setIsUnView("1");
        } else {
            vo.setIsUnView(tFlowService.isCurUser(vo.getMian().getFlowId()) + "");
        }
        if (StringUtils.isNotBlank(actId)) {
            String permissionJson = flowActivityConfigService.judgeFlow(vo.getMian().getFlowName() + "",actId, Integer.parseInt(vo.getMian().getVersionId()));
            if(StringUtils.isBlank(permissionJson)){
                throw new MyOwnRuntimeException(CoreConstClass.MESSAGE_UNACTRIGHT);
            }
            vo.setPermissionJson(permissionJson);
        }
        return vo;
    }

    @Override
    public List<TIssueConsult> selectAll(ConsultListParam consultListParam) {
        return tIssueConsultMapper.selectAll(consultListParam);
    }

    @Override
    public int deleteTIssueGuide(List<String> ids) {
        DraftsVO draftsVO = new DraftsVO(CoreConstClass.T_ISSUE_CONSULT, ids);
        int i = draftsVOMapper.deleteBatchDrafts(draftsVO);
        int j = tIssueConsultMapper.deleteTIssueConsult(ids);
        return i + j;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProcessResult nullifyFlow(Map<String, Object> businessMap) {
        try {
            // 开启事务
            UserInfo info = RequestHolder.getUserInfo();// 获取当前登录人信息
            flowUtil.init(info.getLoginAcct(), info.getLoginName(), CoreConstClass.TENANLID, null, true);
            Map<String, Object> bizInfo = new HashMap<>();// 业务冗余字段
            bizInfo.put(CoreConstClass.BUSINESS_TABLE_CURWORK, "已作废");// 设置当前工作状态-业务冗余字段
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
            tBusinessEnd.setId(StringUtil.getId()).setApproveItemId((String) map.get(CoreConstClass.APPROVEITEMID))
                    .setApproveItemType((String) map.get(approveItemType)).setFlowStatus("已作废")
                    .setFlowId(Long.valueOf(flowId)).setBusinessType("法律支撑")
                    .setModuleName((String) map.get("moduleName")).setUpdateTime(simpleDateFormat.format(new Date()))
                    .setBusinessTitle((String) map.get("busTitle")).setBusinessCode((String) map.get("busCode"));
            i += tFlowLogMapper.insertTBusinessEnd(tBusinessEnd);
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
                    .setApproveItemId((String) map.get(CoreConstClass.APPROVEITEMID))
                    .setApproveItemType("法律支撑")
                    .setStatus("50")
                    .setToerId(info.getLoginAcct())
                    .setToerName(info.getLoginName())
                    .setModifyTime(new Date());
            tFlowLog.setVersionId(Integer.parseInt((String) map.get("versionId")));
            i += tFlowLogMapper.insert(tFlowLog);
            if (i < 2) {
                throw new MyOwnRuntimeException("业务更新失败");
            }
            flowUtil.clientCommit(true);// 提交流程
        } catch (Exception e) {
            flowUtil.clientBack(true);// 流程回退
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚数据
            logger.error(ProcessResult.ERROR,e);
            return new ProcessResult(ProcessResult.ERROR, CoreConstClass.NEXT_FLOW_ERROR);
        }

        return new ProcessResult(ProcessResult.SUCCESS, CoreConstClass.NEXT_FLOW_SUCCESS);
    }

    @Override
    public int updateByPrimaryKeySelective(TIssueConsult tIssueConsult) {
        if (StringUtil.isEmpty(tIssueConsult.getId())) {
            return 0;
        }
        return tIssueConsultMapper.updateByPrimaryKeySelective(tIssueConsult);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProcessResult tempSelective(@RequestBody AddFlowLogVO addFlowLogVO) {
        UserInfo info = RequestHolder.getUserInfo();// 获取当前登录人信息
        try {
            flowUtil.init(info.getLoginAcct(), info.getLoginName(), CoreConstClass.TENANLID, null, true);
            long parentProcID = flowUtil.queryProcessInstDetail(addFlowLogVO.gettFlowLog().getFlowId())
                    .getParentProcID();
            if (addFlowLogVO.getBusinessMap() != null && addFlowLogVO.getBusinessMap().size() != 0) {// 编辑功能
                // 更新业务表数据
                tFlowService.updateByPrimaryKeySelective(addFlowLogVO.gettFlowLog().getApproveItemType(),
                        (HashMap<String, Object>) addFlowLogVO.getBusinessMap());
            }
            if (!StringUtil.isEmpty(addFlowLogVO.getTitle())) {
                Map<String, Object> bizInfo = new HashMap<>(); // 业务冗余字段
                bizInfo.put(CoreConstClass.BUSINESS_TABLE_TITLE, addFlowLogVO.getTitle());// 设置业务冗余数据-当前状态-已办结
                flowUtil.updateBizInfo(parentProcID == 0 ? addFlowLogVO.gettFlowLog().getFlowId() : parentProcID,
                        bizInfo);// 更新主流程业务荣誉字段表
            }
            flowUtil.clientCommit(true);// 提交流程
        } catch (Exception e) {
            flowUtil.clientBack(true);// 流程回退
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚数据
            logger.error(ProcessResult.ERROR,e);
            return new ProcessResult(ProcessResult.ERROR, CoreConstClass.NEXT_FLOW_SAVE_ERROR);
        }
        return new ProcessResult(ProcessResult.SUCCESS, CoreConstClass.NEXT_FLOW_SAVE_SUCCESS);
    }


    @Override
    @Transactional
    public ProcessResult addTFlowLog(AddFlowLogVO vo) {
//        ProcessResult processResult = tFlowService.subFlowDrawBack(vo);
//        if (null != processResult) {
//            return processResult;
//        }
        Map<String, Object> bizInfo = new HashMap<>(); // 业务冗余字段

        UserInfo info = RequestHolder.getUserInfo();// 获取当前登录人信息
        logger.info("当前登录人信息：" + info.toString());
        TFlowLog record = vo.gettFlowLog();// 日志对象
        record.setOptionType(vo.getOptionType());
        List<OrgParticipantVO> orgs = vo.getOrgs();
        boolean finishFlag = false;
        long flowId = getFlowId(record);
        WFProcessInst processInst2 = null;
        try {
            // 流程操作对象
            flowUtil.init(info.getLoginAcct(), info.getLoginName(), CoreConstClass.TENANLID, null, true);
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
                bizInfo.put(CoreConstClass.BUSINESS_TABLE_CURWORK, "已办结");// 设置业务冗余数据-当前状态-已办结
                finishFlag = true;
            }
            bizInfo.put(CoreConstClass.BUSINESS_TABLE_TITLE, vo.getTitle());// 设置业务冗余数据-当前状态-已办结
            flowUtil.setProcessInstName(vo.getTitle(), flowId);
            // 设置委托人，委托类型
            WFWorkItem wfWorkItem = flowUtil.queryWorkItemByActInstId(wfActivityInst.getActivityInstID());
            flowUtil.setMainFlowBusinessFields(flowUtil, bizInfo, processInst2);// 更新主流程业务荣誉字段
            record.setToerId(wfWorkItem.getParticipant());
            record.setToerName(wfWorkItem.getPartiName());
            // 完成当前活动实例-工作项
            logger.info("准备完成工作项");
            flowUtil.finishWorkItemByActInstId(wfActivityInst.getActivityInstID(), false);
//            flowUtil.finishWorkItemByActInstId(record.getFlowDefName(), wfActivityInst, false);
            logger.info("完成工作项");
            record.setActivityInstId(wfActivityInst.getActivityInstID());// 日志表记录活动实例
            record.setFlowLogId(StringUtil.getId());// 生成日志表ID
            BusinessFiledUtil.setReceiverInfo(record, orgs, info);// 设置日志表-下一步审批人及当前审批人信息
            if (!StringUtil.mapEmpty(vo.getBusinessMap())) {// 编辑功能
                // 更新业务表数据
                updateByPrimaryKeySelective(StringUtil.getModel(TIssueConsult.class, (HashMap<String, Object>) vo.getBusinessMap()));
                vo.getBusinessMap().put("approveItemType", CoreConstClass.T_ISSUE_CONSULT);
                vo.getBusinessMap().put("actName", record.getActivityDefName());
                vo.getBusinessMap().put("actInstId", record.getActivityInstId());
                vo.getBusinessMap().put("versionId", record.getVersionId());
                vo.getBusinessMap().put("deptId", info.getDeptId());
                vo.getBusinessMap().put("actParentInstId", record.getActPid() + "");
                vo.getBusinessMap().put("flowId", record.getFlowId() + "");
                vo.getBusinessMap().put("flowPid", record.getFlowPid() + "");
                tFlowService.insertBusinessLog(info, vo.gettFlowLog().getApproveItemId(), vo.getFileBusinessAdvice(),
                        record.getActivityDefId(), vo.getBusinessMap(), processInst2);
            }
            flowUtil.clientCommit(true);// 提交流程
        } catch (Exception e) {
            logger.info("法律支撑审批错误", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚数据
            flowUtil.clientBack(true);// 流程回退
            return new ProcessResult(ProcessResult.ERROR, CoreConstClass.NEXT_FLOW_ERROR);
        }
        flowUtil.init(info.getLoginAcct(), info.getLoginName(), CoreConstClass.TENANLID, null, false);

        try {
            WFActivityInst lastActivityInstByActivityID = flowUtil.findLastActivityInstByActivityID(record.getFlowId(),
                    record.getActivityDefId());
            record.setWorkType(record.getToerId().equals(info.getLoginAcct()) ? "1" : "2");
            if (!StringUtil.listEmpty(orgs)) {
                commonService.delegateWorkItem(flowUtil, lastActivityInstByActivityID); // 委托人判断
            }
            tFlowLogMapper.insertSelective(record);// 生成审批日志记录
        } catch (Exception e) {
            logger.info("法律支撑审批错误", e);
            flowUtil.clientBack(true);// 流程回退
            return new ProcessResult(ProcessResult.ERROR, CoreConstClass.NEXT_FLOW_ERROR);
        }
        if (finishFlag) {
            finishActivity(vo, flowUtil, info, record, processInst2, flowId);
        }
        return new ProcessResult(ProcessResult.SUCCESS, CoreConstClass.NEXT_FLOW_SUCCESS);
    }

    private Long getFlowId(TFlowLog record) {
        return (record.getFlowPid() == null || record.getFlowPid() == 0) ? record.getFlowId()
                : record.getFlowPid();
    }

    @Override
    @Transactional
    public ProcessResult flowDrawBack(Long activityInstId) {
        UserInfo info = RequestHolder.getUserInfo();
        flowUtil.init(info.getLoginAcct(), info.getLoginName(), CoreConstClass.TENANLID, null, false);
        IWFWorkItemDrawbackManager workItemDrawbackManager = flowUtil.getClient().getWorkItemDrawbackManager();
        WFWorkItem wfWorkItem = flowUtil.queryWorkItemByActInstId(activityInstId);
        WFActivityInst wfActivityInst = flowUtil.queryActivityDetail(activityInstId);
        try {
            if (null == wfWorkItem) {
                return new ProcessResult(ProcessResult.ERROR, CoreConstClass.FAILURE_MESS);
            }
            if (workItemDrawbackManager.isDrawbackEnable(wfWorkItem.getWorkItemID())) { // 允许撤回
                // 撤回变为未提交状态
                TFlowLog tFlowLog1 = tFlowLogMapper.selectActivityByFlowInstIdAndActivityInstId(wfActivityInst.getProcessInstID(), activityInstId);
                TFlowMain tFlowMain = tFlowMainMapper.selectByPrimaryKey(wfActivityInst.getProcessInstID());
                String approveItemId = tFlowLog1.getApproveItemId();
                String approveItemType = tFlowLog1.getApproveItemType();
                String businessStateField = getBusinessStateField(approveItemType);
                int i = tFlowLogMapper.updateBusinessTableState(approveItemId, approveItemType, businessStateField, tFlowMain.getApproveItemName());
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
                i += tFlowLogMapper.deleteByApproveItemId(approveItemId, approveItemType);
                i += tFlowMainMapper.deleteByPrimaryKey(wfActivityInst.getProcessInstID());
                // 删除流程实例
                i += flowUtil.getClient().getProcessInstManager().deleteProcessInstance(wfActivityInst.getProcessInstID());
                flowUtil.clientCommit(true);
                return new ProcessResult(ProcessResult.SUCCESS, CoreConstClass.DRAWBACK_SUCCESS, i);
            } else { // 不允许撤回
                List<WFActivityInst> wfActivityDefines = flowUtil.getClient().getActivityInstManager().queryNextActInsts(wfActivityInst.getProcessInstID());
                String msg = getErrorMsg(flowUtil, wfActivityDefines);
                flowUtil.clientCommit(true);
                return new ProcessResult(ProcessResult.ERROR, msg);
            }
        } catch (WFServiceException e) {
            logger.error(CoreConstClass.FAILURE_MESS, e);
            if (flowUtil != null) {
                flowUtil.clientBack(true);// 流程回退
            }
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚数据
            return new ProcessResult(ProcessResult.ERROR, CoreConstClass.DRAWBACK_FLOW_FAIL);
        }
    }
    private String getBusinessStateField(String approveItemType) {
        switch (approveItemType) {
            case "t_issue_consult" :
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
                throw new MyOwnRuntimeException(CoreConstClass.NO_BUSINESS_STATE_FIELD);
        }
    }
    private String getErrorMsg(FlowUtil flowUtil, List<WFActivityInst> wfActivityDefines) {
        if (wfActivityDefines == null || wfActivityDefines.isEmpty()) {
            return CoreConstClass.DRAWBACK_MEET_FAIL;
        } else if (wfActivityDefines.size() > 1 || flowUtil.getParentProcessInst(wfActivityDefines.get(0).getProcessInstID()).getProcessInstID() != 0) { // 送了会签
            return CoreConstClass.DRAWBACK_MEET_FAIL;
        } else if (wfActivityDefines.get(0).getCurrentState() != 0) {
            return CoreConstClass.DRAWBACK_FLOW_FINISH_FAIL;
        }
        return CoreConstClass.DRAWBACK_FLOW_NULL_FAIL;
    }
    public void finishActivity(AddFlowLogVO vo, FlowUtil flowUtil, UserInfo info, TFlowLog record,
                               WFProcessInst processInst2, long flowId) {
        TIssueConsult lawsuit = tIssueConsultMapper.selectByPrimaryKey(record.getApproveItemId());
        AutoView view = new AutoView();
        view.setDeptName(lawsuit.getDeptName()).setPutLoginName(lawsuit.getLoginName())
                .setLogin(new WFParticipant(info.getLoginAcct(), info.getLoginName(), "emp"))
                .setPutLoginDept(lawsuit.getDeptId());
        String flowDefName = record.getFlowDefName();
        if (flowDefName.endsWith("city")) {
            String code2 = RequestHolder.getProvinceCode(lawsuit.getDeptId());
            OrgBeanVO orgBeanVO = orgBeanService.selectOrgInfoByOrgCode(code2);
            view.setStateName(orgBeanVO.getOrgName()).setCityOrgName(lawsuit.getOrgName());
        } else if (flowDefName.endsWith("state")) {
            view.setStateOrgName(lawsuit.getOrgName());
        }
        tFlowService.finishActivity(vo, flowUtil, info, record, processInst2, flowId, view);

    }

}
