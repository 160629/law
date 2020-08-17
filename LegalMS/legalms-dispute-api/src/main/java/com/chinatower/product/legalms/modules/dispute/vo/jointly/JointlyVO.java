package com.chinatower.product.legalms.modules.dispute.vo.jointly;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Id;

import com.chinatower.product.legalms.modules.flow.entity.common.BusinessLogVO;
import org.springframework.format.annotation.DateTimeFormat;

import com.chinatower.product.legalms.modules.flow.entity.common.CaseMainVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.TFlowMainVO;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("案件协办表")
public class JointlyVO {
    @Id
    @ApiModelProperty(value="id",name="jointlyId")
    private String jointlyId;

    @ApiModelProperty(value="编号",name="jointlyCode")
    private String jointlyCode;

    @ApiModelProperty(value="标题",name="jointlyTitle")
    private String jointlyTitle;

    @ApiModelProperty(value = "关联案件",name="jointlyCase")
    private String jointlyCase;

    @ApiModelProperty(value="审理机构",name="approveOrg")
    private String approveOrg;

    @ApiModelProperty(value="案件所属专业线",name="caseLine")
    private String caseLine;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value="立案日期",name="caseCreateTime")
    private Date caseCreateTime;

    @ApiModelProperty(value="起诉/应诉",name="indictOrUnindict")
    private String indictOrUnindict;

    @ApiModelProperty(value="案件类型",name="caseType")
    private String caseType;

    @ApiModelProperty(value="案件审级",name="approveLevel")
    private String approveLevel;

    @ApiModelProperty(value="是否重大诉讼",name="lawsuitSize")
    private String lawsuitSize;

    @ApiModelProperty(value="争议金额",name="lawsuitMoney",example="0")
    private String lawsuitMoney;

    @ApiModelProperty(value="我方诉讼主体",name="ourLawsuitBody")
    private String ourLawsuitBody;

    @ApiModelProperty(value="诉讼对方主体",name="theyLawsuitBody")
    private String theyLawsuitBody;

    @ApiModelProperty(value="我方诉讼地位",name="ourLawsuitStatus")
    private String ourLawsuitStatus;

    @ApiModelProperty(value="第三人",name="thirdPerson")
    private String thirdPerson;

    @ApiModelProperty(value="同案原告",name="plaintiff")
    private String plaintiff;

    @ApiModelProperty(value="同案被告",name="defendant")
    private String defendant;

    @ApiModelProperty(value="案件事实",name="lawsuitDetail")
    private String lawsuitDetail;

    @ApiModelProperty(value="协办部门",name="jointlyOrg")
    private String jointlyOrg;

    @ApiModelProperty(value="协办部门",name="jointlyOrgId")
    private String jointlyOrgId;

    @ApiModelProperty(value="协办事项",name="jointlyItem")
    private String jointlyItem;

    @ApiModelProperty(value="上传附件",name="fileUrl")
    private String fileUrl;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value="协办发起时间",name="createTime")
    private Date createTime;

    @ApiModelProperty(value="是否会签",name="signDept")
    private String signDept;

    @ApiModelProperty(value="状态",name="jointlyStatus")
    private String jointlyStatus;

    @ApiModelProperty(value="案件信息",name="caseMainVO")
    private CaseMainVO caseMainVO;

    @ApiModelProperty(value="附件信息",name="files")
    private List<Map<String, Object>> files;

    @ApiModelProperty("审批主对象")
    private TFlowMainVO mian;
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
    @ApiModelProperty("协办反馈")
    private String  feedBack;

    @ApiModelProperty(value="反馈附件信息",name="files")
    private List<Map<String, Object>> feedBackFiles;

    @ApiModelProperty(value="是否已读",name="isUnView")
    private String isUnView;
    @ApiModelProperty(value="权限json",name="permissionJson")
    private String permissionJson;

    @ApiModelProperty("协办单位")
    private String  jointlyUnitName;

    @ApiModelProperty("协办单位id")
    private String jointlyUnitId;

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

    public List<Map<String, Object>> getFeedBackFiles() {
        return feedBackFiles;
    }

    public void setFeedBackFiles(List<Map<String, Object>> feedBackFiles) {
        this.feedBackFiles = feedBackFiles;
    }

    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
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

    public String getJointlyOrgId() {
        return jointlyOrgId;
    }

    public void setJointlyOrgId(String jointlyOrgId) {
        this.jointlyOrgId = jointlyOrgId;
    }

    public CaseMainVO getCaseMainVO() {
        return caseMainVO;
    }

    public void setCaseMainVO(CaseMainVO caseMainVO) {
        this.caseMainVO = caseMainVO;
    }

    public String getJointlyId() {
        return jointlyId;
    }

    public void setJointlyId(String jointlyId) {
        this.jointlyId = jointlyId;
    }

    public String getJointlyCode() {
        return jointlyCode;
    }

    public void setJointlyCode(String jointlyCode) {
        this.jointlyCode = jointlyCode;
    }

    public String getJointlyTitle() {
        return jointlyTitle;
    }

    public void setJointlyTitle(String jointlyTitle) {
        this.jointlyTitle = jointlyTitle;
    }

    public String getJointlyCase() {
        return jointlyCase;
    }

    public void setJointlyCase(String jointlyCase) {
        this.jointlyCase = jointlyCase;
    }

    public String getApproveOrg() {
        return approveOrg;
    }

    public void setApproveOrg(String approveOrg) {
        this.approveOrg = approveOrg;
    }

    public String getCaseLine() {
        return caseLine;
    }

    public void setCaseLine(String caseLine) {
        this.caseLine = caseLine;
    }

    public Date getCaseCreateTime() {
        return caseCreateTime;
    }

    public void setCaseCreateTime(Date caseCreateTime) {
        this.caseCreateTime = caseCreateTime;
    }

    public String getIndictOrUnindict() {
        return indictOrUnindict;
    }

    public void setIndictOrUnindict(String indictOrUnindict) {
        this.indictOrUnindict = indictOrUnindict;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getApproveLevel() {
        return approveLevel;
    }

    public void setApproveLevel(String approveLevel) {
        this.approveLevel = approveLevel;
    }

    public String getLawsuitSize() {
        return lawsuitSize;
    }

    public void setLawsuitSize(String lawsuitSize) {
        this.lawsuitSize = lawsuitSize;
    }

    public String getLawsuitMoney() {
        return lawsuitMoney;
    }

    public void setLawsuitMoney(String lawsuitMoney) {
        this.lawsuitMoney = lawsuitMoney;
    }

    public String getOurLawsuitBody() {
        return ourLawsuitBody;
    }

    public void setOurLawsuitBody(String ourLawsuitBody) {
        this.ourLawsuitBody = ourLawsuitBody;
    }

    public String getTheyLawsuitBody() {
        return theyLawsuitBody;
    }

    public void setTheyLawsuitBody(String theyLawsuitBody) {
        this.theyLawsuitBody = theyLawsuitBody;
    }

    public String getOurLawsuitStatus() {
        return ourLawsuitStatus;
    }

    public void setOurLawsuitStatus(String ourLawsuitStatus) {
        this.ourLawsuitStatus = ourLawsuitStatus;
    }

    public String getThirdPerson() {
        return thirdPerson;
    }

    public void setThirdPerson(String thirdPerson) {
        this.thirdPerson = thirdPerson;
    }

    public String getPlaintiff() {
        return plaintiff;
    }

    public void setPlaintiff(String plaintiff) {
        this.plaintiff = plaintiff;
    }

    public String getDefendant() {
        return defendant;
    }

    public void setDefendant(String defendant) {
        this.defendant = defendant;
    }

    public String getLawsuitDetail() {
        return lawsuitDetail;
    }

    public void setLawsuitDetail(String lawsuitDetail) {
        this.lawsuitDetail = lawsuitDetail;
    }

    public String getJointlyOrg() {
        return jointlyOrg;
    }

    public void setJointlyOrg(String jointlyOrg) {
        this.jointlyOrg = jointlyOrg;
    }

    public String getJointlyItem() {
        return jointlyItem;
    }

    public void setJointlyItem(String jointlyItem) {
        this.jointlyItem = jointlyItem;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public List<Map<String, Object>> getFiles() {
        return files;
    }

    public void setFiles(List<Map<String, Object>> files) {
        this.files = files;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public String getSignDept() {
        return signDept;
    }

    public void setSignDept(String signDept) {
        this.signDept = signDept;
    }

    public String getJointlyStatus() {
        return jointlyStatus;
    }

    public void setJointlyStatus(String jointlyStatus) {
        this.jointlyStatus = jointlyStatus;
    }


    public TFlowMainVO getMian() {
        return mian;
    }

    public void setMian(TFlowMainVO mian) {
        this.mian = mian;
    }

}
