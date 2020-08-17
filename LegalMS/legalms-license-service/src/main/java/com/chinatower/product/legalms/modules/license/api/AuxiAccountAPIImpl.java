package com.chinatower.product.legalms.modules.license.api;

import com.chinatower.product.legalms.modules.license.commen.ServerResponse;
import com.chinatower.product.legalms.modules.license.entity.AuxiAccountVO;
import com.chinatower.product.legalms.modules.license.service.AuxiAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuxiAccountAPIImpl implements AuxiAccountAPI {

    @Autowired
    private AuxiAccountService auxiAccountService;

/**
 * 功能描述:4A_附属组织同步变更接口
 * @auther: GD
 * @param auxiAccountVO
 * @return com.chinatower.product.legalms.modules.license.commen.ServerResponse
 * @date: 2019/10/10 11:36
 */
    @Override
    public ServerResponse operaAuxiAccount(@RequestBody AuxiAccountVO auxiAccountVO) {
        return auxiAccountService.operaAuxiAccount(auxiAccountVO);
    }
}
