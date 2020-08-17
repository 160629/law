package com.chinatower.product.legalms.modules.system;

import com.alibaba.fastjson.JSONObject;
import com.chinatower.framework.cloud.feignlog.InfoFeginLog;
import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.ThreeDESUtil;
import com.chinatower.provider.call.oauth.OauthServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 公共服务
 */
@RestController
@RequestMapping("/commonService")
@Slf4j
public class CommonService {

    @Autowired
    private OauthServiceClient serviceClient;


    @PostMapping(value = "/test")
    @ResponseBody
    public ProcessResult test(HttpServletRequest request) {
        String token = request.getHeader("token");
        String loginName = request.getHeader("loginName");
        JSONObject json = new JSONObject();
        json.put("TOKEN", token);
        json.put("SERVICEID", "CHNTLEGALMS");
        json.put("LOGINNAME", loginName);
        /** SENDCODE  SERVERCODE*/
        String encode = ThreeDESUtil.encode(json.toJSONString());
        /** mdc put的是esb给的码值,sendcode是发送方,servercode是服务提供方*/
        MDC.put(InfoFeginLog.LOG_SENDCODE,"124");
        MDC.put(InfoFeginLog.LOG_SERVERCODE,"124");
        return serviceClient.checkBwdaToken(encode);
    }

}
