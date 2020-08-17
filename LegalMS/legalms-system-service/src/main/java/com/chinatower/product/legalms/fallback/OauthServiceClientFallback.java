package com.chinatower.product.legalms.fallback;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.provider.call.oauth.OauthServiceClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2019/9/18.
 */
@Component
@Slf4j
public class OauthServiceClientFallback implements FallbackFactory<OauthServiceClient> {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("TransLog");

    @Override
    public OauthServiceClient create(Throwable cause) {
        return new OauthServiceClient() {
            @Override
            public ProcessResult checkBwdaToken(String args) {
             logger.error(SystemInfo.CHECK_TKOEN_ERROR,cause.getMessage());
             return new ProcessResult(ProcessResult.BUZ_EXCEPTION,cause.getMessage());
            }

            @Override
            public ProcessResult createBwdaToken(String args) {
                logger.error(SystemInfo.CREATE_TKOEN_ERROR,cause.getMessage());
                return new ProcessResult(ProcessResult.BUZ_EXCEPTION,cause.getMessage());
            }
        };
    }
}