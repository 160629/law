package com.chinatower.product.legalms.modules.cases.service;

import com.chinatower.product.legalms.modules.cases.entity.SecondInstanceVO;

public interface SecondInstanceService {
    void addSecondInstance(SecondInstanceVO secondInstanceVO);

    SecondInstanceVO selectSecondInstance(String caseId);

    void updateSecondInstance(SecondInstanceVO secondInstanceVO);

    int selectAllSecondInstanceByCount(String caseId);

    int deleteSecondInstance(String caseId);
}
