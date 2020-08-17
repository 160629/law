package com.chinatower.product.legalms.modules.flow.service.common;

import java.util.List;

import com.chinatower.product.legalms.modules.flow.entity.common.FlowActivityPowerVO;
import com.chinatower.product.legalms.modules.flow.vo.common.PageLists;

public interface FlowActivityPowerService {
    List<FlowActivityPowerVO> selActivityPower(FlowActivityPowerVO flowActivityPowerVO);

    int addFlowActivityPower(FlowActivityPowerVO flowActivityPowerVO);

    int updateFlowActivityPower(FlowActivityPowerVO oldFlowActivityPowerVO, FlowActivityPowerVO newFlowActivityPowerVO);

    int deleteFlowActivityPower(FlowActivityPowerVO flowActivityPowerVO);

    List<FlowActivityPowerVO> selFlowActivityPower(PageLists pageLists);

    List<FlowActivityPowerVO> selFlowActivityPowerFlowId();
}
