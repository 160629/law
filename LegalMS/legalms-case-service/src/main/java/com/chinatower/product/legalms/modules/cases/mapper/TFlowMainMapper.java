package com.chinatower.product.legalms.modules.cases.mapper;

import com.chinatower.product.legalms.modules.cases.entity.TFlowMain;
import org.apache.ibatis.annotations.Param;

public interface TFlowMainMapper {
    public String selectFlowMainById(@Param("businessId") String businessId, @Param("businessType") String businessType);

    TFlowMain selectFlowMainByBusinessId(@Param("businessId") String businessId);
}
