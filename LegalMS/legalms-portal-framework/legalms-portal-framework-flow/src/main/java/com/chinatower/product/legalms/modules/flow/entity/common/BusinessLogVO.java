package com.chinatower.product.legalms.modules.flow.entity.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.*;

@Table(name = "t_business_log")
public class BusinessLogVO {
    @Id
    @ApiModelProperty(value="id",name="id")
    private String id;

    @ApiModelProperty(value = "approveItemId",name="业务表id")
    private String approveItemId;

    @ApiModelProperty(value = "approveItemType",name="业务类型")
    private String approveItemType;

    @ApiModelProperty(value = "fileId",name = "文件id")
    private String fileId;

    @ApiModelProperty(value = "business_field1",name="businessField1")
    private String businessField1;

    @ApiModelProperty(value = "business_field2",name="businessField2")
    private String businessField2;

    @ApiModelProperty("公司名称")
    private String orgName;

    @ApiModelProperty("公司id")
    private String orgId;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("部门ID")
    private String deptId;

    @ApiModelProperty("用户名称")
    private String loginName;

    @ApiModelProperty("用户账号")
    private String loginAcct;

    @ApiModelProperty("修改时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;
    @ApiModelProperty(value = "页码", name = "pageNum")
    public Integer pageNum;
    @ApiModelProperty(value = "单页数量", name = "pageSize")
    public Integer pageSize;

    @ApiModelProperty(value="附件信息",name="files")
    private List<Map<String, Object>> files;

    @ApiModelProperty(value="活动定义id",name="actId")
    private String actId;

    @ApiModelProperty(value="活动定义名称",name="actName")
    private String actName;

    @ApiModelProperty(value="活动实例id",name="actInstId")
    private String actInstId;

    @ApiModelProperty(value="父活动实例id",name="actParentInstId")
    private String actParentInstId;

    @ApiModelProperty(value="流程实例id",name="flowId")
    private String flowId;

    @ApiModelProperty(value="父流程实例id",name="flowPid")
    private String flowPid;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @ApiModelProperty(value="执行完毕时间",name="actInstId")
    private Date completedTime;

    @ApiModelProperty(value="排序字段",name="order")
    private Integer sort;

    private List<BusinessLogVO> subBusinessLog;

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getFlowPid() {
        return flowPid;
    }

    public void setFlowPid(String flowPid) {
        this.flowPid = flowPid;
    }

    public List<BusinessLogVO> getSubBusinessLog() {
        return subBusinessLog;
    }

    public void setSubBusinessLog(List<BusinessLogVO> subBusinessLog) {
        this.subBusinessLog = subBusinessLog;
    }

    public String getActParentInstId() {
        return actParentInstId;
    }

    public void setActParentInstId(String actParentInstId) {
        this.actParentInstId = actParentInstId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(Date completedTime) {
        this.completedTime = completedTime;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getActInstId() {
        return actInstId;
    }

    public void setActInstId(String actInstId) {
        this.actInstId = actInstId;
    }

    public String getApproveItemType() {
        return approveItemType;
    }

    public void setApproveItemType(String approveItemType) {
        this.approveItemType = approveItemType;
    }

    public List<Map<String, Object>> getFiles() {
        return files;
    }

    public void setFiles(List<Map<String, Object>> files) {
        this.files = files;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApproveItemId() {
        return approveItemId;
    }

    public void setApproveItemId(String approveItemId) {
        this.approveItemId = approveItemId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getBusinessField1() {
        return businessField1;
    }

    public void setBusinessField1(String businessField1) {
        this.businessField1 = businessField1;
    }

    public String getBusinessField2() {
        return businessField2;
    }

    public void setBusinessField2(String businessField2) {
        this.businessField2 = businessField2;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginAcct() {
        return loginAcct;
    }

    public void setLoginAcct(String loginAcct) {
        this.loginAcct = loginAcct;
    }

    @Override
    public String toString() {
        return "BusinessLogVO{" +
                "id='" + id + '\'' +
                ", approveItemId='" + approveItemId + '\'' +
                ", approveItemType='" + approveItemType + '\'' +
                ", fileId='" + fileId + '\'' +
                ", businessField1='" + businessField1 + '\'' +
                ", businessField2='" + businessField2 + '\'' +
                ", orgName='" + orgName + '\'' +
                ", orgId='" + orgId + '\'' +
                ", deptName='" + deptName + '\'' +
                ", deptId='" + deptId + '\'' +
                ", loginName='" + loginName + '\'' +
                ", loginAcct='" + loginAcct + '\'' +
                ", updateTime=" + updateTime +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", files=" + files +
                ", actId='" + actId + '\'' +
                ", actName='" + actName + '\'' +
                ", actInstId='" + actInstId + '\'' +
                ", actParentInstId='" + actParentInstId + '\'' +
                ", flowId='" + flowId + '\'' +
                ", flowPid='" + flowPid + '\'' +
                ", completedTime='" + completedTime + '\'' +
                ", sort=" + sort +
                ", subBusinessLog=" + subBusinessLog +
                '}';
    }
}
