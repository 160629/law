package com.chinatower.product.legalms.modules.dispute.vo.legislation;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.chinatower.product.legalms.modules.flow.entity.common.BusinessLogVO;
import org.springframework.format.annotation.DateTimeFormat;

import com.chinatower.product.legalms.modules.flow.entity.common.CaseMainVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.TFlowMainVO;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("法律文书转办单")
public class LegislationParam {
    @ApiModelProperty("法律文书转办单id")
    private String id;

    @ApiModelProperty("状态")
    private String state;

    @ApiModelProperty("单号")
    private String odd;

    @ApiModelProperty("创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date creationTime;

    @ApiModelProperty("处理人id")
    private String conductorId;

    @ApiModelProperty("处理人名字")
    private String conductorName;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("案件卷宗")
    private String caseFile;

    @ApiModelProperty("我方执行主体")
    private String ourExecutor;

    @ApiModelProperty("对方执行主体")
    private String counterpartyExecutor;

    @ApiModelProperty("执行金额（元）")
    private Double executionAmount;

    @ApiModelProperty("执行时限")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date executionTimeLimit;

    @ApiModelProperty("执行部门")
    private String executiveArm;

    @ApiModelProperty("执行部门id")
    private String executiveArmId;

    @ApiModelProperty("执行内容")
    private String theContent;

    @ApiModelProperty("执行依据")
    private String enforcementBasis;

    @ApiModelProperty("附件详情")
    private List<Map<String, Object>> files;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty(value="1 =关联页面查询",name="correlate")
    private Integer correlate;

    @ApiModelProperty("当前活动ID")
    private String currActivityDefId;

    @ApiModelProperty("当前活动名称")
    private String currActivityDefName;

    @ApiModelProperty("审批主对象")
    private TFlowMainVO mian;

    @ApiModelProperty(value="是否会签",name="signDept")
    private String signDept;

    @ApiModelProperty(value="案件信息",name="caseMainVO")
    private CaseMainVO caseMainVO;

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

    @ApiModelProperty("用户手机")
    private String mobile;

    @ApiModelProperty("moduleName")
    private String moduleName;

    @ApiModelProperty("执行反馈")
    private String  feedBack;
    @ApiModelProperty(value="反馈附件信息",name="files")
    private List<Map<String, Object>> feedBackFiles;

    @ApiModelProperty(name = "isUnView", value = "是否已读")
    private String isUnView;

    private String permissionJson;

    @ApiModelProperty("执行单位")
    private String  executeUnitName;

    @ApiModelProperty("执行单位id")
    private String executeUnitId;

    @ApiModelProperty(value="历史反馈信息",name="BusinessLogVO")
    private List<BusinessLogVO> feedBackMap;

    @ApiModelProperty(value="案件信息",name="businessLogVO")
    private BusinessLogVO businessLogVO;

    @ApiModelProperty("执行单位层级")
    private String unitLevel;

    public String getUnitLevel() {
        return unitLevel;
    }

    public void setUnitLevel(String unitLevel) {
        this.unitLevel = unitLevel;
    }

    public List<BusinessLogVO> getFeedBackMap() {
        return feedBackMap;
    }

    public void setFeedBackMap(List<BusinessLogVO> feedBackMap) {
        this.feedBackMap = feedBackMap;
    }

    public BusinessLogVO getBusinessLogVO() {
        return businessLogVO;
    }

    public void setBusinessLogVO(BusinessLogVO businessLogVO) {
        this.businessLogVO = businessLogVO;
    }


    public String getExecuteUnitName() {
        return executeUnitName;
    }

    public void setExecuteUnitName(String executeUnitName) {
        this.executeUnitName = executeUnitName;
    }

    public String getExecuteUnitId() {
        return executeUnitId;
    }

    public void setExecuteUnitId(String executeUnitId) {
        this.executeUnitId = executeUnitId;
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

    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }

    public List<Map<String, Object>> getFeedBackFiles() {
        return feedBackFiles;
    }

    public void setFeedBackFiles(List<Map<String, Object>> feedBackFiles) {
        this.feedBackFiles = feedBackFiles;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    @ApiModelProperty("执行完毕时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date updateDate;

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getExecutiveArmId() {
        return executiveArmId;
    }

    public void setExecutiveArmId(String executiveArmId) {
        this.executiveArmId = executiveArmId;
    }

    public CaseMainVO getCaseMainVO() {
        return caseMainVO;
    }

    public void setCaseMainVO(CaseMainVO caseMainVO) {
        this.caseMainVO = caseMainVO;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOdd() {
        return odd;
    }

    public void setOdd(String odd) {
        this.odd = odd;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getConductorId() {
        return conductorId;
    }

    public void setConductorId(String conductorId) {
        this.conductorId = conductorId;
    }

    public String getConductorName() {
        return conductorName;
    }

    public void setConductorName(String conductorName) {
        this.conductorName = conductorName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCaseFile() {
        return caseFile;
    }

    public void setCaseFile(String caseFile) {
        this.caseFile = caseFile;
    }

    public String getOurExecutor() {
        return ourExecutor;
    }

    public void setOurExecutor(String ourExecutor) {
        this.ourExecutor = ourExecutor;
    }

    public String getCounterpartyExecutor() {
        return counterpartyExecutor;
    }

    public void setCounterpartyExecutor(String counterpartyExecutor) {
        this.counterpartyExecutor = counterpartyExecutor;
    }

    public Double getExecutionAmount() {
        return executionAmount;
    }

    public void setExecutionAmount(Double executionAmount) {
        this.executionAmount = executionAmount;
    }

    public Date getExecutionTimeLimit() {
        return executionTimeLimit;
    }

    public void setExecutionTimeLimit(Date executionTimeLimit) {
        this.executionTimeLimit = executionTimeLimit;
    }

    public String getExecutiveArm() {
        return executiveArm;
    }

    public void setExecutiveArm(String executiveArm) {
        this.executiveArm = executiveArm;
    }

    public String getTheContent() {
        return theContent;
    }

    public void setTheContent(String theContent) {
        this.theContent = theContent;
    }

    public String getEnforcementBasis() {
        return enforcementBasis;
    }

    public void setEnforcementBasis(String enforcementBasis) {
        this.enforcementBasis = enforcementBasis;
    }

    public List<Map<String, Object>> getFiles() {
        return files;
    }

    public void setFiles(List<Map<String, Object>> files) {
        this.files = files;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCorrelate() {
        return correlate;
    }

    public void setCorrelate(Integer correlate) {
        this.correlate = correlate;
    }

    public String getCurrActivityDefId() {
        return currActivityDefId;
    }

    public void setCurrActivityDefId(String currActivityDefId) {
        this.currActivityDefId = currActivityDefId;
    }

    public String getCurrActivityDefName() {
        return currActivityDefName;
    }

    public void setCurrActivityDefName(String currActivityDefName) {
        this.currActivityDefName = currActivityDefName;
    }

    public TFlowMainVO getMian() {
        return mian;
    }

    public void setMian(TFlowMainVO mian) {
        this.mian = mian;
    }

    public String getSignDept() {
        return signDept;
    }

    public void setSignDept(String signDept) {
        this.signDept = signDept;
    }
}
