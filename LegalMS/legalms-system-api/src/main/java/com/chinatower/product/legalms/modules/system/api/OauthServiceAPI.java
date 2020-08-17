package com.chinatower.product.legalms.modules.system.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: G D
 * @Date: 2019/10/11 14:27
 * @Description:
 */
public interface OauthServiceAPI {
    /**
     * 功能描述:凭证校验接口
     *
     * @param request
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @auther: GD
     * @date: 2019/10/10 11:39
     */

    @PostMapping(value = "v1/oauth/checkToken")
    public ProcessResult checkToken(HttpServletRequest request);

    /**
     * 功能描述:凭证申请接口
     *
     * @param request
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @auther: G D
     * @date: 2019/10/10 11:40
     */
    @PostMapping(value = "v1/oauth/createToken")
    public ProcessResult createToken(HttpServletRequest request);

    /**
     * 获取加密后的数据 （用于请求4a获取token）
     * @param request
     * @return
     */
    @PostMapping(value = "v1/oauth/getEncode")
    public ProcessResult getEncode(HttpServletRequest request);
}
