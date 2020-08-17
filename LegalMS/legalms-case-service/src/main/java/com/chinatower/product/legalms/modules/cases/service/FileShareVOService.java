package com.chinatower.product.legalms.modules.cases.service;

import com.chinatower.product.legalms.modules.cases.entity.FileMainVO;
import com.chinatower.product.legalms.modules.cases.entity.FileShareVO;

import java.util.List;

public interface FileShareVOService {
    void insertFileShare(FileShareVO fileShareVO);

    List<FileMainVO> selectFileById(String caseId,String shareType);

    int deleteByCase(String caseId, String shareType);

}
