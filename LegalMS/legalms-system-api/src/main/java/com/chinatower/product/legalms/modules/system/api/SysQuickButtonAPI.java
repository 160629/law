package com.chinatower.product.legalms.modules.system.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.system.entity.SysQuickButtonPage;
import com.chinatower.product.legalms.modules.system.entity.SysQuickButtonVO;
import org.springframework.web.bind.annotation.PostMapping;

public interface SysQuickButtonAPI {

    @PostMapping("/v1/quickbutton/sellectQuickButton")
    public ProcessResult sellectQuickButton(SysQuickButtonPage sysQuickButtonPage);
    @PostMapping("/v1/quickbutton/deleteQuickButton")
    public ProcessResult deleteQuickButton(SysQuickButtonVO sysQuickButtonVO);

    @PostMapping("/v1/quickbutton/addQuickButton")
    public ProcessResult addQuickButton(SysQuickButtonVO sysQuickButtonVO);

    @PostMapping("/v1/quickbutton/updateQuickButton")
    public ProcessResult updateQuickButton(SysQuickButtonVO sysQuickButtonVO);
}
