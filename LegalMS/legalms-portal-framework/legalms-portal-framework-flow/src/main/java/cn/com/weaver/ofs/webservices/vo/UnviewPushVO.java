package cn.com.weaver.ofs.webservices.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UnviewPushVO {
	
	private String syscode="CHNTLEGALMS6Read";
	private String flowid;
	private String requestname;
	private String workflowname;
	private String nodename;
	private String pcurl;
	private String appurl;
	private String creator;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
	private Date createdatetime;
	private String receiver;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
	private Date receivedatetime;
	private String pushFlag;
	
	public String getPushFlag() {
		return pushFlag;
	}
	public void setPushFlag(String pushFlag) {
		this.pushFlag = pushFlag;
	}
	public String getSyscode() {
		return syscode;
	}
	public void setSyscode(String syscode) {
		this.syscode = syscode;
	}
	public String getFlowid() {
		return flowid;
	}
	public void setFlowid(String flowid) {
		this.flowid = flowid;
	}
	public String getRequestname() {
		return requestname;
	}
	public void setRequestname(String requestname) {
		this.requestname = requestname;
	}
	public String getWorkflowname() {
		return workflowname;
	}
	public void setWorkflowname(String workflowname) {
		this.workflowname = workflowname;
	}
	public String getNodename() {
		return nodename;
	}
	public void setNodename(String nodename) {
		this.nodename = nodename;
	}
	public String getPcurl() {
		return pcurl;
	}
	public void setPcurl(String pcurl) {
		this.pcurl = pcurl;
		this.appurl = pcurl;
	}
	public String getAppurl() {
		return appurl;
	}
	public void setAppurl(String appurl) {
		this.appurl = appurl;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getCreatedatetime() {
		return createdatetime;
	}
	public void setCreatedatetime(Date createdatetime) {
		this.createdatetime = createdatetime;
		this.receivedatetime= createdatetime;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public Date getReceivedatetime() {
		return receivedatetime;
	}
	public void setReceivedatetime(Date receivedatetime) {
		this.receivedatetime = receivedatetime;
	}
	@Override
	public String toString() {
		return "UnviewPushVO [syscode=" + syscode + ", flowid=" + flowid + ", requestname=" + requestname
				+ ", workflowname=" + workflowname + ", nodename=" + nodename + ", pcurl=" + pcurl + ", appurl="
				+ appurl + ", creator=" + creator + ", createdatetime=" + createdatetime + ", receiver=" + receiver
				+ ", receivedatetime=" + receivedatetime + "]";
	}
	public UnviewPushVO(String flowid, String requestname, String workflowname, String nodename, String creator,
			String receiver) {
		super();
		this.flowid = flowid;
		this.requestname = requestname;
		this.workflowname = workflowname;
		this.nodename = nodename;
		this.creator = creator;
		this.receiver = receiver;
	}
	public UnviewPushVO() {
		super();
	}
	public UnviewPushVO(String flowid, String requestname, String workflowname, String nodename, String receiver) {
		super();
		this.flowid = flowid;
		this.requestname = requestname;
		this.workflowname = workflowname;
		this.nodename = nodename;
		this.receiver = receiver;
	}
	
}
