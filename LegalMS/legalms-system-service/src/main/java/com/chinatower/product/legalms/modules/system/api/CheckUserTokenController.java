package com.chinatower.product.legalms.modules.system.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.ConstClass;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.modules.system.service.CheckUserTokenService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date: 2019/12/17 17:11
 * @Description:
 */
@RestController
@Slf4j
public class CheckUserTokenController  implements CheckUserTokenAPI{

    private static final Logger logger = LoggerFactory.getLogger(SystemInfo.LOGGER_NAME);

    @Autowired
    private CheckUserTokenService checkUserTokenService;
    @Override
    public ProcessResult checkToken(String token, String userId, String appAcctId, String activityInstId, String processInstId,String moduleName,String viewId,String mainProcessInstID) {
        try {
            return checkUserTokenService.checkToken(token,userId,appAcctId,activityInstId,processInstId,moduleName,viewId,mainProcessInstID);
        } catch (Exception e) {
            logger.error(SystemInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.ENCRYPT_USER_ERROR);
        }
    }
}
