package com.chinatower.product.legalms.modules.flow.service.common.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinatower.product.legalms.modules.flow.entity.common.CaseMainVO;
import com.chinatower.product.legalms.modules.flow.mapper.common.CaseMainMapper;
import com.chinatower.product.legalms.modules.flow.service.common.CaseMainService;

/**
 * @author 刘晓亮
 * @create 2019-11-28
 */
@Service
public class CaseMainServiceImpl implements CaseMainService {

    @Autowired
    CaseMainMapper caseMainMapper;

    @Override
    public List<CaseMainVO> selectCase(String[] caseId) {
        return caseMainMapper.selectCase(caseId);
    }

    @Override
    public CaseMainVO selectCaseOne(String jointlyCase) {
        return caseMainMapper.selectCaseOne(jointlyCase);
    }
}
