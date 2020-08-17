package com.chinatower.product.legalms.modules.license.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.license.commen.ServerResponse;
import com.chinatower.product.legalms.modules.license.entity.ListParam;
import com.chinatower.product.legalms.modules.license.entity.MenuVO;
import com.chinatower.product.legalms.modules.license.entity.RecieveRoleMenuVo;
import com.chinatower.product.legalms.modules.license.entity.RoleVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


public interface RoleMenuAPI {
    /**
     * 4A_角色与菜单授权变更接口
     * @param recieveRoleMenuVo
     * @return
     */
    @PostMapping("/v1/rolemenu/recieveRoleMenu")
    public ServerResponse recieveRoleMenu(RecieveRoleMenuVo recieveRoleMenuVo);
    @GetMapping("/v1/rolemenu/selectRoleMenuByRoleCode")
    public ProcessResult selectRoleMenuByRoleCode(@RequestParam("roleCode")String roleCode);
    @PostMapping("/v1/rolemenu/selectRole")
    public ProcessResult selectRole(ListParam listParam);
    @PostMapping("/v1/rolemenu/selectMenu")
    public ProcessResult selectMenu(MenuVO menuVO);
    @PostMapping("/v1/rolemenu/selectRoleMenu")
    public ProcessResult selectRoleMenu(RoleVo roleVo);
    @PostMapping("/v1/rolemenu/selectUser")
    public ProcessResult selectUser(ListParam listParam);
}
