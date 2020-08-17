package com.chinatower.product.legalms.modules.license.api;

import com.chinatower.product.legalms.modules.license.commen.ServerResponse;
import com.chinatower.product.legalms.modules.license.entity.AuxiAccountVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuxiAccountAPI {

    /**
     * 4A_附属组织同步变更接口
     * @param auxiAccountVO
     * @return
     */
    @PostMapping("/v1/account/operaAuxiAccount")
    public ServerResponse operaAuxiAccount(@RequestBody AuxiAccountVO auxiAccountVO);
}
