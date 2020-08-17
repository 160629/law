package com.chinatower.product.legalms.modules.dispute.service.flow;

import java.util.List;

import com.chinatower.product.legalms.modules.flow.entity.delegate.TFlowDelegate;
import com.github.pagehelper.PageInfo;

public interface TFlowDelegateService {
	
	PageInfo<TFlowDelegate> selectAll(TFlowDelegate record,Integer pageNum,Integer pageSize);
	
	List<TFlowDelegate> selectAll(TFlowDelegate record);

	int addOrUpdate(TFlowDelegate record);

	int endDelegate(List<TFlowDelegate> delegates);
	
	int updateBatch(List<TFlowDelegate> list);
	
}
