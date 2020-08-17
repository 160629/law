package com.chinatower.product.legalms.modules.flow.mapper.common;

import java.util.List;

import com.chinatower.product.legalms.modules.flow.entity.common.FlowActivityPowerVO;
import com.chinatower.product.legalms.modules.flow.vo.common.PageLists;
import org.apache.ibatis.annotations.Param;

public interface FlowActivityPowerMapper {
    List<FlowActivityPowerVO> selActivityPower(FlowActivityPowerVO flowActivityPowerVO);

    int addFlowActivityPower(FlowActivityPowerVO flowActivityPowerVO);

    int updateFlowActivityPower(@Param("oldFlowActivityPowerVO") FlowActivityPowerVO oldFlowActivityPowerVO, @Param("newFlowActivityPowerVO") FlowActivityPowerVO newFlowActivityPowerVO);

    int deleteFlowActivityPower(FlowActivityPowerVO flowActivityPowerVO);

    List<FlowActivityPowerVO> selFlowActivityPower(PageLists pageLists);

    List<FlowActivityPowerVO> selFlowActivityPowerFlowId();
}
