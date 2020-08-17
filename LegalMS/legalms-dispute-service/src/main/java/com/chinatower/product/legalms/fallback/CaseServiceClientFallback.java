package com.chinatower.product.legalms.fallback;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.flow.vo.common.RelationshipVO;
import com.chinatower.provider.call.tcase.CaseServiceClient;

import feign.hystrix.FallbackFactory;

/**
 * 
 * @author 23238
 *
 */
@Component
public class CaseServiceClientFallback implements FallbackFactory<CaseServiceClient> {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CaseServiceClientFallback.class);
    @Override
    public CaseServiceClient create(Throwable throwable) {
        return new CaseServiceClient() {
            @Override
            public ProcessResult addAutoCaseMain(String json,String pid) {
                logger.info("addAutoCaseMain请求异常，进入fallback,异常信息{}", throwable.getMessage());
                return new ProcessResult(ProcessResult.BUZ_EXCEPTION, throwable.getMessage());
            }

			@Override
			public ProcessResult updateRelationship(RelationshipVO relationshipVO) {
                logger.info("updateRelationship请求异常，进入fallback,异常信息{}", throwable.getMessage());
                return new ProcessResult(ProcessResult.BUZ_EXCEPTION, throwable.getMessage());
			}
            
        };
    }
}
