package com.chinatower.product.legalms.modules.system.service;

import com.chinatower.framework.common_service.response.ProcessResult;

/**
 * @Date: 2019/12/17 17:13
 * @Description:
 */
public interface CheckUserTokenService {
    ProcessResult checkToken(String token, String userId, String appAcctId, String activityInstId, String processInstId,String moduleName ,String viewId,String mainProcessInstID);
}
