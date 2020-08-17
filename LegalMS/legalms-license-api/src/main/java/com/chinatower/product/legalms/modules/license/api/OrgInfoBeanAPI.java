package com.chinatower.product.legalms.modules.license.api;

import com.chinatower.product.legalms.modules.license.commen.ServerResponseOrg;
import org.springframework.web.bind.annotation.*;

public interface OrgInfoBeanAPI {
    /**
     * 新增或修改组织
     *
     */
    @PostMapping("/v1/orginfoBean/recieveOrginfoBean")
    public ServerResponseOrg recieveOrginfoBean(String json);


    /*@PostMapping("/orginfoBean/tesst")
    public String test(@RequestBody String json);*/


}
