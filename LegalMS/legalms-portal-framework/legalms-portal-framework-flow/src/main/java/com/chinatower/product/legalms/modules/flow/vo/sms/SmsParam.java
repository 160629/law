package com.chinatower.product.legalms.modules.flow.vo.sms;

public class SmsParam extends SmsProperties{
	
	private String mttime;
	private String content; 
	private String phone;
	public String getMttime() {
		return mttime;
	}
	public void setMttime(String mttime) {
		this.mttime = mttime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public SmsParam(String name, String pwd,String mttime, String content, String phone) {
		super(name, pwd);
		this.mttime = mttime;
		this.content = content;
		this.phone = phone;
	}
	public SmsParam() {
	}
}
