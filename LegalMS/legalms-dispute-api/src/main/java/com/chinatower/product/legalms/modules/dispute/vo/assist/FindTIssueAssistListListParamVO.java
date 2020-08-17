package com.chinatower.product.legalms.modules.dispute.vo.assist;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

public class FindTIssueAssistListListParamVO {

    @ApiModelProperty(value="标题",name="title")
    private String title;

    @ApiModelProperty(value="执行单位名称",name="jointlyUnitName")
    private String jointlyUnitName;

    @ApiModelProperty(value="执行单位ID",name="jointlyUnitId")
    private String jointlyUnitId;

    @ApiModelProperty(value="执行类型",name="assistType")
    private String assistType;

    @ApiModelProperty(value = "执行法院名称", name = "courtName")
    private String courtName;

    @ApiModelProperty(value = "编号", name = "code")
    private String code;

    @ApiModelProperty(value = "承办部门名称", name = "jointlyDeptName")
    private String jointlyDeptName;

    @ApiModelProperty(value = "承办部门ID", name = "jointlyDeptId")
    private String jointlyDeptId;

    @ApiModelProperty(value = "提交时间-开始时间", name = "startTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty(value = "提交时间-结束时间", name = "endTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    @ApiModelProperty(value = "被执行人(供应商)名称", name = "supplierName")
    private String supplierName;

    @ApiModelProperty(value = "创建人单位名称", name = "orgName")
    private String orgName;

    @ApiModelProperty(value = "创建人单位Id, 存的是path", name = "orgId")
    private String orgId;

    @ApiModelProperty(value = "创建人单位Id, 存的是path", name = "createOrgId")
    private String createOrgId;

    @ApiModelProperty(value = "单据状态", name = "state")
    private String state;

    @ApiModelProperty(value = "页码", name = "pageNum")
    public Integer pageNum;

    @ApiModelProperty(value = "单页数量", name = "pageSize")
    public Integer pageSize;

    @ApiModelProperty(value = "创建人id", name = "loginAcct")
    private String loginAcct;

    @ApiModelProperty(value = "上级公司", name = "preOrgId")
    List<String> preOrgId;

    @Override
    public String toString() {
        return "FindTIssueAssistListListParamVO{" +
                "title='" + title + '\'' +
                ", jointlyUnitName='" + jointlyUnitName + '\'' +
                ", jointlyUnitId='" + jointlyUnitId + '\'' +
                ", assistType='" + assistType + '\'' +
                ", courtName='" + courtName + '\'' +
                ", code='" + code + '\'' +
                ", jointlyDeptName='" + jointlyDeptName + '\'' +
                ", jointlyDeptId='" + jointlyDeptId + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", supplierName='" + supplierName + '\'' +
                ", orgName='" + orgName + '\'' +
                ", orgId='" + orgId + '\'' +
                ", createOrgId='" + createOrgId + '\'' +
                ", state='" + state + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", loginAcct='" + loginAcct + '\'' +
                ", preOrgId=" + preOrgId +
                '}';
    }

    public String getCreateOrgId() {
        return createOrgId;
    }

    public void setCreateOrgId(String createOrgId) {
        this.createOrgId = createOrgId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public List<String> getPreOrgId() {
        return preOrgId;
    }

    public void setPreOrgId(List<String> preOrgId) {
        this.preOrgId = preOrgId;
    }

    public String getLoginAcct() {
        return loginAcct;
    }

    public void setLoginAcct(String loginAcct) {
        this.loginAcct = loginAcct;
    }

    public String getJointlyUnitId() {
        return jointlyUnitId;
    }

    public void setJointlyUnitId(String jointlyUnitId) {
        this.jointlyUnitId = jointlyUnitId;
    }

    public String getJointlyDeptId() {
        return jointlyDeptId;
    }

    public void setJointlyDeptId(String jointlyDeptId) {
        this.jointlyDeptId = jointlyDeptId;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJointlyUnitName() {
        return jointlyUnitName;
    }

    public void setJointlyUnitName(String jointlyUnitName) {
        this.jointlyUnitName = jointlyUnitName;
    }

    public String getAssistType() {
        return assistType;
    }

    public void setAssistType(String assistType) {
        this.assistType = assistType;
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getJointlyDeptName() {
        return jointlyDeptName;
    }

    public void setJointlyDeptName(String jointlyDeptName) {
        this.jointlyDeptName = jointlyDeptName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


}
