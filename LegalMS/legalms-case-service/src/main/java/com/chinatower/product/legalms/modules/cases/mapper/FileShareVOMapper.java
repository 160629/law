package com.chinatower.product.legalms.modules.cases.mapper;


import com.chinatower.product.legalms.modules.cases.entity.FileMainVO;
import com.chinatower.product.legalms.modules.cases.entity.FileShareVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileShareVOMapper {
    void insertFileShare(FileShareVO fileShareVO);

    List<FileMainVO> selectFileById(@Param("caseId") String caseId,@Param("shareType") String shareType);

    int deleteByCase(@Param("caseId") String caseId,@Param("shareType") String shareType);

}
