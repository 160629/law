package com.chinatower.product.legalms.feignlocal;

import feign.RequestInterceptor;
import feign.RequestTemplate;

import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class FeignInterceptorLocal implements RequestInterceptor {

    /**
     * header add
     * @param requestTemplate
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        Collection<String> strings = requestTemplate.headers().get("Content-Type");
        requestTemplate.header("content-type",strings);
    }


}
