package com.chinatower.provider.call.flowpath;

import com.chinatower.framework.cloud.feignlog.FeignConfiguration;
import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.fallback.FlowServiceClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Date: 2019/12/20 10:26
 * @Description:
 */
@FeignClient(name = "legalms-dispute-service", fallbackFactory = FlowServiceClientFallback.class, configuration = FeignConfiguration.class)
public interface TFlowServiceClient {

    @PostMapping("/TFlowApi/autoUnView")
    public ProcessResult autoUnView(@RequestParam(value = "transferId") String transferId,
                                    @RequestParam(value = "transferUser") String transferUser,
                                    @RequestParam(value = "receptId") String receptId,
                                    @RequestParam(value = "receptUser") String receptUser,
                                    @RequestParam(value = "caseNum") Integer caseNum,
                                    @RequestParam(value = "cause") String cause);

    @PostMapping("/tIssueLawsuit/updateCaseId")
    public ProcessResult updateCaseId(@RequestParam(value = "caseId") String caseId, @RequestParam(value = "id") String id,
                                      @RequestParam(value = "caseTitle") String caseTitle, @RequestParam(value = "createCaseType", required = false) Integer createCaseType);
}
