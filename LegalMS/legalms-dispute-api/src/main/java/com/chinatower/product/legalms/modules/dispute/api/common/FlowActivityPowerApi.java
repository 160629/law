package com.chinatower.product.legalms.modules.dispute.api.common;

import org.springframework.web.bind.annotation.PostMapping;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.flow.entity.common.FlowActivityPowerVO;
import com.chinatower.product.legalms.modules.flow.vo.common.PageLists;

import java.util.Map;

public interface FlowActivityPowerApi {

    //流程权限配置增加
    @PostMapping("/flow/addFlowActivityPower")
    public ProcessResult addFlowActivityPower(FlowActivityPowerVO flowActivityPowerVO);

    //流程权限配置修改
    @PostMapping("/flow/updateFlowActivityPower")
    public ProcessResult updateFlowActivityPower(Map<String, FlowActivityPowerVO> flowActivityPowerVOs);

    //流程权限配置删除
    @PostMapping("/flow/deleteFlowActivityPower")
    public ProcessResult deleteFlowActivityPower(FlowActivityPowerVO flowActivityPowerVO);

    //流程权限配置列表
    @PostMapping("/flow/selFlowActivityPower")
    public ProcessResult selFlowActivityPower(PageLists pageLists);

    @PostMapping("/flow/selActivityPower")
    public ProcessResult selActivityPower(FlowActivityPowerVO flowActivityPowerVO);

    //流程权限配置列表
    @PostMapping("/flow/selFlowActivityPowerFlowId")
    public ProcessResult selFlowActivityPowerFlowId();
}
