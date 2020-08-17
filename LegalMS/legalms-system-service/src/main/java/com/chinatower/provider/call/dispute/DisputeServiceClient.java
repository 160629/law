package com.chinatower.provider.call.dispute;

import com.chinatower.framework.cloud.feignlog.FeignConfiguration;
import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.fallback.DisputeServiceClientFallback;
import com.chinatower.product.legalms.modules.system.entity.TaskVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Date: 2019/12/17 16:33
 * @Description:
 */
@FeignClient(name = "legalms-dispute-service", fallbackFactory = DisputeServiceClientFallback.class, configuration = FeignConfiguration.class)
public interface DisputeServiceClient {

    @PostMapping(value = "/taskList",consumes = "application/json")
    ProcessResult queryPersonWorkItems(@RequestBody TaskVO taskVO);

}
