package com.chinatower.product.legalms.modules.dispute.api.flow;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.flow.entity.version.FlowVersion;
import org.springframework.web.bind.annotation.PostMapping;


public interface FlowVersionApi {
    @PostMapping("/flowVersion/insertRecord")
    public ProcessResult insertRecord(FlowVersion flowVersion);

    @PostMapping("/flowVersion/updateRecord")
    public ProcessResult updateRecord(FlowVersion flowVersion);

    @PostMapping("/flowVersion/selectAllRecords")
    public ProcessResult selectAllRecords();

    @PostMapping("/flowVersion/selectRecordById")
    public ProcessResult selectRecordById(FlowVersion flowVersion);

    @PostMapping("/flowVersion/addFlowVersionToPowerAndConfig")
    public ProcessResult addFlowVersionToPowerAndConfig(FlowVersion flowVersion);
}
