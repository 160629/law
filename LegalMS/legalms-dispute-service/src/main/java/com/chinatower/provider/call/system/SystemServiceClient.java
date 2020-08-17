package com.chinatower.provider.call.system;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chinatower.framework.cloud.feignlog.FeignConfiguration;
import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.fallback.SystemServiceClientFallback;

/**
 * @Auther: GD
 * @Date: 2019/10/11 11:09
 * @Description:
 */
@FeignClient(name = "legalms-system-service", fallbackFactory = SystemServiceClientFallback.class, configuration = FeignConfiguration.class)
public interface SystemServiceClient {

    @GetMapping("/v1/msg/selectMsgByKey")
    public ProcessResult selectMsgByKey(@RequestParam("key") String key);

}
