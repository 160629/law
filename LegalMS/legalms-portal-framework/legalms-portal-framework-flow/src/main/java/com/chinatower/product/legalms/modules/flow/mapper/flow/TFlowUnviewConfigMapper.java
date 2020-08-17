package com.chinatower.product.legalms.modules.flow.mapper.flow;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinatower.product.legalms.modules.flow.entity.unview.TFlowUnviewConfig;


public interface TFlowUnviewConfigMapper {
    int deleteByPrimaryKey(Integer configId);

    int insert(TFlowUnviewConfig record);

    int insertSelective(TFlowUnviewConfig record);

    TFlowUnviewConfig selectByPrimaryKey(Integer configId);

    int updateByPrimaryKeySelective(TFlowUnviewConfig record);

    int updateByPrimaryKey(TFlowUnviewConfig record);
    
    List<TFlowUnviewConfig> selectByType(@Param("approveItemType")String approveItemType,
    		@Param("configOrder")Integer configOrder);
    
    
    String selectActivityDefId(String flowName ,String activityDefName); 
}