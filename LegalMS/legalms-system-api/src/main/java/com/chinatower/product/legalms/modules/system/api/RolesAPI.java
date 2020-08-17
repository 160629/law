package com.chinatower.product.legalms.modules.system.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

public interface RolesAPI {

    /**
     * 角色查询
     * @param request
     * @return
     */
    @PostMapping("/query/selRoles")
    ProcessResult selRoles(HttpServletRequest request);
}
