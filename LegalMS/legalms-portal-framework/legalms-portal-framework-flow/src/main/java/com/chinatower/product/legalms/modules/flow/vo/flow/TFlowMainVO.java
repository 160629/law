package com.chinatower.product.legalms.modules.flow.vo.flow;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("审批主数据")
public class TFlowMainVO {
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
	@ApiModelProperty("审批日志对象")
	private List<TFlowLogVO> logs;

	@ApiModelProperty(value = "跳转页面标识", name = "urlId")
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

    public TFlowMainVO setModuleName(String moduleName) {
        this.moduleName = moduleName;
        return this;
    }

    public List<TFlowLogVO> getLogs() {
		return logs;
	}

	public void setLogs(List<TFlowLogVO> logs) {
		this.logs = logs;
	}

	public String getApproveItemName() {
		return approveItemName;
	}

	public TFlowMainVO setApproveItemName(String approveItemName) {
		this.approveItemName = approveItemName;
		return this;
	}

	public String getOrgName() {
		return orgName;
	}

	public TFlowMainVO setOrgName(String orgName) {
		this.orgName = orgName;
		return this;
	}

	public String getOrgId() {
		return orgId;
	}

	public TFlowMainVO setOrgId(String orgId) {
		this.orgId = orgId;
		return this;
	}

	public Long getFlowId() {
        return flowId;
    }

    public TFlowMainVO setFlowId(Long flowId) {
        this.flowId = flowId;
        return this;
    }

    public String getFlowName() {
        return flowName;
    }

    public TFlowMainVO setFlowName(String flowName) {
        this.flowName = flowName == null ? null : flowName.trim();
        return this;
    }

    public String getFlowStatus() {
        return flowStatus;
    }

    public TFlowMainVO setFlowStatus(String flowStatus) {
        this.flowStatus = flowStatus == null ? null : flowStatus.trim();
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public TFlowMainVO setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public TFlowMainVO setCreateUserId(String createUserId) {
        this.createUserId = createUserId == null ? null : createUserId.trim();
        return this;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public TFlowMainVO setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
        return this;
    }

    public String getApproveItemId() {
        return approveItemId;
    }

    public TFlowMainVO setApproveItemId(String approveItemId) {
        this.approveItemId = approveItemId == null ? null : approveItemId.trim();
        return this;
    }

    public String getApproveItemType() {
        return approveItemType;
    }

    public TFlowMainVO setApproveItemType(String approveItemType) {
        this.approveItemType = approveItemType == null ? null : approveItemType.trim();
        return this;
    }

	@Override
	public String toString() {
		return "TFlowMainVO [flowId=" + flowId + ", flowName=" + flowName + ", flowStatus=" + flowStatus
				+ ", createTime=" + createTime + ", createUserId=" + createUserId + ", createUserName=" + createUserName
				+ ", approveItemId=" + approveItemId + ", approveItemType=" + approveItemType + ", approveItemName="
				+ approveItemName + ", orgName=" + orgName + ", orgId=" + orgId + ", logs=" + logs + "]";
	}
    
    
}
