package com.chinatower.product.legalms.modules.cases.service;

import com.chinatower.product.legalms.modules.cases.entity.PunishVO;

public interface PunishService {
    void addPunish(PunishVO punishVO);

    PunishVO selectPunish(String caseId);

    void updatePunish(PunishVO punishVO);

    int selectAllPunishByCount(String caseId);

    int deletePunish(String caseId);
}
