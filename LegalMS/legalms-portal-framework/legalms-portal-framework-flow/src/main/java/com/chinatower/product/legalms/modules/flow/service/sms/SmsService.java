package com.chinatower.product.legalms.modules.flow.service.sms;

import com.chinatower.product.legalms.modules.flow.entity.sms.TPubSmsTxd;

public interface SmsService {
	
	String sendsms(TPubSmsTxd txd);
	
	TPubSmsTxd selectSmsTxdById(Long txdId);
}
