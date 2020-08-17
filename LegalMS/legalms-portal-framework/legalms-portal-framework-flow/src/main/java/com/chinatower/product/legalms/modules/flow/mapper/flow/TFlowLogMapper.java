package com.chinatower.product.legalms.modules.flow.mapper.flow;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinatower.product.legalms.modules.flow.entity.flow.TBusinessEnd;
import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowLog;


public interface TFlowLogMapper {
    int deleteByPrimaryKey(String flowUserId);

    int insert(TFlowLog record);

    int insertSelective(TFlowLog record);

    TFlowLog selectByPrimaryKey(String flowLogId);

    int updateByPrimaryKeySelective(TFlowLog record);

    int updateByPrimaryKey(TFlowLog record);
    
    List<TFlowLog>  selectAll();

    List<TFlowLog> selectMainFlowLog(@Param("approveItemId") String businessId, @Param("approveItemType") String businessType);

    List<String> selectSubFlowLogs(@Param("flowId") Long flowId);

    int insertTBusinessEnd(@Param("tBusinessEnd") TBusinessEnd tBusinessEnd);

    List<TBusinessEnd> selectCompleteFlow(@Param("loginAcct") String loginAcct, @Param("tBusinessEnd") TBusinessEnd tBusinessEnd);

    TFlowLog selectActivityByFlowInstIdAndActivityInstId(@Param("processInstID")long processInstID, @Param("activityInstId")Long activityInstId);


    int updateBusinessTableState(@Param("approveItemId")String approveItemId, @Param("approveItemType")String approveItemType, @Param("businessStateField")String businessStateField, @Param("businessIdField") String businessIdField);

    int deleteByApproveItemId(@Param("approveItemId")String approveItemId, @Param("approveItemType")String approveItemType);
}