package com.chinatower.product.legalms.common;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.license.service.SyncInfoService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@JobAround(serviceName="legalms-license-service")
public class ScheduledTask {

    private static final Logger logger = LoggerFactory.getLogger(SystemInfo.LOGGER_NAME);

    @Autowired
    private SyncInfoService syncInfoService;

    /**
     * 功能描述:定时任务(4A数据进行同步到业务表)
     *
     * @param
     * @return ProcessResult
     * @auther: G D
     * @date: 2019/10/10 11:34
     */

    @Scheduled(cron = "0 */5 * * * ?")//每5分钟执行一次
    public ProcessResult syncData() {

        try {
            return syncInfoService.syncInfo();
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR,e);
            return new ProcessResult(ProcessResult.ERROR,e.getMessage());
        }
    }
}
