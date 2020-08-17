package com.chinatower.product.legalms.fallback;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.CaseInfo;
import com.chinatower.provider.call.flowpath.TFlowServiceClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2019/9/18.
 */
@Component
@Slf4j
public class FlowServiceClientFallback implements FallbackFactory<TFlowServiceClient> {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("TransLog");


    @Override
    public TFlowServiceClient create(Throwable throwable) {
        return new TFlowServiceClient() {
            @Override
            public ProcessResult autoUnView(String transferId, String transferUser, String receptId, String receptUser, Integer caseNum, String cause) {
                logger.error(CaseInfo.AUTOUNVIEW, cause);
                return new ProcessResult(ProcessResult.BUZ_EXCEPTION, cause);
            }

            @Override
            public ProcessResult updateCaseId(String caseId, String id, String caseTitle, Integer createCaseType) {
                logger.error(CaseInfo.UPDATE_CASE_INFO, caseId, id, caseTitle, createCaseType);
                return new ProcessResult(ProcessResult.BUZ_EXCEPTION);
            }
        };
    }
}