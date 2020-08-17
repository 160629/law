package com.chinatower.product.legalms.modules.cases.service.impl;

import com.chinatower.product.legalms.common.CaseInfo;
import com.chinatower.product.legalms.modules.cases.entity.PunishVO;
import com.chinatower.product.legalms.modules.cases.mapper.CaseMainMappper;
import com.chinatower.product.legalms.modules.cases.mapper.PunishMapper;
import com.chinatower.product.legalms.modules.cases.service.FileShareVOService;
import com.chinatower.product.legalms.modules.cases.service.PunishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
public class PunishServiceImpl implements PunishService {
    @Autowired
    private PunishMapper punishMapper;

    @Autowired
    private CaseMainMappper caseMainMappper;

    @Autowired
    private FileShareVOService fileShareVOService;


    @Override
    @Transactional
    public void addPunish(PunishVO punishVO) {
        long timestamp = System.currentTimeMillis();
        String nonce = String.valueOf(new Random().nextInt(10));
        String id = CaseInfo.ID_HEAD_PUNISH+timestamp+nonce;
        punishVO.setPunishId(id);
/*        if(StringUtils.isNotBlank(punishVO.getAdministrativePenaltyAward())) {
            String shareid = UUID.randomUUID().toString().replaceAll("-", "") + new Random().nextLong();
            FileShareVO fileShareVO = new FileShareVO();
            fileShareVO.setFileShareBusinessKey(id);
            fileShareVO.setFileId(punishVO.getAdministrativePenaltyAward());
            fileShareVO.setFileShareId(shareid);
            fileShareVOService.insertFileShare(fileShareVO);
        }*/
/*        if(StringUtils.isNotBlank(punishVO.getAdministrativeReconsiderAward())) {
            String shareid = UUID.randomUUID().toString().replaceAll("-", "") + new Random().nextLong();
            FileShareVO fileShareVO = new FileShareVO();
            fileShareVO.setFileShareBusinessKey(id);
            fileShareVO.setFileId(punishVO.getAdministrativeReconsiderAward());
            fileShareVO.setFileShareId(shareid);
            fileShareVOService.insertFileShare(fileShareVO);
        }*/
        punishMapper.addPunish(punishVO);
        String caseId = punishVO.getCaseId();
        caseMainMappper.updateLastTime(caseId);
    }

    @Override
    public PunishVO selectPunish(String caseId) {
        return punishMapper.selectPunishById(caseId);
    }

    @Override
    @Transactional
    public void updatePunish(PunishVO punishVO) {
        punishMapper.updatePunish(punishVO);
        String caseId = punishVO.getCaseId();
        caseMainMappper.updateLastTime(caseId);
    }

    @Override
    public int selectAllPunishByCount(String caseId) {
        return punishMapper.selectAllPunishByCount(caseId);
    }

    @Override
    public int deletePunish(String caseId) {
        String status = CaseInfo.D_STATUS;
        fileShareVOService.deleteByCase(caseId, "punish");
        return punishMapper.deletePunish(caseId,status);
    }
}
