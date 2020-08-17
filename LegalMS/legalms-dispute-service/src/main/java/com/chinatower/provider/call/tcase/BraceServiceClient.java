package com.chinatower.provider.call.tcase;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.alibaba.fastjson.JSONObject;
import com.chinatower.framework.cloud.feignlog.FeignConfiguration;
import com.chinatower.product.legalms.fallback.BraceServiceClientFallback;


/**
 * 
 * @author 23238
 *
 */

@FeignClient(name = "inner-gateway",url = "${gateway.url}", fallbackFactory = BraceServiceClientFallback.class, configuration = FeignConfiguration.class)
public interface BraceServiceClient {

    @PostMapping("/CHINATOWER-MAIN-LAWBRACEPLAT-API/v1/chntlbp/lawSum")
    public String lawSum(@RequestBody JSONObject jsonObject);
    
    @PostMapping("/CHINATOWER-MAIN-LAWBRACEPLAT-API/v1/chntlbp/subjectMatter")
    public String subjectMatter(@RequestBody JSONObject jsonObject);


}
