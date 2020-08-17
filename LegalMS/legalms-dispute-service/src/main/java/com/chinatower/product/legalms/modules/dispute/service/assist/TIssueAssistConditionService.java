package com.chinatower.product.legalms.modules.dispute.service.assist;

import com.chinatower.product.legalms.common.FlowUtil;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.dispute.entity.assist.TIssueAssistCondition;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowLogVO;

import java.util.List;

public interface TIssueAssistConditionService {
    /**
     * 新增或更新执行情况, 审批中执行
     * @param vo
     * @param info
     * @return
     */
    int insertAssistCondition(AddFlowLogVO vo, UserInfo info, FlowUtil flowUtil);

    /**
     * 新增执行记录，点击添加删除关联关系
     * @param tIssueAssistCondition
     * @return
     */
    int insertAssistCondition(TIssueAssistCondition tIssueAssistCondition);

    /**
     * 执行情况查询
     * @param info
     * @param recordType
     * @param id
     * @return
     */
    List<TIssueAssistCondition> selectConditionByIds(UserInfo info, String recordType, String id);

    TIssueAssistCondition selectConditionById(String conditionId);

    int updateRelationIdById(String conditionId, String assistId);
}
