package com.chinatower.product.legalms.modules.dispute.api.common;

import org.springframework.web.bind.annotation.GetMapping;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.flow.entity.common.OrgBeanVO;

public interface OrgBeanAPI {

    @GetMapping("/v1/baseorg/selectOrgLevel")
    public ProcessResult selectOrgLevelByorgCode(OrgBeanVO orgBeanVO);

}
