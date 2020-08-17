package com.chinatower.product.legalms.fallback;



import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.provider.call.oauth.SelServiceClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SelServiceClientFallback implements FallbackFactory<SelServiceClient> {

    private static final Logger logger = LoggerFactory.getLogger("TransLog");


    @Override
    public SelServiceClient create(Throwable throwable) {
        return new SelServiceClient() {

            @Override
            public ProcessResult serviceRoles(String args) {
                logger.error(SystemInfo.SERVICE_ROLES_ERROR,throwable.getMessage());
                return new ProcessResult(ProcessResult.BUZ_EXCEPTION,throwable.getMessage());
            }

            @Override
            public ProcessResult serviceMenus(String args) {
                logger.error(SystemInfo.SERVICE_MENUS_ERROR,throwable.getMessage());
                return new ProcessResult(ProcessResult.BUZ_EXCEPTION,throwable.getMessage());
        }

            @Override
            public ProcessResult roleMenus(String args) {
                logger.error(SystemInfo.ROLE_MENUS_ERROR,throwable.getMessage());
                return new ProcessResult(ProcessResult.BUZ_EXCEPTION,throwable.getMessage());
            }

            @Override
            public ProcessResult userRoles(String args) {
                logger.error(SystemInfo.USER_ROLE_ERROR,throwable.getMessage());
                return new ProcessResult(ProcessResult.BUZ_EXCEPTION,throwable.getMessage());
            }
        };
    }
}
