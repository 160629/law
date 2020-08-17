package com.chinatower.product.legalms.fallback;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.provider.call.license.LicenseServiceClient;
import feign.hystrix.FallbackFactory;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Auther: G D
 * @Date: 2019/10/12 10:54
 * @Description:
 */
@Component
public class LicenseServiceClientFallback implements FallbackFactory<LicenseServiceClient> {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("TransLog");
    @Override
    public LicenseServiceClient create(Throwable throwable) {
        return new LicenseServiceClient() {
            @Override
            public ProcessResult selectOrgTree(Map map) {
                logger.error("请求异常，进入fallback,异常信息{}", throwable.getMessage());
                return new ProcessResult(ProcessResult.BUZ_EXCEPTION, throwable.getMessage());
            }
        };
    }
}
