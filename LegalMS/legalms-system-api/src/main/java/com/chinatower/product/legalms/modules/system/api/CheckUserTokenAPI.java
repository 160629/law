package com.chinatower.product.legalms.modules.system.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Date: 2019/11/14 11:37
 * @Description:
 */
public interface CheckUserTokenAPI {

    @PostMapping("/v1/system/checkToken")
    public ProcessResult checkToken (String token,
                                     String userId,
                                     String appAcctId,
                                     String activityInstId,
                                     String processInstId,
                                     String moduleName,
                                     String viewId,
                                     String mainProcessInstID);
}
