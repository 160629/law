package com.chinatower.product.legalms.modules.cases.service.impl;

import com.chinatower.product.legalms.modules.cases.entity.TFlowMain;
import com.chinatower.product.legalms.modules.cases.mapper.TFlowMainMapper;
import com.chinatower.product.legalms.modules.cases.service.TFlowMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TFlowMainServiceImpl implements TFlowMainService {

    @Autowired
    private TFlowMainMapper tFlowMainMapper;

    @Override
    public String selectFlowMainById(String businessId, String businessType) {
        return tFlowMainMapper.selectFlowMainById(businessId, businessType);
    }

    @Override
    public TFlowMain selectFlowMainByBusinessId(String businessId) {
        return tFlowMainMapper.selectFlowMainByBusinessId(businessId);
    }
}
