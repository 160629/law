package com.chinatower.product.legalms.modules.cases.mapper;

import com.chinatower.product.legalms.modules.cases.entity.FirstInstanceVO;
import org.apache.ibatis.annotations.Param;

public interface FirstInstanceMapper {
    void addFirstInstance(FirstInstanceVO firstInstanceVO);

    FirstInstanceVO selectFirstInstanceById(@Param("caseId") String caseId);

    void updateFirstInstance(FirstInstanceVO firstInstanceVO);

    int selectAllFirstInstanceByCount(String caseId);

    int deleteFirstInstance(@Param("caseId") String caseId,@Param("status") String status);
}
