package com.chinatower.product.legalms.fallback;

import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.chinatower.product.legalms.vo.CacheModel;
import com.chinatower.provider.call.oauth.RedisServiceClient;

import feign.hystrix.FallbackFactory;
@Component
public class RedisServiceClientFallback implements FallbackFactory<RedisServiceClient>{
	 private static final org.slf4j.Logger logger = LoggerFactory.getLogger("TransLog");

	    @Override
	    public RedisServiceClient create(Throwable cause) {
	        return new RedisServiceClient() {
	            @Override
	            public Map<String ,Object> query(CacheModel cacheModel) {
	                logger.error("请求异常，redis的query方法进入fallback,异常信息{}",cause.getMessage());
	                return null;
	            }
	            @Override
	            public Map<String ,Object> insert(CacheModel cacheModel) {
	                logger.error("请求异常，redis的insert方法进入fallback,异常信息{}",cause.getMessage());
	                return null;
	            }
	            @Override
	            public Map<String ,Object> update(CacheModel cacheModel) {
	                logger.error("请求异常，redis的update方法进入fallback,异常信息{}",cause.getMessage());
	                return null;
	            }
	            @Override
	            public Map<String ,Object> del(CacheModel cacheModel) {
	                logger.error("请求异常，redis的del方法进入fallback,异常信息{}",cause.getMessage());
	                return null;
	            }
	        };

	    }
	}