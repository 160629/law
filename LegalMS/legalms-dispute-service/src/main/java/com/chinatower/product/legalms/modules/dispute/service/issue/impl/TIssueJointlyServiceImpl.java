package com.chinatower.product.legalms.modules.dispute.service.issue.impl;

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

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;

import com.chinatower.product.legalms.modules.dispute.entity.issue.TIssueJointly;
import com.chinatower.product.legalms.modules.dispute.mapper.issue.TIssueGuideMapper;
import com.chinatower.product.legalms.modules.dispute.mapper.issue.TIssueJointlyMapper;
import com.chinatower.product.legalms.modules.dispute.mapper.legislation.LegislationMapper;
import com.chinatower.product.legalms.modules.dispute.service.flow.TFlowService;
import com.chinatower.product.legalms.modules.dispute.service.issue.TIssueJointlyService;
import com.chinatower.product.legalms.modules.dispute.vo.jointly.JointlyParam;
import com.chinatower.product.legalms.modules.dispute.vo.jointly.JointlyVO;
import com.chinatower.product.legalms.modules.dispute.vo.querylist.TIssueJointlyListVO;
import com.chinatower.product.legalms.modules.flow.entity.common.CaseMainVO;
import com.chinatower.product.legalms.modules.flow.entity.common.DraftsVO;
import com.chinatower.product.legalms.modules.flow.entity.common.OrgBeanVO;
import com.chinatower.product.legalms.modules.flow.entity.flow.FlowStartVO;
import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowLog;
import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowMain;
import com.chinatower.product.legalms.modules.flow.mapper.common.DraftsVOMapper;
import com.chinatower.product.legalms.modules.flow.mapper.flow.TFlowLogMapper;
import com.chinatower.product.legalms.modules.flow.mapper.flow.TFlowMainMapper;
import com.chinatower.product.legalms.modules.flow.service.unview.TFlowCommonService;
import com.chinatower.product.legalms.modules.flow.service.version.FlowVersionService;
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

/**
 * @author 刘晓亮
 * @create 2019-11-27
 */

@Service("tIssueJointly")
public class TIssueJointlyServiceImpl implements TIssueJointlyService {

    @Autowired
    FlowUtil flowUtil;
    @Autowired
    TFlowCommonService commonService;
    @Autowired
    private TIssueJointlyMapper tIssueJointlyMapper;
    @Autowired
    TFlowMainMapper tFlowMainMapper;
    @Autowired
    TFlowLogMapper tFlowLogMapper;
    @Autowired
    DraftsVOMapper draftsVOMapper;
    @Autowired
    CaseMainService caseMainService;
    @Autowired
    TIssueGuideMapper tIssueGuideMapper;
    @Autowired
    LegislationMapper legislationMapper;
    @Autowired
    OrgBeanService orgBeanService;
    @Autowired
    OrgDepLeaderService orgDepLeaderService;
    @Autowired
    TFlowService tFlowService;
    private static final Logger logger = LoggerFactory.getLogger("TransLog");
    @Autowired
    FlowActivityConfigService flowActivityConfigService;
    @Autowired
    FlowVersionService flowVersionService;
    @Autowired
    BusinessLogService businessLogService;
    @Autowired
    private OrgBeanService orgBean;
    /**
     * 根据主键更新
     *
     * @param jointly
     * @return
     */
    @Override
    public int updateByPrimaryKey(TIssueJointly jointly) {
        return tIssueJointlyMapper.updateByPrimaryKey(jointly);
    }

    @Override
    public int deleteTIssueJointly(List<String> jointlyIds) {
        DraftsVO draftsVO=new DraftsVO(DisputeConstClass.T_ISSUE_JOINTLY, jointlyIds);
        int i =draftsVOMapper.deleteBatchDrafts(draftsVO);
        int j = tIssueJointlyMapper.deleteTIssueJointly(jointlyIds);
        return i+j;
    }



    @Override
    public String selectCode(String unitCode, String orgCode) {
        return tIssueJointlyMapper.selectCode(unitCode,orgCode);
    }

    @Override
    public int updateByPrimaryKeySelective(TIssueJointly jointly) {
        if(StringUtil.isEmpty(jointly.getJointlyId())) {
            return 0;
        }
        muitRelationship(jointly, jointly.getModuleName(), 0);
        return tIssueJointlyMapper.updateByPrimaryKeySelective(jointly);
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
                updateByPrimaryKeySelective(StringUtil.getModel(TIssueJointly.class,(HashMap<String, Object>) vo.getBusinessMap()));
                vo.getBusinessMap().put("approveItemType", DisputeConstClass.T_ISSUE_JOINTLY);
                vo.getBusinessMap().put("actName", record.getActivityDefName());
                vo.getBusinessMap().put("actInstId", record.getActivityInstId());
                vo.getBusinessMap().put("versionId", record.getVersionId());
                vo.getBusinessMap().put("businessAdvice", (String) vo.getBusinessMap().get("feedBack"));
                vo.getBusinessMap().put("deptId", info.getDeptId());
                vo.getBusinessMap().put("actParentInstId", record.getActPid() + "");
                vo.getBusinessMap().put("flowId", record.getFlowId() + "");
                vo.getBusinessMap().put("flowPid", record.getFlowPid() + "");
                tFlowService.insertBusinessLog2(info, vo.gettFlowLog().getApproveItemId(), (String) vo.getBusinessMap().get("file_feedBack"),
                        record.getActivityDefId(), vo.getBusinessMap(), processInst2);
            }
            flowUtil.clientCommit(true);// 提交流程
        } catch (Exception e) {
            if (flowUtil != null) {
                flowUtil.clientBack(true);// 流程回退
            }
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚数据
            logger.info(DisputeConstClass.OPERATION_EXCEPTION,e);
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
            logger.info("案件协办审批错误",e);
            return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.NEXT_FLOW_ERROR);
        }
        if(finishFlag) {
            AutoView view=new AutoView();
            tFlowService.finishActivity(vo, flowUtil, info, record, processInst2, flowId,view);
        }
        return new ProcessResult(ProcessResult.SUCCESS, DisputeConstClass.NEXT_FLOW_SUCCESS);
    }

    /**
     * 根据主键删除（物理删除）
     *
     * @param jointlyId
     * @return
     */
    public int deleteByPrimaryKey(String jointlyId) {
        return tIssueJointlyMapper.deleteByPrimaryKey(jointlyId);
    }

    /**
     * 暂存（草稿箱表，案件交办表）
     *
     * @param vo
     * @return
     */
    @Override
    @Transactional
    public int tempTIssueJointly(AddFlowVO<TIssueJointly> vo, UserInfo info,TIssueJointly tIssueJointly) {
        DraftsVO record = new DraftsVO(); // 创建草稿箱对象
        record.setApproveItemType(DisputeConstClass.T_ISSUE_JOINTLY)
                .setApproveItemName(DisputeConstClass.JOINTLY_ID)
                .setApproveItemId(tIssueJointly.getJointlyId())
                .setModuleName(vo.getModuleName())
                .setApplyType("5")
                .setLawCaseTitle(tIssueJointly.getJointlyTitle())
                .setUserCode(info.getLoginAcct());
        tIssueJointly.setJointlyStatus("10");
        tIssueJointly.setCreateTime(new Date());
        int i = 0;
        if (null!=vo.getAddFlag() && 1==vo.getAddFlag()) {
            if (StringUtils.isBlank(tIssueJointly.getJointlyId())) {
                tIssueJointly.setJointlyId(StringUtil.getId());
            }
            record.setCreateTime(new Date());
            record.setLawCaseCode(tIssueJointly.getJointlyCode());
            i = i + tIssueJointlyMapper.insertSelective(tIssueJointly);
            i = i + draftsVOMapper.insert(record);
        } else {
            record.setUpdateTime(new Date());
            i = i + tIssueJointlyMapper.updateByPrimaryKeySelective(tIssueJointly);
            i = i + draftsVOMapper.updateDrafts(record);
        }
        return i;
    }

    /**
     * 查询详情
     * @param jointlyId
     * @param actId
     * @return
     */
    @Override
    public JointlyVO findOneTIssueJointly(String jointlyId, String actId) {
        JointlyVO jointlyVO = tIssueJointlyMapper.findOneTIssueJointly(jointlyId);// 查询详情
        if (null == jointlyVO) {
            return null;
        }
        List<TFlowLogVO> logs = jointlyVO.getMian().getLogs();
        TFlowLogVO tFlowLogVO;
        for (int i = 0; i < logs.size(); i++) {
            tFlowLogVO = logs.get(i);
            tFlowLogVO.setGroupingSubLogs(tFlowLogVO.getSubLogs().stream().collect(Collectors.groupingBy(SubTFlowLogVO::getDeptName)));
        }
        //  查询案件信息
        if (StringUtils.isNotBlank(jointlyVO.getJointlyCase())) {
            String[] caseId = jointlyVO.getJointlyCase().split(",");
            List<CaseMainVO> caseMainVO = caseMainService.selectCase(caseId);
            jointlyVO.setCaseMainVO(caseMainVO == null ? null : caseMainVO.get(0));
        }

        //查询历史反馈记录
        BusinessLogVO businessLogVO = new BusinessLogVO();
        businessLogVO.setApproveItemId(jointlyId);
        List<BusinessLogVO> list = businessLogService.selectBusinessLog2(businessLogVO);
        if (list != null && !list.isEmpty()) {
            jointlyVO.setFeedBackMap(list.stream().filter(x -> jointlyVO.getJointlyUnitId().equals(x.getOrgId())).collect(Collectors.toList()));
        }
        //  查询文件列表
        List<Map<String, Object>> maps = tIssueGuideMapper.selectFile(jointlyId);
        jointlyVO.setFiles(maps);
        if (jointlyVO.getMian().getFlowId() == null) {
            jointlyVO.setIsUnView("1");
        } else {
            jointlyVO.setIsUnView(tFlowService.isCurUser2(jointlyVO.getMian().getFlowId()) + "");
        }
        if (StringUtils.isNotBlank(actId)) {
            String permissionJson = flowActivityConfigService.judgeFlow(jointlyVO.getMian().getFlowName(), actId, Integer.parseInt(jointlyVO.getMian().getVersionId()));
            if(StringUtils.isBlank(permissionJson)){
                throw new MyOwnRuntimeException(DisputeConstClass.MESSAGE_UNACTRIGHT);
            }
            jointlyVO.setPermissionJson(permissionJson);
        }
        return jointlyVO;
    }


    @Override
    public ProcessResult selectAll(JointlyParam param) {
        List<TIssueJointly> result = null;//查询结果
        PageInfo<TIssueJointly> pageInfo = null;// 分页对象
        UserInfo info = RequestHolder.getUserInfo();// 获取当前登录人信息
        if ( StringUtils.isNotBlank(param.getJointlyUnitId())) {// 判断执行单位id不为空
            List<String> orgCodeList = orgBean.selectOrgListByOrgCode(param.getJointlyUnitId());
            if(!orgCodeList.isEmpty()){
                param.setOrgCodeList(orgCodeList);
            }else {
                param.setOrgCodeList(Arrays.asList(param.getJointlyUnitId()));
            }
        }
        PageHelper.startPage(param.getPageNum(), param.getPageSize());// 分页对象  分页查询
        result = tIssueJointlyMapper.selectAll(param,info.getLoginAcct());
        pageInfo = new PageInfo<>(result);
        return new ProcessResult(ProcessResult.SUCCESS, "SUCCESS", pageInfo);
    }

    public int muitRelationship(TIssueJointly tIssueJointly, String moduleName, int addFlag) {
        int k=0;
        String caseId = tIssueJointly.getJointlyCase();
        if(StringUtil.isEmpty(caseId)) {
            return k;
        }
        RelationshipVO relationshipVO =new RelationshipVO(DisputeConstClass.T_ISSUE_JOINTLY,caseId,tIssueJointly.getJointlyId(),
                tIssueJointly.getJointlyTitle(),tIssueJointly.getJointlyCode(),moduleName);
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
    /**
     * 起草 开启流程
     *
     * @param vo
     * @param flowUtil
     * @param info
     * @return
     */
    @Override
    @Transactional
    public int addTIssueJointly(AddFlowVO<TIssueJointly> vo, FlowUtil flowUtil, UserInfo info,TIssueJointly tIssueJointly) {
        tIssueJointly.setJointlyStatus("20");// 设置状态 审批中（数据字典值20）
        tIssueJointly.setCreateTime(new Date());
        tIssueJointly.setLoginAcct(info.getLoginAcct());// 设置案件协办单创建人id
        tIssueJointly.setMobile(info.getMobile());
        tIssueJointly.setDeptName(info.getDeptName());
        tIssueJointly.setDeptId(info.getDeptId());
        tIssueJointly.setLoginName(info.getLoginName());
        tIssueJointly.setOrgId(info.getOrgId());
        tIssueJointly.setOrgName(info.getOrgName());
        //创建案件卷宗与法律文书关联对象
        RelationshipVO relationshipVO = new RelationshipVO();
        relationshipVO.setBusinessType(DisputeConstClass.T_ISSUE_JOINTLY);
        relationshipVO.setCaseId(tIssueJointly.getJointlyCase());
        relationshipVO.setBusinessId(tIssueJointly.getJointlyId());
        relationshipVO.setTittle(tIssueJointly.getJointlyTitle());
        relationshipVO.setModuleName(vo.getModuleName());
        int i = 0;// 数据记录处理更新数
        if (vo.getAddFlag() == null || 1!=vo.getAddFlag()) {// 有编号（数据库中已有）（编号，id此时都存在）进行更新
            i = tIssueJointlyMapper.updateByPrimaryKey(tIssueJointly);// 草稿箱中提交 更新案件协办表
            DraftsVO draftsVO = new DraftsVO(DisputeConstClass.T_ISSUE_JOINTLY,tIssueJointly.getJointlyId());
           draftsVOMapper.delDraftsByItem(draftsVO);
            int k = muitRelationship(tIssueJointly, vo.getModuleName(), 0);
            logger.info("案件协办修改关联卷宗：" + k);
        } else if(StringUtil.isEmpty(tIssueJointly.getJointlyId())){
            tIssueJointly.setJointlyId(StringUtil.getId());
            i = tIssueJointlyMapper.insertSelective(tIssueJointly);// 插入案件协办单数据
            int k = muitRelationship(tIssueJointly, vo.getModuleName(), 1);
            logger.info("案件协办新增关联卷宗：" + k);
        }else {
            i = tIssueJointlyMapper.insertSelective(tIssueJointly);
            int k = muitRelationship(tIssueJointly, vo.getModuleName(), 1);
            logger.info("案件协办新增关联卷宗：" + k);
        }
        if (i < 1) {// 更新记录<1表示起草失败
            throw new MyOwnRuntimeException("起草失败");
        }
        Map<String, Object> map = new HashMap<>();// 流程相关数据对象
        List<OrgParticipantVO> orgs = vo.getOrgs();// 从参数列表中获取  流程参与者对象
        String signDept = vo.getModel().getSignDept();// 从参数列表中获取  是否会签判断条件
        if ("1".equals(signDept)) {// 1 表示下一步为会签
            List<Map<String, Object>> list = AddFlowVO.getObjectToMap(vo.getOrgs());// 转换流程参与者
            map.put("orgs", list);// 将会签参与者添加到相关数据对象中
        } else {// 不是会签
            map.put(vo.getActDefParam() + "P", orgs.get(0).getDepaInterPersonP());// // 将下一步参与者添加到相关数据对象中
        }
        map.put("next", vo.getCurActDefParam() + vo.getActDefParam());// 设置流程方向判断条件
        map.put(vo.getCurActDefParam() + "P",
                new WFParticipant(info.getLoginAcct(), info.getLoginName(), "emp"));// 设置流程启动者并添加到相关数据对象中
        map.put("unView", 0);
        BusinessFields businessFields = new BusinessFields();
        businessFields.setBusinessTitle(tIssueJointly.getJointlyTitle())
                .setBusinessCode(tIssueJointly.getJointlyCode())
                .setBusinessType("案件协办")
                .setBusinessTableName(DisputeConstClass.T_ISSUE_JOINTLY)
                .setBusinessId(tIssueJointly.getJointlyId())
                .setBusinessIdName(DisputeConstClass.JOINTLY_ID)
                .setCurState("审批中")
                .setAccountId(info.getLoginAcct())
                .setModuleName(vo.getModuleName());
        Map<String, Object> bizInfo = BusinessFiledUtil.setBusinessFields(businessFields);// 设置业务冗余字段
        FlowStartVO flowStartVO = new FlowStartVO();
        flowStartVO.setDefName(vo.getFlowName())
                .setInstName(tIssueJointly.getJointlyTitle())
                .setInstDesc(vo.getModel().getJointlyTitle())
                .setBizInfo(bizInfo)
                .setMap(map)
                .setTableName("LMSBIZINFO")
                .setTransactionSplit(false)
                .setParams(new Object[0]);
        long flowId = flowUtil.getFlowIdWithBizInfo(flowStartVO);// 开启流程
        flowUtil.setFlowId(flowId);// 记录主流程实例ID
        return i;
    }

    /**
     * 起草 记录流程信息（流程主表，流程日志表）
     *
     * @param vo
     * @param flowUtil
     * @param info
     * @return
     */
    @Override
    @Transactional
    public int addFlow(AddFlowVO<TIssueJointly> vo, FlowUtil flowUtil, UserInfo info,TFlowLog log) {
        TIssueJointly model = vo.getModel();// 从参数列表中获取  案件协办对象
        TFlowMain flow = new TFlowMain(); // 流程主表对象
        flow.setFlowName(vo.getFlowName()).setCreateUserId(info.getLoginAcct()).setApproveItemName("jointly_id")
                .setCreateUserName(info.getLoginName()).setApproveItemId(model.getJointlyId().toString())
                .setFlowId(flowUtil.getFlowId()).setCreateTime(new Date())
                .setApproveItemType(model.getClass().getAnnotationsByType(Table.class)[0].name()).setModuleName(vo.getModuleName());// 设置流程主表属性值
        flow.setVersionId(flowVersionService.selectLatestVersion(vo.getFlowName()).getVersionId() + "");
        WFWorkItem work = flowUtil.finishFirstWork(flowUtil.getFlowId(), false);// 完成流程第一步（起草）
        flowUtil.setActivityInstID(work.getActivityInstID());
        if ("1".equals(model.getSignDept())) {// 判断下一步是否为会签
            flowUtil.setRelativeData(work.getProcessInstID(), "parentActInstId", work.getActivityInstID());// 下一步为会签，将当前环节实例ID存在流程实例的相关数据中，作为子流程和主流程环节的关联
        }
        log.setFlowLogId(StringUtil.getId())
                .setActivityDefId(work.getActivityDefID())// 设置流程日志对象属性值
                .setActivityDefName(work.getWorkItemName())
                .setNextActivityDefId(vo.getActDefParam())
                .setNextActivityDefName(vo.getActDefName())
                .setStatus(flowUtil.getWorkStatus(work.getCurrentState()))
                .setUserId(flowUtil.getUserId()).setActivityInstId(work.getActivityInstID())
                .setApproveItemId(model.getJointlyId())
                .setApproveItemType(model.getClass().getAnnotationsByType(Table.class)[0].name())
                .setFlowDefName(vo.getFlowName())
                .setFlowId(flowUtil.getFlowId())
                .setNextActivityDefId(vo.getActDefParam())
                .setNextActivityDefName(vo.getActDefName()).setWorkType("1");
        log.setVersionId(vo.getVersionId());
		log.setToerId(info.getLoginAcct());
		log.setToerName(info.getLoginName());
        List<OrgParticipantVO> orgs = vo.getOrgs(); // 从参数列表中获取  下一步参与者对象
        BusinessFiledUtil.setReceiverInfo(log, orgs, info);// 设置下一步接收者
        int i = tFlowMainMapper.insertSelective(flow); // 插入流程主表
        i = i + tFlowLogMapper.insertSelective(log);// 插入流程日志表
        return i;
    }
    /**
     * 综合查询
     * @param param
     * @return
     */
    @Override
    public ProcessResult findTIssueJointlyList(TIssueJointlyListVO param) {
        List<TIssueJointly> result = null;//查询结果
        PageInfo<TIssueJointly> pageInfo = null;// 分页对象
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
        if (StringUtils.isNotBlank(param.getJointlyUnitId())) { // 执行单位
            OrgBeanVO orgBeanVO = orgBeanService.selectOrgInfoByOrgCode(param.getJointlyUnitId());
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
        result = tIssueJointlyMapper.selectWholeData(param);
        pageInfo = new PageInfo<>(result);
        return new ProcessResult(ProcessResult.SUCCESS, "SUCCESS", pageInfo);
    }

    private void setFixedFields(TIssueJointlyListVO param, UserInfo info, List<String> preOrgIds) {
        if ("01".equals(info.getOrgLevel())) {
            param.setExecUnitId(null);
            preOrgIds.isEmpty();
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
                headOrgCode = tIssueJointlyMapper.getHeadOrgCode(getEnterpType(info.getUnitCode()));
                preOrgIds.add(headOrgCode);
                break;
            case "03":
                // 地市
                String provinceCode = RequestHolder.getProvinceCode(info.getDeptId());
                preOrgIds.add(provinceCode);
                headOrgCode = tIssueJointlyMapper.getHeadOrgCode(getEnterpType(info.getUnitCode()));
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
}
