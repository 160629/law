package com.chinatower.product.legalms.modules.filecommon.mapper.filecommon;

import com.chinatower.product.legalms.modules.filecommon.vo.filecommon.FileShareVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileShareMapper {
    void insertFileShare(FileShareVO fileShareVO);

    int delectFileShare(FileShareVO fileShareVO);

    int delectShareArr(@Param("delIds") String[] delIds);

    int addshareVos(@Param("shareVos") List<FileShareVO> shareVos);
}
