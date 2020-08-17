package com.chinatower.product.legalms.modules.cases.mapper;

import com.chinatower.product.legalms.modules.cases.entity.*;
import com.chinatower.product.legalms.modules.cases.issue.ListParam;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CaseMainMappper {
    List<CaseMainVO> selectAllCaseMain(@Param("param") ListParam param, List<String> deptIds);

    List<CaseMainVO> selectAllCaseMains(@Param("param") ListParam param,@Param("loginAcct") String loginAcct);


    int addCaseMain(@Param("caseMainVO") CaseMainVO caseMainVO);

    CaseMainVO selectCaseMainById(@Param("caseId") String caseId);

    int updateExplain(@Param("caseId") String caseId,@Param("caseExplain") String caseExplain,
                      @Param("caseFinishWay")String caseFinishWay,@Param("caseFinishTime")Date caseFinishTime);

    int updaterulingClassesById(@Param("caseId") String caseId,@Param("rulingClasses") String rulingClasses, @Param("caseReviewGrade") String caseReviewGrade);

    int updateCaseMainById(CaseMainVO caseMainVO);

    int deleteCaseMainByIds(@Param("caseId") String caseId, @Param("deleteStatus") int deleteStatus);

    int updateCaseStatusById(@Param("caseId") String caseId,@Param("caseStatus") String caseStatus);

    void updateLastTime(@Param("caseId") String caseId);

    List<CaseMainVO> selectAllRelevanceCaseMain(@Param("param")ListParam param,@Param("orgId") String orgId);

    List<CaseMainVO> selectAllCaseMainByDeptA(@Param("unitCode")String unitCode,@Param("param") ListParam param);

    List<CaseMainVO> selectAllCaseMainByDeptB(@Param("orgId")String orgId,@Param("param") ListParam param);

    List<CaseMainVO> selectAllCaseMainByDeptC(@Param("orgId")String orgId,@Param("param") ListParam param);

    int selectAllCaseMainByCount(@Param("caseId")String caseId);

    int addAutoCaseMain(@Param("caseMainAutoVO") CaseMainAutoVO caseMainAutoVO);

    List<Map<String, String>> dictMaps(@Param("dictType") String dictType);

    List<Map<String, String>> getExcelDate();

    List<CaseMainVO> selectAllRelevanceCaseMains(@Param("param") ListParam param, @Param("loginAcct") String loginAcct);

    List<String> selectCode(@Param("unitCode") String unitCode,@Param("codeNull") String codeNull,@Param("orgCode") String orgCode);

    public OrgBeanVO selectOrgInfoByOrgCode(String deptId);

    public List<CaseMainVO> selectAllCase(@Param("param") ListParam param);

    int deleteCaseMainByIdsArray(@Param("caseMainVOS")List<CaseMainVO> caseMainVOS);

    List<CaseMainVO> selectAllRelevanceCaseMain2(@Param("param") ListParam param, @Param("orgId") String orgId);

    int updateInvolveBusinessTablesByRelationshipVOS(@Param("relationshipVOS") List<RelationshipVO> relationshipVOS, @Param("caseMainVO") CaseMainVO caseMainVO);

    List<CaseMainVO> selectNewCaseMainList(@Param("param")  NewCaseListParam param);
}
