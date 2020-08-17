package com.chinatower.provider.call.oauth;

import com.chinatower.framework.cloud.feignlog.FeignConfiguration;
import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.fallback.QueryServiceClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inner-gateway",url = "${gateway.url}", fallbackFactory = QueryServiceClientFallback.class, configuration = FeignConfiguration.class)
public interface QueryServiceClient {
    /**
     * 功能描述:获取登录用户信息
     * @auther: GD
     * @param args
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @date: 2019/10/10 11:39
     */
    @GetMapping(value = "/QUERY4A/user/queryLoginUserInfo")
    ProcessResult queryLoginUserInfo(@RequestParam(value = "args") String args);

    /**
     * 功能描述:登录用户获取菜单拥有的接口
     * @auther: GD
     * @param args
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @date: 2019/10/10 11:39
     */
    @GetMapping(value = "/QUERY4A/user/queryButtons")
    ProcessResult queryButtons(@RequestParam(value = "args") String args);

}
