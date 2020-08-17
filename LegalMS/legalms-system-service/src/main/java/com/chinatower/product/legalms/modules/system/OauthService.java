package com.chinatower.product.legalms.modules.system;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.BuildEncodeMsg;
import com.chinatower.product.legalms.modules.system.api.OauthServiceAPI;
import com.chinatower.provider.call.oauth.OauthServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 认证接口
 */
@RestController
@Slf4j
public class OauthService implements OauthServiceAPI {

    @Autowired
    private OauthServiceClient serviceClient;


    /**
     * 功能描述:凭证校验接口
     *
     * @param request
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @auther: GD
     * @date: 2019/10/10 11:39
     */

    public ProcessResult checkToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        String loginName = request.getHeader("loginName");
        String encode = BuildEncodeMsg.getCheckEncode(token, loginName);
        return serviceClient.checkBwdaToken(encode);
    }

    /**
     * 功能描述:凭证申请接口
     *
     * @param request
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @auther: G D
     * @date: 2019/10/10 11:40
     */
    public ProcessResult createToken(HttpServletRequest request) {
        String accessIpAddr = request.getHeader("accessIpAddr");
        String loginAcct = request.getHeader("loginAcct");
        String encode = BuildEncodeMsg.getCreateEncode(loginAcct, accessIpAddr);
        return serviceClient.createBwdaToken(encode);
    }

    /**
     * 获取加密后的数据 （用于请求4a获取token）
     *
     * @param request
     * @return
     */
    public ProcessResult getEncode(HttpServletRequest request) {
        String accessIpAddr = request.getHeader("accessIpAddr");
        String loginAcct = request.getHeader("loginAcct");
        String encode = BuildEncodeMsg.getCreateEncode(loginAcct, accessIpAddr);
        return new ProcessResult(ProcessResult.SUCCESS, "成功",encode);
    }
}
