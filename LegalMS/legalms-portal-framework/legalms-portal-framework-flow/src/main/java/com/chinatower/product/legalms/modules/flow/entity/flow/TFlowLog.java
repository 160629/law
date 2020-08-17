package com.chinatower.product.legalms.modules.flow.entity.flow;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 审批处理日志表
 *
 * @author wangyong
 * @date   2019/10/21
 */
@Table(name="t_flow_log")
@ApiModel("审批处理日志表")
public class TFlowLog {
    @Id
    @ApiModelProperty(value="ID",name="flowLogId")
    private String flowLogId;

    @ApiModelProperty(value="审批状态",name="status")
    private String status;

    @ApiModelProperty("下一步接收人公司ID")
    private String receiverCompanyId;

    @ApiModelProperty("下一步接收人公司名称")
    private String receiverCompanyName;

    @ApiModelProperty("下一步接收人部门ID")
    private String receiverOrgId;

    @ApiModelProperty("下一步接收人部门名称")
    private String receiverOrgName;

    @ApiModelProperty("下一步接收人ID")
    private String receiverId;

    @ApiModelProperty("下一步接收人名称")
    private String receiverName;

    @ApiModelProperty(value = "业务审批意见", name = "businessAdvice")
    public String businessAdvice;

    @ApiModelProperty(value="流程审批意见",name="opinion")
    private String opinion;

    @ApiModelProperty(value="修改时间",name="modifyTime")
    private Date modifyTime;

    @ApiModelProperty(value="活动实列ID",name="activityInstId")
    private Long activityInstId;
    
    @ApiModelProperty(value="活动定义ID",name="activityDefId")
    private String activityDefId;

    @ApiModelProperty(value="活动定义名称",name="activityDefName")
    private String activityDefName;

    @ApiModelProperty(value="下活动定义ID",name="nextActivityDefId")
    private String nextActivityDefId;

    @ApiModelProperty(value="下活动定义名称",name="nextActivityDefName")
    private String nextActivityDefName;

    @ApiModelProperty(value="发送人公司名称",name="orgName")
    private String orgName;

    @ApiModelProperty(value="发送人公司ID",name="orgId")
    private String orgId;

    @ApiModelProperty(value="审批人",name="userName")
    private String userName;

    @ApiModelProperty(value="审批人ID",name="userId")
    private String userId;

    @ApiModelProperty(value="审批类型",name="workType")
    private String workType;

    @ApiModelProperty(value="流程ID",name="flowId",example="0")
    private Long flowId;

    @ApiModelProperty(value="流程父ID",name="flowPid",example="0")
    private Long flowPid;

    @ApiModelProperty(value="审批事项ID",name="approveItemId")
    private String approveItemId;

    @ApiModelProperty(value="审批事项类型",name="approveItemType")
    private String approveItemType;

    @ApiModelProperty(value = "流程定义名称", name = "flowDefName")
    private String flowDefName;
    
    @ApiModelProperty(value = "委托人ID", name = "toerId")
    private String toerId;
    
    @ApiModelProperty(value = "委托人名称", name = "toerName")
    private String toerName;
    
    @ApiModelProperty(value = "父活动节点ID", name = "actPid")
    private Long actPid;

    @ApiModelProperty("操作类型。0：下一步；1：退回")
    String optionType;

    @ApiModelProperty(value="部门名称",name="orgName")
    protected String deptName ;
    
    @ApiModelProperty(value="部门ID",name="deptId")
    protected String deptId;

    @ApiModelProperty(value="流程版本",name="versionId")
    private Integer versionId;

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
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

	public String getOptionType() {
        return optionType;
    }

    public TFlowLog setOptionType(String optionType) {
        this.optionType = optionType;
        return this;
    }

    public String getFlowDefName() {
        return flowDefName;
    }

    public TFlowLog setFlowDefName(String flowDefName) {
        this.flowDefName = flowDefName;
        return this;
    }

    public String getApproveItemId() {
        return approveItemId;
    }

    public TFlowLog setApproveItemId(String approveItemId) {
        this.approveItemId = approveItemId;
        return this;
    }

    public String getApproveItemType() {
        return approveItemType;
    }

    public TFlowLog setApproveItemType(String approveItemType) {
        this.approveItemType = approveItemType;
        return this;
    }



    public String getReceiverCompanyId() {
        return receiverCompanyId;
    }

    public TFlowLog setReceiverCompanyId(String receiverCompanyId) {
        this.receiverCompanyId = receiverCompanyId;
        return this;
    }

    public String getReceiverCompanyName() {
        return receiverCompanyName;
    }

    public TFlowLog setReceiverCompanyName(String receiverCompanyName) {
        this.receiverCompanyName = receiverCompanyName;
        return this;
    }

    public String getReceiverOrgId() {
        return receiverOrgId;
    }

    public TFlowLog setReceiverOrgId(String receiverOrgId) {
        this.receiverOrgId = receiverOrgId;
        return this;
    }

    public String getReceiverOrgName() {
        return receiverOrgName;
    }

    public TFlowLog setReceiverOrgName(String receiverOrgName) {
        this.receiverOrgName = receiverOrgName;
        return this;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public TFlowLog setReceiverId(String receiverId) {
        this.receiverId = receiverId;
        return this;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public TFlowLog setReceiverName(String receiverName) {
        this.receiverName = receiverName;
        return this;
    }

    public String getBusinessAdvice() {
        return businessAdvice;
    }

    public void setBusinessAdvice(String businessAdvice) {
        this.businessAdvice = businessAdvice;
    }

    public String getFlowLogId() {
        return flowLogId;
    }

    public TFlowLog setFlowLogId(String flowLogId) {
        this.flowLogId = flowLogId == null ? null : flowLogId.trim();
        return this;
    }

    public String getStatus() {
        return status;
    }

    public TFlowLog setStatus(String status) {
        this.status = status == null ? null : status.trim();
        return this;
    }

    public String getOpinion() {
        return opinion;
    }

    public TFlowLog setOpinion(String opinion) {
        this.opinion = opinion == null ? null : opinion.trim();
        return this;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public TFlowLog setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }

    public String getActivityDefId() {
        return activityDefId;
    }

    public TFlowLog setActivityDefId(String activityDefId) {
        this.activityDefId = activityDefId == null ? null : activityDefId.trim();
        return this;
    }

    public String getActivityDefName() {
        return activityDefName;
    }

    public TFlowLog setActivityDefName(String activityDefName) {
        this.activityDefName = activityDefName == null ? null : activityDefName.trim();
        return this;
    }

    public String getNextActivityDefId() {
        return nextActivityDefId;
    }

    public TFlowLog setNextActivityDefId(String nextActivityDefId) {
        this.nextActivityDefId = nextActivityDefId == null ? null : nextActivityDefId.trim();
        return this;
    }

    public String getNextActivityDefName() {
        return nextActivityDefName;
    }

    public TFlowLog setNextActivityDefName(String nextActivityDefName) {
        this.nextActivityDefName = nextActivityDefName == null ? null : nextActivityDefName.trim();
        return this;
    }

    public String getOrgName() {
        return orgName;
    }

    public TFlowLog setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
        return this;
    }

    public String getOrgId() {
        return orgId;
    }

    public TFlowLog setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public TFlowLog setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public TFlowLog setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
        return this;
    }

    public String getWorkType() {
        return workType;
    }

    public TFlowLog setWorkType(String workType) {
        this.workType = workType == null ? null : workType.trim();
        return this;
    }

    public Long getFlowId() {
        return flowId;
    }

    public TFlowLog setFlowId(Long flowId) {
        this.flowId = flowId;
        return this;
    }

    public Long getFlowPid() {
        return flowPid;
    }

    public TFlowLog setFlowPid(Long flowPid) {
        this.flowPid = flowPid;
        return this;
    }

	public Long getActivityInstId() {
		return activityInstId;
	}

	public TFlowLog setActivityInstId(Long activityInstId) {
		this.activityInstId = activityInstId;
		return this;
	}

	public String getToerId() {
		return toerId;
	}

	public TFlowLog setToerId(String toerId) {
		this.toerId = toerId;
		return this;
	}

	public String getToerName() {
		return toerName;
	}

	public TFlowLog setToerName(String toerName) {
		this.toerName = toerName;
		return this;
	}


	public Long getActPid() {
		return actPid;
	}

	public void setActPid(Long actPid) {
		this.actPid = actPid;
	}

    @Override
    public String toString() {
        return "TFlowLog{" +
                "flowLogId='" + flowLogId + '\'' +
                ", status='" + status + '\'' +
                ", receiverCompanyId='" + receiverCompanyId + '\'' +
                ", receiverCompanyName='" + receiverCompanyName + '\'' +
                ", receiverOrgId='" + receiverOrgId + '\'' +
                ", receiverOrgName='" + receiverOrgName + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", businessAdvice='" + businessAdvice + '\'' +
                ", opinion='" + opinion + '\'' +
                ", modifyTime=" + modifyTime +
                ", activityInstId=" + activityInstId +
                ", activityDefId='" + activityDefId + '\'' +
                ", activityDefName='" + activityDefName + '\'' +
                ", nextActivityDefId='" + nextActivityDefId + '\'' +
                ", nextActivityDefName='" + nextActivityDefName + '\'' +
                ", orgName='" + orgName + '\'' +
                ", orgId='" + orgId + '\'' +
                ", userName='" + userName + '\'' +
                ", userId='" + userId + '\'' +
                ", workType='" + workType + '\'' +
                ", flowId=" + flowId +
                ", flowPid=" + flowPid +
                ", approveItemId='" + approveItemId + '\'' +
                ", approveItemType='" + approveItemType + '\'' +
                ", flowDefName='" + flowDefName + '\'' +
                ", toerId='" + toerId + '\'' +
                ", toerName='" + toerName + '\'' +
                ", actPid=" + actPid +
                ", optionType='" + optionType + '\'' +
                ", deptName='" + deptName + '\'' +
                ", deptId='" + deptId + '\'' +
                ", versionId=" + versionId +
                '}';
    }
}