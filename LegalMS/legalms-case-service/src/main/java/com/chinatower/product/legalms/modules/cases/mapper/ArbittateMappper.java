package com.chinatower.product.legalms.modules.cases.mapper;

import com.chinatower.product.legalms.modules.cases.entity.ArbittateVO;
import org.apache.ibatis.annotations.Param;

public interface ArbittateMappper {
    void addArbittate(ArbittateVO arbittateVO);

    ArbittateVO selectArbittateById(@Param("caseId") String caseId);

    void updateArbittate(ArbittateVO arbittateVO);

    int selectAllArbittateVOByCount(String caseId);

    int deleteArbittate(@Param("caseId") String caseId, @Param("status") String status);
}
