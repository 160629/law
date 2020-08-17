package com.chinatower.product.legalms.modules.cases.service;

import com.chinatower.product.legalms.modules.cases.entity.AgainInstanceVO;

public interface AgainInstanceService {
    void addAgainInstance(AgainInstanceVO againInstanceVO);

    AgainInstanceVO selectAgainInstance(String caseId);

    void updateAgainInstance(AgainInstanceVO againInstanceVO);

    int selectAllAgainInstanceByCount(String caseId);

    int deleteAgainInstance(String caseId);
}
