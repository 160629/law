package com.chinatower.product.legalms.modules.dispute.api.issue;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.dispute.entity.issue.TIssueGuide;
import com.chinatower.product.legalms.modules.dispute.vo.issue.ListParam;
import com.chinatower.product.legalms.modules.dispute.vo.querylist.TIssueGuideListVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowVO;

/**
 * <p>
 * 引诉信息表 前端控制器
 * </p>
 *
 * @author wangyong
 * @since 2019-09-30
 */
public interface TIssueGuideApi {
	
	@PostMapping("/tIssueGuide/findTIssueGuide")
	ProcessResult findTIssueGuide(ListParam param);
	
	@PostMapping("/tIssueGuide/selectAllByCase")
	ProcessResult selectAllByCase(ListParam param);
	
	@GetMapping("/tIssueGuide/selectByPrimaryKey")
	ProcessResult selectByPrimaryKey(String guideId);
	
	@GetMapping("/tIssueGuide/findOne")
	ProcessResult findOne(String guideId, String actId);
	
	@PostMapping("/tIssueGuide/addTIssueGuide")
	ProcessResult addTIssueGuide(AddFlowVO<TIssueGuide> vo);
	
	@PostMapping("/tIssueGuide/tempTIssueGuide")
	ProcessResult tempTIssueGuide(AddFlowVO<TIssueGuide> vo);
	
	@PostMapping("/tIssueGuide/updateTIssueGuide")
	ProcessResult updateTIssueGuide(TIssueGuide guide);
	
	@PostMapping("/tIssueGuide/deleteTIssueGuide")
	ProcessResult deleteTIssueGuide(List<String> guideIds);
	
	@PostMapping("/tIssueGuide/test")
	void test(HttpServletResponse response,HashMap<String,Object> map) throws IOException ;
	
	@PostMapping("/tIssueGuide/selectByType")
	Map<String,Object> selectByType(String tableName,String fieldName, String fieldValue);

	/*引诉纠纷综合查询*/
	@PostMapping("/tIssueGuide/findTIssueGuideList")
	ProcessResult findTIssueGuideList(TIssueGuideListVO param);
	
	//诉讼审批接口
	@PostMapping("/tIssueGuide/addTFlowLog")
	ProcessResult addTFlowLog(HttpServletRequest request);
}

