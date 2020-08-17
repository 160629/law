package com.chinatower.product.legalms.modules.dispute.api.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.flow.entity.common.FlowActivityConfigVO;
import com.chinatower.product.legalms.modules.flow.vo.common.PageList;

public interface FlowActivityConfigApi {

    @PostMapping("/flow/selParamActivityConfig")
    public ProcessResult selParamActivityConfig(PageList param);

    @PostMapping("/flow/selActivityConfig")
    public ProcessResult selActivityConfig(FlowActivityConfigVO flowActivityConfigVO);

    @GetMapping("/flow/selActivityId")
    public ProcessResult selActivityId(String flowId,String beginId,String endId,Integer versionId);

    @PostMapping("/flow/selActivityCount")
    public ProcessResult selActivityCount(FlowActivityConfigVO flowActivityConfigVO);

    @PostMapping("/flow/selActivityJurisdiction")
    public ProcessResult selActivityJurisdiction(FlowActivityConfigVO flowActivityConfigVO);

    @PostMapping("/flow/updateActivityConfig")
    public ProcessResult updateActivityConfig(FlowActivityConfigVO   flowActivityConfigVO);

    @PostMapping("/flow/addActivityConfig")
    public ProcessResult addActivityConfig(FlowActivityConfigVO flowActivityConfigVO);

    @PostMapping("/flow/delActivityConfig")
    public ProcessResult delActivityConfig(FlowActivityConfigVO flowActivityConfigVO);
}
