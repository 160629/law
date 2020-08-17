package com.chinatower.product.legalms.modules.dispute.api.common;

import com.chinatower.framework.common_service.response.ProcessResult;
import org.springframework.web.bind.annotation.PostMapping;

public interface SysOrgareamapperAPI {
    @PostMapping("/v1/sysOrgareamapper/selectSysOrgareamapperByorgCode")
    public ProcessResult selectSysOrgareamapperByorgCode(String orgCode,String areaCode,String areaName);
}
