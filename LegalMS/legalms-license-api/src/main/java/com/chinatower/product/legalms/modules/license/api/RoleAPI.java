package com.chinatower.product.legalms.modules.license.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.license.commen.ServerResponse;
import com.chinatower.product.legalms.modules.license.entity.AccRoleImpowerVO;
import com.chinatower.product.legalms.modules.license.entity.RoleVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface RoleAPI {
    /**
     * 4A_角色变更接口
     * @param json
     * @return
     */
    @PostMapping("/v1/role/recieveRole")
    public ServerResponse recieveRole(@RequestBody RoleVo json);
    @GetMapping("/v1/role/selectRoleByRoleCode")
    public ProcessResult selectRoleByRoleCode(String roleCode);

    /**
     * 功能描述: 查询所有角色表
     * @auther: guodong
     * @param
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @date: 2019/12/31 17:22
     */
    @PostMapping("/v1/role/selectAllRole")
    public ProcessResult selectAllRole();

    /**
     * 功能描述:未授权角色查询
     * @auther: guodong
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @date: 2019/12/30 20:40
     */
    @PostMapping("/v1/account/selectUnAccRoleImpowerByAccountId")
    public ProcessResult selectUnAccRoleImpowerByAccountId(AccRoleImpowerVO accRoleImpowerVO);
}
