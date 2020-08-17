package com.chinatower.product.legalms.modules.dispute.service.flow;

import java.util.List;

import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowMain;


/**
 * <p>
 * 审批表 服务类
 * </p>
 *
 * @author wangyong
 * @since 2019-09-30
 */
public interface TFlowMainService  {
	List<TFlowMain> selectAll();
	int insertSelective(TFlowMain flow);
}
