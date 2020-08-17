package com.chinatower.product.legalms.modules.cases.mapper;

import com.chinatower.product.legalms.modules.cases.entity.RulingExecutiveVO;
import org.apache.ibatis.annotations.Param;

public interface RulingExecutiveMapper {
    void addRulingExecutive(RulingExecutiveVO rulingExecutiveVO);

    RulingExecutiveVO selectRulingExecutiveById(@Param("caseId") String caseId);

    void updateRulingExecutive(RulingExecutiveVO rulingExecutiveVO);

    int selectAllRulingExecutiveByCount(String caseId);
}
