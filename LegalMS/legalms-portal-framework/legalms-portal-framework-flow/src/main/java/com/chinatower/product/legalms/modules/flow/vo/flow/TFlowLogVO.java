package com.chinatower.product.legalms.modules.flow.vo.flow;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("审批处理日志表")
public class TFlowLogVO {
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
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
    
    @ApiModelProperty(value = "被委托人ID", name = "toerId")
    private String toerId;
    
    @ApiModelProperty(value = "被委托人", name = "toerName")
    private String toerName;

    @ApiModelProperty(value = "父活动节点ID", name = "actPid")
    private Long actPid;
    @ApiModelProperty(value="会签日志",name="subLogs")
    private List<SubTFlowLogVO> subLogs;
    @ApiModelProperty(value="分组后的会签日志",name="groupingSubLogs")
    Map<String, List<SubTFlowLogVO>> groupingSubLogs;

    @ApiModelProperty("操作类型。0：下一步；1：退回")
    String optionType;

    
    @ApiModelProperty(value="部门名称",name="orgName")
    protected String deptName ;
    
    @ApiModelProperty(value="部门ID",name="deptId")
    protected String deptId;
    
    
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

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public Map<String, List<SubTFlowLogVO>> getGroupingSubLogs() {
        return groupingSubLogs;
    }

    public void setGroupingSubLogs(Map<String, List<SubTFlowLogVO>> groupingSubLogs) {
        this.groupingSubLogs = groupingSubLogs;
    }

    public List<SubTFlowLogVO> getSubLogs() {
		return subLogs;
	}

	public void setSubLogs(List<SubTFlowLogVO> subLogs) {
		this.subLogs = subLogs;
	}

	public String getApproveItemId() {
        return approveItemId;
    }

    public TFlowLogVO setApproveItemId(String approveItemId) {
        this.approveItemId = approveItemId;
        return this;
    }

    public String getApproveItemType() {
        return approveItemType;
    }

    public TFlowLogVO setApproveItemType(String approveItemType) {
        this.approveItemType = approveItemType;
        return this;
    }


    public String getReceiverCompanyId() {
        return receiverCompanyId;
    }

    public TFlowLogVO setReceiverCompanyId(String receiverCompanyId) {
        this.receiverCompanyId = receiverCompanyId;
        return this;
    }

    public String getReceiverCompanyName() {
        return receiverCompanyName;
    }

    public TFlowLogVO setReceiverCompanyName(String receiverCompanyName) {
        this.receiverCompanyName = receiverCompanyName;
        return this;
    }

    public String getReceiverOrgId() {
        return receiverOrgId;
    }

    public TFlowLogVO setReceiverOrgId(String receiverOrgId) {
        this.receiverOrgId = receiverOrgId;
        return this;
    }

    public String getReceiverOrgName() {
        return receiverOrgName;
    }

    public TFlowLogVO setReceiverOrgName(String receiverOrgName) {
        this.receiverOrgName = receiverOrgName;
        return this;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public TFlowLogVO setReceiverId(String receiverId) {
        this.receiverId = receiverId;
        return this;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public TFlowLogVO setReceiverName(String receiverName) {
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

    public TFlowLogVO setFlowLogId(String flowLogId) {
        this.flowLogId = flowLogId == null ? null : flowLogId.trim();
        return this;
    }

    public String getStatus() {
        return status;
    }

    public TFlowLogVO setStatus(String status) {
        this.status = status == null ? null : status.trim();
        return this;
    }

    public String getOpinion() {
        return opinion;
    }

    public TFlowLogVO setOpinion(String opinion) {
        this.opinion = opinion == null ? null : opinion.trim();
        return this;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public TFlowLogVO setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }

    public String getActivityDefId() {
        return activityDefId;
    }

    public TFlowLogVO setActivityDefId(String activityDefId) {
        this.activityDefId = activityDefId == null ? null : activityDefId.trim();
        return this;
    }

    public String getActivityDefName() {
        return activityDefName;
    }

    public TFlowLogVO setActivityDefName(String activityDefName) {
        this.activityDefName = activityDefName == null ? null : activityDefName.trim();
        return this;
    }

    public String getNextActivityDefId() {
        return nextActivityDefId;
    }

    public TFlowLogVO setNextActivityDefId(String nextActivityDefId) {
        this.nextActivityDefId = nextActivityDefId == null ? null : nextActivityDefId.trim();
        return this;
    }

    public String getNextActivityDefName() {
        return nextActivityDefName;
    }

    public TFlowLogVO setNextActivityDefName(String nextActivityDefName) {
        this.nextActivityDefName = nextActivityDefName == null ? null : nextActivityDefName.trim();
        return this;
    }

    public String getOrgName() {
        return orgName;
    }

    public TFlowLogVO setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
        return this;
    }

    public String getOrgId() {
        return orgId;
    }

    public TFlowLogVO setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public TFlowLogVO setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public TFlowLogVO setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
        return this;
    }

    public String getWorkType() {
        return workType;
    }

    public TFlowLogVO setWorkType(String workType) {
        this.workType = workType == null ? null : workType.trim();
        return this;
    }

    public Long getFlowId() {
        return flowId;
    }

    public TFlowLogVO setFlowId(Long flowId) {
        this.flowId = flowId;
        return this;
    }

    public Long getFlowPid() {
        return flowPid;
    }

    public TFlowLogVO setFlowPid(Long flowPid) {
        this.flowPid = flowPid;
        return this;
    }

	public Long getActivityInstId() {
		return activityInstId;
	}

	public TFlowLogVO setActivityInstId(Long activityInstId) {
		this.activityInstId = activityInstId;
		return this;
	}

	public String getToerId() {
		return toerId;
	}

	public void setToerId(String toerId) {
		this.toerId = toerId;
	}

	public String getToerName() {
		return toerName;
	}

	public void setToerName(String toerName) {
		this.toerName = toerName;
	}

	public Long getActPid() {
		return actPid;
	}

	public void setActPid(Long actPid) {
		this.actPid = actPid;
	}

	@Override
	public String toString() {
		return "TFlowLogVO [flowLogId=" + flowLogId + ", status=" + status + ", receiverCompanyId=" + receiverCompanyId
				+ ", receiverCompanyName=" + receiverCompanyName + ", receiverOrgId=" + receiverOrgId
				+ ", receiverOrgName=" + receiverOrgName + ", receiverId=" + receiverId + ", receiverName="
				+ receiverName + ", businessAdvice=" + businessAdvice + ", opinion=" + opinion + ", modifyTime="
				+ modifyTime + ", activityInstId=" + activityInstId + ", activityDefId=" + activityDefId
				+ ", activityDefName=" + activityDefName + ", nextActivityDefId=" + nextActivityDefId
				+ ", nextActivityDefName=" + nextActivityDefName + ", orgName=" + orgName + ", orgId=" + orgId
				+ ", userName=" + userName + ", userId=" + userId + ", workType=" + workType + ", flowId=" + flowId
				+ ", flowPid=" + flowPid + ", approveItemId=" + approveItemId + ", approveItemType=" + approveItemType
				+ ", toerId=" + toerId + ", toerName=" + toerName + ", actPid=" + actPid + ", subLogs=" + subLogs
				+ ", groupingSubLogs=" + groupingSubLogs + ", optionType=" + optionType + ", deptName=" + deptName
				+ ", deptId=" + deptId + "]";
	}



	
	
}
