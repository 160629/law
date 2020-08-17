package com.chinatower.product.legalms.modules.dispute.api.flow;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.flow.entity.delegate.TFlowDelegate;

/**
 * <p>
 * 审批表 前端控制器
 * </p>
 *
 * @author wangyong
 * @since 2019-09-30
 */
public interface TFlowDelegateApi {
	
	@PostMapping("/TFlowDelegate/selectAll")
	ProcessResult selectAll(TFlowDelegate record,Integer pageNum,Integer pageSize);
	
	@PostMapping("/TFlowDelegate/addOrUpdate")
	ProcessResult addOrUpdate(TFlowDelegate record);
	
	@PostMapping("/TFlowDelegate/endDelegate")
	ProcessResult endDelegate(List<TFlowDelegate> delegates);
	
    @GetMapping("/TFlowDelegate/setLawKafa")
    ProcessResult setLawKafa(String lawKafa);
	

}

