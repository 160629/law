package com.chinatower.product.legalms.modules.flow.vo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("待办、已办、办结信息")
public class TaskVO {

	@ApiModelProperty("标题")
    String title;

	@ApiModelProperty("编号")
    String code;

	@ApiModelProperty("类别")
    String type;

	@ApiModelProperty("上一步处理人/当前处理人")
    String person;

	@ApiModelProperty("接收时间/送出时间")
    String time;

	@ApiModelProperty("开始时间")
	String startTime;

	@ApiModelProperty("结束时间")
	String endTime;

	@ApiModelProperty("开始位置")
	Integer pageNum;

	@ApiModelProperty("长度")
	Integer pageSize = 10;

	@ApiModelProperty("当前处理环节")
    String work;

	@ApiModelProperty("主账号/兼职账号")
	String account;

	@ApiModelProperty("流程实例ID")
    String processInstId;

	@ApiModelProperty("当前节点活动定义ID")
	String currActivityDefId;

	@ApiModelProperty("当前节点活动定义名称")
	String currActivityDefName;

	@ApiModelProperty("待办已办标识")
	String chooseType;

	@ApiModelProperty("活动实例ID")
	String activityInstId;

	@ApiModelProperty("pid")
	String pid;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public TaskVO setTitle(String title) {
		this.title = title;
		return this;
	}

	public TaskVO setCode(String code) {
		this.code = code;
		return this;
	}

	public TaskVO setType(String type) {
		this.type = type;
		return this;
	}

	public TaskVO setPerson(String person) {
		this.person = person;
		return this;
	}

	public TaskVO setTime(String time) {
		this.time = time;
		return this;
	}

	public TaskVO setStartTime(String startTime) {
		this.startTime = startTime;
		return this;
	}

	public TaskVO setEndTime(String endTime) {
		this.endTime = endTime;
		return this;
	}

	public TaskVO setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
		return this;
	}

	public TaskVO setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
		return this;
	}

	public TaskVO setWork(String work) {
		this.work = work;
		return this;
	}

	public TaskVO setAccount(String account) {
		this.account = account;
		return this;
	}

	public TaskVO setProcessInstId(String processInstId) {
		this.processInstId = processInstId;
		return this;
	}

	public TaskVO setCurrActivityDefId(String currActivityDefId) {
		this.currActivityDefId = currActivityDefId;
		return this;
	}

	public TaskVO setCurrActivityDefName(String currActivityDefName) {
		this.currActivityDefName = currActivityDefName;
		return this;
	}

	public TaskVO setChooseType(String chooseType) {
		this.chooseType = chooseType;
		return this;
	}

	public TaskVO setActivityInstId(String activityInstId) {
		this.activityInstId = activityInstId;
		return this;
	}

	public TaskVO() {
		super();
	}

	public String getTitle() {
		return title;
	}

	public String getCode() {
		return code;
	}

	public String getType() {
		return type;
	}

	public String getPerson() {
		return person;
	}

	public String getTime() {
		return time;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public String getWork() {
		return work;
	}

	public String getAccount() {
		return account;
	}

	public String getProcessInstId() {
		return processInstId;
	}

	public String getCurrActivityDefId() {
		return currActivityDefId;
	}

	public String getCurrActivityDefName() {
		return currActivityDefName;
	}

	public String getChooseType() {
		return chooseType;
	}

	public String getActivityInstId() {
		return activityInstId;
	}

	@Override
	public String toString() {
		return "TaskVO{" +
				"title='" + title + '\'' +
				", code='" + code + '\'' +
				", type='" + type + '\'' +
				", person='" + person + '\'' +
				", time='" + time + '\'' +
				", startTime='" + startTime + '\'' +
				", endTime='" + endTime + '\'' +
				", pageNum=" + pageNum +
				", pageSize=" + pageSize +
				", work='" + work + '\'' +
				", account='" + account + '\'' +
				", processInstId='" + processInstId + '\'' +
				", currActivityDefId='" + currActivityDefId + '\'' +
				", currActivityDefName='" + currActivityDefName + '\'' +
				", chooseType='" + chooseType + '\'' +
				", activityInstId='" + activityInstId + '\'' +
				", pid='" + pid + '\'' +
				'}';
	}
}
