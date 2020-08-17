package com.chinatower.product.legalms.modules.cases.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.cases.entity.RelationshipVO;
import com.chinatower.product.legalms.modules.cases.issue.ListTissueLawsuitParam;
import org.springframework.web.bind.annotation.PostMapping;

public interface RelationshipApi {


    /**
     * 关联创建
     *
     */
    @PostMapping("/case/addRelationship")
    public ProcessResult addRelationship(RelationshipVO relationshipVO);


    /**
     * 关联删除
     *
     */
    @PostMapping("/case/deleteRelationship")
    public ProcessResult deleteRelationship(RelationshipVO relationshipVO);

    /**
     * 修改卷宗关联关系
     * @return
     */
    @PostMapping("/case/updateRelationship")
    public ProcessResult updateRelationshipByLawsuit(RelationshipVO relationshipVO);


    /**
     * 关联查询
     *
     */
    @PostMapping("/case/selectRelationship")
    public ProcessResult selectRelationship(RelationshipVO relationshipVO);

    @PostMapping("/case/selectTIssueLawsuitList")
    public ProcessResult selectTIssueLawsuitList(ListTissueLawsuitParam param);

    @PostMapping("/case/selectRelationshipByCaseId")
    public ProcessResult selectRelationshipByCaseId(String caseId);
}
