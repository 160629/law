package com.chinatower.product.legalms.modules.cases.service;

import com.chinatower.product.legalms.modules.cases.entity.FirstInstanceVO;

public interface FirstInstanceService {

    void addFirstInstance(FirstInstanceVO firstInstanceVO);

    FirstInstanceVO selectFirstInstance(String caseId);

    void updateFirstInstance(FirstInstanceVO firstInstanceVO);

    int selectAllFirstInstanceByCount(String caseId);

    int deleteFirstInstance(String caseId);
}
