package com.chinatower.product.legalms.modules.license.service;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.license.entity.SmsConfigurationVO;

/**
 * @Date: 2020/7/12 13:23
 * @Description:
 */
public interface SmsConfigurationService {

    ProcessResult selectUserInfo(SmsConfigurationVO smsConfigurationVO);

    ProcessResult updateUserInfo(SmsConfigurationVO smsConfigurationVO);
}
