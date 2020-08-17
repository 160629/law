package com.chinatower.product.legalms.modules.cases.service;

import com.chinatower.product.legalms.modules.cases.entity.ArbittateVO;

public interface ArbittateService {
    void addArbittate(ArbittateVO arbittateVO);

    ArbittateVO selectArbittate(String caseId);

    void updateArbittate(ArbittateVO arbittateVO);

    int selectAllArbittateVOByCount(String caseId);

    int deleteArbittate(String caseId);
}
