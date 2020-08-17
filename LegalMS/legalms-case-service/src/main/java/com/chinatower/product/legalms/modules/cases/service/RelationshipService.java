package com.chinatower.product.legalms.modules.cases.service;

import com.chinatower.product.legalms.modules.cases.entity.RelationshipVO;
import com.chinatower.product.legalms.modules.cases.issue.ListTissueLawsuitParam;
import com.chinatower.product.legalms.modules.cases.issue.TIssueLawsuit;

import java.util.List;

public interface RelationshipService {
    int addRelationship(RelationshipVO relationshipVO);

    int deleteRelationship(RelationshipVO relationshipVO);

    List<RelationshipVO> selectRelationship(RelationshipVO relationshipVO);

    int updateRelationship(RelationshipVO relationshipVO);

    List<TIssueLawsuit> selectAll(ListTissueLawsuitParam param);

    int updateRelationshipByLawsuit(RelationshipVO relationshipVO);

    List<RelationshipVO> selectRelationshipByCaseIds(String oldCaseId);

    int updateRelationshipsCaseId(List<RelationshipVO> relationshipVOS, String newCaseId);
}
