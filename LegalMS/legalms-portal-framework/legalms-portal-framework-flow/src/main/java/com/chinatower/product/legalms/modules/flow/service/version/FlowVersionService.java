package com.chinatower.product.legalms.modules.flow.service.version;


import com.chinatower.product.legalms.modules.flow.entity.version.FlowVersion;

import java.util.List;

public interface FlowVersionService {
    public int insertRecord(FlowVersion flowVersion);
    public int updateRecord(FlowVersion flowVersion);
    public List<FlowVersion> selectAllRecords();
    public FlowVersion selectRecordById(FlowVersion flowVersion);
    public int addFlowVersionToPowerAndConfig(FlowVersion flowVersion);
    public FlowVersion selectLatestVersion(String flowId);
}
