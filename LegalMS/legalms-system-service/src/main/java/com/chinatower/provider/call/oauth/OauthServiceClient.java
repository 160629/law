package com.chinatower.provider.call.oauth;

import com.chinatower.framework.cloud.feignlog.FeignConfiguration;
import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.fallback.OauthServiceClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Administrator on 2019/9/18.
 */

@FeignClient(name = "inner-gateway",url = "${gateway.url}",fallbackFactory = OauthServiceClientFallback.class, configuration = FeignConfiguration.class)
public interface OauthServiceClient {
    /**
     * 功能描述:统一凭证校验
     * @auther: GD
     * @param args
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @date: 2019/10/10 11:38
     */
    @GetMapping(value = "/WS4A/token/checkBwdaToken")
    ProcessResult checkBwdaToken(@RequestParam(value = "args") String args);

    /**
     * 功能描述:统一凭证申请
     * @auther: GD
     * @param args
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @date: 2019/10/10 11:39
     */
    @GetMapping(value = "WS4A/token/createBwdaToken")
    ProcessResult createBwdaToken(@RequestParam(value = "args") String args);



}
