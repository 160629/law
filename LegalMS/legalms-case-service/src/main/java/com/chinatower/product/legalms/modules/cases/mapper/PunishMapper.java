package com.chinatower.product.legalms.modules.cases.mapper;

import com.chinatower.product.legalms.modules.cases.entity.PunishVO;
import org.apache.ibatis.annotations.Param;

public interface PunishMapper {
    void addPunish(PunishVO punishVO);

    PunishVO selectPunishById(@Param("caseId") String caseId);

    void updatePunish(PunishVO punishVO);

    int selectAllPunishByCount(String caseId);

    int deletePunish(@Param("caseId")String caseId, @Param("status")String status);
}
