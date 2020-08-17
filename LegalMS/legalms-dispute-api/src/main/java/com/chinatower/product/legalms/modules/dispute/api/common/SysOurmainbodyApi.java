package com.chinatower.product.legalms.modules.dispute.api.common;

import org.springframework.web.bind.annotation.PostMapping;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.flow.entity.common.SysOurmainbodyVO;
import com.chinatower.product.legalms.modules.flow.vo.common.SysOurmainbodyPage;

public interface SysOurmainbodyApi {

    @PostMapping("/v1/SysOurmainbody/selectSysOurmainbody")
    public ProcessResult selectSysOurmainbody(SysOurmainbodyPage sysOurmainbodyPage);

    //主体关联表与主体表关联查询
    @PostMapping("/v1/SysOurmainbody/selectSysOurmainbodyAndConfig")
    public ProcessResult selectSysOurmainbodyAndConfig(SysOurmainbodyVO sysOurmainbodyVO);
}
