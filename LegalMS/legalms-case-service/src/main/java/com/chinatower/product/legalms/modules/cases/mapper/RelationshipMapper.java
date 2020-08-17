package com.chinatower.product.legalms.modules.cases.mapper;

import com.chinatower.product.legalms.modules.cases.entity.RelationshipVO;
import com.chinatower.product.legalms.modules.cases.issue.ListTissueLawsuitParam;
import com.chinatower.product.legalms.modules.cases.issue.TIssueLawsuit;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RelationshipMapper {
    int addRelationship(RelationshipVO relationshipVO);

    int deleteRelationship(RelationshipVO relationshipVO);

    int deleteRelationships(@Param("relationshipVOS") List<RelationshipVO> relationshipVOS);

    List<RelationshipVO> selectRelationship(RelationshipVO relationshipVO);

    int selectRelationshipById(RelationshipVO relationshipVO);

    int addRelationships(@Param("relationshipVOS") List<RelationshipVO> relationshipVOS);

    int isLawsuitAutoGuide(@Param("relationshipVO") RelationshipVO relationshipVO);

    int isLawsuitAutoLawsuit(@Param("relationshipVO") RelationshipVO relationshipVO);

    int updateRelationship(@Param("relationshipVO") RelationshipVO relationshipVO);

    List<TIssueLawsuit> selectAll(ListTissueLawsuitParam param);

    RelationshipVO selectRelationshipByIds(@Param("businessId")String businessId, @Param("oldCaseId")String oldCaseId);

    List<RelationshipVO> selectRelationshipByCaseIds(@Param("oldCaseId") String oldCaseId);

}
