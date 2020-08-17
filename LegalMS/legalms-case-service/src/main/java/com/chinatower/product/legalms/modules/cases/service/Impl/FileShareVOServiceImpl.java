package com.chinatower.product.legalms.modules.cases.service.impl;

import com.chinatower.product.legalms.modules.cases.entity.FileMainVO;
import com.chinatower.product.legalms.modules.cases.entity.FileShareVO;
import com.chinatower.product.legalms.modules.cases.mapper.FileShareVOMapper;
import com.chinatower.product.legalms.modules.cases.service.FileShareVOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileShareVOServiceImpl implements FileShareVOService {
    @Autowired
    private FileShareVOMapper fileShareVOMapper;

    @Override
    public void insertFileShare(FileShareVO fileShareVO) {
        fileShareVOMapper.insertFileShare(fileShareVO);
    }

    @Override
    public List<FileMainVO> selectFileById(String caseId,String shareType) {
        return fileShareVOMapper.selectFileById(caseId,shareType);
    }

    @Override
    public int deleteByCase(String caseId, String shareType) {
        return fileShareVOMapper.deleteByCase(caseId,shareType);
    }
}
