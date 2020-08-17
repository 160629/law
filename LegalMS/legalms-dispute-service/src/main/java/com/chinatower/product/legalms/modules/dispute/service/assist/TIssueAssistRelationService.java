package com.chinatower.product.legalms.modules.dispute.service.assist;

import com.chinatower.product.legalms.modules.dispute.entity.assist.TIssueAssistRelation;

import java.util.List;

public interface TIssueAssistRelationService {
    int insertRelation(TIssueAssistRelation tIssueAssistRelation);

    int deleteRelation(TIssueAssistRelation tIssueAssistRelation);

    List<TIssueAssistRelation> selectRelationByAssistPid(String assistId);
}
