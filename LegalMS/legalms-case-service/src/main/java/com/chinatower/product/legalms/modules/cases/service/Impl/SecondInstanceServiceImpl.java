package com.chinatower.product.legalms.modules.cases.service.impl;

import com.chinatower.product.legalms.common.CaseInfo;
import com.chinatower.product.legalms.modules.cases.entity.SecondInstanceVO;
import com.chinatower.product.legalms.modules.cases.mapper.CaseMainMappper;
import com.chinatower.product.legalms.modules.cases.mapper.SecondInstanceMapper;
import com.chinatower.product.legalms.modules.cases.service.FileShareVOService;
import com.chinatower.product.legalms.modules.cases.service.SecondInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
public class SecondInstanceServiceImpl implements SecondInstanceService {
    @Autowired
    private SecondInstanceMapper secondInstanceMapper;

    @Autowired
    private CaseMainMappper caseMainMappper;

    @Autowired
    private FileShareVOService fileShareVOService;

    @Override
    @Transactional
    public void addSecondInstance(SecondInstanceVO secondInstanceVO) {
        long timestamp = System.currentTimeMillis();
        String nonce = String.valueOf(new Random().nextInt(10));
        String id = CaseInfo.ID_HEAD_SECONDINSTANCE+timestamp+nonce;
        secondInstanceVO.setErshenId(id);
/*        if(StringUtils.isNotBlank(secondInstanceVO.getAward())) {
            String shareid = UUID.randomUUID().toString().replaceAll("-", "") + new Random().nextLong();
            FileShareVO fileShareVO = new FileShareVO();
            fileShareVO.setFileShareBusinessKey(id);
            fileShareVO.setFileId(secondInstanceVO.getAward());
            fileShareVO.setFileShareId(shareid);
            fileShareVOService.insertFileShare(fileShareVO);
        }*/
        secondInstanceMapper.addSecondInstance(secondInstanceVO);
        String caseId = secondInstanceVO.getCaseId();
        caseMainMappper.updateLastTime(caseId);
    }

    @Override
    public SecondInstanceVO selectSecondInstance(String caseId) {
        return secondInstanceMapper.selectSecondInstanceById(caseId);
    }

    @Override
    @Transactional
    public void updateSecondInstance(SecondInstanceVO secondInstanceVO) {
        secondInstanceMapper.updateSecondInstance(secondInstanceVO);
        String caseId = secondInstanceVO.getCaseId();
        caseMainMappper.updateLastTime(caseId);
    }

    @Override
    public int selectAllSecondInstanceByCount(String caseId) {
        return secondInstanceMapper.selectAllSecondInstanceByCount(caseId);
    }

    @Override
    public int deleteSecondInstance(String caseId) {
        String status = CaseInfo.D_STATUS;
        fileShareVOService.deleteByCase(caseId, "secondinstance");
        return secondInstanceMapper.deleteSecondInstance(caseId,status);
    }
}
