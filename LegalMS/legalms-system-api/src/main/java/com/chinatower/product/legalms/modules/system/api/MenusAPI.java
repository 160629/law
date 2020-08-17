package com.chinatower.product.legalms.modules.system.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

public interface MenusAPI {
    /**
     * 菜单查询
     * @param request
     * @return
     */
    @PostMapping("/query/selMenus")
    ProcessResult selMenus(HttpServletRequest request);

}
