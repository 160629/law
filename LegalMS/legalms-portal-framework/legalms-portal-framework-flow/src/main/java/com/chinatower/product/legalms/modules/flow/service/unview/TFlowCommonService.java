package com.chinatower.product.legalms.modules.flow.service.unview;

import java.util.List;
import java.util.Map;

import com.chinatower.product.legalms.common.FlowUtil;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.flow.entity.delegate.TFlowDelegate;
import com.chinatower.product.legalms.modules.flow.entity.sms.TPubSmsTxd;
import com.chinatower.product.legalms.modules.flow.entity.unview.TFlowUnview;
import com.chinatower.product.legalms.modules.flow.vo.flow.OrgParticipantVO;
import com.chinatower.product.legalms.modules.flow.vo.unview.AutoView;
import com.eos.workflow.data.WFActivityInst;
import com.eos.workflow.omservice.WFParticipant;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.com.weaver.ofs.webservices.vo.UnviewPushVO;

public interface TFlowCommonService {
	// 流程生成待阅
	public int autoUnView(String shortFlowName, String title, String id, WFParticipant login,
			List<WFParticipant> toers);

	// 案件移交生成待阅
	public int autoUnView(String shortFlowName, WFParticipant login, List<WFParticipant> toers, Integer caseNum,
			String cause);

	// 委托生成待阅
	public int autoUnView(String shortFlowName, WFParticipant login, List<WFParticipant> toers, String startTime,
			String endTime, Integer configOrder);

	public int autoUnView(AutoView autoUnView, UserInfo info);

	public int autoUnView(AutoView autoUnView);

	public TFlowDelegate verifyDelegate(String loginAcct);

	public void delegateWorkItem(FlowUtil flowUtil, WFActivityInst lastActivityInstByActivityID);

	public void setApprovalAccountId(List<OrgParticipantVO> orgs, WFParticipant participant);

	public List<TFlowDelegate> selectDelegates(TFlowDelegate record);

	public String getRoleLevel(List<Object> roleCodeList);

	public int updateByPrimaryKeyStatus(String itemType, String itemId, String itemName, String itemStatus);

	Map<String, Object> test();

	String txt1(TPubSmsTxd bySendId);

	String txt2(List<TPubSmsTxd> contents);

	String txt3(List<TPubSmsTxd> contents);
	
	String unviewPush(List<TFlowUnview> temps, String lawKafka);
	
	String unviewDonePush(List<TFlowUnview> list);
	
	String unviewDonePushPre(List<String> viewIds);
	
	void unviewDonePushRetry(List<TFlowUnview> list);
	
	String unviewPush1(List<UnviewPushVO> arrayList,ObjectMapper objectMapper) ;
	
	String unviewDonePush1(List<UnviewPushVO> arrayList,ObjectMapper objectMapper) ;
	
	List<TFlowUnview>  pushSelectList(String viewStatus,String pushFlag);
	
	void updateLawKafka(String status);
	
	String getLawKafka();

}
