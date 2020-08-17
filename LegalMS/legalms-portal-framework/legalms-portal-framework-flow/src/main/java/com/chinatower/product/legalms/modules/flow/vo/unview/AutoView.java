package com.chinatower.product.legalms.modules.flow.vo.unview;

import java.util.List;

import com.eos.workflow.omservice.WFParticipant;

public class AutoView {
	//流程名称
	private String shortFlowName;
	//业务标题
	private String title;
	//业务id
	private String id;
	//登陆用户
	private WFParticipant login;
	//接收用户
	private List<WFParticipant> toers;
	//案件数量
	private Integer caseNum;
	//配置排序
	private Integer configOrder;
	//开始时间
	private String startTime;
	//结束时间
	private String endTime;
	//移交原由
	private String cause;
	
	
	
	//省名称  3.18修改
	private String stateName;
	//省公司名称
	private String stateOrgName;
	//地市公司名称
	private String cityOrgName;
	//部门名称
	private String deptName;
	//起草人
	private String putLoginName;
	//起草人部门
	private String putLoginDept;
	//转出单位
	private String assignOrgName;
	//主送单位
	private String seedOrgName;
	
    private String involvedAccountId;
    private String involvedAccountName;
    private String involvedDeptId;



	public String getInvolvedAccountName() {
		return involvedAccountName;
	}

	public void setInvolvedAccountName(String involvedAccountName) {
		this.involvedAccountName = involvedAccountName;
	}

	public String getInvolvedAccountId() {
		return involvedAccountId;
	}

	public AutoView setInvolvedAccountId(String involvedAccountId) {
		this.involvedAccountId = involvedAccountId;
		return this;
	}

	public String getInvolvedDeptId() {
		return involvedDeptId;
	}

	public AutoView setInvolvedDeptId(String involvedDeptId) {
		this.involvedDeptId = involvedDeptId;
		return this;
	}

	public String getPutLoginDept() {
		return putLoginDept;
	}

	public void setPutLoginDept(String putLoginDept) {
		this.putLoginDept = putLoginDept;
	}


	public String getShortFlowName() {
		return shortFlowName;
	}

	public AutoView setShortFlowName(String shortFlowName) {
		this.shortFlowName = shortFlowName;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public AutoView setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getId() {
		return id;
	}

	public AutoView setId(String id) {
		this.id = id;
		return this;
	}

	public WFParticipant getLogin() {
		return login;
	}

	public AutoView setLogin(WFParticipant login) {
		this.login = login;
		return this;
	}

	public List<WFParticipant> getToers() {
		return toers;
	}

	public AutoView setToers(List<WFParticipant> toers) {
		this.toers = toers;
		return this;
	}

	public Integer getCaseNum() {
		return caseNum;
	}

	public AutoView setCaseNum(Integer caseNum) {
		this.caseNum = caseNum;
		return this;
	}

	public Integer getConfigOrder() {
		return configOrder;
	}

	public AutoView setConfigOrder(Integer configOrder) {
		this.configOrder = configOrder;
		return this;
	}

	public String getStartTime() {
		return startTime;
	}

	public AutoView setStartTime(String startTime) {
		this.startTime = startTime;
		return this;
	}

	public String getEndTime() {
		return endTime;
	}

	public AutoView setEndTime(String endTime) {
		this.endTime = endTime;
		return this;
	}

	public String getCause() {
		return cause;
	}

	public AutoView setCause(String cause) {
		this.cause = cause;
		return this;
	}

	public String getStateName() {
		return stateName;
	}

	public AutoView setStateName(String stateName) {
		this.stateName = stateName;
		return this;
	}

	public String getStateOrgName() {
		return stateOrgName;
	}

	public AutoView setStateOrgName(String stateOrgName) {
		this.stateOrgName = stateOrgName;
		return this;
	}

	public String getCityOrgName() {
		return cityOrgName;
	}

	public AutoView setCityOrgName(String cityOrgName) {
		this.cityOrgName = cityOrgName;
		return this;
	}

	public String getDeptName() {
		return deptName;
	}

	public AutoView setDeptName(String deptName) {
		this.deptName = deptName;
		return this;
	}

	public String getPutLoginName() {
		return putLoginName;
	}

	public AutoView setPutLoginName(String putLoginName) {
		this.putLoginName = putLoginName;
		return this;
	}

	public String getAssignOrgName() {
		return assignOrgName;
	}

	public AutoView setAssignOrgName(String assignOrgName) {
		this.assignOrgName = assignOrgName;
		return this;
	}

	public String getSeedOrgName() {
		return seedOrgName;
	}

	public AutoView setSeedOrgName(String seedOrgName) {
		this.seedOrgName = seedOrgName;
		return this;
	}


	public AutoView(String shortFlowName, WFParticipant login, List<WFParticipant> toers, Integer configOrder,
			String startTime, String endTime) {
		super();
		this.shortFlowName = shortFlowName;
		this.login = login;
		this.toers = toers;
		this.configOrder = configOrder;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public AutoView(String shortFlowName, WFParticipant login, List<WFParticipant> toers, Integer caseNum,
			String cause) {
		super();
		this.shortFlowName = shortFlowName;
		this.login = login;
		this.toers = toers;
		this.caseNum = caseNum;
		this.cause = cause;
	}

	public AutoView(String shortFlowName, String title, String id, WFParticipant login,
			List<WFParticipant> toers) {
		super();
		this.shortFlowName = shortFlowName;
		this.title = title;
		this.id = id;
		this.login = login;
		this.toers = toers;
	}


	public AutoView() {
		super();
	}

	@Override
	public String toString() {
		return "AutoView [shortFlowName=" + shortFlowName + ", title=" + title + ", id=" + id + ", login=" + login
				+ ", toers=" + toers + ", caseNum=" + caseNum + ", configOrder=" + configOrder + ", startTime="
				+ startTime + ", endTime=" + endTime + ", cause=" + cause + ", stateName=" + stateName
				+ ", stateOrgName=" + stateOrgName + ", cityOrgName=" + cityOrgName + ", deptName=" + deptName
				+ ", putLoginName=" + putLoginName + ", putLoginDept=" + putLoginDept + ", assignOrgName="
				+ assignOrgName + ", seedOrgName=" + seedOrgName + ", involvedAccountId=" + involvedAccountId
				+ ", involvedAccountName=" + involvedAccountName + ", involvedDeptId=" + involvedDeptId + "]";
	}
}
