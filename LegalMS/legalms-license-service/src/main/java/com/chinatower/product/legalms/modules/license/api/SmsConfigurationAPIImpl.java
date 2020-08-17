package com.chinatower.product.legalms.modules.license.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.base.BaseController;
import com.chinatower.product.legalms.common.ConstClass;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.modules.license.entity.SmsConfigurationVO;
import com.chinatower.product.legalms.modules.license.service.SmsConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date: 2020/7/12 13:19
 * @Description:
 */
@RestController
public class SmsConfigurationAPIImpl extends BaseController implements SmsConfigurationAPI {

    private static final Logger logger = LoggerFactory.getLogger(SystemInfo.LOGGER_NAME);

    @Autowired
    private SmsConfigurationService smsConfigurationService;

    @Override
    public ProcessResult selectUserInfo(@RequestBody SmsConfigurationVO smsConfigurationVO) {
        try {
            return smsConfigurationService.selectUserInfo(smsConfigurationVO);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR,e);
            return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.SMS_CONFIG_ERROR);
        }
    }

    @Override
    public ProcessResult updateUserInfo(@RequestBody SmsConfigurationVO smsConfigurationVO) {
        try {
            return smsConfigurationService.updateUserInfo(smsConfigurationVO);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR,e);
            return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.SMS_CONFIG_ERROR);
        }
    }


}
