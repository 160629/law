package com.chinatower.product.legalms.modules.dispute.mapper.assist;

import com.chinatower.product.legalms.modules.dispute.entity.assist.TIssueAssistRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TIssueAssistRelationMapper {
    int insertRelation(@Param("tIssueAssistRelation") TIssueAssistRelation tIssueAssistRelation);

    int deleteRelation(@Param("tIssueAssistRelation") TIssueAssistRelation tIssueAssistRelation);

    List<TIssueAssistRelation> selectRelationByAssistPid(@Param("assistId") String assistId);
}
