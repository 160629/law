package com.chinatower.product.legalms.modules.cases.mapper;

import com.chinatower.product.legalms.modules.cases.entity.SecondInstanceVO;
import org.apache.ibatis.annotations.Param;

public interface SecondInstanceMapper {
    void addSecondInstance(SecondInstanceVO secondInstanceVO);

    SecondInstanceVO selectSecondInstanceById(@Param("caseId") String caseId);

    void updateSecondInstance(SecondInstanceVO secondInstanceVO);

    int selectAllSecondInstanceByCount(String caseId);

    int deleteSecondInstance(@Param("caseId") String caseId,@Param("status") String status);
}
