package com.chinatower.product.legalms.modules.system.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.system.entity.SysQuickButtonVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface FlowContractAPI {
    @PostMapping(value = "v1/FlowContract/selectByRoleCode")
    public ProcessResult selectByRoleCode(@RequestBody SysQuickButtonVO sysQuickButtonVO);

    @GetMapping(value = "v1/FlowContract/selectQuickByUserId")
    public ProcessResult selectQuickByUserId(String userId );
}
