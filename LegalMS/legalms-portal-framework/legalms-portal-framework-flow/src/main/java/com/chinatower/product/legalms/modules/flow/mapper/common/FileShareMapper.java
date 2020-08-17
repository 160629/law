package com.chinatower.product.legalms.modules.flow.mapper.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinatower.product.legalms.modules.flow.entity.common.FileShareVO;

/**
 * @author 刘晓亮
 * @create 2019-10-23 11:02
 */
public interface FileShareMapper {
    int insertFileList(@Param("fileList") List<FileShareVO> fileList);
    List<FileShareVO> selectFileList(@Param("businessId") String businessId);

    int updateFileList(@Param("fileList")List<FileShareVO> fileList);

    void deleteByBusinessId(@Param("businessId") String businessId);
}
