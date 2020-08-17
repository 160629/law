package com.chinatower.product.legalms.modules.filecommon.service.filecommon;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.filecommon.vo.filecommon.Filelist;

import java.util.List;
import java.util.Map;

public interface FilecommonService {
    public ProcessResult updateFile(List<Filelist> filelist, UserInfo userInfo, String primaryKey);

    Map<String, Object> test();
}
