package com.chinatower.product.legalms.modules.dispute.api.flow;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.flow.entity.flow.FlowChart;
import com.chinatower.product.legalms.modules.flow.vo.common.TaskVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowLogVO;

/**
 * <p>
 * 审批人员表 前端控制器
 * </p>
 *
 * @author wangyong
 * @since 2019-09-30
 */
public interface TFlowLogApi {
	
	@PostMapping("/tFlowLog/addTFlowLog")
	ModelAndView addTFlowLog(AddFlowLogVO vo);
	
	@GetMapping("/tFlowLog/findTFlowLog")
	ProcessResult findTFlowLog(Integer pageNum, Integer pageSize);

	@GetMapping("/tFlowLog/selectMainFlowLogs")
	ProcessResult selectMainFlow(String businessId, String businessType);

	@GetMapping("/tFlowLog/selectSubFlowLogs")
	ProcessResult selectSubFlow(String subProcessFlowLogId);

	@PostMapping("/tFlowLog/nullifyFlow")
	ProcessResult nullifyFlow(Map<String, Object> businessMap);
	
	@GetMapping("/tFlowLog/downloadExcel")
	void downloadExcel(Integer pageNum, Integer pageSize,HttpServletResponse response)throws IOException;

	@PostMapping("/tFlowLog/tempSelective")
	ProcessResult tempSelective(AddFlowLogVO addFlowLogVO);

	@PostMapping("/tFlowLog/getFlowChart")
	ProcessResult getFlowChart(FlowChart flowChart);

	@PostMapping("/tFlowLog/getCompleteFlow")
	ProcessResult getCompleteFlow(TaskVO taskVO);


	@PostMapping("/TFlowApi/flowDrawBack")
	public ProcessResult flowDrawBack(Long activityInstId);
}

