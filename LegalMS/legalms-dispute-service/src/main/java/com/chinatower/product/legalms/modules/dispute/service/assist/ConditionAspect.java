package com.chinatower.product.legalms.modules.dispute.service.assist;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.common.CoreConstClass;
import com.chinatower.product.legalms.common.FlowUtil;
import com.chinatower.product.legalms.common.StringUtil;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.dispute.entity.assist.TIssueAssist;
import com.chinatower.product.legalms.modules.dispute.entity.assist.TIssueAssistCondition;
import com.chinatower.product.legalms.modules.dispute.mapper.assist.TIssueAssistConditionMapper;
import com.chinatower.product.legalms.modules.dispute.mapper.assist.TIssueAssistMapper;
import com.chinatower.product.legalms.modules.flow.entity.common.AccountLogic;
import com.chinatower.product.legalms.modules.flow.service.common.UserInfoService;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowLogVO;
import com.eos.workflow.data.WFActivityInst;
import com.eos.workflow.data.WFProcessInst;
import com.primeton.workflow.api.WFServiceException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Aspect
@Component
public class ConditionAspect {

    private static final Logger logger = LoggerFactory.getLogger(ConditionAspect.class);

    @Autowired
    FlowUtil flowUtil;
    @Autowired
    TIssueAssistConditionMapper tIssueAssistConditionMapper;
    @Autowired
    TIssueAssistMapper tIssueAssistMapper;
    @Autowired
    UserInfoService userInfoService;

    @Pointcut("execution(* com.chinatower.product.legalms.modules.dispute.service.assist.impl.TIssueAssistServiceImpl.addTFlowLog(..))")
    public void pointCutToAddTFlowLog() {
        // 协助执行流程：每次审批结束的时候判断，
        // 1、是否是子流程
        // 2、执行单位不是本单位
    }

    @Around("pointCutToAddTFlowLog()")
    public ProcessResult afterAddTFlowLog(ProceedingJoinPoint point) {
        logger.info("协助执行流程，执行单位执行状态处理开始：" + point.toString());
        ProcessResult processResult = null;
        try {
            // 获取审批参数
            AddFlowLogVO arg = (AddFlowLogVO) point.getArgs()[0];
            // 获取审批方法执行结果
            processResult = (ProcessResult) point.proceed();
            // 获取开始节点定义id
            String activityBeginDefId = arg.gettFlowLog().getActivityDefId();
            // 获取结束节点定义id
            String activityEndDefId = arg.gettFlowLog().getNextActivityDefId();
            // 判断是否是子流程结束,子流程结束，表示执行单位or执行部门本次执行完成，需要修改执行进展为已完成。(写死的环节id，如果子流程最后一个节点更改，要修这里的判断)
            boolean isChange = false;
            String moduleName = arg.getModuleName();
            if (
                // 总部流程 本单位部门
                    (("issue_assist_head".equals(moduleName) && "cbbmfzrsp".equals(activityBeginDefId) && "fwrycs".equals(activityEndDefId))
                            // 总部流程 非本单位 送 省分
                            || ("issue_assist_head".equals(moduleName) && "sfzjlsp2".equals(activityBeginDefId) && "fwrycs2".equals(activityEndDefId))
                            || ("issue_assist_head".equals(moduleName) && "sffgldsp".equals(activityBeginDefId) && "fwrycs2".equals(activityEndDefId))
                            || ("issue_assist_head".equals(moduleName) && "sffwbmfzrsp".equals(activityBeginDefId) && "fwrycs2".equals(activityEndDefId))
                            // 省分流程 本单位部门
                            || ("issue_assist_state".equals(moduleName) && "cbbmfzrsp".equals(activityBeginDefId) && "fwrysh".equals(activityEndDefId))
                            // 省分流程 非本单位 送 地市
                            || ("issue_assist_state".equals(moduleName) && "dszjlsp3".equals(activityBeginDefId) && "fwrysh2".equals(activityEndDefId))
                            || ("issue_assist_state".equals(moduleName) && "dsfgldsp".equals(activityBeginDefId) && "fwrysh2".equals(activityEndDefId))
                            || ("issue_assist_state".equals(moduleName) && "dszhbfzrsp".equals(activityBeginDefId) && "fwrysh2".equals(activityEndDefId))
                            // 省分流程 非本单位 送 总部
                            || ("issue_assist_state".equals(moduleName) && (("zbfwbmfzrsp".equals(activityBeginDefId) || "zbfgldsp2".equals(activityBeginDefId) || "zbzjlsp2".equals(activityBeginDefId) || "dszsp".equals(activityBeginDefId)) && "fwrysh2".equals(activityEndDefId)))
                            // 地市流程送本单位
                            || ("issue_assist_city".equals(moduleName) && "cbbmfzrsp".equals(activityBeginDefId) && "fwrysh".equals(activityEndDefId))
                            // 地市流程 非本单位 送 省分
                            || ("issue_assist_city".equals(moduleName) && (("sffwbmfzrsp".equals(activityBeginDefId) || "sffgldsp".equals(activityBeginDefId) || "sfzjlsp".equals(activityBeginDefId)) && "fwrysh2".equals(activityEndDefId)))
                            // 地市流程 非本单位 送 总部
                            || ("issue_assist_city".equals(moduleName) && (("zbfwbmfzrsp".equals(activityBeginDefId) || "zbfgldsp".equals(activityBeginDefId) || "zbzjlsp".equals(activityBeginDefId) || "dszsp".equals(activityBeginDefId)) && "sffwryzs".equals(activityEndDefId)))
                    )) {
                isChange = true;
            }

            // 发起会签
            // 判断是否要创建执行记录
            if (
                // 总部 送 本单位部门 发起会签
                    (("issue_assist_head".equals(moduleName) && "fwbmfzrsp".equals(activityBeginDefId) && "cbbmfzrsh".equals(activityEndDefId))
                            // 总部 送 非本单位 发起会签
                            || ("issue_assist_head".equals(moduleName) && "fwryzs".equals(activityBeginDefId) && "sffwrycl".equals(activityEndDefId))
                            // 省分 送 本单位部门 发起会签
                            || ("issue_assist_state".equals(moduleName) && "fwbmfzrsp".equals(activityBeginDefId) && "cbbmfzrsh".equals(activityEndDefId))
                            // 省分 送 地市 发起会签
                            || ("issue_assist_state".equals(moduleName) && "fwryzs2".equals(activityBeginDefId) && "dsfwrycl".equals(activityEndDefId))
                            // 省分 送 总部
                            || ("issue_assist_state".equals(moduleName) && "fwryzs".equals(activityBeginDefId) && "zbfwrycl".equals(activityEndDefId))
                            // 地市流程 送 本单位部门 发起会签
                            || ("issue_assist_city".equals(moduleName) && "zhbfzrsp".equals(activityBeginDefId) && "cbbmfzrsh".equals(activityEndDefId))
                            // 地市 送 省分
                            || ("issue_assist_city".equals(moduleName) && "fwryzs".equals(activityBeginDefId) && "sffwrycl".equals(activityEndDefId))
                            // 地市 送 总部
                            || ("issue_assist_city".equals(moduleName) && "fwryzs2".equals(activityBeginDefId) && "zbfwrycl".equals(activityEndDefId)))
                            && "SUCCESS".equals(processResult.getResultStat())
            ) {
                int i = getConditionVO(arg);
                logger.info("协助执行力流程，创建执行记录成功：" + i);
            } else {
                // 失败，返回原结果
                logger.error("协助执行力流程，创建执行记录失败，当前环节不需要创建执行记录");
            }
            // 判断是否要修改执行进度，判断审批方法是否成功，成功则修改执行情况
            if (isChange && "SUCCESS".equals(processResult.getResultStat())) {
                // 查询业务表数据
                TIssueAssist tIssueAssist = tIssueAssistMapper.selectByPrimaryKey(arg.gettFlowLog().getApproveItemId());
                // 成功，需要修改执行进展
                // 根据子流程对应的流程实例id，查询出对应的执行情况记录。修改该记录的执行进展状态值
                int i = tIssueAssistConditionMapper.updateConditionState(arg.gettFlowLog().getFlowId() + "", tIssueAssist.getJointlyUnitId().split(","), "已完成");
                logger.info("协助执行力流程，执行单位执行状态处理，被修改子流程实例id：" + arg.gettFlowLog().getFlowId());
                logger.info("协助执行力流程，执行单位执行状态处理结果：" + i);
            } else {
                // 失败，返回原结果
                logger.error("协助执行力流程，执行单位执行状态处理失败----审批方法错误，或当前不是送结束，不进行执行进展处理");
            }
            // 记录修改结果
            // 返回审批方法的结果，审批正常结束
        } catch (Exception e) {
            logger.error("协助执行流程，执行单位执行状态处理失败", e);
        } catch (Throwable throwable) {
            logger.error("协助执行流程，执行单位执行状态处理失败---转换参数结果失败", throwable);
        }
        logger.info("协助执行流程，执行单位执行状态结束：" + point.toString());
        return processResult;
    }

    private int getConditionVO(AddFlowLogVO arg) {
        TIssueAssist tIssueAssist = tIssueAssistMapper.selectByPrimaryKey(arg.gettFlowLog().getApproveItemId());
        UserInfo info = RequestHolder.getUserInfo();
        flowUtil.init(info.getLoginAcct(), info.getLoginName(), CoreConstClass.TENANLID, null, false);
        int i = 0;
        try {
            List<WFActivityInst> wfActivityInsts = flowUtil.getClient().getActivityInstManager().queryActivityInstsByProcessInstID(arg.gettFlowLog().getFlowId(), null);
            WFActivityInst wfActivityInst = wfActivityInsts.stream().filter(x -> x.getCurrentState() == 2).collect(Collectors.toList()).get(0);
            if ("manual".equals(wfActivityInst.getActivityType())) {
                AccountLogic accountLogic = userInfoService.selectUserInfo(flowUtil.queryWorkItemByActInstId(wfActivityInst.getActivityInstID()).getParticipant());
                // 不是会签
                TIssueAssistCondition tIssueAssistCondition = new TIssueAssistCondition();
                // 共有数据
                // 主键id
                tIssueAssistCondition.setId(StringUtil.getId());
                // 流程实例id
                tIssueAssistCondition.setFlowId(wfActivityInst.getProcessInstID() + "");
                // 活动实例id
                tIssueAssistCondition.setActInstId(wfActivityInst.getActivityInstID() + "");
                // 活动定义id
                tIssueAssistCondition.setActId(wfActivityInst.getActivityDefID());
                // 协助执行单id
                tIssueAssistCondition.setAssistId(arg.gettFlowLog().getApproveItemId());
                // 执行单位id
                tIssueAssistCondition.setJointlyUnitId(tIssueAssist.getJointlyUnitId());
                // 执行单位名称
                tIssueAssistCondition.setJointlyUnitName(tIssueAssist.getJointlyUnitName());
                // 执行人id
                tIssueAssistCondition.setAccountId(accountLogic.getAccountId());
                // 执行人名称
                tIssueAssistCondition.setAccountName(accountLogic.getAccountName());
                // 记录类型1,本单位填写记录；2,非本单位填写记录
                tIssueAssistCondition.setRecordType(tIssueAssist.getOrgId().equals(tIssueAssist.getJointlyUnitId()) ? "1" : "2");
                // 执行进展
                tIssueAssistCondition.setState("执行中");
                // 新增
                i += tIssueAssistConditionMapper.insertCondition(tIssueAssistCondition);
            } else if ("subflow".equals(wfActivityInst.getActivityType())) {
                long[] subProInstIds = flowUtil.getClient().getProcessInstManager().querySubProcessInstIDsByActivityInstID(wfActivityInst.getActivityInstID());
                for (Long subProInstId : subProInstIds) {
                    WFProcessInst wfProcessInst = flowUtil.queryProcessInstDetail(subProInstId);
                    List<WFActivityInst> activityInsts1 = flowUtil.getClient().getActivityInstManager().queryActivityInstsByProcessInstID(wfProcessInst.getProcessInstID(), null);
                    AccountLogic accountLogic = userInfoService.selectUserInfo(flowUtil.queryWorkItemByActInstId(activityInsts1.get(1).getActivityInstID()).getParticipant());
                    TIssueAssistCondition tIssueAssistCondition = new TIssueAssistCondition();
                    if ("com.tower.issue.assist.meet".equals(wfProcessInst.getProcessDefName())) {
                        // 本单位部门会签
                        // 记录类型1,本单位填写记录；2,非本单位填写记录
                        tIssueAssistCondition.setRecordType("1");
                    } else if ("com.tower.issue.assist.statemeet".equals(wfProcessInst.getProcessDefName())) {
                        // 总包送省分会签
                        // 记录类型1,本单位填写记录；2,非本单位填写记录
                        tIssueAssistCondition.setRecordType("2");
                    } else if ("com.tower.issue.assist.citymeet".equals(wfProcessInst.getProcessDefName())) {
                        // 省分送地市会签
                        // 记录类型1,本单位填写记录；2,非本单位填写记录
                        tIssueAssistCondition.setRecordType("2");
                    }
                    WFActivityInst wfActivityInst1 = flowUtil.getClient().getActivityInstManager().queryActivityInstsByProcessInstID(subProInstId, null).get(1);
                    // 共有数据
                    // 主键id
                    tIssueAssistCondition.setId(StringUtil.getId());
                    // 流程实例id
                    tIssueAssistCondition.setFlowId(subProInstId + "");
                    // 活动实例id
                    tIssueAssistCondition.setActInstId(wfActivityInst1.getActivityInstID() + "");
                    // 活动定义id
                    tIssueAssistCondition.setActId(wfActivityInst1.getActivityDefID());
                    // 协助执行单id
                    tIssueAssistCondition.setAssistId(arg.gettFlowLog().getApproveItemId());

                    // 执行人id
                    tIssueAssistCondition.setAccountId(accountLogic.getAccountId());
                    // 执行人名称
                    tIssueAssistCondition.setAccountName(accountLogic.getAccountName());

                    // 执行单位id
                    tIssueAssistCondition.setJointlyUnitId(accountLogic.getAccountUnitId());
                    // 执行单位名称
                    tIssueAssistCondition.setJointlyUnitName(accountLogic.getCompanyName());
                    // 承办部门id
                    tIssueAssistCondition.setJointlyDeptId(accountLogic.getAccountOrgId());
                    // 承办部门名称
                    tIssueAssistCondition.setJointlyDeptName(accountLogic.getDeptName());

                    // 执行进展
                    tIssueAssistCondition.setState("执行中");
                    i += tIssueAssistConditionMapper.insertCondition(tIssueAssistCondition);
                }
            }
        } catch (WFServiceException e) {
            e.printStackTrace();
        }
        return i;
    }
}
