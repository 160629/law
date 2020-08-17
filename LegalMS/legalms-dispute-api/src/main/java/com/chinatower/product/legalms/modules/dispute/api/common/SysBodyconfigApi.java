package com.chinatower.product.legalms.modules.dispute.api.common;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.flow.entity.common.SysBodyconfigVO;

public interface SysBodyconfigApi {

    //主体关联表查询
    @PostMapping("/v1/SysBodyconfig/selectSysBodyconfig")
    public ProcessResult selectSysBodyconfig(SysBodyconfigVO sysBodyconfigVO);

    //主体关联表添加
    @PostMapping("/v1/SysBodyconfig/addSysBodyconfig")
    public ProcessResult addSysBodyconfig(List<SysBodyconfigVO> list);

    //主体关联表修改
    @PutMapping("/v1/SysBodyconfig/updateSysBodyconfig")
    public ProcessResult updateSysBodyconfig(SysBodyconfigVO sysBodyconfigVO);

    //主体关联表删除
    @DeleteMapping("/v1/SysBodyconfig/deleteSysBodyconfig")
    public ProcessResult deleteSysBodyconfig(String approveItemId);
}
