package com.chinatower.product.legalms.modules.flow.vo.sms;

import com.chinatower.product.legalms.common.StringUtil;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

public class SmsLogList {
	private String mttime;
	private String content;
	private String txdCode;
	private String loginAcct;
	private String loginName;
	private String businessType;
	private String businessTitle;
	private String phone;
	private String ackStatus;
	private String sendId;
	private String status;
	public String getMttime() {
		return mttime;
	}
	public void setMttime(String mttime) {
		if(!StringUtil.isEmpty(mttime)) {
			DateTime parse = DateUtil.parse(mttime, "yyyyMMddHHmmss");
			this.mttime = DateUtil.format(parse,  "yyyy-MM-dd HH:mm:ss");
		}else {
			this.mttime = mttime;
		}
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTxdCode() {
		return txdCode;
	}
	public void setTxdCode(String txdCode) {
		this.txdCode = txdCode;
	}
	public String getLoginAcct() {
		return loginAcct;
	}
	public void setLoginAcct(String loginAcct) {
		this.loginAcct = loginAcct;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getBusinessTitle() {
		return businessTitle;
	}
	public void setBusinessTitle(String businessTitle) {
		this.businessTitle = businessTitle;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAckStatus() {
		return ackStatus;
	}
	public void setAckStatus(String ackStatus) {
		this.ackStatus = ackStatus;
	}
	public String getSendId() {
		return sendId;
	}
	public void setSendId(String sendId) {
		this.sendId = sendId;
	}
	public String getStatus() {
		if("200".equals(this.txdCode) && "发送成功".equals(this.ackStatus)) {
			return "是";
		}
		return "否";
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "SmsLogList [mttime=" + mttime + ", content=" + content + ", txdCode=" + txdCode + ", loginAcct="
				+ loginAcct + ", loginName=" + loginName + ", businessType=" + businessType + ", businessTitle="
				+ businessTitle + ", phone=" + phone + ", ackStatus=" + ackStatus + ", sendId=" + sendId + ", status="
				+ status + "]";
	}

	
	
}
