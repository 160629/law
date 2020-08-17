package com.chinatower.product.legalms.modules.dispute.vo.assist;

import com.chinatower.product.legalms.modules.dispute.entity.assist.TIssueAssistCondition;
import com.chinatower.product.legalms.modules.flow.vo.flow.TFlowMainVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;
import java.util.Map;

@ApiModel("协助执行单详情")
public class TIssueAssistVO {

    @ApiModelProperty(value = "id", name = "id")
    private String id;

    @ApiModelProperty(value = "编号", name = "code")
    private String code;

    @ApiModelProperty(value = "标题", name = "title")
    private String title;

    @ApiModelProperty(value = "单据创建时间", name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "单据创建人账号", name = "login_acct")
    private String loginAcct;

    @ApiModelProperty(value = "创建人名称", name = "login_name")
    private String loginName;

    @ApiModelProperty(value = "创建人单位名称", name = "org_name")
    private String orgName;

    @ApiModelProperty(value = "创建人单位id", name = "org_id")
    private String orgId;

    @ApiModelProperty(value = "创建人部门名称", name = "dept_name")
    private String deptName;

    @ApiModelProperty(value = "创建人部门id", name = "dept_id")
    private String deptId;

    @ApiModelProperty(value = "创建人手机号", name = "mobile")
    private String mobile;

    @ApiModelProperty(value = "单据状态", name = "state")
    private String state;

    @ApiModelProperty(value = "执行单位名称", name = "jointly_unit_name")
    private String jointlyUnitName;

    @ApiModelProperty(value = "执行单位id", name = "jointly_unit_id")
    private String jointlyUnitId;

    @ApiModelProperty(value = "执行单位的层级(01,02,03)", name = "unit_level")
    private String unitLevel;

    @ApiModelProperty(value = "承办部门名称", name = "jointly_dept_name")
    private String jointlyDeptName;

    @ApiModelProperty(value = "承办部门id", name = "jointly_dept_id")
    private String jointlyDeptId;

    @ApiModelProperty(value = "执行法院id", name = "court_id")
    private String courtId;

    @ApiModelProperty(value = "执行法院名称", name = "court_name")
    private String courtName;

    @ApiModelProperty(value = "最后修改时间", name = "modify_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date modifyTime;

    @ApiModelProperty(value = "单据删除状态(0正常 1删除)", name = "delete_status")
    private Integer deleteStatus;

    @ApiModelProperty(value = "被执行人(供应商)id ", name = "supplier_code")
    private String supplierCode;

    @ApiModelProperty(value = "被执行人(供应商)名称", name = "supplier_name")
    private String supplierName;

    @ApiModelProperty(value = "关联执行单id", name = "assist_id")
    private String assistId;

    @ApiModelProperty(value = "关联执行单标题", name = "assist_title")
    private String assistTitle;

    @ApiModelProperty(value = "执行类型(数据字典的值)", name = "assist_type")
    private String assistType;

    @ApiModelProperty(value = "执行内容", name = "assist_content")
    private String executeContent;

    @ApiModelProperty(value = "是否送会签", name = "signDept")
    private String signDept;

    @ApiModelProperty(value = "审批主对象", name = "main")
    private TFlowMainVO mian;

    @ApiModelProperty(value = "审批主对象", name = "isUnView")
    private String isUnView;

    @ApiModelProperty(value="页面标识",name="moduleName")
    private String moduleName;

    @ApiModelProperty(value = "权限", name = "permissionJson")
    private String permissionJson;

    @ApiModelProperty(value="附件信息",name="files")
    private List<Map<String, Object>> files;

    @ApiModelProperty(value="执行情况列表",name="tIssueAssistConditions")
    private List<TIssueAssistCondition> tIssueAssistConditions;

    @ApiModelProperty(value="非本单位执行进展列表",name="jointUnitConditions")
    private List<Map<String, Object>> jointUnitConditions;

    @ApiModelProperty(value="当前代办流程与主流程关系1：主流程 2：子流程 3：子流程的子流程",name="flowLevel")
    private Integer flowLevel;

    @ApiModelProperty(value="当前代办流程的父流程实例id",name="parentProcID")
    private String parentProcID;

    public Integer getFlowLevel() {
        return flowLevel;
    }

    public void setFlowLevel(Integer flowLevel) {
        this.flowLevel = flowLevel;
    }

    public String getParentProcID() {
        return parentProcID;
    }

    public void setParentProcID(String parentProcID) {
        this.parentProcID = parentProcID;
    }

    public List<Map<String, Object>> getJointUnitConditions() {
        return jointUnitConditions;
    }

    public void setJointUnitConditions(List<Map<String, Object>> jointUnitConditions) {
        this.jointUnitConditions = jointUnitConditions;
    }

    public List<TIssueAssistCondition> gettIssueAssistConditions() {
        return tIssueAssistConditions;
    }

    public void settIssueAssistConditions(List<TIssueAssistCondition> tIssueAssistConditions) {
        this.tIssueAssistConditions = tIssueAssistConditions;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public List<Map<String, Object>> getFiles() {
        return files;
    }

    public void setFiles(List<Map<String, Object>> files) {
        this.files = files;
    }

    public String getPermissionJson() {
        return permissionJson;
    }

    public void setPermissionJson(String permissionJson) {
        this.permissionJson = permissionJson;
    }

    public String getIsUnView() {
        return isUnView;
    }

    public void setIsUnView(String isUnView) {
        this.isUnView = isUnView;
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

    public String getSignDept() {
        return signDept;
    }

    public void setSignDept(String signDept) {
        this.signDept = signDept;
    }

    public TFlowMainVO getMian() {
        return mian;
    }

    public void setMian(TFlowMainVO mian) {
        this.mian = mian;
    }
}
