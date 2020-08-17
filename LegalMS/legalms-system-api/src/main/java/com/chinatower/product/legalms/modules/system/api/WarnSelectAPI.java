package com.chinatower.product.legalms.modules.system.api;

import org.springframework.web.bind.annotation.PostMapping;

import com.chinatower.framework.common_service.response.ProcessResult;
/**
 * 
 * @author 23238
 *
 */
public interface WarnSelectAPI {
    @PostMapping("/warnSelect/selectBySql")
    ProcessResult selectBySql(String sql);
}
