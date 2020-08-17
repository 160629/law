package com.chinatower.product.legalms.modules.dispute.mapper.assist;

import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.dispute.entity.assist.TIssueAssistCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TIssueAssistConditionMapper {
    TIssueAssistCondition selectByIds(@Param("tIssueAssistCondition") TIssueAssistCondition tIssueAssistCondition);

    int updateById(@Param("tIssueAssistCondition") TIssueAssistCondition tIssueAssistCondition);

    int insertCondition(@Param("tIssueAssistCondition") TIssueAssistCondition tIssueAssistCondition);

    List<TIssueAssistCondition> selectConditionByIds(@Param("info") UserInfo info, @Param("recordType") String recordType, @Param("id") String id);

    TIssueAssistCondition selectByFlowId(@Param("flowId") String flowId);

    TIssueAssistCondition selectByActInstId(@Param("activityInstID") String activityInstID);


    int updateConditionState(@Param("flowId") String flowId, @Param("joinUnitIds") String[] joinUnitIds, @Param("state") String state);

    TIssueAssistCondition selectById(@Param("conditionId") String conditionId);

    int updateRelationIdById(@Param("conditionId") String conditionId, @Param("assistId") String assistId);


    TIssueAssistCondition selectByFlowIdAndJoinUnit(@Param("flowId") String flowId, @Param("joinUnitIds") String[] joinUnitIds);

}
