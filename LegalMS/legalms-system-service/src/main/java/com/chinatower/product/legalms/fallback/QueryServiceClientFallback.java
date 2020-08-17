package com.chinatower.product.legalms.fallback;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.provider.call.oauth.QueryServiceClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class QueryServiceClientFallback implements FallbackFactory<QueryServiceClient> {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("TransLog");

    @Override
    public QueryServiceClient create(Throwable cause) {
        return new QueryServiceClient() {
            @Override
            public ProcessResult queryLoginUserInfo(String args) {
                logger.error(SystemInfo.QUERY_USERINFO_ERROR, cause.getMessage());
                return new ProcessResult(ProcessResult.BUZ_EXCEPTION, cause.getMessage());
            }

            @Override
            public ProcessResult queryButtons(String args) {
                logger.error(SystemInfo.QUERY_BUTTON_ERROR, cause.getMessage());
                return new ProcessResult(ProcessResult.BUZ_EXCEPTION, cause.getMessage());
            }
        };
    }
}
