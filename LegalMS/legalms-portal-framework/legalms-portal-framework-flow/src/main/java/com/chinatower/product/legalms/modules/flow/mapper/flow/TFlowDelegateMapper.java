package com.chinatower.product.legalms.modules.flow.mapper.flow;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinatower.product.legalms.modules.flow.entity.delegate.TFlowDelegate;


public interface TFlowDelegateMapper {
    int deleteByPrimaryKey(Long delegateId);

    int insert(TFlowDelegate record);

    int insertSelective(TFlowDelegate record);

    TFlowDelegate selectByPrimaryKey(Long delegateId);
    
    List<TFlowDelegate> selectAll(TFlowDelegate record);
    
    int updateByPrimaryKeySelective(TFlowDelegate record);

    int updateByPrimaryKey(TFlowDelegate record);
    
    int updateStatusByPrimaryKey(TFlowDelegate record);

	int endDelegate(List<Long> list);
	 
	int updateBatch(@Param("list") List<TFlowDelegate> list);
}