package com.chinatower.product.legalms.modules.system.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import org.springframework.web.bind.annotation.GetMapping;

public interface OrgBetweenAPI {

    /**
     * 通过父id对组织表进行查询其子节点
     */
    @GetMapping("/OrgBean/selOrgBean")
    public ProcessResult selectOrgBean(String orgCode);
}
