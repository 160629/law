package com.chinatower.product.legalms.modules.license.service;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.license.entity.AccountLogicVO;

/**
 * @Auther: G D
 * @Date: 2019/10/12 10:52
 * @Description:
 */
public interface AccountLogicService {


    ProcessResult selectUserInfByAccountId(AccountLogicVO accountVO);

    ProcessResult selectUserInfoByUserCode(AccountLogicVO accountVO);

    ProcessResult selectUserInfoByOrgCode(AccountLogicVO accountVO);

    ProcessResult updateUserInfoByAccountId(AccountLogicVO accountVO);
}
