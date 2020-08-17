package com.chinatower.product.legalms.modules.flow.service.common.impl;

import java.util.List;

import com.chinatower.product.legalms.modules.flow.service.common.FlowActivityPowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinatower.product.legalms.modules.flow.entity.common.FlowActivityPowerVO;
import com.chinatower.product.legalms.modules.flow.mapper.common.FlowActivityPowerMapper;
import com.chinatower.product.legalms.modules.flow.vo.common.PageLists;

@Service
@Transactional(rollbackFor = Exception.class)
public class FlowActivityPowerServiceImpl implements FlowActivityPowerService {

    @Autowired
    private FlowActivityPowerMapper flowActivityPowerMapper;


    @Override
    public List<FlowActivityPowerVO> selActivityPower(FlowActivityPowerVO flowActivityPowerVO) {
        return flowActivityPowerMapper.selActivityPower(flowActivityPowerVO);
    }

    @Override
    public int addFlowActivityPower(FlowActivityPowerVO flowActivityPowerVO) {
        return flowActivityPowerMapper.addFlowActivityPower(flowActivityPowerVO);
    }

    @Override
    public int updateFlowActivityPower(FlowActivityPowerVO oldFlowActivityPowerVO, FlowActivityPowerVO newFlowActivityPowerVO) {
        return flowActivityPowerMapper.updateFlowActivityPower(oldFlowActivityPowerVO, newFlowActivityPowerVO);
    }

    @Override
    public int deleteFlowActivityPower(FlowActivityPowerVO flowActivityPowerVO) {
        return flowActivityPowerMapper.deleteFlowActivityPower(flowActivityPowerVO);
    }

    @Override
    public List<FlowActivityPowerVO> selFlowActivityPower(PageLists pageLists) {
        return flowActivityPowerMapper.selFlowActivityPower(pageLists);
    }

    @Override
    public List<FlowActivityPowerVO> selFlowActivityPowerFlowId() {
        return flowActivityPowerMapper.selFlowActivityPowerFlowId();
    }
}
