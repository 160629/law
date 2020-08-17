package com.chinatower.product.legalms.modules.flow.vo.sms;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="sms-properties")
public class SmsProperties {
	private  String name;
	private  String pwd;
	private  String url;
	private  String sign;
	private  String webServiceUrl;
	
	
	public String getWebServiceUrl() {
		return webServiceUrl;
	}
	public void setWebServiceUrl(String webServiceUrl) {
		this.webServiceUrl = webServiceUrl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public SmsProperties(String name, String pwd) {
		super();
		this.name = name;
		this.pwd = pwd;

	}
	public SmsProperties() {
		super();
	}
	
	

}
