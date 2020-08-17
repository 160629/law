package com.chinatower.provider.call.oauth;

import java.util.Map;

import com.chinatower.framework.cloud.feignlog.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.chinatower.product.legalms.fallback.RedisServiceClientFallback;
import com.chinatower.product.legalms.vo.CacheModel;

@FeignClient(name = "codis", url = "${spring.redis.url}", fallbackFactory = RedisServiceClientFallback.class, configuration = FeignConfiguration.class)
public interface RedisServiceClient {
    /**
     * redis查询
     * @param cacheModel
     * @return
     */
    @PostMapping(value = "/query",consumes = "application/json")
    Map<String ,Object> query(@RequestBody CacheModel cacheModel);
    /**
     * redis插入
     * @param cacheModel
     * @return
     */
    @PostMapping(value = "/insert",consumes = "application/json")
    Map<String ,Object> insert(@RequestBody CacheModel cacheModel);

    /**
     * redis更新
     * @param cacheModel
     * @return
     */
    @PostMapping(value = "/update",consumes = "application/json")
    Map<String ,Object> update(@RequestBody CacheModel cacheModel);

    /**
     * redis删除
     * @param cacheModel
     * @return
     */
    @PostMapping(value = "/del",consumes = "application/json")
    Map<String ,Object> del(@RequestBody CacheModel cacheModel);
}
