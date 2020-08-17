package com.chinatower.product.legalms.modules.cases.service.impl;

import com.chinatower.product.legalms.common.CaseInfo;
import com.chinatower.product.legalms.modules.cases.entity.FirstInstanceVO;
import com.chinatower.product.legalms.modules.cases.mapper.CaseMainMappper;
import com.chinatower.product.legalms.modules.cases.mapper.FirstInstanceMapper;
import com.chinatower.product.legalms.modules.cases.service.FileShareVOService;
import com.chinatower.product.legalms.modules.cases.service.FirstInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
public class FirstInstanceServiceImpl implements FirstInstanceService {

    @Autowired
    private FirstInstanceMapper firstInstanceMapper;

    @Autowired
    private CaseMainMappper caseMainMappper;

    @Autowired
    private FileShareVOService fileShareVOService;

    @Override
    @Transactional
    public void addFirstInstance(FirstInstanceVO firstInstanceVO) {
        long timestamp = System.currentTimeMillis();
        String nonce = String.valueOf(new Random().nextInt(10));
        String id = CaseInfo.ID_HEAD_FIRSTINSTANCE+timestamp+nonce;
        firstInstanceVO.setYishenUuid(id);
     /*   if(StringUtils.isNotBlank(firstInstanceVO.getAward())) {
            String shareid = UUID.randomUUID().toString().replaceAll("-", "") + new Random().nextLong();
            FileShareVO fileShareVO = new FileShareVO();
            fileShareVO.setFileShareBusinessKey(id);
            fileShareVO.setFileId(firstInstanceVO.getCaseId());
            fileShareVO.setFileShareId(shareid);
            fileShareVOService.insertFileShare(fileShareVO);
        }*/
        firstInstanceMapper.addFirstInstance(firstInstanceVO);
        String caseId = firstInstanceVO.getCaseId();
        caseMainMappper.updateLastTime(caseId);
    }

    @Override
    public FirstInstanceVO selectFirstInstance(String caseId) {
        return firstInstanceMapper.selectFirstInstanceById(caseId);
    }

    @Override
    @Transactional
    public void updateFirstInstance(FirstInstanceVO firstInstanceVO) {
       firstInstanceMapper.updateFirstInstance(firstInstanceVO);
        String caseId = firstInstanceVO.getCaseId();
        caseMainMappper.updateLastTime(caseId);
    }

    @Override
    public int selectAllFirstInstanceByCount(String caseId) {
        return firstInstanceMapper.selectAllFirstInstanceByCount(caseId);
    }

    @Override
    public int deleteFirstInstance(String caseId) {
        String status = CaseInfo.D_STATUS;
        fileShareVOService.deleteByCase(caseId, "firstinstance");
        return firstInstanceMapper.deleteFirstInstance(caseId,status);
    }
}
