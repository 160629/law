package com.chinatower.product.legalms.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="push-service")
public class ScheduledProperties {
	

	private  String lawSumService;
	private  String subjectMatterService;
	private  String addAutoCaseMain;
	private  String formurl;
	private  String formName;
	private  String unviewUrl;
	private  String workItemUrl;

	
	

	public String getUnviewUrl() {
		return unviewUrl;
	}
	public void setUnviewUrl(String unviewUrl) {
		this.unviewUrl = unviewUrl;
	}
	public String getWorkItemUrl() {
		return workItemUrl;
	}
	public void setWorkItemUrl(String workItemUrl) {
		this.workItemUrl = workItemUrl;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public String getFormurl() {
		return formurl;
	}
	public void setFormurl(String formurl) {
		this.formurl = formurl;
	}
	public String getAddAutoCaseMain() {
		return addAutoCaseMain;
	}
	public void setAddAutoCaseMain(String addAutoCaseMain) {
		this.addAutoCaseMain = addAutoCaseMain;
	}
	public String getLawSumService() {
		return lawSumService;
	}
	public void setLawSumService(String lawSumService) {
		this.lawSumService = lawSumService;
	}
	public String getSubjectMatterService() {
		return subjectMatterService;
	}
	public void setSubjectMatterService(String subjectMatterService) {
		this.subjectMatterService = subjectMatterService;
	}
	
	
}
