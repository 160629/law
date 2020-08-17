package com.chinatower.provider.call.tcase;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.chinatower.framework.cloud.feignlog.FeignConfiguration;
import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.fallback.CaseServiceClientFallback;
import com.chinatower.product.legalms.modules.flow.vo.common.RelationshipVO;

/**
 * 
 * @author 23238
 *
 */
@FeignClient(name = "legalms-case-service", fallbackFactory = CaseServiceClientFallback.class, configuration = FeignConfiguration.class)
public interface CaseServiceClient {

    @PostMapping("/case/addAutoCaseMain")
    public ProcessResult addAutoCaseMain(@RequestBody String json, @RequestHeader(value = "pid")String pid);

    @PostMapping("/case/updateRelationship")
    public ProcessResult updateRelationship(@RequestBody RelationshipVO  relationshipVO);
}
