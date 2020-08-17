package com.chinatower.product.legalms.modules.flow.entity.flow;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 审批表
 *
 * @author wangyong
 * @date   2019/10/08
 */
@Table(name="t_flow_main")
@ApiModel("审批主数据")
public class TFlowMain {
	@Id
	@ApiModelProperty(value="流程ID",name="flowId")
    private Long flowId;

	@ApiModelProperty(value="流程名称",name="flowName")
    private String flowName;

	@ApiModelProperty(value="流程状态",name="flowStatus")
    private String flowStatus;

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

    @ApiModelProperty(value="跳转页面标识",name="moduleName")
	private String moduleName;
    
    @ApiModelProperty(value="版本id",name="versionId")
    private String versionId;

    
    
    public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	public String getModuleName() {
        return moduleName;
    }

    public TFlowMain setModuleName(String moduleName) {
        this.moduleName = moduleName;
        return this;
    }

    public String getApproveItemName() {
		return approveItemName;
	}

	public TFlowMain setApproveItemName(String approveItemName) {
		this.approveItemName = approveItemName;
		return this;
	}

	public String getOrgName() {
		return orgName;
	}

	public TFlowMain setOrgName(String orgName) {
		this.orgName = orgName;
		return this;
	}

	public String getOrgId() {
		return orgId;
	}

	public TFlowMain setOrgId(String orgId) {
		this.orgId = orgId;
		return this;
	}

	public Long getFlowId() {
        return flowId;
    }

    public TFlowMain setFlowId(Long flowId) {
        this.flowId = flowId;
        return this;
    }

    public String getFlowName() {
        return flowName;
    }

    public TFlowMain setFlowName(String flowName) {
        this.flowName = flowName == null ? null : flowName.trim();
        return this;
    }

    public String getFlowStatus() {
        return flowStatus;
    }

    public TFlowMain setFlowStatus(String flowStatus) {
        this.flowStatus = flowStatus == null ? null : flowStatus.trim();
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public TFlowMain setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public TFlowMain setCreateUserId(String createUserId) {
        this.createUserId = createUserId == null ? null : createUserId.trim();
        return this;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public TFlowMain setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
        return this;
    }

    public String getApproveItemId() {
        return approveItemId;
    }

    public TFlowMain setApproveItemId(String approveItemId) {
        this.approveItemId = approveItemId == null ? null : approveItemId.trim();
        return this;
    }

    public String getApproveItemType() {
        return approveItemType;
    }

    public TFlowMain setApproveItemType(String approveItemType) {
        this.approveItemType = approveItemType == null ? null : approveItemType.trim();
        return this;
    }

    @Override
    public String toString() {
        return "TFlowMain{" +
                "flowId=" + flowId +
                ", flowName='" + flowName + '\'' +
                ", flowStatus='" + flowStatus + '\'' +
                ", createTime=" + createTime +
                ", createUserId='" + createUserId + '\'' +
                ", createUserName='" + createUserName + '\'' +
                ", approveItemId='" + approveItemId + '\'' +
                ", approveItemType='" + approveItemType + '\'' +
                ", approveItemName='" + approveItemName + '\'' +
                ", orgName='" + orgName + '\'' +
                ", orgId='" + orgId + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", versionId='" + versionId + '\'' +
                '}';
    }
}