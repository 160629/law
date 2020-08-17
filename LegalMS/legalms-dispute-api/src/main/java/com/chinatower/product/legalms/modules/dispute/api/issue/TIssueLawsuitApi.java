package com.chinatower.product.legalms.modules.dispute.api.issue;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.dispute.entity.issue.TIssueLawsuit;
import com.chinatower.product.legalms.modules.dispute.vo.issue.ListParam;
import com.chinatower.product.legalms.modules.dispute.vo.querylist.TIssueLawsuitListVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowVO;

/**
 * <p>
 * 引诉信息表 前端控制器
 * </p>
 *
 * @author wangyong
 * @since 2019-09-30
 */
public interface TIssueLawsuitApi {
	
	@PostMapping("/tIssueLawsuit/findTIssueLawsuit")
	ProcessResult findTIssueLawsuit(ListParam param);
	
	@GetMapping("/tIssueLawsuit/selectByPrimaryKey")
	ProcessResult selectByPrimaryKey(String lawsuitId);
	
	@GetMapping("/tIssueLawsuit/findOne")
	ProcessResult findOne(String lawsuitId, String actId);
	
	@PostMapping("/tIssueLawsuit/addTIssueLawsuit")
	ProcessResult addTIssueLawsuit(AddFlowVO<TIssueLawsuit> vo);
	
	@PostMapping("/tIssueLawsuit/tempTIssueLawsuit")
	ProcessResult tempTIssueLawsuit(AddFlowVO<TIssueLawsuit> vo);
	
	@PostMapping("/tIssueLawsuit/updateTIssueLawsuit")
	ProcessResult updateTIssueLawsuit(TIssueLawsuit lawsuit);
	
	@PostMapping("/tIssueLawsuit/deleteTIssueLawsuit")
	ProcessResult deleteTIssueLawsuit(List<String> lawsuitIds);
	
	@PostMapping("/tIssueLawsuit/selectAllByCase")
	ProcessResult selectAllByCase(ListParam param);
	
	@PostMapping("/tIssueLawsuit/test")
	ProcessResult test(String approveItemType, String id,HttpServletRequest request);

	/*案件协办综合查询*/
	@PostMapping("/tIssueLawsuit/findTIssueLawsuitList")
	ProcessResult findTIssueLawsuitList(TIssueLawsuitListVO param);
	
	@PostMapping("/tIssueLawsuit/addTFlowLog")
	ProcessResult addTFlowLog(HttpServletRequest request);
	
	@PostMapping("/tIssueLawsuit/updateCaseId")
	ProcessResult updateCaseId(String caseId,String id,String caseTitle, Integer createCaseType);
}

