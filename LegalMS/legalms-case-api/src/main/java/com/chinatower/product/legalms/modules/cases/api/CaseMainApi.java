package com.chinatower.product.legalms.modules.cases.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.cases.entity.CaseMainVO;
import com.chinatower.product.legalms.modules.cases.entity.NewCaseListParam;
import com.chinatower.product.legalms.modules.cases.issue.ListParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface CaseMainApi{
    /**
     * 查询列表
     */
    @PostMapping("/case/selCaseMain")
    public ProcessResult selCaseMain(ListParam param);

    /*移交卷宗查询*/
    @PostMapping("/case/selCaseMains")
    public ProcessResult selCaseMains(ListParam param);

    /**
     * 卷宗关联查询列表
     */
    @PostMapping("/case/selRelevanceCaseMain")
    public ProcessResult selRelevanceCaseMain(ListParam param);

/*    *//**
     * 卷宗法律文书，案件协办关联查询列表
     *//*
    @PostMapping("/case/selRelevanceCaseMains")
    public ProcessResult selRelevanceCaseMains(ListParam param);*/

    /**
     * 卷宗综合查询
     */
    @PostMapping("/case/selectAllCaseMainByDept")
    public ProcessResult selectAllCaseMainByDept(ListParam param);


    /**
     * 增加案宗
     */
    @PostMapping("/case/addCaseMain")
    public ProcessResult addCaseMain(CaseMainVO caseMainVO);

    /**
     * 查询案宗详情
     */
    @PostMapping("/case/selectCaseMain")
    public ProcessResult selectCaseMain(CaseMainVO caseMain);

    /**
     * 修改主表结案说明
     */
    @PostMapping("/case/updateExplain")
    public ProcessResult updateExplain(CaseMainVO caseMainVO);

    /**
     * 修改主表裁决类别
     */
    @PostMapping("/case/updaterulingClasses")
    public ProcessResult updaterulingClassesById(CaseMainVO caseMainVO);

    /**
     * 修改卷宗主表
     */
    @PostMapping("/case/updateCaseMain")
    public ProcessResult updateCaseMain(CaseMainVO caseMainVO);

    /*
     * 删除卷宗主表
     */
    @PostMapping("/case/deleteCaseMain")
    public ProcessResult deleteCaseMain(CaseMainVO caseMainVO);

    @PostMapping("/case/deleteCaseMains")
    public ProcessResult deleteCaseMains(List<CaseMainVO> caseMainVOS);

    /**
     * 修改卷宗状态
     */
    @PostMapping("/case/updateCaseStatus")
    public ProcessResult updateCaseStatus(CaseMainVO caseMainVO);


    /**
     * 创建或修改卷宗
     */
    @PostMapping("/case/addOrUpdateCaseMain")
    public ProcessResult addOrUpdateCaseMain(CaseMainVO caseMainVO);

    /**.
     * 自动创建创建卷宗
     */
    @PostMapping("/case/addAutoCaseMain")
    public ProcessResult addAutoCaseMain(String json);

    /**
     * 案件导出
     * */
    @GetMapping("/case/test")
    void test(HttpServletResponse response) throws IOException;

    /**
     * 作废接口
     * @param params
     * @return
     */
    @PostMapping("/case/nullifyCase")
    public ProcessResult nullifyCase(String params);

    /**
     * 作废后新卷宗列表查询
     * @param param
     * @return
     */
    @PostMapping("/case/selectNewCaseMainList")
    public ProcessResult selectNewCaseMainList(NewCaseListParam param);
}