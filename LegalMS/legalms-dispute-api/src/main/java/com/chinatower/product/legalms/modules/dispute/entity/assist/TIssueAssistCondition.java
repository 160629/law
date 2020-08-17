package com.chinatower.product.legalms.modules.dispute.entity.assist;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Table(name = "t_issue_assist_condition")
@ApiModel("协助执行-执行情况表")
public class TIssueAssistCondition {
    @Id
    @ApiModelProperty(value = "id", name = "id")
    private String id;

    @Column(name = "assist_id")
    @ApiModelProperty(value = "关联执行单id", name = "assistId")
    private String assistId;

    @Column(name = "create_time")
    @ApiModelProperty(value = "提交时间", name = "createTime")
    private String createTime;

    @Column(name = "modify_time")
    @ApiModelProperty(value = "最后修改时间", name = "modifyTime")
    private String modifyTime;

    @Column(name = "jointly_unit_name")
    @ApiModelProperty(value = "执行单位名称", name = "jointlyUnitName")
    private String jointlyUnitName;

    @Column(name = "jointly_unit_id")
    @ApiModelProperty(value = "执行单位id", name = "jointlyUnitId")
    private String jointlyUnitId;

    @Column(name = "account_id")
    @ApiModelProperty(value = "执行人id", name = "accountId")
    private String accountId;

    @Column(name = "account_name")
    @ApiModelProperty(value = "执行人姓名", name = "accountName")
    private String accountName;

    @Column(name = "jointly_dept_name")
    @ApiModelProperty(value = "承办部门名称", name = "jointlyDeptName")
    private String jointlyDeptName;

    @Column(name = "jointly_dept_id")
    @ApiModelProperty(value = "承办部门id", name = "jointlyDeptId")
    private String jointlyDeptId;

    @Column(name = "check_content")
    @ApiModelProperty(value = "检查情况", name = "checkContent")
    private String checkContent;

    @Column(name = "finance_content")
    @ApiModelProperty(value = "财务确认情况", name = "financeContent")
    private String financeContent;

    @Column(name = "business_content")
    @ApiModelProperty(value = "反馈内容", name = "businessContent")
    private String businessContent;

    @Column(name = "check_file")
    @ApiModelProperty(value = "检查情况附件id列表", name = "checkFile")
    private String checkFile;

    @Column(name = "finance_file")
    @ApiModelProperty(value = "财务确认情况附件id列表", name = "financeFile")
    private String financeFile;

    @Column(name = "business_file")
    @ApiModelProperty(value = "反馈内容附件id列表", name = "businessFile")
    private String businessFile;

    @Column(name = "assist_content")
    @ApiModelProperty(value = "执行情况", name = "assistContent")
    private String assistContent;

    @Column(name = "assist_file")
    @ApiModelProperty(value = "执行情况附件id列表", name = "assistFile")
    private String assistFile;

    @Column(name = "assist_relation_ids")
    @ApiModelProperty(value = "关联的协助执行单id列表", name = "assistRelationIds")
    private String assistRelationIds;

    @Column(name = "state")
    @ApiModelProperty(value = "执行进展（执行中；已完成）", name = "state")
    private String state;

    @Column(name = "act_id")
    @ApiModelProperty(value = "执行环节id", name = "actId")
    private String actId;

    @Column(name = "act_parent_inst_id")
    @ApiModelProperty(value = "父活动实例ID", name = "actParentInstId")
    private String actParentInstId;

    @Column(name = "act_inst_id")
    @ApiModelProperty(value = "活动实例ID", name = "actInstId")
    private String actInstId;

    @Column(name = "flow_id")
    @ApiModelProperty(value = "流程实例id", name = "flowId")
    private String flowId;

    @Column(name = "flow_pid")
    @ApiModelProperty(value = "父流程实例id", name = "flowPid")
    private String flowPid;

    @Column(name = "record_type")
    @ApiModelProperty(value = "1;本单位填写记录；2;非本单位填写记录", name = "recordType")
    private String recordType;

    @ApiModelProperty(value = "关联执行单id及对应标题", name = "assistIdAndTitle")
    private List<Map<String,String>> assistIdAndTitle;

    @ApiModelProperty(value = "关联文件id列表", name = "filesMap")
    List<Map<String, Object>> filesMap;

    public List<Map<String, Object>> getFilesMap() {
        return filesMap;
    }

    public void setFilesMap(List<Map<String, Object>> filesMap) {
        this.filesMap = filesMap;
    }

    public List<Map<String, String>> getAssistIdAndTitle() {
        return assistIdAndTitle;
    }

    public void setAssistIdAndTitle(List<Map<String, String>> assistIdAndTitle) {
        this.assistIdAndTitle = assistIdAndTitle;
    }

    public TIssueAssistCondition() {
        // 我就是一个空的构造方法，代码扫描 不让我 是空的，我就来加一个注释吧
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssistId() {
        return assistId;
    }

    public void setAssistId(String assistId) {
        this.assistId = assistId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getJointlyUnitName() {
        return jointlyUnitName;
    }

    public void setJointlyUnitName(String jointlyUnitName) {
        this.jointlyUnitName = jointlyUnitName;
    }

    public String getJointlyUnitId() {
        return jointlyUnitId;
    }

    public void setJointlyUnitId(String jointlyUnitId) {
        this.jointlyUnitId = jointlyUnitId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getJointlyDeptName() {
        return jointlyDeptName;
    }

    public void setJointlyDeptName(String jointlyDeptName) {
        this.jointlyDeptName = jointlyDeptName;
    }

    public String getJointlyDeptId() {
        return jointlyDeptId;
    }

    public void setJointlyDeptId(String jointlyDeptId) {
        this.jointlyDeptId = jointlyDeptId;
    }

    public String getCheckContent() {
        return checkContent;
    }

    public void setCheckContent(String checkContent) {
        this.checkContent = checkContent;
    }

    public String getFinanceContent() {
        return financeContent;
    }

    public void setFinanceContent(String financeContent) {
        this.financeContent = financeContent;
    }

    public String getBusinessContent() {
        return businessContent;
    }

    public void setBusinessContent(String businessContent) {
        this.businessContent = businessContent;
    }

    public String getCheckFile() {
        return checkFile;
    }

    public void setCheckFile(String checkFile) {
        this.checkFile = checkFile;
    }

    public String getFinanceFile() {
        return financeFile;
    }

    public void setFinanceFile(String financeFile) {
        this.financeFile = financeFile;
    }

    public String getBusinessFile() {
        return businessFile;
    }

    public void setBusinessFile(String businessFile) {
        this.businessFile = businessFile;
    }

    public String getAssistContent() {
        return assistContent;
    }

    public void setAssistContent(String assistContent) {
        this.assistContent = assistContent;
    }

    public String getAssistFile() {
        return assistFile;
    }

    public void setAssistFile(String assistFile) {
        this.assistFile = assistFile;
    }

    public String getAssistRelationIds() {
        return assistRelationIds;
    }

    public void setAssistRelationIds(String assistRelationIds) {
        this.assistRelationIds = assistRelationIds;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getActParentInstId() {
        return actParentInstId;
    }

    public void setActParentInstId(String actParentInstId) {
        this.actParentInstId = actParentInstId;
    }

    public String getActInstId() {
        return actInstId;
    }

    public void setActInstId(String actInstId) {
        this.actInstId = actInstId;
    }

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

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TIssueAssistCondition that = (TIssueAssistCondition) o;
        boolean result =  assistId.equals(that.assistId) && jointlyUnitId.equals(that.jointlyUnitId);
        if (jointlyDeptId == null) {
            if (that.jointlyDeptId == null) {
                return result;
            } else {
                return false;
            }
        } else {
            return result && jointlyDeptId.equals(that.jointlyDeptId);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(assistId, jointlyUnitId, jointlyDeptId);
    }

    @Override
    public String toString() {
        return "TIssueAssistCondition{" +
                "id='" + id + '\'' +
                ", assistId='" + assistId + '\'' +
                ", createTime='" + createTime + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                ", jointlyUnitName='" + jointlyUnitName + '\'' +
                ", jointlyUnitId='" + jointlyUnitId + '\'' +
                ", accountId='" + accountId + '\'' +
                ", accountName='" + accountName + '\'' +
                ", jointlyDeptName='" + jointlyDeptName + '\'' +
                ", jointlyDeptId='" + jointlyDeptId + '\'' +
                ", checkContent='" + checkContent + '\'' +
                ", financeContent='" + financeContent + '\'' +
                ", businessContent='" + businessContent + '\'' +
                ", checkFile='" + checkFile + '\'' +
                ", financeFile='" + financeFile + '\'' +
                ", businessFile='" + businessFile + '\'' +
                ", assistContent='" + assistContent + '\'' +
                ", assistFile='" + assistFile + '\'' +
                ", assistRelationIds='" + assistRelationIds + '\'' +
                ", state='" + state + '\'' +
                ", actId='" + actId + '\'' +
                ", actParentInstId='" + actParentInstId + '\'' +
                ", actInstId='" + actInstId + '\'' +
                ", flowId='" + flowId + '\'' +
                ", flowPid='" + flowPid + '\'' +
                ", recordType='" + recordType + '\'' +
                '}';
    }
}
