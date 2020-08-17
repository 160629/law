package com.chinatower.product.legalms.modules.dispute.api.flow;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.flow.entity.unview.TFlowUnview;
import com.chinatower.product.legalms.modules.flow.vo.sms.SmsLogParam;
import com.chinatower.product.legalms.modules.flow.vo.unview.TFlowUnviewParam;

/**
 * <p>
 * 审批表 前端控制器
 * </p>
 *
 * @author wangyong
 * @since 2019-09-30
 */
public interface TFlowUnviewApi {
	
	@PostMapping("/TFlowUnvie/selectAll")
	ProcessResult selectAll(TFlowUnviewParam record);
	
	@GetMapping("/TFlowUnvie/selectViewTypes")
	ProcessResult selectViewTypes(String viewStatus);
	
	@PostMapping("/TFlowUnvie/addOrUpdate")
	ProcessResult addOrUpdate(TFlowUnview record);
	
	@PostMapping("/TFlowUnvie/updateStatus")
	ProcessResult updateStatus(List<String> viewIds);
	
	@GetMapping("/TFlowUnvie/findOne")
	ProcessResult findOne(String viewId);
	
	@PostMapping("/smsController/backMsg")
	String backMsg(String msgtype, String phone, String receivetime, String sendtime, String sendid,
			String state, String content);
	
	@PostMapping("/smsController/smsLogList")
	ProcessResult smsLogList(SmsLogParam param);
	
	@GetMapping("/smsController/selectTypes")
	ProcessResult selectTypes();

}

