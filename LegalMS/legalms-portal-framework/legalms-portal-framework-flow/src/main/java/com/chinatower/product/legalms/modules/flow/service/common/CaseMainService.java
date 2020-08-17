package com.chinatower.product.legalms.modules.flow.service.common;

import java.util.List;

import com.chinatower.product.legalms.modules.flow.entity.common.CaseMainVO;


/**
 * @author 刘晓亮
 * @create 2019-11-28
 */
public interface CaseMainService {
    List<CaseMainVO> selectCase(String[] caseId);

    CaseMainVO selectCaseOne(String jointlyCase);
}
