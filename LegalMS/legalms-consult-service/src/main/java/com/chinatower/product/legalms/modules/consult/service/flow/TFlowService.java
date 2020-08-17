package com.chinatower.product.legalms.modules.consult.service.flow;

import java.util.List;
import java.util.Map;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.*;
import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowLog;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowLogVO;
import com.chinatower.product.legalms.modules.flow.vo.unview.AutoView;
import com.eos.workflow.data.WFProcessInst;

public interface TFlowService {
	
	public Object flowBack(Class clazz,String id,FlowUtil flowUtil);
	
	public int updateByPrimaryKeySelective(String approveItemType, Map<String, Object> map);
	
	public int autoCase(String approveItemType, String id,String pid, Long flowId,String flowName);

	public String getRoleLevel(List<Object> roleCodeList);

	public String  setEnterpType(UserInfo info);
	
	public void autoUnViewBefore(String title, TFlowLog record, FlowUtil flowUtil, UserInfo info,AutoView view); 
	
	public void insertBusinessLog(UserInfo info, String approveItemId, String fileBusinessAdvice, String activityDefId, Map<String, Object> businessMap, WFProcessInst processInst2) ;

	public void finishActivity(AddFlowLogVO vo, FlowUtil flowUtil, UserInfo info, TFlowLog record,WFProcessInst processInst2 ,
			long flowId,AutoView view);

	public int isCurUser(Long flowId);

	public void setIsUnView(String signDept, UserInfo info, Long flowId);

	public ProcessResult subFlowDrawBack(AddFlowLogVO vo);
}
