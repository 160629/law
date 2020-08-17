package com.chinatower.product.legalms.modules.cases.service.impl;

import com.chinatower.product.legalms.common.CaseInfo;
import com.chinatower.product.legalms.modules.cases.entity.RulingExecutiveVO;
import com.chinatower.product.legalms.modules.cases.mapper.CaseMainMappper;
import com.chinatower.product.legalms.modules.cases.mapper.RulingExecutiveMapper;
import com.chinatower.product.legalms.modules.cases.service.RulingExecutiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
public class RulingExecutiveServiceImpl implements RulingExecutiveService {
    @Autowired
    private RulingExecutiveMapper rulingExecutiveMapper;

    @Autowired
    private CaseMainMappper caseMainMappper;

    @Override
    @Transactional
    public void addRulingExecutive(RulingExecutiveVO rulingExecutiveVO) {
        long timestamp = System.currentTimeMillis();
        String nonce = String.valueOf(new Random().nextInt(10));
        String id = CaseInfo.ID_RULING_EXECUTIVE+timestamp+nonce;
        rulingExecutiveVO.setRulingId(id);
        rulingExecutiveMapper.addRulingExecutive(rulingExecutiveVO);
        String caseId = rulingExecutiveVO.getCaseId();
        caseMainMappper.updateLastTime(caseId);
    }

    @Override
    public RulingExecutiveVO selectRulingExecutive(String caseId) {
        return rulingExecutiveMapper.selectRulingExecutiveById(caseId);
    }

    @Override
    @Transactional
    public void updateRulingExecutive(RulingExecutiveVO rulingExecutiveVO) {
            rulingExecutiveMapper.updateRulingExecutive(rulingExecutiveVO);
        String caseId = rulingExecutiveVO.getCaseId();
        caseMainMappper.updateLastTime(caseId);
    }

    @Override
    public int selectAllRulingExecutiveByCount(String caseId) {
        return rulingExecutiveMapper.selectAllRulingExecutiveByCount(caseId);
    }
}
