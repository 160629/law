package com.chinatower.product.legalms.modules.flow.entity.flow;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Table;

/**
 * @author 刘晓亮
 * @create 2020-01-10
 */
@Table(name = "t_business_end")
@ApiModel("办结表")
public class TBusinessEnd {
    @ApiModelProperty(name = "id", value = "id")
    String id;
    @ApiModelProperty(name = "businessTitle", value = "业务标题")
    String businessTitle;
    @ApiModelProperty(name = "businessCode", value = "业务编号")
    String businessCode;
    @ApiModelProperty(name = "bussinessType", value = "业务类型")
    String businessType;
    @ApiModelProperty(name = "flowStatus", value = "流程状态")
    String flowStatus;
    @ApiModelProperty(name = "updateTime", value = "办结时间")
    String updateTime;
    @ApiModelProperty(name = "approveItemId", value = "业务ID")
    String approveItemId;
    @ApiModelProperty(name = "approveItemType", value = "业务类型")
    String approveItemType;
    @ApiModelProperty(name = "moduleName", value = "页面标识")
    String moduleName;
    @ApiModelProperty(name = "flowId", value = "流程实例ID")
    Long flowId;
    @ApiModelProperty(name = "startTime", value = "查询条件开始时间")
    String startTime;
    @ApiModelProperty(name = "endTime", value = "查询条件结束时间")
    String endTime;

    public String getStartTime() {
        return startTime;
    }

    public TBusinessEnd setStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public String getEndTime() {
        return endTime;
    }

    public TBusinessEnd setEndTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    public String getId() {
        return id;
    }

    public TBusinessEnd setId(String id) {
        this.id = id;
        return this;
    }

    public String getBusinessTitle() {
        return businessTitle;
    }

    public TBusinessEnd setBusinessTitle(String businessTitle) {
        this.businessTitle = businessTitle;
        return this;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public TBusinessEnd setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
        return this;
    }

    public String getBusinessType() {
        return businessType;
    }

    public TBusinessEnd setBusinessType(String businessType) {
        this.businessType = businessType;
        return this;
    }

    public String getFlowStatus() {
        return flowStatus;
    }

    public TBusinessEnd setFlowStatus(String flowStatus) {
        this.flowStatus = flowStatus;
        return this;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public TBusinessEnd setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public String getApproveItemId() {
        return approveItemId;
    }

    public TBusinessEnd setApproveItemId(String approveItemId) {
        this.approveItemId = approveItemId;
        return this;
    }

    public String getApproveItemType() {
        return approveItemType;
    }

    public TBusinessEnd setApproveItemType(String approveItemType) {
        this.approveItemType = approveItemType;
        return this;
    }

    public String getModuleName() {
        return moduleName;
    }

    public TBusinessEnd setModuleName(String moduleName) {
        this.moduleName = moduleName;
        return this;
    }

    public Long getFlowId() {
        return flowId;
    }

    public TBusinessEnd setFlowId(Long flowId) {
        this.flowId = flowId;
        return this;
    }
}
