package com.chinatower.product.legalms.modules.license.api;

import com.chinatower.product.legalms.modules.license.commen.ServerResponse;
import com.chinatower.product.legalms.modules.license.entity.AccountVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface AccountAPI {

    /**
     * 功能描述:4A_账号同步变更接口
     * @auther: GD
     * @param accountVO
     * @return com.chinatower.product.legalms.modules.license.commen.ServerResponse
     * @date: 2019/10/10 11:32
     */
    @PostMapping("/v1/account/operaAccount")
    public ServerResponse operaAccount(@RequestBody AccountVO accountVO);

}
