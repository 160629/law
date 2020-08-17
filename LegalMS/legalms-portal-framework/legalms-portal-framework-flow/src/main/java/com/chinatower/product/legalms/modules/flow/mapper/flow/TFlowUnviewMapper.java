package com.chinatower.product.legalms.modules.flow.mapper.flow;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinatower.product.legalms.modules.flow.entity.unview.TFlowUnview;
import com.chinatower.product.legalms.modules.flow.vo.unview.TFlowUnviewParam;


public interface TFlowUnviewMapper {
    int deleteByPrimaryKey(String viewId);

    int insert(TFlowUnview record);

    int insertSelective(TFlowUnview record);

    TFlowUnview selectByPrimaryKey(String viewId);

    int updateByPrimaryKeySelective(TFlowUnview record);

    int updateByPrimaryKey(TFlowUnview record);
    
    List<TFlowUnview> selectAll(TFlowUnviewParam record);
    
    List<String> selectViewTypes(@Param("viewStatus") String viewStatus);

	int updateStatus(List<String> viewIds);
	
	//移交待阅,toerId 移交人ID,toerName 移交人ID ,oldToerId 原待阅人ID
	int updateToer(String toerId,String toerName,String oldToerId);
}