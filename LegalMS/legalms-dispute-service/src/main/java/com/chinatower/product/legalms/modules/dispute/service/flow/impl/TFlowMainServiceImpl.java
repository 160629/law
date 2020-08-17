package com.chinatower.product.legalms.modules.dispute.service.flow.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinatower.product.legalms.modules.dispute.service.flow.TFlowMainService;
import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowMain;
import com.chinatower.product.legalms.modules.flow.mapper.flow.TFlowMainMapper;

/**
 * <p>
 * 审批表 服务实现类
 * </p>
 *
 * @author wangyong
 * @since 2019-09-30
 */
@Service
public class TFlowMainServiceImpl implements TFlowMainService {
	@Autowired
	TFlowMainMapper mapper;
	@Override
	public List<TFlowMain> selectAll() {
		return mapper.selectList();
	}
	@Override
	public int insertSelective(TFlowMain flow) {
		return mapper.insertSelective(flow);
	}

}
