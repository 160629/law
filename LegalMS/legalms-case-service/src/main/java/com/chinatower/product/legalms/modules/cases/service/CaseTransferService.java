package com.chinatower.product.legalms.modules.cases.service;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.cases.entity.CaseTransfer;

import java.util.List;
import java.util.Map;

/**
 * @Date: 2019/12/11 11:51
 * @Description:
 */
public interface CaseTransferService {


    ProcessResult selectCaseTransferInfo(CaseTransfer caseTransfer);

    ProcessResult addCaseTransferInfo(CaseTransfer caseTransfer);


    List<Map<String, Object>> getExcelDate();
}
