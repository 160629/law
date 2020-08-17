package com.chinatower.product.legalms.modules.license.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.license.commen.ServerResponseAdm;
import com.chinatower.product.legalms.modules.license.entity.AdmVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface AdmAPI {
    /**
     * 新增或修改行政区
     *
     */
    @PostMapping("/v1/adm/recieveAdm")
    public ServerResponseAdm recieveAdm(@RequestBody String json);

    @PostMapping("/v1/adm/selectAdmByAdmLevelAndParentAdmCode")
    public ProcessResult selectAdmByAdmLevelAndParentAdmCode(@RequestBody AdmVO admVO);
}
