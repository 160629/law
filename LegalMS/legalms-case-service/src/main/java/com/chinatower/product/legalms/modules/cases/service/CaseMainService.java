package com.chinatower.product.legalms.modules.cases.service;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.cases.entity.CaseMainAutoVO;
import com.chinatower.product.legalms.modules.cases.entity.CaseMainVO;
import com.chinatower.product.legalms.modules.cases.entity.NewCaseListParam;
import com.chinatower.product.legalms.modules.cases.issue.ListParam;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface CaseMainService {
    PageInfo<CaseMainVO> selectAllCaseMain(ListParam param);

    ProcessResult selectAllCaseMains(ListParam param);

    int addCaseMain(CaseMainVO caseMainVO);

    CaseMainVO selectCaseMain(String caseId);

    String updateExplain(CaseMainVO caseMainVO);

    int updaterulingClassesById(String caseId,String rulingClasses, String caseReviewGrade);

    int updateCaseMain(CaseMainVO caseMainVO);

    int deleteCaseMainByIds(String caseId);

    String updateCaseStatusById(CaseMainVO caseMainVO);

    List<CaseMainVO> selectAllRelevanceCaseMain(ListParam param);

    PageInfo<CaseMainVO> selectAllCaseMainByDept(ListParam param);

    int selectAllCaseMainByCount(String caseId);

    int addAutoCaseMain(CaseMainAutoVO caseMainAutoVO);

    List<Map<String, Object>> getExcelDate();

    Map<String, String> getDictMap(String dictType);

    List<String> selectCode(String unitCode, String codeNull, String orgCode);

    int deleteCaseMainByIdsArray(List<CaseMainVO> caseMainVOS);

    int nullifyCaseByIdsArray(String oldCaseId, String newCaseId);

    List<CaseMainVO> selectNewCaseMainList(NewCaseListParam param);
}
