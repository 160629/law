package com.chinatower.product.legalms.fallback;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.provider.call.system.SystemServiceClient;

import feign.hystrix.FallbackFactory;

/**
 * @Auther: G D
 * @Date: 2019/10/11 11:28
 * @Description:
 */
@Component
public class SystemServiceClientFallback implements FallbackFactory<SystemServiceClient> {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("TransLog");
    @Override
    public SystemServiceClient create(Throwable throwable) {
        return new SystemServiceClient() {
            @Override
            public ProcessResult selectMsgByKey(String userKey) {
                logger.error("请求异常，进入fallback,异常信息{}", throwable.getMessage());
                return new ProcessResult(ProcessResult.BUZ_EXCEPTION, throwable.getMessage());
            }

        };
    }
}
