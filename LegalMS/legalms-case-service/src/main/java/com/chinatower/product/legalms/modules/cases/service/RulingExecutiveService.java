package com.chinatower.product.legalms.modules.cases.service;

import com.chinatower.product.legalms.modules.cases.entity.RulingExecutiveVO;

public interface RulingExecutiveService {
    void addRulingExecutive(RulingExecutiveVO rulingExecutiveVO);

    RulingExecutiveVO selectRulingExecutive(String caseId);

    void updateRulingExecutive(RulingExecutiveVO rulingExecutiveVO);

    int selectAllRulingExecutiveByCount(String caseId);
}
