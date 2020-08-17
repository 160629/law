package com.chinatower.product.legalms.modules.flow.service.version.impl;

import com.chinatower.product.legalms.modules.flow.entity.common.FlowActivityConfigVO;
import com.chinatower.product.legalms.modules.flow.entity.common.FlowActivityPowerVO;
import com.chinatower.product.legalms.modules.flow.entity.version.FlowVersion;
import com.chinatower.product.legalms.modules.flow.mapper.version.FlowVersionMapper;
import com.chinatower.product.legalms.modules.flow.service.version.FlowVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FlowVersionServiceImpl implements FlowVersionService {

    @Autowired
    FlowVersionMapper flowVersionMapper;

    @Override
    @Transactional
    public int insertRecord(FlowVersion flowVersion) {
        return flowVersionMapper.insertRecord(flowVersion);
    }

    @Override
    @Transactional
    public int updateRecord(FlowVersion flowVersion) {
        return flowVersionMapper.updateRecord(flowVersion);
    }

    @Override
    @Transactional
    public List<FlowVersion> selectAllRecords() {
        return flowVersionMapper.selectAllRecords();
    }

    @Override
    @Transactional
    public FlowVersion selectRecordById(FlowVersion flowVersion) {
        return flowVersionMapper.selectRecordById(flowVersion);
    }

    @Override
    @Transactional
    public int addFlowVersionToPowerAndConfig(FlowVersion flowVersion) {
        List<FlowActivityPowerVO> flowActivityPowerVOS = flowVersionMapper.selectFromPowerByFlowId(flowVersion.getFlowId());
        List<FlowActivityConfigVO> flowActivityConfigVOS = flowVersionMapper.selectFromConfigByFlowId(flowVersion.getFlowId());
        flowActivityPowerVOS.stream().forEach(x -> x.setVersionId(x.getVersionId() + 1));
        flowActivityConfigVOS.stream().forEach(x -> x.setVersionId(x.getVersionId() + 1));
        int i = flowVersionMapper.insertPowers(flowActivityPowerVOS);
        i += flowVersionMapper.insertConfigs(flowActivityConfigVOS);
        return i;
    }

    @Override
    public FlowVersion selectLatestVersion(String flowId) {
        return flowVersionMapper.selectLatestVersion(flowId);
    }
}
