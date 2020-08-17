package com.chinatower.product.legalms.modules.dispute.entity.assist;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Table(name="t_issue_assist")
@ApiModel("协助执行-业务主表")
public class TIssueAssist {
    @Id
    @ApiModelProperty(value="id",name="id")
    private String id;

    @Column(name = "code")
    @ApiModelProperty(value="编号",name="code")
    private String code;

    @Column(name = "title")
    @ApiModelProperty(value="标题",name="title")
    private String title;

    @Column(name = "create_time")
    @ApiModelProperty(value="单据创建时间",name="create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @Column(name = "login_acct")
    @ApiModelProperty(value="单据创建人账号",name="login_acct")
    private String loginAcct;

    @Column(name = "login_name")
    @ApiModelProperty(value="创建人名称",name="login_name")
    private String loginName;

    @Column(name = "org_name")
    @ApiModelProperty(value="创建人单位名称",name="org_name")
    private String orgName;

    @Column(name = "org_id")
    @ApiModelProperty(value="创建人单位id",name="org_id")
    private String orgId;

    @Column(name = "dept_name")
    @ApiModelProperty(value="创建人部门名称",name="dept_name")
    private String deptName;

    @Column(name = "dept_id")
    @ApiModelProperty(value="创建人部门id",name="dept_id")
    private String deptId;

    @Column(name = "mobile")
    @ApiModelProperty(value="创建人手机号",name="mobile")
    private String mobile;

    @Column(name = "state")
    @ApiModelProperty(value="单据状态",name="state")
    private String state;

    @Column(name = "jointly_unit_name")
    @ApiModelProperty(value="执行单位名称",name="jointly_unit_name")
    private String jointlyUnitName;

    @Column(name = "jointly_unit_id")
    @ApiModelProperty(value="执行单位id",name="jointly_unit_id")
    private String jointlyUnitId;

    @Column(name = "unit_level")
    @ApiModelProperty(value="执行单位的层级(01,02,03)",name="unit_level")
    private String unitLevel;

    @Column(name = "jointly_dept_name")
    @ApiModelProperty(value="承办部门名称",name="jointly_dept_name")
    private String jointlyDeptName;

    @Column(name = "jointly_dept_id")
    @ApiModelProperty(value="承办部门id",name="jointly_dept_id")
    private String jointlyDeptId;

    @Column(name = "court_id")
    @ApiModelProperty(value="执行法院id",name="court_id")
    private String courtId;

    @Column(name = "court_name")
    @ApiModelProperty(value="执行法院名称",name="court_name")
    private String courtName;

    @Column(name = "modify_time")
    @ApiModelProperty(value="最后修改时间",name="modify_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date modifyTime;

    @Column(name = "delete_status")
    @ApiModelProperty(value="单据删除状态(0正常 1删除)",name="delete_status")
    private Integer deleteStatus;

    @Column(name = "supplier_code")
    @ApiModelProperty(value="被执行人(供应商)id ",name="supplier_code")
    private String supplierCode;

    @Column(name = "supplier_name")
    @ApiModelProperty(value="被执行人(供应商)名称",name="supplier_name")
    private String supplierName;

    @Column(name = "assist_id")
    @ApiModelProperty(value="关联执行单id",name="assist_id")
    private String assistId;

    @Column(name = "assist_title")
    @ApiModelProperty(value="关联执行单标题",name="assist_title")
    private String assistTitle;

    @Column(name = "assist_type")
    @ApiModelProperty(value="执行类型(数据字典的值)",name="assist_type")
    private String assistType;

    @Column(name = "assist_content")
    @ApiModelProperty(value="执行内容",name="assist_content")
    private String executeContent;

    @ApiModelProperty(value = "是否送会签", name = "signDept")
    private String signDept;

    @Column(name = "module_name")
    @ApiModelProperty(value="页面标识",name="moduleName")
    private String moduleName;

    @ApiModelProperty(name = "mainFlowId", value = "关联主单据的代办流程实例id")
    private String mainProcessInstId;

    @ApiModelProperty(name = "flowId", value = "表单对应主流程id")
    private String flowId;

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getMainProcessInstId() {
        return mainProcessInstId;
    }

    public void setMainProcessInstId(String mainProcessInstId) {
        this.mainProcessInstId = mainProcessInstId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getSignDept() {
        return signDept;
    }

    public void setSignDept(String signDept) {
        this.signDept = signDept;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLoginAcct() {
        return loginAcct;
    }

    public void setLoginAcct(String loginAcct) {
        this.loginAcct = loginAcct;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getUnitLevel() {
        return unitLevel;
    }

    public void setUnitLevel(String unitLevel) {
        this.unitLevel = unitLevel;
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

    public String getCourtId() {
        return courtId;
    }

    public void setCourtId(String courtId) {
        this.courtId = courtId;
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getAssistId() {
        return assistId;
    }

    public void setAssistId(String assistId) {
        this.assistId = assistId;
    }

    public String getAssistTitle() {
        return assistTitle;
    }

    public void setAssistTitle(String assistTitle) {
        this.assistTitle = assistTitle;
    }

    public String getAssistType() {
        return assistType;
    }

    public void setAssistType(String assistType) {
        this.assistType = assistType;
    }

    public String getExecuteContent() {
        return executeContent;
    }

    public void setExecuteContent(String executeContent) {
        this.executeContent = executeContent;
    }

    @Override
    public String toString() {
        return "TIssueAssist{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", createTime=" + createTime +
                ", loginAcct='" + loginAcct + '\'' +
                ", loginName='" + loginName + '\'' +
                ", orgName='" + orgName + '\'' +
                ", orgId='" + orgId + '\'' +
                ", deptName='" + deptName + '\'' +
                ", deptId='" + deptId + '\'' +
                ", mobile='" + mobile + '\'' +
                ", state='" + state + '\'' +
                ", jointlyUnitName='" + jointlyUnitName + '\'' +
                ", jointlyUnitId='" + jointlyUnitId + '\'' +
                ", unitLevel='" + unitLevel + '\'' +
                ", jointlyDeptName='" + jointlyDeptName + '\'' +
                ", jointlyDeptId='" + jointlyDeptId + '\'' +
                ", courtId='" + courtId + '\'' +
                ", courtName='" + courtName + '\'' +
                ", modifyTime=" + modifyTime +
                ", deleteStatus=" + deleteStatus +
                ", supplierCode='" + supplierCode + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", assistId='" + assistId + '\'' +
                ", assistTitle='" + assistTitle + '\'' +
                ", assistType='" + assistType + '\'' +
                ", executeContent='" + executeContent + '\'' +
                ", signDept='" + signDept + '\'' +
                ", moduleName='" + moduleName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TIssueAssist that = (TIssueAssist) o;
        return id.equals(that.id) &&
                code.equals(that.code) &&
                title.equals(that.title) &&
                createTime.equals(that.createTime) &&
                loginAcct.equals(that.loginAcct) &&
                loginName.equals(that.loginName) &&
                orgName.equals(that.orgName) &&
                orgId.equals(that.orgId) &&
                deptName.equals(that.deptName) &&
                deptId.equals(that.deptId) &&
                mobile.equals(that.mobile) &&
                state.equals(that.state) &&
                jointlyUnitName.equals(that.jointlyUnitName) &&
                jointlyUnitId.equals(that.jointlyUnitId) &&
                unitLevel.equals(that.unitLevel) &&
                jointlyDeptName.equals(that.jointlyDeptName) &&
                jointlyDeptId.equals(that.jointlyDeptId) &&
                courtId.equals(that.courtId) &&
                courtName.equals(that.courtName) &&
                modifyTime.equals(that.modifyTime) &&
                deleteStatus.equals(that.deleteStatus) &&
                supplierCode.equals(that.supplierCode) &&
                supplierName.equals(that.supplierName) &&
                assistId.equals(that.assistId) &&
                assistTitle.equals(that.assistTitle) &&
                assistType.equals(that.assistType) &&
                executeContent.equals(that.executeContent) &&
                signDept.equals(that.signDept) &&
                moduleName.equals(that.moduleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, title, createTime, loginAcct, loginName, orgName, orgId, deptName, deptId, mobile, state, jointlyUnitName, jointlyUnitId, unitLevel, jointlyDeptName, jointlyDeptId, courtId, courtName, modifyTime, deleteStatus, supplierCode, supplierName, assistId, assistTitle, assistType, executeContent, signDept, moduleName);
    }
}
