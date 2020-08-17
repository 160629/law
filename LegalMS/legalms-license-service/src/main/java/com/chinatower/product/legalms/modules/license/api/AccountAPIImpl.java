package com.chinatower.product.legalms.modules.license.api;

import com.chinatower.product.legalms.modules.license.commen.ServerResponse;
import com.chinatower.product.legalms.modules.license.entity.AccountVO;
import com.chinatower.product.legalms.modules.license.service.AccountService;
import com.chinatower.product.legalms.modules.license.service.SyncInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountAPIImpl implements AccountAPI {


    @Autowired
    private AccountService accountService;

    @Autowired
    private SyncInfoService syncInfoService;
    /**
     * 功能描述:4A_账号同步变更接口
     * @auther: GD
     * @param accountVO
     * @return com.chinatower.product.legalms.modules.license.commen.ServerResponse
     * @date: 2019/10/10 11:35
     */
    @Override
    public ServerResponse operaAccount(@RequestBody AccountVO accountVO) {
        return accountService.operaAccount(accountVO);
    }

    /**
     * 功能描述:测试
     * @auther: guodong
     * @param
     * @return void
     * @date: 2020/1/3 14:38
     */
    @GetMapping("/test")
    public void test() {
        syncInfoService.syncInfo();
    }
}
