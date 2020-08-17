package com.chinatower.product.legalms.modules.license.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.license.entity.AccountLogicVO;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Auther: G D
 * @Date: 2019/10/12 10:49
 * @Description:
 */

public interface AccountLogicAPI {


    /**
     * 功能描述:根据指定accountId 查人
     *
     * @param accountVO
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @auther: guodong
     * @date: 2020/1/2 9:58
     */
    @PostMapping("/v1/accountlogic/selectUserInfoByAccountId")
    public ProcessResult selectUserInfByAccountId(AccountLogicVO accountVO);

    /**
     * 功能描述:根据组织查询当前组织下的人员信息
     *
     * @param accountVO
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @auther: guodong
     * @date: 2020/1/2 10:05
     */
    @PostMapping("/v1/accountlogic/selectUserInfoByOrgCode")
    public ProcessResult selectUserInfoByOrgCode(AccountLogicVO accountVO);


    /**
     * 功能描述:根据用户查询账号及组织信息
     *
     * @return
     * @auther: guodong
     * @date: 2020/3/25 16:06
     */
    @PostMapping("/v1/accountlogic/selectUserInfoByUserCode")
    public ProcessResult selectUserInfoByUserCode(AccountLogicVO accountVO);


    /**
     * 功能描述:根据用户更新账号及组织信息（服务商账号组织迁移）
     *
     * @return
     * @auther: guodong
     * @date: 2020/3/25 16:06
     */
    @PostMapping("/v1/accountlogic/updateUserInfoByAccountId")
    public ProcessResult updateUserInfoByAccountId(AccountLogicVO accountVO);
}
