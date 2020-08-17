package com.chinatower.product.legalms.modules.filecommon.mapper.filecommon;

import com.chinatower.product.legalms.modules.filecommon.vo.filecommon.FileMainVO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface FileMainMapper extends Mapper<FileMainVO> {

    void insertFileMain(FileMainVO fileMainVO);

    int updatefilemain(FileMainVO fileMainVO);

    List<Map> selectAllfile(@Param("fileShareBusinessKey") String fileShareBusinessKey,@Param("shareType")String shareType,@Param("processInstId")String processInstId);

    int deleteFileCaseMainById(FileMainVO fileMainVO);
}
