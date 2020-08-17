package com.chinatower.product.legalms.modules.system.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.ConstClass;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.modules.system.service.UserMsgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: G D
 * @Date: 2019/10/18 17:14
 * @Description:
 */

@RestController
@Api(tags = {"登录用户信息"})
@Slf4j
public class UserMsgController implements UserMsgAPI {

    private static final Logger logger = LoggerFactory.getLogger(SystemInfo.LOGGER_NAME);

    @Autowired
    private UserMsgService userMsgService;

    /**
     * 功能描述:
     *
     * @param
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @auther: G D
     * @date: 2019/10/18 17:18
     */
    @Override
    @ApiOperation("用户加密信息")
    public ProcessResult reqEncryptUserMsg(HttpServletRequest request) {

        try {
            return userMsgService.reqEncryptUserMsg(request);
        } catch (Exception e) {
            logger.error(SystemInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.ENCRYPT_USER_ERROR);
        }
    }

    @Override
    public ProcessResult reqEncryptUserMsgInfo(HttpServletRequest request) {
        try {
            return userMsgService.reqEncryptUserMsgInfo(request);
        } catch (Exception e) {
            logger.error(SystemInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.ENCRYPT_USER_ERROR);
        }
    }

    /**
     * 功能描述:根据Headers信息账号参数查询对应的用户信息
     * @auther: guodong
     * @param loginAcct
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @date: 2019/12/2 9:42
     */
    @Override
    @ApiOperation("根据指定账号查询用户信息")
    public ProcessResult selectLoginUserMsg(@RequestParam(value = "loginAcct",required = true) String loginAcct) {
        try {
            return userMsgService.selectLoginUserMsg(loginAcct);
        } catch (Exception e) {
            logger.error(SystemInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR,e.toString());
        }
    }
}
