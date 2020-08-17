package com.chinatower.product.legalms.modules.dispute.service.flow.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinatower.product.legalms.modules.dispute.service.flow.TFlowDelegateService;
import com.chinatower.product.legalms.modules.flow.entity.delegate.TFlowDelegate;
import com.chinatower.product.legalms.modules.flow.mapper.flow.TFlowDelegateMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class TFlowDelegateServiceImpl implements TFlowDelegateService {
	private static final  Logger logger = LoggerFactory.getLogger(TFlowDelegateServiceImpl.class);

	@Autowired
	TFlowDelegateMapper mapper;

	@Override
	public List<TFlowDelegate> selectAll(TFlowDelegate record) {
		PageInfo<TFlowDelegate> selectAll = selectAll(record, null, null);
		return selectAll.getList();
	}

	@Override
	@Transactional
	public PageInfo<TFlowDelegate> selectAll(TFlowDelegate record, Integer pageNum, Integer pageSize) {
		if (null != pageNum && null != pageSize)
			PageHelper.startPage(pageNum, pageSize);
		List<TFlowDelegate> selectAll = mapper.selectAll(record);
		PageInfo<TFlowDelegate> pageInfo = new PageInfo<>(selectAll);
		ArrayList<TFlowDelegate> list1 = new ArrayList<>();
		for (TFlowDelegate td : selectAll) {
			long time = new Date().getTime();
			if (null != td.getStartTime() && "17".equals(td.getDelegateStatus())
					&& td.getStartTime().getTime() < time) {
				list1.add(td.setDelegateStatus("18"));
			} else if (null != td.getEndTime() && "18".equals(td.getDelegateStatus())
					&& td.getEndTime().getTime() < time) {
				list1.add(td.setDelegateStatus("19"));
			}
		}
		if (!list1.isEmpty()) {
			int i = 0;
			for (TFlowDelegate tFlowDelegate : list1) {
				i += mapper.updateStatusByPrimaryKey(tFlowDelegate);
			}
			selectAll(record, pageNum, pageSize);
			logger.info("委托待办修改数据：" + i);
		}
		return pageInfo;
	}

	@Override
	@Transactional
	public int addOrUpdate(TFlowDelegate td) {
		if (null == td.getDelegateId()) {
			if (null == td.getStartTime() || null == td.getEndTime()) {
				return 0;
			}
			updateStatus(td);
			return mapper.insertSelective(td);
		} else {
			return mapper.updateByPrimaryKeySelective(td);
		}
	}

	@Override
	@Transactional
	public int endDelegate(List<TFlowDelegate> delegates) {
		List<Long> list = delegates.stream().map(TFlowDelegate::getDelegateId).collect(Collectors.toList());
		return  mapper.endDelegate(list);
	}

	@Override
	@Transactional
	public int updateBatch(List<TFlowDelegate> list) {
		return mapper.updateBatch(list);
	}

	public void updateStatus(TFlowDelegate td) {
		long time = new Date().getTime();
		if (td.getStartTime().getTime() > time && td.getEndTime().getTime() > time) {
			td.setRealEndTime(td.getEndTime());
			td.setDelegateStatus("17");
		} else if (td.getStartTime().getTime() < time && td.getEndTime().getTime() > time) {
			td.setRealEndTime(td.getEndTime());
			td.setDelegateStatus("18");
		} else if (td.getEndTime().getTime() < time) {
			td.setRealEndTime(td.getEndTime());
			td.setDelegateStatus("19");
		}
	}

}
