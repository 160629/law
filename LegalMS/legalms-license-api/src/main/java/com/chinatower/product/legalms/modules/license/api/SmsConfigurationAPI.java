package com.chinatower.product.legalms.modules.license.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.license.entity.SmsConfigurationVO;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Date: 2020/7/10 15:17
 * @Description:
 */
public interface SmsConfigurationAPI {



    @PostMapping("/v1/smsconfig/selectUserInfo")
    public ProcessResult selectUserInfo(SmsConfigurationVO smsConfigurationVO);


    @PostMapping("/v1/smsconfig/updateUserInfo")
    public ProcessResult updateUserInfo(SmsConfigurationVO smsConfigurationVO);

}
