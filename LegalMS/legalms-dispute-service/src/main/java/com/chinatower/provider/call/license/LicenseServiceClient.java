package com.chinatower.provider.call.license;

import com.chinatower.framework.cloud.feignlog.FeignConfiguration;
import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.fallback.LicenseServiceClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @Auther: G D
 * @Date: 2019/10/12 10:46
 * @Description:
 */
@FeignClient(name = "legalms-license-service", fallbackFactory = LicenseServiceClientFallback.class, configuration = FeignConfiguration.class)
public interface LicenseServiceClient {

    @PostMapping("/v1/accountlogic/selectOrgTree")
    public ProcessResult selectOrgTree(@RequestBody Map map);


}
