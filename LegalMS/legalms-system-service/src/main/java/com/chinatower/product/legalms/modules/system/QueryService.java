package com.chinatower.product.legalms.modules.system;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.BuildEncodeMsg;
import com.chinatower.product.legalms.modules.system.api.QueryServiceAPI;
import com.chinatower.provider.call.oauth.QueryServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 查询接口
 */
@RestController
@Slf4j
public class QueryService implements QueryServiceAPI {

    @Autowired
    private QueryServiceClient queryServiceClient;

    /**
     * 功能描述:获取登录用户信息
     *
     * @param request
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @auther: GD
     * @date: 2019/10/10 11:40
     */

    public ProcessResult selectLoginUserInfo( HttpServletRequest request) {
        String loginAccount = request.getHeader("loginAccount");
        String token = request.getHeader("token");
        String encode  = BuildEncodeMsg.getQueryEncode(token,loginAccount);
        return queryServiceClient.queryLoginUserInfo(encode);
    }

    /**
     * 功能描述:登录用户获取菜单拥有的接口
     *
     * @param request
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @auther: GD
     * @date: 2019/10/10 11:40
     */
    public ProcessResult selectButtons(HttpServletRequest request) {
        String loginAccount = request.getHeader("loginAccount");
        String token = request.getHeader("token");
        String menucode = request.getHeader("menucode");
        String encode = BuildEncodeMsg.getButtonsEncode(loginAccount, token, menucode);
        return queryServiceClient.queryButtons(encode);
    }


}
