package com.chinatower.product.legalms.fallback;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.modules.system.entity.TaskVO;
import com.chinatower.provider.call.dispute.DisputeServiceClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2019/9/18.
 */
@Component
@Slf4j
public class DisputeServiceClientFallback implements FallbackFactory<DisputeServiceClient> {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("TransLog");

    @Override
    public DisputeServiceClient create(Throwable cause) {
        return new DisputeServiceClient() {
            @Override
            public ProcessResult queryPersonWorkItems(TaskVO taskVO) {
                    logger.error(SystemInfo.QUERY_PERSONWORK, cause.toString());
                    return new ProcessResult(ProcessResult.BUZ_EXCEPTION, cause.toString());
            }
        };
    }
}