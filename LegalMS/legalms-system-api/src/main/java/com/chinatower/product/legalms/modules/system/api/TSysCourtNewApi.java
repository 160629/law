package com.chinatower.product.legalms.modules.system.api;

/**
 * @author shiyuqi
 * @date
 */

import com.chinatower.framework.common_service.response.ProcessResult;
import org.springframework.web.bind.annotation.PostMapping;
public interface TSysCourtNewApi {
    @PostMapping("/tSysCourtNew/readTxtFile")
    public ProcessResult readTxtFile();
}
