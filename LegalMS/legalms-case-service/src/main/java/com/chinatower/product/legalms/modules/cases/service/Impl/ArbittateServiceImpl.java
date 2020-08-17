package com.chinatower.product.legalms.modules.cases.service.impl;

import com.chinatower.product.legalms.common.CaseInfo;
import com.chinatower.product.legalms.modules.cases.entity.ArbittateVO;
import com.chinatower.product.legalms.modules.cases.mapper.ArbittateMappper;
import com.chinatower.product.legalms.modules.cases.mapper.CaseMainMappper;
import com.chinatower.product.legalms.modules.cases.service.ArbittateService;
import com.chinatower.product.legalms.modules.cases.service.FileShareVOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
public class ArbittateServiceImpl implements ArbittateService {

    @Autowired
    private ArbittateMappper arbittateMappper;

    @Autowired
    private CaseMainMappper caseMainMappper;

    @Autowired
    private FileShareVOService fileShareVOService;

    @Override
    @Transactional
    public void addArbittate(ArbittateVO arbittateVO) {
        long timestamp = System.currentTimeMillis();
        String nonce = String.valueOf(new Random().nextInt(10));
        String id = CaseInfo.ID_HEAD_ARBITTATE+timestamp+nonce;
        arbittateVO.setArbitrateId(id);
/*        if(StringUtils.isNotBlank(arbittateVO.getArbitralAward())) {
            String shareid = UUID.randomUUID().toString().replaceAll("-", "") + new Random().nextLong();
            FileShareVO fileShareVO = new FileShareVO();
            fileShareVO.setFileShareBusinessKey(id);
            fileShareVO.setFileId(arbittateVO.getArbitralAward());
            fileShareVO.setFileShareId(shareid);
            fileShareVOService.insertFileShare(fileShareVO);
        }
        System.out.println(arbittateVO.getCaseId());*/
        arbittateMappper.addArbittate(arbittateVO);
        String caseId = arbittateVO.getCaseId();
        caseMainMappper.updateLastTime(caseId);
    }

    @Override
    public ArbittateVO selectArbittate(String caseId) {
        return arbittateMappper.selectArbittateById(caseId);
    }

    @Override
    @Transactional
    public void updateArbittate(ArbittateVO arbittateVO) {
        arbittateMappper.updateArbittate(arbittateVO);
        String caseId = arbittateVO.getCaseId();
        caseMainMappper.updateLastTime(caseId);
    }

    @Override
    public int selectAllArbittateVOByCount(String caseId) {
        return arbittateMappper.selectAllArbittateVOByCount(caseId);
    }

    @Override
    public int deleteArbittate(String caseId) {
        String status = CaseInfo.D_STATUS;
        fileShareVOService.deleteByCase(caseId, "arbittate");
        return arbittateMappper.deleteArbittate(caseId,status);
    }
}
