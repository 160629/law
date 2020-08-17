package com.chinatower.product.legalms.modules.dispute.service.flow;

import java.util.List;

import com.chinatower.product.legalms.modules.flow.entity.unview.TFlowUnview;
import com.chinatower.product.legalms.modules.flow.vo.sms.SmsLogList;
import com.chinatower.product.legalms.modules.flow.vo.sms.SmsLogParam;
import com.chinatower.product.legalms.modules.flow.vo.unview.TFlowUnviewParam;

public interface TFlowUnviewService {
    List<TFlowUnview> selectAll(TFlowUnviewParam record);

	int addOrUpdate(TFlowUnview record);

	List<String> selectViewTypes(String viewStatus);

	int updateStatus(List<String> viewIds);

	TFlowUnview findOne(String viewId);
	
	void backMsg(String msgtype, String phone, String receivetime, String sendtime,
			String sendid,String state, String content);
	
	List<SmsLogList> smsLogList(SmsLogParam param);

	List<String> selectTypes();
}
