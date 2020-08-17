package com.chinatower.product.legalms.fallback;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.chinatower.provider.call.tcase.BraceServiceClient;

import feign.hystrix.FallbackFactory;

/**
 * 
 * @author 23238
 *
 */
@Component
public class BraceServiceClientFallback implements FallbackFactory<BraceServiceClient> {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(BraceServiceClientFallback.class);
    @Override
    public BraceServiceClient create(Throwable throwable) {
    	   return new BraceServiceClient() {
               @Override
               public String lawSum(JSONObject jsonObject) {
                   logger.info("lawSum请求异常，进入fallback,异常信息{}", throwable.getMessage());
                   return "lawSum请求异常";
               }

               @Override
               public String subjectMatter(JSONObject jsonObject) {
                   logger.info("subjectMatter请求异常，进入fallback,异常信息{}", throwable.getMessage());
                   return "subjectMatter请求异常";
               }
           };
    }

}
