package com.chinatower.product.legalms.modules.dispute.service.assist.impl;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.common.*;
import com.chinatower.product.legalms.modules.dispute.entity.assist.TIssueAssist;
import com.chinatower.product.legalms.modules.dispute.entity.assist.TIssueAssistCondition;
import com.chinatower.product.legalms.modules.dispute.entity.assist.TIssueAssistRelation;
import com.chinatower.product.legalms.modules.dispute.mapper.assist.TIssueAssistConditionMapper;
import com.chinatower.product.legalms.modules.dispute.mapper.assist.TIssueAssistMapper;
import com.chinatower.product.legalms.modules.dispute.service.TaskService;
import com.chinatower.product.legalms.modules.dispute.service.assist.TIssueAssistConditionService;
import com.chinatower.product.legalms.modules.dispute.service.assist.TIssueAssistRelationService;
import com.chinatower.product.legalms.modules.dispute.service.assist.TIssueAssistService;
import com.chinatower.product.legalms.modules.dispute.service.flow.TFlowService;
import com.chinatower.product.legalms.modules.dispute.vo.assist.*;
import com.chinatower.product.legalms.modules.flow.entity.common.AccountLogic;
import com.chinatower.product.legalms.modules.flow.entity.common.DraftsVO;
import com.chinatower.product.legalms.modules.flow.entity.common.FileShareVO;
import com.chinatower.product.legalms.modules.flow.entity.common.OrgBeanVO;
import com.chinatower.product.legalms.modules.flow.entity.flow.FlowStartVO;
import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowLog;
import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowMain;
import com.chinatower.product.legalms.modules.flow.mapper.common.DraftsVOMapper;
import com.chinatower.product.legalms.modules.flow.mapper.common.FileShareMapper;
import com.chinatower.product.legalms.modules.flow.mapper.flow.TFlowLogMapper;
import com.chinatower.product.legalms.modules.flow.mapper.flow.TFlowMainMapper;
import com.chinatower.product.legalms.modules.flow.service.common.FlowActivityConfigService;
import com.chinatower.product.legalms.modules.flow.service.common.OrgBeanService;
import com.chinatower.product.legalms.modules.flow.service.common.UserInfoService;
import com.chinatower.product.legalms.modules.flow.service.unview.TFlowCommonService;
import com.chinatower.product.legalms.modules.flow.vo.common.MyOwnRuntimeException;
import com.chinatower.product.legalms.modules.flow.vo.flow.*;
import com.chinatower.product.legalms.modules.flow.vo.unview.AutoView;
import com.chinatower.product.legalms.vo.CacheModel;
import com.chinatower.provider.call.oauth.RedisServiceClient;
import com.eos.workflow.data.WFActivityInst;
import com.eos.workflow.data.WFProcessInst;
import com.eos.workflow.data.WFWorkItem;
import com.eos.workflow.omservice.WFParticipant;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.primeton.workflow.api.WFServiceException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.persistence.Table;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service("tIssueAssist")
public class TIssueAssistServiceImpl implements TIssueAssistService {

    // 日志对象
    private static final Logger logger = LoggerFactory.getLogger(TIssueAssistServiceImpl.class);

    // 协助执行mapper
    @Autowired
    private TIssueAssistMapper tIssueAssistMapper;

    // 草稿箱mapper
    @Autowired
    private DraftsVOMapper draftsVOMapper;

    // 流程操作对象
    @Autowired
    private FlowUtil flowUtil;

    // 流程主表mapper
    @Autowired
    TFlowMainMapper tFlowMainMapper;

    // 流程日志表mapper
    @Autowired
    TFlowLogMapper tFlowLogMapper;

    // 流程service
    @Autowired
    TFlowService tFlowService;

    @Autowired
    TFlowCommonService commonService;

    @Autowired
    private RedisServiceClient redisServiceClient;

    @Autowired
    OrgBeanService orgBeanService;

    @Autowired
    TIssueAssistRelationService tIssueAssistRelationService;

    @Autowired
    TIssueAssistConditionService tIssueAssistConditionService;

    @Autowired
    FlowActivityConfigService flowActivityConfigService;

    @Autowired
    TIssueAssistConditionMapper tIssueAssistConditionMapper;

    @Autowired
    TaskService taskService;

    @Autowired
    private FileShareMapper fileShareMapper;

    @Autowired
    UserInfoService userInfoService;

    /**
     * 起草暂存
     * 新增业务表数据
     * 新增草稿想数据
     *
     * @param vo
     * @return
     */
    @Override
    @Transactional
    public int tempTIssueAssist(AddFlowVO<TIssueAssist> vo) {
        UserInfo info = RequestHolder.getUserInfo();
        TIssueAssist model = vo.getModel();
        // 校验参数
        if (info == null || model == null) {
            logger.error("协助执行流程起草暂存失败, 参数错误：");
            return -1;
        }
        // 获取id
        if (StringUtils.isEmpty(model.getId())) {
            model.setId(StringUtil.getId());
            vo.setAddFlag(1);
        }
        // 获取编号
        if (StringUtils.isEmpty(model.getCode())) {
            model.setCode(tIssueAssistMapper.selectCode(info.getUnitCode(), DisputeConstClass.BUSINESS_TYPE, info.getOrgCode()));
            vo.setAddFlag(1);
        }
        // 将创建人信息（当前登录人信息）拷贝到业务表
        StringUtil.copyProperties(model, info);
        // 创建草稿箱数据
        model.setState("10");
        DraftsVO record = new DraftsVO();
        record.setApproveItemType(DisputeConstClass.T_ISSUE_ASSIST)
                .setApproveItemName("id")
                .setApproveItemId(model.getId())
                .setModuleName(vo.getModuleName())
                .setApplyType("9")
                .setLawCaseTitle(model.getTitle())
                .setUserCode(info.getLoginAcct());

        int i = 0;
        // 新增草稿
        if (null != vo.getAddFlag() && 1 == vo.getAddFlag()) {
            if (StringUtil.isEmpty(model.getId())) {
                model.setId(StringUtil.getId());
            }
            record.setCreateTime(new Date());
            record.setLawCaseCode(model.getCode());
            i = i + tIssueAssistMapper.insertSelective(model);
            i = i + draftsVOMapper.insert(record);
        } else {
            // 更新草稿
            record.setUpdateTime(new Date());
            model.setModifyTime(new Date());
            i = i + tIssueAssistMapper.updateByPrimaryKey(model);
            i = i + draftsVOMapper.updateDrafts(record);
        }
        return i;
    }

    /**
     * 列表分页查询
     * 查询未删除数据
     *
     * @param param
     * @return
     */
    @Override
    public PageInfo<TIssueAssist> selectAll(SelectAllListParamVO param) {
        // 参数校验
        if (null == param) {
            logger.error(DisputeConstClass.TISSUE_ASSIST_SELECT_ALL_FAILED, "。失败原因：" + DisputeConstClass.PARAM_EMPTY);
            return null;
        }
        UserInfo info = RequestHolder.getUserInfo();
        // 分页
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        // 查询列表
        List<TIssueAssist> tIssueAssistList = tIssueAssistMapper.selectAll(param, info);
        return new PageInfo<>(tIssueAssistList);
    }

    /**
     * 删除未提交数据
     * 删除业务表数据
     * 删除草稿箱数据
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional
    public int deleteTIssueAssist(List<String> ids) {
        // 参数校验
        if (ids == null || ids.isEmpty()) {
            return -1;
        }
        // 删除草稿箱
        DraftsVO draftsVO = new DraftsVO(DisputeConstClass.T_ISSUE_ASSIST, ids);
        int i = draftsVOMapper.deleteBatchDrafts(draftsVO);
        // 删除业务表
        int j = tIssueAssistMapper.deleteTIssueAssist(ids);
        return i + j;
    }

    @Override
    @Transactional
    public int updateByPrimaryKeySelective(TIssueAssist tIssueAssist) {
        if (null == tIssueAssist || StringUtils.isEmpty(tIssueAssist.getId())) {
            logger.error(DisputeConstClass.TISSUE_ASSIST_UPDATE_FAILED + "。错误原因：" + DisputeConstClass.PARAM_EMPTY);
            return -1;
        }
        logger.info("协助执行流程更新参数：" + tIssueAssist.toString());
        int i = tIssueAssistMapper.updateByPrimaryKey(tIssueAssist);
        logger.info(DisputeConstClass.TISSUE_ASSIST_UPDATE_SUCCESS, i);
        return i;
    }

    @Override
    public PageInfo<TIssueAssist> selectRelationshipList(SelectRelationshipListParamVO param) {
        // 校验参数
        if (null == param) {
            logger.error(DisputeConstClass.TISSUE_ASSIST_SELECT_RELATIONSHIP_LIST_FAILED, "。失败原因：" + DisputeConstClass.PARAM_EMPTY);
            return null;
        }
        // 获取用户信息
        UserInfo info = RequestHolder.getUserInfo();
        // 查询执行记录中已关联的执行单
        String[] relations = null;
        if (StringUtils.isNotBlank(param.getProcessInstId())) {
            StringBuilder sb = new StringBuilder();
            TIssueAssistCondition tIssueAssistCondition = tIssueAssistConditionMapper.selectByFlowId(param.getProcessInstId());
            TIssueAssist tIssueAssist = tIssueAssistMapper.selectByPrimaryKey(tIssueAssistCondition.getAssistId());
            // 过滤执行记录中执行单
            if (StringUtils.isNotBlank(tIssueAssistCondition.getAssistRelationIds())) {
                sb.append(tIssueAssistCondition.getAssistRelationIds()).append(",");
            }
            // 过滤主单据执行单
            if (StringUtils.isNotBlank(tIssueAssist.getAssistId())) {
                sb.append(tIssueAssist.getAssistId());
            }
            if (StringUtils.isNotBlank(sb.toString())) {
                relations = sb.toString().split(",");
            }
        }
        // 分页
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        // 查询列表
        List<TIssueAssist> tIssueAssistList = tIssueAssistMapper.selectRelationshipList(param, info, relations);
        return new PageInfo<>(tIssueAssistList);
    }

    @Override
    public String selectCode(String unitCode, String type, String orgCode) {
        return tIssueAssistMapper.selectCode(unitCode, type, orgCode);
    }

    @Override
    @Transactional
    public int addTIssueAssist(AddFlowVO<TIssueAssist> vo, FlowUtil flowUtil, UserInfo info) {
        TIssueAssist tIssueAssist = vo.getModel();
        tIssueAssist.setState("20");
        int i = 0;
        if (StringUtil.isEmpty(tIssueAssist.getId())) {
            tIssueAssist.setId(StringUtil.getId());
        }
        if (vo.getAddFlag() == null || 1 != vo.getAddFlag()) {
            tIssueAssist.setModifyTime(new Date());
            i = tIssueAssistMapper.updateByPrimaryKey(tIssueAssist);
            DraftsVO draftsVO = new DraftsVO(DisputeConstClass.T_ISSUE_ASSIST, tIssueAssist.getId());
            int j = draftsVOMapper.delDraftsByItem(draftsVO);
            logger.info("协助执行删除草稿：" + j);
        } else {
            i = tIssueAssistMapper.insertSelective(tIssueAssist);
        }
        // 创建协助执行单与上级单据的关联关系
        if (!StringUtils.isEmpty(tIssueAssist.getAssistId())) {
            TIssueAssistRelation tIssueAssistRelation = new TIssueAssistRelation(StringUtil.getId(), tIssueAssist.getId(), tIssueAssist.getMainProcessInstId(), info.getOrgId(), new Date(), new Date());
            if (StringUtils.isNotBlank(tIssueAssist.getMainProcessInstId())) {
                // 审批中 按钮添加
                // 设置对应执行记录 的 流程实例id
                tIssueAssistRelation.setFlowId(tIssueAssist.getMainProcessInstId());
            } else {
                // 起草时直接添加
                // 查询 被关联的执行单
                TIssueAssist tIssueAssist1 = tIssueAssistMapper.selectByPrimaryKey(tIssueAssist.getAssistId());
                // 设置对应执行记录 的 流程实例id
                String conditionFlowId = getConditionFlowId(tIssueAssist1, info);
                tIssueAssistRelation.setFlowId(conditionFlowId);
                vo.getModel().setMainProcessInstId(conditionFlowId);
            }
            // 若是执行记录中起草的协助执行单，需要更新对应执行记录中的关联协助执行单信息
            // 若主单据的flowId不为空，表示执行单位起草新单据关联主单据
            i += updateCondition(vo, flowUtil, info);
            i += tIssueAssistRelationService.insertRelation(tIssueAssistRelation);
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
        businessFields.setBusinessTitle(tIssueAssist.getTitle())
                .setBusinessCode(tIssueAssist.getCode())
                .setBusinessType("协助执行")
                .setBusinessTableName(DisputeConstClass.T_ISSUE_ASSIST)
                .setBusinessId(tIssueAssist.getId())
                .setBusinessIdName(CoreConstClass.ID)
                .setCurState("审批中")
                .setAccountId(flowUtil.getUserId())
                .setModuleName(vo.getModuleName());
        Map<String, Object> bizInfo = BusinessFiledUtil.setBusinessFields(businessFields);
        FlowStartVO flowStartVO = new FlowStartVO();
        flowStartVO.setDefName(vo.getFlowName())
                .setInstName(tIssueAssist.getTitle())
                .setInstDesc(tIssueAssist.getTitle())
                .setBizInfo(bizInfo).setMap(map)
                .setTableName("LMSBIZINFO")
                .setTransactionSplit(false)
                .setParams(new Object[0]);
        long flowId = flowUtil.getFlowIdWithBizInfo(flowStartVO);
        flowUtil.setFlowId(flowId);
        return i;
    }

    private String getConditionFlowId(TIssueAssist tIssueAssist1, UserInfo info) {
        TFlowMain tFlowMain = tFlowMainMapper.selectByBusinessId(tIssueAssist1.getId());
        Long flowId = tFlowMain.getFlowId();
        try {
            // 获取主流程中所有活动实例
            List<WFActivityInst> activityInsts = flowUtil.getClient().getActivityInstManager().queryActivityInstsByProcessInstID(flowId, null);
            // 过滤出当前代办
            WFActivityInst wfActivityInst = activityInsts.stream().filter(x -> x.getCurrentState() == 2).collect(Collectors.toList()).get(0);
            if ("manual".equals(wfActivityInst.getActivityType())) {
                // 主流程
                return flowId + "";
            } else if ("subflow".equals(wfActivityInst.getActivityType())) {
                // 子流程
                long[] subProInstIds = flowUtil.getClient().getProcessInstManager().querySubProcessInstIDsByActivityInstID(wfActivityInst.getActivityInstID());
                for (long subProInstId : subProInstIds) {
                    // 获取第二个环节实例（第一个是开始环节）
                    WFActivityInst wfActivityInst1 = flowUtil.getClient().getActivityInstManager().queryActivityInstsByProcessInstID(subProInstId, null).get(1);
                    WFWorkItem wfWorkItem = flowUtil.queryWorkItemByActInstId(wfActivityInst1.getActivityInstID());
                    AccountLogic accountLogic = userInfoService.selectUserInfo(wfWorkItem.getParticipant());
                    if (accountLogic.getAccountUnitId().equals(info.getOrgId())) {
                        return subProInstId + "";
                    }
                }
            }
        } catch (WFServiceException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int updateCondition(AddFlowVO<TIssueAssist> vo, FlowUtil flowUtil, UserInfo info) {
        // 著单据流程实例id
        String mainProcessInstId = vo.getModel().getMainProcessInstId();
        // 创建执行记录
        // 获取当前子流程实例
        WFProcessInst flowInst = flowUtil.queryProcessInstDetail(Long.parseLong(mainProcessInstId));
        // 获取当前运行状态的活动节点，代办
        WFActivityInst wfActivityInst = null;
        try {
            List<WFActivityInst> activityInsts = flowUtil.getClient().getActivityInstManager().queryActivityInstsByProcessInstID(Long.parseLong(mainProcessInstId), null);
            wfActivityInst = activityInsts.stream().filter(x -> x.getCurrentState() == 2).collect(Collectors.toList()).get(0);
        } catch (WFServiceException e) {
            e.printStackTrace();
        }
        if (wfActivityInst == null) {
            logger.error("获取当前运行状态的活动节点，代办失败，当前节点对象为null");
            return 0;
        }
        // 查询业务主表数据
        TIssueAssist tIssueAssist = tIssueAssistMapper.selectByPrimaryKey(vo.getModel().getAssistId());
        // 查询 执行记录表 判断是否存在 该流程实例id（该单位）的执行记录
        // 有-更新，无-创建
        // 子流程填写的情况
        TIssueAssistCondition tIssueAssistCondition1 = tIssueAssistConditionMapper.selectByFlowIdAndJoinUnit(mainProcessInstId, tIssueAssist.getJointlyUnitId().split(","));
        // 插入执行记录
        if (tIssueAssistCondition1 == null) {
            // 无-创建
            tIssueAssistCondition1 = new TIssueAssistCondition();
            // 执行记录id
            tIssueAssistCondition1.setId(StringUtil.getId());
            // 共有数据
            // 设置流程实例id
            tIssueAssistCondition1.setFlowId(mainProcessInstId);
            // 活动实例id
            tIssueAssistCondition1.setActInstId("" + wfActivityInst.getActivityInstID());
            // 父活动实例id
            tIssueAssistCondition1.setActParentInstId("" + flowInst.getParentActID());
            // 活动定义id
            tIssueAssistCondition1.setActId((String) wfActivityInst.getActivityDefID());
            // 协助执行单id
            tIssueAssistCondition1.setAssistId(tIssueAssist.getId());
            // 执行单位id
            tIssueAssistCondition1.setJointlyUnitId(info.getOrgId());
            // 执行单位名称
            tIssueAssistCondition1.setJointlyUnitName(info.getOrgName());
            // 执行人id
            tIssueAssistCondition1.setAccountId(info.getLoginAcct());
            // 执行人名称
            tIssueAssistCondition1.setAccountName(info.getLoginName());
            // 记录类型 2,非本单位填写记录
            tIssueAssistCondition1.setRecordType("2");
            // 非本单位填写字段
            // 关联的协助执行单id列表
            tIssueAssistCondition1.setAssistRelationIds(vo.getModel().getId());
            // 执行进展
            tIssueAssistCondition1.setState("执行中");
            return tIssueAssistConditionMapper.insertCondition(tIssueAssistCondition1);
        } else {
            // 执行记录id
            tIssueAssistCondition1.setId(tIssueAssistCondition1.getId());
            tIssueAssistCondition1.setAssistRelationIds(tIssueAssistCondition1.getAssistRelationIds() + "," + vo.getModel().getId());
            // 有-更新
            return tIssueAssistConditionMapper.updateById(tIssueAssistCondition1);
        }
    }

    @Override
    public int addFlow(AddFlowVO<TIssueAssist> vo, FlowUtil flowUtil, UserInfo info, TFlowLog log) {
        TIssueAssist model = vo.getModel();
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
    @Transactional
    public ProcessResult addTFlowLog(AddFlowLogVO vo) {
        logger.info("协助执行流程审批下一步参数：" + vo.toString());
        Map<String, Object> bizInfo = new HashMap<>(); // 业务冗余字段
        UserInfo info = RequestHolder.getUserInfo();// 获取当前登录人信息
        TFlowLog record = vo.gettFlowLog();// 日志对象
        record.setOptionType(vo.getOptionType());
        List<OrgParticipantVO> orgs = vo.getOrgs();
        boolean finishFlag = false;
        long flowPid = getFlowId(record);
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
            processInst2 = flowUtil.queryProcessInstDetail(flowPid);// 查询流程详情
            // 审批最后一步
            if ("finishActivity".equals(vo.gettFlowLog().getNextActivityDefId())
                    && processInst.getParentProcID() == 0) {
                bizInfo.put(CoreConstClass.BUSINESS_TABLE_CURWORK, "已办结");// 设置业务冗余数据-当前状态-已办结
                finishFlag = true;
            }
            bizInfo.put(CoreConstClass.BUSINESS_TABLE_TITLE, vo.getTitle());// 设置业务冗余数据-当前状态-已办结
            flowUtil.setProcessInstName(vo.getTitle(), flowPid);
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
                updateByPrimaryKeySelective(StringUtil.getModel(TIssueAssist.class, (HashMap<String, Object>) vo.getBusinessMap()));
                // 新增执行情况
                vo.getBusinessMap().put("flowId", record.getFlowId());
                vo.getBusinessMap().put("flowPid", flowPid);
                vo.getBusinessMap().put("actInstId", record.getActivityInstId());
                vo.getBusinessMap().put("actParentInstId", record.getActPid());
                vo.getBusinessMap().put("actId", record.getActivityDefId());
                tIssueAssistConditionService.insertAssistCondition(vo, info, flowUtil);
            }
            flowUtil.clientCommit(true);// 提交流程
        } catch (Exception e) {
            logger.info("协助执行流程审批错误", e);
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
            logger.info("协助执行流程审批错误", e);
            flowUtil.clientBack(true);// 流程回退
            return new ProcessResult(ProcessResult.ERROR, CoreConstClass.NEXT_FLOW_ERROR);
        }
        if (finishFlag) {
            finishActivity(vo, flowUtil, info, record, processInst2, flowPid);
        }
        return new ProcessResult(ProcessResult.SUCCESS, CoreConstClass.NEXT_FLOW_SUCCESS);
    }

    @Override
    @Transactional
    public ProcessResult tempSelective(AddFlowLogVO addFlowLogVO) {
        logger.info("协助执行流程审批下一步参数：" + addFlowLogVO.toString());
        TFlowLog record = addFlowLogVO.gettFlowLog();
        UserInfo info = RequestHolder.getUserInfo();// 获取当前登录人信息
        TIssueAssist tIssueAssist = tIssueAssistMapper.selectByPrimaryKey(addFlowLogVO.gettFlowLog().getApproveItemId());
        try {
            flowUtil.init(info.getLoginAcct(), info.getLoginName(), CoreConstClass.TENANLID, null, true);
            long parentProcID = flowUtil.queryProcessInstDetail(addFlowLogVO.gettFlowLog().getFlowId())
                    .getParentProcID();
            if (addFlowLogVO.getBusinessMap() != null && addFlowLogVO.getBusinessMap().size() != 0) {// 编辑功能

                // 更新业务表数据
                tFlowService.updateByPrimaryKeySelective(addFlowLogVO.gettFlowLog().getApproveItemType(),
                        (HashMap<String, Object>) addFlowLogVO.getBusinessMap());
                // 创建协助执行单与上级单据的关联关系
                TIssueAssistRelation tIssueAssistRelation = new TIssueAssistRelation();
                // 删除原先的关联关系
                tIssueAssistRelation.setAssistId(tIssueAssist.getId());
                tIssueAssistRelation.setAssistParentId(tIssueAssist.getAssistId());
                tIssueAssistRelationService.deleteRelation(tIssueAssistRelation);
                String assistId1 = (String) addFlowLogVO.getBusinessMap().get("assistId");
                if (StringUtils.isNotBlank(assistId1)) {
                    // 最新 关联执行单 不为空 新增一条关联关系
                    // 创建新关联关系
                    tIssueAssistRelation = new TIssueAssistRelation(StringUtil.getId(), tIssueAssist.getId(), tIssueAssist.getAssistId(), info.getOrgId(), new Date(), new Date());
                    tIssueAssistRelationService.insertRelation(tIssueAssistRelation);
                }
                // 更新执行记录
                if (!StringUtils.isNotBlank(tIssueAssist.getAssistId()) && !StringUtils.isNotBlank(assistId1)) {
                    // 保存前后  关联协助执行单  都为空
                } else if (StringUtils.isNotBlank(tIssueAssist.getAssistId()) && !StringUtils.isNotBlank(assistId1)) {
                    // 保存前不为空  保存后 为空  删除
                    updateCondition(2, tIssueAssist.getId(), getMainFlowId(flowUtil, info, tIssueAssist.getAssistId()));
                } else if (!StringUtils.isNotBlank(tIssueAssist.getAssistId()) && StringUtils.isNotBlank(assistId1)) {
                    // 保存前为空  保存后 不为空  新增
                    updateCondition(1, tIssueAssist.getId(), getMainFlowId(flowUtil, info, assistId1));
                } else {
                    // 保存前后  关联协助执行单  都不为空  先删除后更新
                    updateCondition(2, tIssueAssist.getId(), getMainFlowId(flowUtil, info, tIssueAssist.getAssistId()));
                    updateCondition(1, tIssueAssist.getId(), getMainFlowId(flowUtil, info, assistId1));
                }
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
            logger.error(ProcessResult.ERROR, e);
            return new ProcessResult(ProcessResult.ERROR, CoreConstClass.NEXT_FLOW_SAVE_ERROR);
        }
        return new ProcessResult(ProcessResult.SUCCESS, CoreConstClass.NEXT_FLOW_SAVE_SUCCESS);
    }

    private void updateCondition(int optionType, String id, String mainFlowId) {
        TIssueAssistCondition tIssueAssistCondition = tIssueAssistConditionMapper.selectByFlowId(mainFlowId);
        String[] splits = tIssueAssistCondition.getAssistRelationIds().split(",");
        StringBuilder stringBuilder = new StringBuilder(splits.length);
        if (optionType == 1) {
            // 新增
            if (!tIssueAssistCondition.getAssistRelationIds().contains(id)) {
                stringBuilder.append(tIssueAssistCondition.getAssistRelationIds()).append(",").append(id);
            }
        } else {
            // 删除
            for (String split : splits) {
                if (!split.equals(id)) {
                    stringBuilder.append(split);
                }
            }
        }
        // 更新执行记录 对应的 关联协助执行单列表
        tIssueAssistConditionService.updateRelationIdById(tIssueAssistCondition.getId(), stringBuilder.toString());
    }


    private String getMainFlowId(FlowUtil flowUtil, UserInfo info, String assistId1) throws WFServiceException {
        TFlowMain tFlowMain = tFlowMainMapper.selectByBusinessId(assistId1);
        List<WFActivityInst> activityInsts = flowUtil.getClient().getActivityInstManager().queryActivityInstsByProcessInstID(tFlowMain.getFlowId(), null);
        WFActivityInst wfActivityInst = activityInsts.stream().filter(x -> x.getCurrentState() == 2).collect(Collectors.toList()).get(0);
        String mainProcessInstId = "0";
        if ("manual".equals(wfActivityInst.getActivityType())) {
            return wfActivityInst.getProcessInstID() + "";
        } else if ("subflow".equals(wfActivityInst.getActivityType())) {
            long[] subProInstIds = flowUtil.getClient().getProcessInstManager().querySubProcessInstIDsByActivityInstID(wfActivityInst.getActivityInstID());
            for (Long subProInstId : subProInstIds) {
                WFProcessInst wfProcessInst = flowUtil.queryProcessInstDetail(subProInstId);
                List<WFActivityInst> activityInsts1 = flowUtil.getClient().getActivityInstManager().queryActivityInstsByProcessInstID(wfProcessInst.getProcessInstID(), null);
                AccountLogic accountLogic = userInfoService.selectUserInfo(flowUtil.queryWorkItemByActInstId(activityInsts1.get(1).getActivityInstID()).getParticipant());
                if (accountLogic.getAccountUnitId().equals(info.getOrgId())) {
                    return subProInstId + "";
                }
            }
        }
        return mainProcessInstId;
    }

    /**
     * @param param optionType 添加 1 ； 删除 2
     *              assistId 协助执行单id
     *              assistParentId 被关联协助执行单id
     *              flowId 被关联协助执行单 对应 代办 流程实例id （对应 唯一一条执行记录）
     * @return
     */
    @Override
    public ProcessResult updateRelation(Map<String, String> param) {
        // 获取当前登录用户信息
        UserInfo info = RequestHolder.getUserInfo();
        // 初始化流程操作对象
        flowUtil.init(info.getLoginAcct(), info.getLoginName(), CoreConstClass.TENANLID, null, false);
        // 获取当前子流程实例
        WFProcessInst flowInst = flowUtil.queryProcessInstDetail(Long.parseLong(param.get("mainProcessInstId")));
        // 获取当前运行状态的活动节点，代办
        WFActivityInst wfActivityInst = null;
        try {
            List<WFActivityInst> activityInsts = flowUtil.getClient().getActivityInstManager().queryActivityInstsByProcessInstID(Long.parseLong(param.get("mainProcessInstId")), null);
            wfActivityInst = activityInsts.stream().filter(x -> x.getCurrentState() == 2).collect(Collectors.toList()).get(0);
        } catch (WFServiceException e) {
            e.printStackTrace();
        }
        if (wfActivityInst == null) {
            logger.error("获取当前运行状态的活动节点，代办失败，当前节点为null");
            return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.TISSUE_ASSIST_UPDATE_RELATION_FAILED, 0);
        }

        // 查询业务主表数据
        TIssueAssist tIssueAssist = tIssueAssistMapper.selectByPrimaryKey(param.get("assistParentId"));

        // 执行记录影响条数
        AtomicInteger i = new AtomicInteger();
        // 一种根据 流程实例id查询 一种根据父流程实例查询
        // 查询更新前 执行记录 对应的 关联协助执行单列表
        TIssueAssistCondition tIssueAssistCondition = tIssueAssistConditionMapper.selectByFlowIdAndJoinUnit(param.get("mainProcessInstId"), tIssueAssist.getJointlyUnitId().split(","));
        if (tIssueAssistCondition == null) {
            // 当前还没有执行记录 需要创建一个条执行记录
            // 创建执行记录
            tIssueAssistCondition = new TIssueAssistCondition();
            // 流程实例id
            tIssueAssistCondition.setFlowId(param.get("mainProcessInstId"));
            // 共有数据
            // 执行记录id
            tIssueAssistCondition.setId(StringUtil.getId());
            // 父流程实例id
            tIssueAssistCondition.setFlowPid(flowInst.getParentProcID() + "");
            // 活动实例id
            tIssueAssistCondition.setActInstId("" + wfActivityInst.getActivityInstID());
            // 父活动实例id
            tIssueAssistCondition.setActParentInstId("" + flowInst.getParentActID());
            // 活动定义id
            tIssueAssistCondition.setActId((String) wfActivityInst.getActivityDefID());
            // 协助执行单id
            tIssueAssistCondition.setAssistId(tIssueAssist.getId());
            // 执行单位id
            tIssueAssistCondition.setJointlyUnitId(info.getOrgId());
            // 执行单位名称
            tIssueAssistCondition.setJointlyUnitName(info.getOrgName());
            // 执行人id
            tIssueAssistCondition.setAccountId(info.getLoginAcct());
            // 执行人名称
            tIssueAssistCondition.setAccountName(info.getLoginName());
            // 记录类型 2,非本单位填写记录
            tIssueAssistCondition.setRecordType("2");
            // 非本单位填写字段
            // 关联的协助执行单id列表
            tIssueAssistCondition.setAssistRelationIds(param.get("assistId"));
            // 承办部门id
            tIssueAssistCondition.setJointlyDeptId(info.getDeptId());
            // 承办部门名称
            tIssueAssistCondition.setJointlyDeptName(info.getDeptName());
            // 执行进展
            tIssueAssistCondition.setState("执行中");
            // 插入执行记录
            i.addAndGet(tIssueAssistConditionMapper.insertCondition(tIssueAssistCondition));
        } else {
            // 当前有执行记录id 需要关联该执行记录id
            String oldIds = tIssueAssistCondition.getAssistRelationIds();
            String newIds = param.get("assistId");

            TIssueAssistRelation tIssueAssistRelation = new TIssueAssistRelation();

            if (StringUtils.isEmpty(oldIds) && StringUtils.isNotBlank(newIds)) {
                // 1、原来的是空的，新的不是空
                tIssueAssistRelation.setId(StringUtil.getId());
                tIssueAssistRelation.setAssistId(newIds);
                tIssueAssistRelation.setAssistParentId(param.get("assistParentId"));
                tIssueAssistRelation.setOrgId(info.getOrgId());
                i.addAndGet(tIssueAssistRelationService.insertRelation(tIssueAssistRelation));
            } else if (StringUtils.isNotBlank(oldIds) && StringUtils.isEmpty(newIds)) {
                //  2、原来不是空的，新的是空
                tIssueAssistRelation.setAssistId(oldIds);
                tIssueAssistRelation.setAssistParentId(param.get("assistParentId"));
                i.addAndGet(tIssueAssistRelationService.deleteRelation(tIssueAssistRelation));
            } else {
                List<String> oldAssistIds = Arrays.asList(tIssueAssistCondition.getAssistRelationIds().split(","));
                List<String> newAssistIds = Arrays.asList(param.get("assistId").split(","));
                newAssistIds = newAssistIds.stream().distinct().collect(Collectors.toList());
                // 全都不是空的
                if ("1".equals(param.get("optionType"))) {
                    // 新增关联关系 新的 = 旧的 + 1
                    newAssistIds.forEach(x -> {
                        if (!oldAssistIds.contains(x)) {
                            // 创建关联关系对象
                            tIssueAssistRelation.setId(StringUtil.getId());
                            tIssueAssistRelation.setAssistId(x);
                            tIssueAssistRelation.setAssistParentId(param.get("assistParentId"));
                            tIssueAssistRelation.setOrgId(info.getOrgId());
                            i.addAndGet(tIssueAssistRelationService.insertRelation(tIssueAssistRelation));
                        }
                    });
                } else {
                    // 删除关联关系 新的 = 旧的 - 1
                    List<String> finalNewAssistIds = newAssistIds;
                    oldAssistIds.forEach(x -> {
                        if (!finalNewAssistIds.contains(x)) {
                            // 创建关联关系对象
                            tIssueAssistRelation.setAssistId(x);
                            tIssueAssistRelation.setAssistParentId(param.get("assistParentId"));
                            i.addAndGet(tIssueAssistRelationService.deleteRelation(tIssueAssistRelation));
                        }
                    });
                }
            }
            // 更新执行记录 对应的 关联协助执行单列表
            i.addAndGet(tIssueAssistConditionService.updateRelationIdById(tIssueAssistCondition.getId(), param.get("assistId")));
        }
        return new ProcessResult(ProcessResult.SUCCESS, DisputeConstClass.TISSUE_ASSIST_UPDATE_RELATION_SUCCESS, i);
    }

    @Override
    public ProcessResult findOne(String id, String actId, String activityInstId) {
        // 查询业务主表及流程日志
        TIssueAssistVO vo = tIssueAssistMapper.findOne(id);
        if (null == vo) {
            return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.TISSUE_ASSIST_FIND_ONE_FAILED);
        }
        // 获取当前登录人信息
        UserInfo info = RequestHolder.getUserInfo();
        // 查询协助执行单执行情况
        // 查询协助执行单下级反馈情况列表
        // 查所有=1，查看自己单位=2，查看上级单位=3
        if (vo.getJointlyUnitId() != null) {
            String recordType = getRecordType(vo, info);
            // 查询执行情况数据
            if ("3".equals(recordType)) {
                info.setOrgId(RequestHolder.getProvinceCode(info.getDeptId()));
            }
            // 查询所有 此执行单的所有执行情况数据
            List<TIssueAssistCondition> tIssueAssistConditions = tIssueAssistConditionService.selectConditionByIds(info, recordType, id);
            // 过滤组装数据
            // 去除 非 所选 执行单位or执行部门 的执行情况数据
            // 过滤出 每个 执行单位or执行部门的最新数据
            List<TIssueAssistCondition> collect2 = tIssueAssistConditions.stream().filter(x -> {
                if (vo.getOrgId().equals(vo.getJointlyUnitId())) {
                    // 如果 执行单位是起草单位，显示部门的数据
                    // 获取所有执行部门
                    List<String> deptIds = Arrays.asList(vo.getJointlyDeptId().split(","));
                    return deptIds.contains(x.getJointlyDeptId());
                } else {
                    // 如果 执行单位不是起草单位，显示执行单位的数据
                    // 获取所有执行单位
                    List<String> unitIds = Arrays.asList(vo.getJointlyUnitId().split(","));
                    return unitIds.contains(x.getJointlyUnitId());
                }
            }).distinct().collect(Collectors.toList());
            // 按  执行单位 or 执行部门 分组 后 取最新一条记录
            Map<String, List<TIssueAssistCondition>> groupCollection = null;
            if (vo.getOrgId().equals(vo.getJointlyUnitId())) {
                // 如果 执行单位是起草单位，显示部门的数据
                groupCollection = collect2.stream().collect(Collectors.groupingBy(TIssueAssistCondition::getJointlyDeptId));
            } else {
                // 如果 执行单位不是起草单位，显示执行单位的数据
                groupCollection = collect2.stream().collect(Collectors.groupingBy(TIssueAssistCondition::getJointlyUnitId));
            }
            // 取最新记录
            List<TIssueAssistCondition> latestConditions = new ArrayList<>(groupCollection.size());
            groupCollection.entrySet().stream().forEach(x -> {
                List<TIssueAssistCondition> collect = x.getValue().stream().sorted(Comparator.comparing(TIssueAssistCondition::getCreateTime)).collect(Collectors.toList());
                latestConditions.add(collect.get(collect.size() - 1));
            });
            List<TIssueAssistCondition> collect = latestConditions;
            // 查所有=1，查看自己单位=2，查看上级单位=3
            if (recordType.equals("2")) {
                collect = latestConditions.stream().filter(x -> x.getJointlyUnitId().equals(info.getOrgId())).collect(Collectors.toList());
            } else if (recordType.equals("3")) {
                collect = latestConditions.stream().filter(x -> x.getJointlyUnitId().equals(RequestHolder.getProvinceCode(info.getDeptId()))).collect(Collectors.toList());
            }
            vo.settIssueAssistConditions(collect);
            // 生成 执行记录列表对象
            if (!vo.getJointlyUnitId().equals(vo.getOrgId())) {
                // 非本单位执行
                // 执行单位列表
                List<String> splitIds = Arrays.asList(vo.getJointlyUnitId().split(","));
                // 记录执行进展列表数据
                List<Map<String, Object>> jointUnitCondition = new ArrayList<>(splitIds.size());
                for (int i = 0; i < collect.size(); i++) {
                    Map<String, Object> tempMap = new HashMap<>(3);
                    tempMap.put("joinUnitId", collect.get(i).getJointlyUnitId());
                    tempMap.put("jointlyUnitName", collect.get(i).getJointlyUnitName());
                    tempMap.put("state", collect.get(i).getState());
                    tempMap.put("data", collect.get(i));
                    jointUnitCondition.add(tempMap);
                }
                vo.setJointUnitConditions(jointUnitCondition);
            }
            // 获取 执行记录关联信息（协助执行单 and 文件）
            vo.gettIssueAssistConditions().forEach(x -> {
                // 获取反馈记录对应的关联执行单标题及id
                String ids = x.getAssistRelationIds();
                if (StringUtils.isNotBlank(ids)) {
                    x.setAssistIdAndTitle(tIssueAssistMapper.selectAssistIdAndTitleByAssistIds(ids.split(",")));
                }
                // 获取执行记录 关联的所有文件
                String fileIds = getFiles(x);
                if (StringUtils.isNotBlank(fileIds)) {
                    x.setFilesMap(tIssueAssistMapper.selectFileByIds(fileIds.split(",")));
                }
            });
            // 当前环节对应流程 的 父流程实例id，为承办部门人员办理，财务部门人员确认 会签 返回应该显示 哪条执行记录
            if (StringUtils.isNotBlank(actId) && !vo.gettIssueAssistConditions().isEmpty() && StringUtils.isNotBlank(activityInstId)) {
                flowUtil.init(info.getLoginAcct(), info.getLoginName(), CoreConstClass.TENANLID, null, false);
                WFActivityInst wfActivityInst = flowUtil.queryActivityDetail(Long.parseLong(activityInstId));
                long parentProcID = flowUtil.queryProcessInstDetail(wfActivityInst.getProcessInstID()).getParentProcID();
                vo.setParentProcID(parentProcID + "");
                if (parentProcID == 0) {
                    // 主流程
                    vo.setFlowLevel(1);
                } else {
                    long parentProcID1 = flowUtil.queryProcessInstDetail(parentProcID).getParentProcID();
                    if (parentProcID1 == 0) {
                        vo.setFlowLevel(2);
                    } else {
                        vo.setFlowLevel(3);
                    }
                }
            }
        }
        // 过滤组装数据，协助执行子流程的各个环节从待办页面进去展示当前所在子流程中的承办部门执行情况(前台做)

        // 查询表单关联文件
        List<Map<String, Object>> map = tIssueAssistMapper.selectFile(id);
        vo.setFiles(map);
        if (vo.getMian() != null) {

            // 如果不是 暂存状态的，是  已经提交了流程的数据
            // 解析组装流程日志
            List<TFlowLogVO> logs = vo.getMian().getLogs();
            TFlowLogVO tFlowLogVO;
            for (int i = 0; i < logs.size(); i++) {
                tFlowLogVO = logs.get(i);
                List<SubTFlowLogVO> subLogs = tFlowLogVO.getSubLogs();
                if (!StringUtil.listEmpty(subLogs)) {
                    // 更具时间排序  按审批时间顺序展示
                    // 根据流程实例id分组  为了确保  财务部门 不会被单独 分出来
                    Map<Long, List<SubTFlowLogVO>> collect3 = subLogs.stream().sorted(Comparator.comparing(SubTFlowLogVO::getModifyTime)).collect(Collectors.groupingBy(SubTFlowLogVO::getFlowId));
                    Map<String, List<SubTFlowLogVO>> collect1 = new HashMap<>(collect3.size());
                    collect3.entrySet().stream().forEach(x -> {
                        // 是 就按部门分组
                        // 不是 就按公司分组
                        TIssueAssistCondition tIssueAssistCondition = tIssueAssistConditionMapper.selectByFlowId(x.getKey() + "");
                        // 如果送本单位多部门  按 部门 分组
                        // 如果送非本单位 按 单位 分组
                        collect1.put("1".equals(tIssueAssistCondition.getRecordType()) ? x.getValue().get(0).getDeptName() : x.getValue().get(0).getOrgName(), x.getValue());
                    });
                    tFlowLogVO.setGroupingSubLogs(collect1);
                }
            }
            // 判断是否已读
            if (vo.getMian().getFlowId() == null) {
                vo.setIsUnView("1");
            } else {
                vo.setIsUnView(tFlowService.isCurUser2(vo.getMian().getFlowId()) + "");
            }
        }

        if (StringUtils.isNotBlank(actId)) {
            // 查询当前节点权限
            String permissionJson = flowActivityConfigService.judgeFlow(vo.getMian().getFlowName() + "", actId, Integer.parseInt(vo.getMian().getVersionId()));
            if (StringUtils.isBlank(permissionJson)) {
                throw new MyOwnRuntimeException(CoreConstClass.MESSAGE_UNACTRIGHT);
            }
            vo.setPermissionJson(permissionJson);
        }
        return new ProcessResult(ProcessResult.SUCCESS, DisputeConstClass.TISSUE_ASSIST_FIND_ONE_SUCCESS, vo);
    }

    private String getFiles(TIssueAssistCondition x) {
        StringBuilder sb = new StringBuilder(4);
        if (StringUtils.isNotBlank(x.getAssistFile())) {
            sb.append(x.getAssistFile() + ",");
        }
        if (StringUtils.isNotBlank(x.getBusinessFile())) {
            sb.append(x.getBusinessFile() + ",");
        }
        if (StringUtils.isNotBlank(x.getCheckFile())) {
            sb.append(x.getCheckFile() + ",");
        }
        if (StringUtils.isNotBlank(x.getFinanceFile())) {
            sb.append(x.getFinanceFile() + ",");
        }
        return sb.toString();
    }

    private String getRecordType(TIssueAssistVO vo, UserInfo info) {
        // 执行单位不是起草单位
        if (!vo.getOrgId().equals(vo.getJointlyUnitId()) && StringUtils.isEmpty(vo.getJointlyDeptId())) {
            // 执行单位id列表
            List<String> jointlyUnitIds = Arrays.asList(vo.getJointlyUnitId().split(","));
            if (jointlyUnitIds.contains(info.getOrgId())) {
                // 当前人单位是执行单位,查询自己单位执行情况列表
                return "2";
            } else {
                // 既不是起草单位，也不是执行单位
                // 判断当前人是起草单位的上级单位or下级单位
                if (judgeUnitLevel(info, vo.getOrgId()) == -1) {
                    // 当前人为起草单位上级，查所有
                    return "1";
                } else if (judgeUnitLevel(info, vo.getOrgId()) == 1) {
                    // 当前人为起草单位下级，查看上级单位执行情况
                    return "3";
                } else if (info.getOrgId().equals(vo.getOrgId())) {
                    // 当前登录人与起草单位同级，查看自己单位执行情况（什么都看不到）
                    // 当前人和起草单位是同一个单位，当前人是起草单位的人
                    return "1";
                } else {
                    // 同级别  但不是起草单位
                    return "2";
                }
            }
        } else {
            // 执行单位是起草单位，查所有
            return "1";
        }
    }

    /**
     * 判断当前登录人与orgId的上下级关系
     * -1：当前登录人为orgId上级
     * 1：当前登录人为orgId下级
     * 0：同级
     *
     * @param info
     * @param orgId
     * @return
     */
    private int judgeUnitLevel(UserInfo info, String orgId) {
        String orgLevel = tIssueAssistMapper.selectOrgLevelByOrgId(orgId);
        return info.getOrgLevel().compareTo(orgLevel);
    }


    /***
     * 协助执行流程综合查询
     * @param param
     * @return
     */
    @Override
    public ProcessResult findTIssueAssistList(FindTIssueAssistListListParamVO param) {
        logger.info("协助执行流程综合查询参数：" + param.toString());

        if (StringUtils.isNotBlank(param.getOrgId())) {
            // 起草单位 条件，允许选择虚节点
            OrgBeanVO orgBeanVO = orgBeanService.selectOrgInfoByOrgCode(param.getOrgId());
            String path = orgBeanVO.getOrgPath();
            param.setCreateOrgId(path);
        }

        List<TIssueAssist> result = null;// 查询结果
        PageInfo<TIssueAssist> pageInfo = null;// 分页对象
        UserInfo info = RequestHolder.getUserInfo();// 获取当前登录人信息
        // 判断是否为财务部门人员。财务部门看自己审批过的协助执行单
        if (info.getDeptName().contains("财务")) {
            PageHelper.startPage(param.getPageNum(), param.getPageSize());// 分页对象 分页查询
            result = tIssueAssistMapper.selectByLogs(param, info);
            pageInfo = new PageInfo<>(result);
            return new ProcessResult(ProcessResult.SUCCESS, ProcessResult.SUCCESS, pageInfo);
        }
        List<Object> roleCodeList = info.getRoleCodeList();// 获取当前人的角色列表
        String roleLevel = tFlowService.getRoleLevel(roleCodeList);// 判断并获取当前人角色权限
        List<String> preOrgIds = new ArrayList<>(2);
        param.setPreOrgId(preOrgIds);// 设置上级单位
        setFixedFields(param, info, preOrgIds);
        switch (roleLevel) {
            case "whole":
                PageHelper.startPage(param.getPageNum(), param.getPageSize());// 分页对象 分页查询
                result = tIssueAssistMapper.selectAllUnit(param, info);
                pageInfo = new PageInfo<>(result);
                return new ProcessResult(ProcessResult.SUCCESS, ProcessResult.SUCCESS, pageInfo);
            case "all":
                break;
            default:
                param.setJointlyDeptId(info.getDeptId());
                break;
        }
        PageHelper.startPage(param.getPageNum(), param.getPageSize());// 分页对象 分页查询
        result = tIssueAssistMapper.selectWholeData(param);
        pageInfo = new PageInfo<>(result);
        return new ProcessResult(ProcessResult.SUCCESS, ProcessResult.SUCCESS, pageInfo);
    }

    @Override
    public ProcessResult deleteByPrimaryKey(String id) {
        logger.info("协助执行流程删除业务表数据参数：" + id);
        List<String> ids = new ArrayList<>(1);
        ids.add(id);
        int i = tIssueAssistMapper.deleteTIssueAssist(ids);
        return new ProcessResult(ProcessResult.SUCCESS, ProcessResult.SUCCESS, i);
    }

    @Override
    public PageInfo<TIssueAssist> selectRelationshipListInCreate(SelectRelationshipListInCreateParamVO param) {
        // 校验参数
        if (null == param) {
            logger.error(DisputeConstClass.TISSUE_ASSIST_SELECT_RELATIONSHIP_LIST_IN_CREATE_FAILED, "。失败原因：" + DisputeConstClass.PARAM_EMPTY);
            return null;
        }
        UserInfo info = RequestHolder.getUserInfo();
        // 分页
        List<TIssueAssist> tIssueAssistList = tIssueAssistMapper.selectRelationshipListInCreate(param, info);
        flowUtil.init(info.getLoginAcct(), info.getLoginName(), CoreConstClass.TENANLID, null, false);
        // 过滤当前代办在本单位的执行单
        final int[] i = {0};
        List<TIssueAssist> collect = tIssueAssistList.stream().filter(x ->
                {
                    i[0] += 1;
                    return isCurUnitTask(flowUtil, x, info);
                }
        ).collect(Collectors.toList());
        return list2PageInfo(collect, param.getPageNum(), param.getPageSize());
    }


    public static <T> PageInfo<T> list2PageInfo(List<T> arrayList, Integer pageNum, Integer pageSize) {
        //实现list分页
        PageHelper.startPage(pageNum, pageSize);
        int pageStart = pageNum == 1 ? 0 : (pageNum - 1) * pageSize;
        int pageEnd = arrayList.size() < pageSize * pageNum ? arrayList.size() : pageSize * pageNum;
        List<T> pageResult = new LinkedList<>();
        if (arrayList.size() > pageStart) {
            pageResult = arrayList.subList(pageStart, pageEnd);
        }
        PageInfo<T> pageInfo = new PageInfo<>(pageResult);
        //获取PageInfo其他参数
        pageInfo.setTotal(arrayList.size());
        int endRow = pageInfo.getEndRow() == 0 ? 0 : (pageNum - 1) * pageSize + pageInfo.getEndRow() + 1;
        pageInfo.setEndRow(endRow);
        boolean hasNextPage = arrayList.size() >= pageSize * pageNum;
        pageInfo.setHasNextPage(hasNextPage);
        boolean hasPreviousPage = pageNum != 1;
        pageInfo.setHasPreviousPage(hasPreviousPage);
        pageInfo.setIsFirstPage(!hasPreviousPage);
        boolean isLastPage = (arrayList.size() > pageSize * (pageNum - 1) && arrayList.size() <= pageSize * pageNum);
        pageInfo.setIsLastPage(isLastPage);
        int pages = arrayList.size() % pageSize == 0 ? arrayList.size() / pageSize : (arrayList.size() / pageSize) + 1;
        pageInfo.setNavigateLastPage(pages);
        int[] navigatePageNums = new int[pages];
        for (int i = 1; i < pages; i++) {
            navigatePageNums[i - 1] = i;
        }
        pageInfo.setNavigatepageNums(navigatePageNums);
        int nextPage = pageNum < pages ? pageNum + 1 : 0;
        pageInfo.setNextPage(nextPage);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        pageInfo.setPages(pages);
        pageInfo.setPrePage(pageNum - 1);
        pageInfo.setSize(pageInfo.getList().size());
        int starRow = arrayList.size() < pageSize * pageNum ? 1 + pageSize * (pageNum - 1) : 0;
        pageInfo.setStartRow(starRow);
        return pageInfo;
    }

    private boolean isCurUnitTask(FlowUtil flowUtil, TIssueAssist x, UserInfo info) {
        try {
            WFProcessInst wfProcessInst = flowUtil.queryProcessInstDetail(Long.parseLong(x.getFlowId()));
            List<WFActivityInst> wfActivityInsts = flowUtil.getClient().getActivityInstManager().queryActivityInstsByProcessInstID(wfProcessInst.getProcessInstID(), null);
            WFActivityInst wfActivityInst = wfActivityInsts.stream().filter(y -> y.getCurrentState() == 2).collect(Collectors.toList()).get(0);
            if ("manual".equals(wfActivityInst.getActivityType())) {
                List<WFActivityInst> activityInsts = null;
                switch (wfProcessInst.getProcessDefName()) {
                    case "com.tower.issue.assist.state":
                        // 省分送总部流程
                        activityInsts = flowUtil.queryActivityInstsByActivityID(wfProcessInst.getProcessInstID(), "zbfwrycl", null);
                        break;
                    case "com.tower.issue.assist.city":
                        if ("02".equals(x.getUnitLevel())) {
                            // 地市送省分流程
                            activityInsts = flowUtil.queryActivityInstsByActivityID(wfProcessInst.getProcessInstID(), "sffwrycl", null);
                        } else if ("01".equals(x.getUnitLevel())) {
                            // 地市送总部流程
                            activityInsts = flowUtil.queryActivityInstsByActivityID(wfProcessInst.getProcessInstID(), "zbfwrycl", null);
                        }
                        break;
                    default:
                        return false;
                }
                if (activityInsts != null && !activityInsts.isEmpty()) {
                    List<WFActivityInst> collect = activityInsts.stream().filter(z -> z.getCurrentState() == 2).collect(Collectors.toList());
                    if (collect != null && !collect.isEmpty()) {
                        WFActivityInst wfActivityInst1 = collect.get(0);
                        // 活动实例
                        WFWorkItem wfWorkItem = flowUtil.queryWorkItemByActInstId(wfActivityInst1.getActivityInstID());
                        AccountLogic accountLogic = userInfoService.selectUserInfo(wfWorkItem.getParticipant());
                        return accountLogic.getAccountUnitId().equals(info.getOrgId());
                    }
                }
            } else if ("subflow".equals(wfActivityInst.getActivityType())) {
                // 会签环节
                System.out.println("processInstId：" + wfProcessInst.getProcessInstID() + "---" + "activityInstId：" + wfActivityInst.getActivityInstID());
                long[] longs = flowUtil.getClient().getProcessInstManager().querySubProcessInstIDsByActivityInstID(wfActivityInst.getActivityInstID());
                for (Long flowId : longs) {
                    WFProcessInst wfProcessInst1 = flowUtil.queryProcessInstDetail(flowId);
                    // 正在运行的子流程
                    if (wfProcessInst1.getCurrentState() == 2) {
                        // 当前子流程代办环节
                        List<WFActivityInst> activityInsts = null;
                        switch (wfProcessInst1.getProcessDefName()) {
                            case "com.tower.issue.assist.statemeet":
                                // 总部送省分流程
                                activityInsts = flowUtil.queryActivityInstsByActivityID(flowId, "sffwrycl", null);
                                break;
                            case "com.tower.issue.assist.citymeet":
                                // 省分送地市流程
                                activityInsts = flowUtil.queryActivityInstsByActivityID(flowId, "dsfwrycl", null);
                                break;
                            default:
                                continue;
                        }
                        if (activityInsts != null) {
                            List<WFActivityInst> collect = activityInsts.stream().filter(y -> y.getCurrentState() == 2).collect(Collectors.toList());
                            if (collect != null && !collect.isEmpty()) {
                                WFWorkItem wfWorkItem = flowUtil.queryWorkItemByActInstId(collect.get(0).getActivityInstID());
                                AccountLogic accountLogic = userInfoService.selectUserInfo(wfWorkItem.getParticipant());
                                if (accountLogic.getAccountUnitId().equals(info.getOrgId())) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        } catch (WFServiceException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ProcessResult findBaseInfo(String id) {
        TIssueAssist tIssueAssist = null;
        Map<String, Object> result = new HashMap<>();
        try {
            tIssueAssist = tIssueAssistMapper.selectByPrimaryKey(id);
            List<Map<String, Object>> filesMap = tIssueAssistMapper.selectFile(id);
            result.put("model", tIssueAssist);
            result.put("files", filesMap);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR, DisputeConstClass.TISSUE_ASSIST_FIND_BASE_INFO_FAILED, e);
            return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.TISSUE_ASSIST_FIND_BASE_INFO_FAILED);
        }
        return new ProcessResult(ProcessResult.SUCCESS, DisputeConstClass.TISSUE_ASSIST_FIND_BASE_INFO_SUCCESS, result);
    }

    @Override
    public ProcessResult findBaseInfoAndBindFiles(String id) {
        TIssueAssist tIssueAssist = null;
        Map<String, Object> result = new HashMap<>();
        try {
            // 获取主单据基础信息
            tIssueAssist = tIssueAssistMapper.selectByPrimaryKey(id);
            // 获取主单据关联的所有文件
            List<Map<String, Object>> filesMap = tIssueAssistMapper.selectFile(id);
            // 过滤文件，过滤出执行依据和附件 对应的 文件
            filesMap = filesMap.stream().filter(x -> "1".equals((String) x.get("file_share_type")) || "2".equals((String) x.get("file_share_type"))).collect(Collectors.toList());
            result.put("model", tIssueAssist);
            result.put("files", filesMap);
            // 生成新表单id，并于过滤后文件做绑定
            tIssueAssist.setId(StringUtil.getId());
            // 获取文件id列表，拼接文件id
            if (!filesMap.isEmpty()) {
                bindFiles(filesMap, tIssueAssist.getId());
            }
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR, DisputeConstClass.TISSUE_ASSIST_FIND_BASE_INFO_FAILED, e);
            return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.TISSUE_ASSIST_FIND_BASE_INFO_FAILED);
        }
        return new ProcessResult(ProcessResult.SUCCESS, DisputeConstClass.TISSUE_ASSIST_FIND_BASE_INFO_SUCCESS, result);
    }

    /***
     * 将单据与文件绑定
     * @param filesMap
     * @param businessId
     */
    private void bindFiles(List<Map<String, Object>> filesMap, String businessId) {
        List<FileShareVO> fileShareVOS = new ArrayList<>(filesMap.size());
        FileShareVO fileTempVO = null;
        // 遍历文件列表，创建fileShare对象
        for (Map<String, Object> fileTempMap : filesMap) {
            fileTempVO = new FileShareVO();
            fileTempVO.setFileShareId(UUID.randomUUID().toString().replaceAll("-", "") + new Random().nextLong())
                    .setFileId((String) fileTempMap.get("file_id"))
                    .setFileHttpUrl((String) fileTempMap.get("file_http_url"))
                    .setFileName((String) fileTempMap.get("fileName"))
                    .setFileExtension((String) fileTempMap.get("file_extension"))
                    .setFileLength((String) fileTempMap.get("file_length"))
                    .setFileShareBusinessKey(businessId)
                    .setFileShareType((String) fileTempMap.get("file_share_type"));
            fileShareVOS.add(fileTempVO);
        }
        int affectedFile = fileShareMapper.insertFileList(fileShareVOS);
        logger.info("单据：" + businessId + "绑定文件成功：" + affectedFile);
    }

    private void setFixedFields(FindTIssueAssistListListParamVO param, UserInfo info, List<String> preOrgIds) {
        if ("01".equals(info.getOrgLevel())) {
            param.setJointlyUnitId(null);
            preOrgIds.isEmpty();
        } else if ("02".equals(info.getOrgLevel()) || "03".equals(info.getOrgLevel())) {
            setPreOrgIds(preOrgIds, info);
            param.setJointlyUnitId(info.getOrgId());
        }
        param.setOrgId(getPath(info) + "%");
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
                headOrgCode = tIssueAssistMapper.getHeadOrgCode(getEnterpType(info.getUnitCode()));
                preOrgIds.add(headOrgCode);
                break;
            case "03":
                // 地市
                String provinceCode = RequestHolder.getProvinceCode(info.getDeptId());
                preOrgIds.add(provinceCode);
                headOrgCode = tIssueAssistMapper.getHeadOrgCode(getEnterpType(info.getUnitCode()));
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

    private Long getFlowId(TFlowLog record) {
        return (record.getFlowPid() == null || record.getFlowPid() == 0) ? record.getFlowId()
                : record.getFlowPid();
    }

    public void finishActivity(AddFlowLogVO vo, FlowUtil flowUtil, UserInfo info, TFlowLog record,
                               WFProcessInst processInst2, long flowId) {
        TIssueAssist lawsuit = tIssueAssistMapper.selectByPrimaryKey(record.getApproveItemId());
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
