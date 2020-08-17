package com.chinatower.provider.call.oauth;

import com.chinatower.framework.cloud.feignlog.FeignConfiguration;
import com.chinatower.framework.common_service.response.ProcessResult;

import com.chinatower.product.legalms.fallback.SelServiceClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inner-gateway",url = "${gateway.url}", fallbackFactory = SelServiceClientFallback.class, configuration = FeignConfiguration.class)
public interface SelServiceClient {

    /**
     * 角色查询接口
     *
     * @param args
     * @return
     */
    @GetMapping(value = "/QUERY4A/query/serviceRoles")
    ProcessResult serviceRoles(@RequestParam(value = "args") String args);

    /**
     * 菜单列表查询接口
     *
     * @param args
     * @return
     */
    @GetMapping(value = "/QUERY4A/query/serviceMenus")
    ProcessResult serviceMenus(@RequestParam(value = "args") String args);

    /**
     * 角色-菜单关系查询接口
     *
     * @param args
     * @return
     */
    @GetMapping(value = "/QUERY4A/query/roleMenus")
    ProcessResult roleMenus(@RequestParam(value = "args") String args);

    /**
     *人员-角色关系查询接口
     *
     * @param args
     * @return
     */
    @GetMapping(value = "/QUERY4A/query/userRoles")
    ProcessResult userRoles(@RequestParam(value = "args") String args);
}
