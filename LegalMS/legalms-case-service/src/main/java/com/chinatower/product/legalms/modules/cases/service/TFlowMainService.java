package com.chinatower.product.legalms.modules.cases.service;

import com.chinatower.product.legalms.modules.cases.entity.TFlowMain;

public interface TFlowMainService {
    String selectFlowMainById(String businessId, String businessType);

    TFlowMain selectFlowMainByBusinessId(String businessId);
}
