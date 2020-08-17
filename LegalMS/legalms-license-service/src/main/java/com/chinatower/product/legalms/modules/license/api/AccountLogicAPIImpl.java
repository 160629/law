package com.chinatower.product.legalms.modules.license.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.base.BaseController;
import com.chinatower.product.legalms.common.ConstClass;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.modules.license.entity.AccountLogicVO;
import com.chinatower.product.legalms.modules.license.service.AccountLogicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: G D
 * @Date: 2019/10/12 10:50
 * @Description:
 */
@RestController
public class AccountLogicAPIImpl extends BaseController implements AccountLogicAPI {

    private static final Logger logger = LoggerFactory.getLogger(SystemInfo.LOGGER_NAME);

    @Autowired
    private AccountLogicService accountLogicService;

    @Override
    public ProcessResult selectUserInfByAccountId(@RequestBody AccountLogicVO accountVO) {

        try {
            return accountLogicService.selectUserInfByAccountId(accountVO);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR, e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.FIND_USER_ERROR);
        }
    }

    @Override
    public ProcessResult selectUserInfoByUserCode(@RequestBody AccountLogicVO accountVO) {

        try {
            return accountLogicService.selectUserInfoByUserCode(accountVO);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR, e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.FIND_USER_ERROR);
        }
    }

    @Override
    public ProcessResult updateUserInfoByAccountId(@RequestBody AccountLogicVO accountVO) {
        try {
            return accountLogicService.updateUserInfoByAccountId(accountVO);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR, e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.UPDATE_USER_ERROR);
        }
    }

    @Override
    public ProcessResult selectUserInfoByOrgCode(@RequestBody AccountLogicVO accountVO) {
        try {
            return accountLogicService.selectUserInfoByOrgCode(accountVO);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR, e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.FIND_USER_ERROR);
        }
    }
}
