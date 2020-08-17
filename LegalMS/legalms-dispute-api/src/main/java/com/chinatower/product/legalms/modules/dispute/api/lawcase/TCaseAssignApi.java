package com.chinatower.product.legalms.modules.dispute.api.lawcase;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.dispute.entity.lawcase.TCaseAssign;
import com.chinatower.product.legalms.modules.dispute.vo.issue.ListParam;
import com.chinatower.product.legalms.modules.dispute.vo.querylist.TCaseAssignListVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowVO;

public interface TCaseAssignApi {
	
	@PostMapping("/tCaseAssign/findTCaseAssign")
	ProcessResult findTCaseAssign(ListParam param);
	
	@GetMapping("/tCaseAssign/findOne")
	ProcessResult findOne(String assignId, String actId);
	
	@PostMapping("/tCaseAssign/addTCaseAssign")
	ProcessResult addTCaseAssign(AddFlowVO<TCaseAssign> vo);
	
	@PostMapping("/tCaseAssign/tempTCaseAssign")
	ProcessResult tempTCaseAssign(AddFlowVO<TCaseAssign> vo);
	
	@PostMapping("/tCaseAssign/updateTCaseAssign")
	ProcessResult updateTCaseAssign(TCaseAssign assign);
	
	@PostMapping("/tCaseAssign/deleteTCaseAssign")
	ProcessResult deleteTCaseAssign(List<String> assignIds);

	@PostMapping("/tCaseAssign/findTCaseAssignList")
	ProcessResult findTCaseAssignList(TCaseAssignListVO param);
	
	@PostMapping("/tCaseAssign/addTFlowLog")
	ProcessResult addTFlowLog(HttpServletRequest request);
	
	@GetMapping("/tCaseAssign/test")
	ProcessResult test();
}
