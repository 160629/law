package com.chinatower.product.legalms.modules.system.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: G D
 * @Date: 2019/10/11 14:26
 * @Description:
 */
public interface QueryServiceAPI {


    /**
     * 功能描述:获取登录用户信息
     *
     * @param request
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @auther: GD
     * @date: 2019/10/10 11:40
     */

    @GetMapping(value = "/v1/query/selectLoginUserInfo")
    public ProcessResult selectLoginUserInfo(HttpServletRequest request);

    /**
     * 功能描述:登录用户获取菜单拥有的接口
     *
     * @param request
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @auther: GD
     * @date: 2019/10/10 11:40
     */
    @GetMapping(value = "/v1/query/selectButtons")
    public ProcessResult selectButtons(HttpServletRequest request);


}
