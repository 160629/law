package com.chinatower.product.legalms.modules.license.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.license.commen.ServerResponse;
import com.chinatower.product.legalms.modules.license.entity.AccRoleImpowerVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface AccRoleImpowerAPI {
    /**
     * 功能描述:4A_账号与角色授权变更接口
     * @auther: GD
     * @param accRoleImpowerVO
     * @return com.chinatower.product.legalms.modules.license.commen.ServerResponse
     * @date: 2019/10/10 11:32
     */
    @PostMapping("/v1/account/operaAccRoleImpower")
    public ServerResponse operaAccRoleImpower(@RequestBody AccRoleImpowerVO accRoleImpowerVO);

    /**
     * 功能描述: 已授权角色查询
     * @auther: guodong
     * @param accRoleImpowerVO
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @date: 2019/12/30 20:40
     */
    @PostMapping("/v1/account/selectAccRoleImpowerByAccountId")
    public ProcessResult selectAccRoleImpowerByAccountId(AccRoleImpowerVO accRoleImpowerVO);

}
