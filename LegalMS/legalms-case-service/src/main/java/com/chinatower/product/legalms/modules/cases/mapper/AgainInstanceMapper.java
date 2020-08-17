package com.chinatower.product.legalms.modules.cases.mapper;

import com.chinatower.product.legalms.modules.cases.entity.AgainInstanceVO;
import org.apache.ibatis.annotations.Param;

public interface AgainInstanceMapper {
    void addAgainInstance(AgainInstanceVO againInstanceVO);

    AgainInstanceVO selectAgainInstanceById(@Param("caseId") String caseId);

    void updateAgainInstance(AgainInstanceVO againInstanceVO);

    int selectAllAgainInstanceByCount(String caseId);

    int deleteAgainInstance(@Param("caseId") String caseId, @Param("status") String status);
}
