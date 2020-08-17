package com.chinatower.product.legalms.modules.dispute.service.assist.impl;

import com.chinatower.product.legalms.common.FlowUtil;
import com.chinatower.product.legalms.common.StringUtil;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.dispute.entity.assist.TIssueAssist;
import com.chinatower.product.legalms.modules.dispute.entity.assist.TIssueAssistCondition;
import com.chinatower.product.legalms.modules.dispute.mapper.assist.TIssueAssistConditionMapper;
import com.chinatower.product.legalms.modules.dispute.mapper.assist.TIssueAssistMapper;
import com.chinatower.product.legalms.modules.dispute.service.assist.TIssueAssistConditionService;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowLogVO;
import com.eos.workflow.data.WFProcessInst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("tIssueAssistConditionService")
public class TIssueAssistConditionServiceImpl implements TIssueAssistConditionService {
    // 日志对象
    private static final Logger logger = LoggerFactory.getLogger(TIssueAssistConditionServiceImpl.class);

    @Autowired
    TIssueAssistConditionMapper tIssueAssistConditionMapper;

    @Autowired
    TIssueAssistMapper tIssueAssistMapper;

    @Autowired
    FlowUtil flowUtil;

    @Override
    @Transactional
    public int insertAssistCondition(AddFlowLogVO vo, UserInfo info, FlowUtil flowUtil) {
        // 获取业务主表对象
        TIssueAssist tIssueAssist = tIssueAssistMapper.selectByPrimaryKey(vo.gettFlowLog().getApproveItemId());
        // 判断当前环节是否有添加or更新执行记录的权限
        // 查询当前流程实例
        WFProcessInst wfProcessInst = flowUtil.queryProcessInstDetail(vo.gettFlowLog().getFlowId());
        if ("finishActivity".equals(vo.gettFlowLog().getNextActivityDefId())) {
            // 如果送结束 ， 不做任何操作
            return 0;
        }
        // 本单位部门执行流程 (总部、省分、地市)
        if ("com.tower.issue.assist.meet".equals(wfProcessInst.getProcessDefName())
                // 非本单位执行
                // 省分送地市单位流程
                || "com.tower.issue.assist.citymeet".equals(wfProcessInst.getProcessDefName())
                // 总部送省分单位流程
                || "com.tower.issue.assist.statemeet".equals(wfProcessInst.getProcessDefName())
                // 省分流程 送 总部 执行
                || ("com.tower.issue.assist.state".equals(wfProcessInst.getProcessDefName())
                        && "zbfwrycl".equals(vo.gettFlowLog().getActivityDefId())
                        // 判断 起草单位 和 执行单位 上下级关系
                        && compareOrgLevelAndJoinUnitLevel(tIssueAssist.getOrgId(), tIssueAssist.getJointlyUnitId()) == 1)
                // 地市流程 送 省分 执行
                || ("com.tower.issue.assist.city".equals(wfProcessInst.getProcessDefName())
                        && "sffwrycl".equals(vo.gettFlowLog().getActivityDefId())
                        // 判断 起草单位 和 执行单位 上下级关系
                        && compareOrgLevelAndJoinUnitLevel(tIssueAssist.getOrgId(), tIssueAssist.getJointlyUnitId()) == 1)
                // 地市流程 送 总部执行
                || ("com.tower.issue.assist.city".equals(wfProcessInst.getProcessDefName())
                        && "zbfwrycl".equals(vo.gettFlowLog().getActivityDefId())
                        // 判断 起草单位 和 执行单位 上下级关系
                        && compareOrgLevelAndJoinUnitLevel(tIssueAssist.getOrgId(), tIssueAssist.getJointlyUnitId()) == 2))
        {
            Map<String, Object> businessMap = vo.getBusinessMap();
            // 根据 子流程实例id 确定是否存在 该省分或该部门 的执行反馈记录
            // 每个子流程实例对应一次送会签（本单位你部门or非本单位执行单位）
            TIssueAssistCondition tIssueAssistCondition = tIssueAssistConditionMapper.selectByFlowIdAndJoinUnit(vo.gettFlowLog().getFlowId() + "", tIssueAssist.getJointlyUnitId().split(","));
            // 存在：执行部门已经填写过核查情况等信息or到了财务部门审批，反正就是还在这一次的审批当中（某个单位or某个部门的一次审批）
            // 不存在：1、该部门或该单位第一次审批；2、该单位或该部门再一次审批q
            int i = 0;
            if (null == tIssueAssistCondition) {
                // 不存在：创建新执行情况记录对象，新增
                tIssueAssistCondition = new TIssueAssistCondition();
                // 共有数据
                // 主键id
                tIssueAssistCondition.setId(StringUtil.getId());
                // 流程实例id
                tIssueAssistCondition.setFlowId("" + businessMap.get("flowId"));
                // 父流程实例id
                tIssueAssistCondition.setFlowPid("" + businessMap.get("flowPid"));
                // 活动实例id
                tIssueAssistCondition.setActInstId("" + businessMap.get("actInstId"));
                // 父活动实例id
                tIssueAssistCondition.setActParentInstId("" + businessMap.get("actParentInstId"));
                // 活动定义id
                tIssueAssistCondition.setActId((String) businessMap.get("actId"));
                // 协助执行单id
                tIssueAssistCondition.setAssistId(vo.gettFlowLog().getApproveItemId());
                // 执行单位id
                tIssueAssistCondition.setJointlyUnitId(info.getOrgId());
                // 执行单位名称
                tIssueAssistCondition.setJointlyUnitName(info.getOrgName());
                // 执行人id
                tIssueAssistCondition.setAccountId(info.getLoginAcct());
                // 执行人名称
                tIssueAssistCondition.setAccountName(info.getLoginName());
                // 记录类型1,本单位填写记录；2,非本单位填写记录
                tIssueAssistCondition.setRecordType((String) businessMap.get("recordType"));
                // 本单位填写字段
                // 检查情况
                tIssueAssistCondition.setCheckContent((String) businessMap.get("checkContent"));
                // 财务确认情况
                tIssueAssistCondition.setFinanceContent((String) businessMap.get("financeContent"));
                // 反馈内容
                tIssueAssistCondition.setBusinessContent((String) businessMap.get("businessContent"));
                // 核查情况附件id列表
                tIssueAssistCondition.setCheckFile((String) businessMap.get("file_checkFile"));
                // 财务确认情况附件id列表
                tIssueAssistCondition.setFinanceFile((String) businessMap.get("file_financeFile"));
                // 反馈内容附件id列表
                tIssueAssistCondition.setBusinessFile((String) businessMap.get("file_businessFile"));
                // 非本单位填写字段
                // 执行情况
                tIssueAssistCondition.setAssistContent((String) businessMap.get("assistContent"));
                // 执行情况附件id列表
                tIssueAssistCondition.setAssistFile((String) businessMap.get("file_assistFile"));
                // 关联的协助执行单id列表
                tIssueAssistCondition.setAssistRelationIds((String) businessMap.get("assistRelationIds"));
                // 承办部门id
                tIssueAssistCondition.setJointlyDeptId(info.getDeptId());
                // 承办部门名称
                tIssueAssistCondition.setJointlyDeptName(info.getDeptName());
                // 执行进展
                tIssueAssistCondition.setState("执行中");
                // 新增
                i += tIssueAssistConditionMapper.insertCondition(tIssueAssistCondition);
            } else {
                // 存在：修改执行情况记录对象，更新
                // 执行情况
                tIssueAssistCondition.setAssistContent((String) businessMap.get("assistContent"));
                // 执行情况附件id列表
                tIssueAssistCondition.setAssistFile((String) businessMap.get("file_assistFile"));
                // 关联的协助执行单id列表
                tIssueAssistCondition.setAssistRelationIds((String) businessMap.get("assistRelationIds"));
                // 检查情况
                tIssueAssistCondition.setCheckContent((String) businessMap.get("checkContent"));
                // 财务确认情况
                tIssueAssistCondition.setFinanceContent((String) businessMap.get("financeContent"));
                // 反馈内容
                tIssueAssistCondition.setBusinessContent((String) businessMap.get("businessContent"));
                // 核查情况附件id列表
                tIssueAssistCondition.setCheckFile((String) businessMap.get("file_checkFile"));
                // 财务确认情况附件id列表
                tIssueAssistCondition.setFinanceFile((String) businessMap.get("file_financeFile"));
                // 反馈内容附件id列表
                tIssueAssistCondition.setBusinessFile((String) businessMap.get("file_businessFile"));
                // 如果时送上级（地市流程及省分流程送总部）要更新创建人信息及创建时间
                if (compareOrgLevelAndJoinUnitLevel(tIssueAssist.getOrgId(), tIssueAssist.getJointlyUnitId()) == 1 || compareOrgLevelAndJoinUnitLevel(tIssueAssist.getOrgId(), tIssueAssist.getJointlyUnitId()) == 2) {
                    // 创建时间
                    tIssueAssistCondition.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    // 创建人信息
                    // 承办部门id
                    tIssueAssistCondition.setJointlyDeptId(info.getDeptId());
                    // 承办部门名称
                    tIssueAssistCondition.setJointlyDeptName(info.getDeptName());
                    // 执行进展
                    tIssueAssistCondition.setState("执行中");
                    // 执行单位id
                    tIssueAssistCondition.setJointlyUnitId(info.getOrgId());
                    // 执行单位名称
                    tIssueAssistCondition.setJointlyUnitName(info.getOrgName());
                    // 执行人id
                    tIssueAssistCondition.setAccountId(info.getLoginAcct());
                    // 执行人名称
                    tIssueAssistCondition.setAccountName(info.getLoginName());
                    // 记录类型1,本单位填写记录；2,非本单位填写记录
                    tIssueAssistCondition.setRecordType((String) businessMap.get("recordType"));
                    // 活动实例id
                    tIssueAssistCondition.setActInstId("" + businessMap.get("actInstId"));
                }
                // 存在 更新
                i += tIssueAssistConditionMapper.updateById(tIssueAssistCondition);
            }
            return i;
        } else {
            logger.error("记录执行记录错误，该环节没有权限新增或删除执行记录权限.");
            logger.error("流程审批参数：" + vo.toString());
            logger.error("当前人信息：" + info.toString());
            return 0;
        }
    }

    /**
     * 判断 起草单位 和 执行单位 上下级关系
     * 送 上级 1直接上级，2上上级（地市送总部）
     * 送 下级 1直接下级
     * 送 本单位部门 0
     *
     * @param orgId
     * @param jointlyUnitId
     * @return
     */
    private int compareOrgLevelAndJoinUnitLevel(String orgId, String jointlyUnitId) {
        String jointlyUnitLevel = tIssueAssistMapper.selectOrgLevelByOrgId(jointlyUnitId);
        String orgLevel = tIssueAssistMapper.selectOrgLevelByOrgId(orgId);
        if (jointlyUnitId.indexOf(',') != -1) {
            // 送 下级
            return 1;
        } else if (orgLevel.compareTo(jointlyUnitLevel) > 0) {
            // 送 上级
            return Integer.parseInt(orgLevel) - Integer.parseInt(jointlyUnitLevel);
        } else if (orgLevel.compareTo(jointlyUnitLevel) < 0) {
            // 送 下级
            return 1;
        } else {
            // 送 本单位部门
            return 0;
        }
    }

    @Override
    public int insertAssistCondition(TIssueAssistCondition tIssueAssistCondition) {

        return 0;
    }

    @Override
    public List<TIssueAssistCondition> selectConditionByIds(UserInfo info, String recordType, String id) {
        return tIssueAssistConditionMapper.selectConditionByIds(info, recordType, id);
    }

    @Override
    public TIssueAssistCondition selectConditionById(String conditionId) {
        return tIssueAssistConditionMapper.selectById(conditionId);
    }

    @Override
    public int updateRelationIdById(String conditionId, String assistId) {
        return tIssueAssistConditionMapper.updateRelationIdById(conditionId, assistId);
    }
}
