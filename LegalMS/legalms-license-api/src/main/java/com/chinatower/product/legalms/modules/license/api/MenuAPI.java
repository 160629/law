package com.chinatower.product.legalms.modules.license.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.license.commen.ServerResponse;
import com.chinatower.product.legalms.modules.license.entity.MenuVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface MenuAPI {
    /**
     * 4A_菜单变更接口
     * @param json
     * @return
     */
    @PostMapping("/v1/menu/recieveMenu")
    public ServerResponse recieveMenu(@RequestBody MenuVO json);
    @GetMapping("/v1/menu/selectMenuByMenuCode")
    public ProcessResult selectMenuByMenuCode(@RequestParam("menuCode")String menuCode);

}
