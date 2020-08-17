package com.chinatower.product.legalms.modules.dispute.service.lawcase;

import java.util.List;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.FlowUtil;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.dispute.entity.lawcase.TCaseAssign;
import com.chinatower.product.legalms.modules.dispute.vo.issue.ListParam;
import com.chinatower.product.legalms.modules.dispute.vo.issue.TCaseAssignVO;
import com.chinatower.product.legalms.modules.dispute.vo.querylist.TCaseAssignListVO;
import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowLog;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowLogVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowVO;

public interface TCaseAssignService {
	
	int addTCaseAssign (AddFlowVO<TCaseAssign> vo, FlowUtil flowUtil,UserInfo info);
	
	int tempTCaseAssign (AddFlowVO<TCaseAssign> vo,UserInfo info);

	List<TCaseAssign> selectAll(ListParam param, String unitId);
	
	int addFlow(AddFlowVO<TCaseAssign> vo, FlowUtil flowUtil, UserInfo info,TFlowLog log);

	int deleteTCaseAssign(List<String> assignIds);

	TCaseAssignVO findOne(String assignId, String actId);
	
	int updateByPrimaryKey(TCaseAssign assign);
	
	int updateByPrimaryKeySelective(TCaseAssign assign);
	
	int autoSaveCase(String approveItemType,String id,String pid,UserInfo userInfo);
	
	int deleteByPrimaryKey(String assignId);

	ProcessResult selectAlles(TCaseAssignListVO param);

	ProcessResult addTFlowLog(AddFlowLogVO vo);
	
	ProcessResult test();
}
