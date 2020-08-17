package com.chinatower.product.legalms.modules.cases.service.impl;

import com.chinatower.product.legalms.common.CaseInfo;
import com.chinatower.product.legalms.modules.cases.entity.AgainInstanceVO;
import com.chinatower.product.legalms.modules.cases.mapper.AgainInstanceMapper;
import com.chinatower.product.legalms.modules.cases.mapper.CaseMainMappper;
import com.chinatower.product.legalms.modules.cases.service.AgainInstanceService;
import com.chinatower.product.legalms.modules.cases.service.FileShareVOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
public class AgainInstanceServiceImpl implements AgainInstanceService {
    @Autowired
    private AgainInstanceMapper againInstanceMapper;

    @Autowired
    private CaseMainMappper caseMainMappper;

    @Autowired
    private FileShareVOService fileShareVOService;

    @Override
    @Transactional
    public void addAgainInstance(AgainInstanceVO againInstanceVO) {
        long timestamp = System.currentTimeMillis();
        String nonce = String.valueOf(new Random().nextInt(10));
        String id = CaseInfo.ID_HEAD_AGAININSTANCE+timestamp+nonce;
        againInstanceVO.setZaishenId(id);
/*        if(StringUtils.isNotBlank(againInstanceVO.getAward())) {
            String shareid = UUID.randomUUID().toString().replaceAll("-", "") + new Random().nextLong();
            FileShareVO fileShareVO = new FileShareVO();
            fileShareVO.setFileShareBusinessKey(id);
            fileShareVO.setFileId(againInstanceVO.getAward());
            fileShareVO.setFileShareId(shareid);
            fileShareVOService.insertFileShare(fileShareVO);
        }*/
        againInstanceMapper.addAgainInstance(againInstanceVO);
        String caseId = againInstanceVO.getCaseId();
        caseMainMappper.updateLastTime(caseId);
    }

    @Override
    public AgainInstanceVO selectAgainInstance(String caseId) {
        return againInstanceMapper.selectAgainInstanceById(caseId);
    }

    @Override
    @Transactional
    public void updateAgainInstance(AgainInstanceVO againInstanceVO) {
        againInstanceMapper.updateAgainInstance(againInstanceVO);
        String caseId = againInstanceVO.getCaseId();
        caseMainMappper.updateLastTime(caseId);
    }

    @Override
    public int selectAllAgainInstanceByCount(String caseId) {
        return againInstanceMapper.selectAllAgainInstanceByCount(caseId);
    }

    @Override
    public int deleteAgainInstance(String caseId) {
        String status = CaseInfo.D_STATUS;
        fileShareVOService.deleteByCase(caseId, "againinstance");
        return againInstanceMapper.deleteAgainInstance(caseId,status);
    }
}
