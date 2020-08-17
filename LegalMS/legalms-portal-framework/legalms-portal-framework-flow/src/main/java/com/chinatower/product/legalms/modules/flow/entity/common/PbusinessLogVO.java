package com.chinatower.product.legalms.modules.flow.entity.common;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Table;
import java.util.*;

@Table(name = "t_business_log")
public class PbusinessLogVO {
    @ApiModelProperty(value="流程ID",name="flowId")
    private Long flowId;

    @ApiModelProperty(value="流程名称",name="flowName")
    private String flowName;

    @ApiModelProperty(value="流程状态",name="flowStatus")
    private String flowStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value="创建时间",name="createTime")
    private Date createTime;

    @ApiModelProperty(value="创建ID",name="createUserId")
    private String createUserId;

    @ApiModelProperty(value="创建人名称",name="createUserName")
    private String createUserName;

    @ApiModelProperty(value="审批事项ID",name="approveItemId")
    private String approveItemId;

    @ApiModelProperty(value="审批事项类型",name="approveItemType")
    private String approveItemType;

    @ApiModelProperty(value="审批事项名称",name="approveItemName")
    private String approveItemName;

    @ApiModelProperty(value="部门名称",name="orgName")
    private String orgName;

    @ApiModelProperty(value="部门ID",name="orgId")
    private String orgId;

    @ApiModelProperty(value = "跳转页面标识", name = "urlId")
    private String moduleName;

    @ApiModelProperty(value="版本id",name="versionId")
    private String versionId;

    List<BusinessLogVO> businessLogVOS;

    public List<BusinessLogVO> getBusinessLogVOS() {
        return businessLogVOS;
    }

    public void setBusinessLogVOS(List<BusinessLogVO> businessLogVOS) {
        this.businessLogVOS = businessLogVOS;
    }

    public Long getFlowId() {
        return flowId;
    }

    public void setFlowId(Long flowId) {
        this.flowId = flowId;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getFlowStatus() {
        return flowStatus;
    }

    public void setFlowStatus(String flowStatus) {
        this.flowStatus = flowStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getApproveItemId() {
        return approveItemId;
    }

    public void setApproveItemId(String approveItemId) {
        this.approveItemId = approveItemId;
    }

    public String getApproveItemType() {
        return approveItemType;
    }

    public void setApproveItemType(String approveItemType) {
        this.approveItemType = approveItemType;
    }

    public String getApproveItemName() {
        return approveItemName;
    }

    public void setApproveItemName(String approveItemName) {
        this.approveItemName = approveItemName;
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

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }
}
