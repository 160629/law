package com.chinatower.product.legalms.modules.flow.service.common;

import java.util.List;

import com.chinatower.product.legalms.modules.flow.entity.common.FileShareVO;


/**
 * @author 刘晓亮
 * @create 2019-10-23 10:07
 * 文件关系表
 */
public interface FileShareService {
    void insertFileList(String fileIds, String businessId, String userId);
    List<FileShareVO> selectFileList(String businessId);
    void updateFileList(String fileIds, String businessId, String userId);
}
