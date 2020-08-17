package com.chinatower.product.legalms.modules.cases.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.cases.entity.CaseTransfer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Date: 2019/12/11 10:54
 * @Description:卷宗移交模块
 */
public interface CaseTransferApi {

    /**
     * 功能描述:移交卷宗记录查询
     * @auther: guodong
     * @param caseTransfer
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @date: 2020/1/3 14:50
     */
    @PostMapping("/v1/casetransfer/selectCaseTransferInfo")
    public ProcessResult selectCaseTransferInfo(CaseTransfer caseTransfer);


    /**
     * 功能描述:移交卷宗增加
     * @auther: guodong
     * @param caseTransfer
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @date: 2020/1/3 14:51
     */
    @PostMapping("/v1/casetransfer/addCaseTransferInfo")
    public ProcessResult addCaseTransferInfo(CaseTransfer caseTransfer);


    @GetMapping("/v1/casetransfer/getExcelDate")
    void getExcelDate(HttpServletResponse response) throws IOException;

}
