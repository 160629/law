package com.chinatower.product.legalms.modules.dispute.api.flow;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.dispute.vo.push.NotifAndUndone;
import com.eos.workflow.omservice.WFParticipant;

/**
 * <p>
 * 审批表 前端控制器
 * </p>
 *
 * @author wangyong
 * @since 2019-09-30
 */
public interface TFlowMainApi {
	
	@GetMapping("/tFlowMain/selectAll")
	ProcessResult selectAll();
	
	@GetMapping("/tFlowMain/queryNotifications")
	ProcessResult queryNotifications(String status);
	
	@GetMapping("/tFlowMain/queryNotifAndUndone")
	NotifAndUndone queryNotifAndUndone(String serviceId,String userCode,String flag,String userName,Integer pageNum,Integer pageSize);
	
	@PostMapping("/tFlowMain/confirmNotification(")
	ProcessResult confirmNotification(long notificationID);
	
	@GetMapping("/tFlowMain/getParticipants")
	ProcessResult getParticipants(long processDefID, String activityDefID);
	
	@GetMapping("/tFlowMain/getActivityDefineByName")
	ProcessResult getActivityDefineByName(String name, boolean hasParticipant);
	
	@GetMapping("/tFlowMain/getActivityDefineByNameOrID")
	ProcessResult getActivityDefineByNameOrID(String name, String activityName,Long actInstID);
	
	@PostMapping("/tFlowMain/updateParticipant")
	ProcessResult updateParticipant(long workItemID, List<WFParticipant> participants);
	
	@PostMapping("/tFlowMain/getPrimaryKey")
	ProcessResult getPrimaryKey();
}

