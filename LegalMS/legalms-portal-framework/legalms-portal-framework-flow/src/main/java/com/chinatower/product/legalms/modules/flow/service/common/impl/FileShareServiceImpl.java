package com.chinatower.product.legalms.modules.flow.service.common.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinatower.product.legalms.common.CoreConstClass;
import com.chinatower.product.legalms.modules.flow.entity.common.FileShareVO;
import com.chinatower.product.legalms.modules.flow.mapper.common.FileShareMapper;
import com.chinatower.product.legalms.modules.flow.service.common.FileShareService;
import com.chinatower.product.legalms.modules.flow.vo.common.MyOwnRuntimeException;

import io.swagger.annotations.Api;


/**
 * @author 刘晓亮
 * @create 2019-10-23 10:15
 */
@Api("文件业务关系")
@Service
public class FileShareServiceImpl implements FileShareService {
	@Autowired
	FileShareMapper fileShareMapper;


    private static final Logger log = LoggerFactory.getLogger(CoreConstClass.TENANLID);


	@Override
	public void insertFileList(String fileIds, String businessId, String userId) {
		String fileStrs = fileIds;
		List<FileShareVO> fileList = new ArrayList<>();
		if (StringUtils.isNotBlank(fileStrs)) {
			List<String> fileIdStrList = new ArrayList<>(Arrays.asList(fileStrs.split(",")));
			for (int i = 0; i < fileIdStrList.size(); i++) {
				FileShareVO fileVO = new FileShareVO();
				String fileShareId = UUID.randomUUID().toString().replaceAll("-", "") + new Random().nextLong();
				fileVO.setFileShareId(fileShareId)
						.setFileId(fileIdStrList.get(i))
						.setFileOrder(Double.parseDouble(i + ""))
						.setFileType(3)
						.setFileShareBusinessKey(businessId)
						.setFileShareType("WORKDOC")
						.setFileShareStatusUpdUserId(userId)
						.setFileOrder(Double.parseDouble(i + ""))
						.setFileShareStatus("normal");
				fileList.add(fileVO);
			}
			// 存储附件
			int affectedFile = fileShareMapper.insertFileList(fileList);
			if (affectedFile < 0) {
				log.error(CoreConstClass.FILE_SAVE_FAIL, fileStrs);
				throw new MyOwnRuntimeException(CoreConstClass.FILE_SAVE_FAIL);
			}
		}
	}

	@Override
	public List<FileShareVO> selectFileList(String businessId) {
		return fileShareMapper.selectFileList(businessId);
	}

	@Override
	public void updateFileList(String fileIds, String businessId, String userId) {
		// 删除
		fileShareMapper.deleteByBusinessId(businessId);
		// 新增
		insertFileList(fileIds, businessId, userId);
	}
}
