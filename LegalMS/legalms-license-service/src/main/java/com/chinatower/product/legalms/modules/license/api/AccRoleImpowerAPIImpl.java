package com.chinatower.product.legalms.modules.license.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.ConstClass;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.modules.license.commen.ServerResponse;
import com.chinatower.product.legalms.modules.license.entity.AccRoleImpowerVO;
import com.chinatower.product.legalms.modules.license.service.AccRoleImpowerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccRoleImpowerAPIImpl implements AccRoleImpowerAPI {

    private static final Logger logger = LoggerFactory.getLogger(SystemInfo.LOGGER_NAME);

    @Autowired
    private AccRoleImpowerService accRoleImpowerService;

/**
 * 功能描述:4A_账号与角色授权变更接口
 * @auther: GD
 * @param accRoleImpowerVO
 * @return com.chinatower.product.legalms.modules.license.commen.ServerResponse
 * @date: 2019/10/10 11:35
 */
    @Override
    public ServerResponse operaAccRoleImpower(@RequestBody AccRoleImpowerVO accRoleImpowerVO) {
        return accRoleImpowerService.operaAccRoleImpower(accRoleImpowerVO);
    }

    @Override
    public ProcessResult selectAccRoleImpowerByAccountId(@RequestBody AccRoleImpowerVO accRoleImpowerVO) {

        try {
            return accRoleImpowerService.selectAccRoleImpowerByAccountId(accRoleImpowerVO);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR,e);
            return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.USERPOWER_DEAL_ERROR);
        }
    }
}
