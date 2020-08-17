package com.chinatower.product.legalms.modules.dispute.vo.issue;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.chinatower.product.legalms.modules.flow.entity.unview.Tbase;
import com.chinatower.product.legalms.modules.flow.vo.flow.TFlowMainVO;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("案件交办对象")
public class TCaseAssignVO extends Tbase {
	@ApiModelProperty(value = "id", name = "assignId")
	private String assignId;

	@ApiModelProperty(value = "编码", name = "assignCode")
	private String assignCode;

	@ApiModelProperty(value = "交办类型", name = "assignType")
	private String assignType;

	// 标题信息
	@ApiModelProperty(value = "标题信息", name = "assignTitle")
	private String assignTitle;
	// 审理机构省份
	@ApiModelProperty(value = "审理机构省份", name = "approveStateName")
	private String approveStateName;

	// 审理机构地市
	@ApiModelProperty(value = "审理机构地市", name = "approveCityName")
	private String approveCityName;
	// 审理机构省份
	@ApiModelProperty(value = "审理机构省份", name = "approveState")
	private String approveState;

	// 审理机构地市
	@ApiModelProperty(value = "审理机构地市", name = "approveCity")
	private String approveCity;

	// 审理机构名称
	@ApiModelProperty(value = "审理机构名称", name = "approveOrg")
	private String approveOrg;

	// 立案时间
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@ApiModelProperty(value = "立案时间", name = "caseCreateTime")
	private Date caseCreateTime;

	// 审理机关案件编号
	@ApiModelProperty(value = "审理机关案件编号", name = "caseCode")
	private String caseCode;

	// 案件所属专业线
	@ApiModelProperty(value = "案件所属专业线", name = "caseLine")
	private String caseLine;

	// 案由
	@ApiModelProperty(value = "案由", name = "caseCause")
	private String caseCause;

	// 案件类型
	@ApiModelProperty(value = "案件类型", name = "caseType")
	private String caseType;

	// 是否是重大诉讼
	@ApiModelProperty(value = "是否是重大诉讼", name = "lawsuitSize")
	private String lawsuitSize;

	// 争议金额
	@ApiModelProperty(value = "争议金额", name = "lawsuitMoney", example = "0")
	private Double lawsuitMoney;

	// 我方诉讼主体
	@ApiModelProperty(value = "我方诉讼主体", name = "ourLawsuitBody")
	private String ourLawsuitBody;

	@ApiModelProperty(value = "我方诉讼主体名称", name = "ourLawsuitBodyName")
	private String ourLawsuitBodyName;

	// 对方诉讼主体
	@ApiModelProperty(value = "对方诉讼主体", name = "theyLawsuitBody")
	private String theyLawsuitBody;

	// 我方诉讼地位
	@ApiModelProperty(value = "我方诉讼地位", name = "ourLawsuitStatus")
	private String ourLawsuitStatus;

	// 其他涉案方
	@ApiModelProperty(value = "其他涉案方", name = "otherRelated")
	private String otherRelated;

	// 第三人
	@ApiModelProperty(value = "第三人", name = "thirdPerson")
	private String thirdPerson;

	// 同案原告
	@ApiModelProperty(value = "同案原告", name = "plaintiff")
	private String plaintiff;

	// 同案被告
	@ApiModelProperty(value = "同案被告", name = "defendant")
	private String defendant;

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@ApiModelProperty(value = "送达时间", name = "sendTime")
	private Date sendTime;

	// 原告诉讼请求
	@ApiModelProperty(value = "原告诉讼请求", name = "plaintiffRequest")
	private String plaintiffRequest;

	// 案件材料/附件
	@ApiModelProperty(value = "案件材料/附件", name = "caseFile")
	private String caseFile;

	// 转出单位
	@ApiModelProperty(value = "转出单位", name = "assignOrg")
	private String assignOrg;

	// 主送单位
	@ApiModelProperty(value = "主送单位", name = "mainSeedOrg")
	private String mainSeedOrg;

	// 主送单位
	@ApiModelProperty(value = "主送单位", name = "mainSeedOrgId")
	private String mainSeedOrgId;

	// 交办事项
	@ApiModelProperty(value = "交办事项", name = "assignItem")
	private String assignItem;

	// 上传附件
	@ApiModelProperty(value = "上传附件", name = "assignFile")
	private String assignFile;

	@ApiModelProperty(value = "是否会签", name = "signDept")
	private String signDept;

	@ApiModelProperty(value = "状态", name = "assignStatus")
	private String assignStatus;

	@ApiModelProperty(value = "附件信息", name = "files")
	private List<Map<String, Object>> files;

	@ApiModelProperty("审批主对象")
	private TFlowMainVO mian;

	@ApiModelProperty("当前活动ID")
	private String currActivityDefId;

	@ApiModelProperty("当前活动名称")
	private String currActivityDefName;

	@ApiModelProperty("moduleName")
	private String moduleName;


	@ApiModelProperty(name = "isUnView", value = "是否已读")
	private String isUnView;

	@ApiModelProperty(name = "involvedAccountId", value = "涉案人ID")
    private String involvedAccountId;

	@ApiModelProperty(name = "involvedAccountName", value = "涉案人名称")
    private String involvedAccountName;

	@ApiModelProperty(name = "involvedDeptId", value = "涉案部门ID")
    private String involvedDeptId;

	@ApiModelProperty(name = "involvedDeptName", value = "涉案部门名称")
    private String involvedDeptName;

	@ApiModelProperty(name = "involvedOrgId", value = "涉案单位ID")
    private String involvedOrgId;

	@ApiModelProperty(name = "involvedOrgName", value = "涉案单位名称")
    private String involvedOrgName;
	
	@ApiModelProperty(name = "createCaseType", value = "生成卷宗类型 自动:0,关联:1")
	private Integer createCaseType;
	
	@ApiModelProperty(name = "caseTitle", value = "关联卷宗标题")
	private String caseTitle;
	
	@ApiModelProperty(name = "caseId", value = "关联卷宗id")
	private String caseId;
	
	
	public Integer getCreateCaseType() {
		return createCaseType;
	}

	public void setCreateCaseType(Integer createCaseType) {
		this.createCaseType = createCaseType;
	}

	public String getCaseTitle() {
		return caseTitle;
	}

	public void setCaseTitle(String caseTitle) {
		this.caseTitle = caseTitle;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getInvolvedAccountId() {
		return involvedAccountId;
	}

	public void setInvolvedAccountId(String involvedAccountId) {
		this.involvedAccountId = involvedAccountId;
	}

	public String getInvolvedAccountName() {
		return involvedAccountName;
	}

	public void setInvolvedAccountName(String involvedAccountName) {
		this.involvedAccountName = involvedAccountName;
	}

	public String getInvolvedDeptId() {
		return involvedDeptId;
	}

	public void setInvolvedDeptId(String involvedDeptId) {
		this.involvedDeptId = involvedDeptId;
	}

	public String getInvolvedDeptName() {
		return involvedDeptName;
	}

	public void setInvolvedDeptName(String involvedDeptName) {
		this.involvedDeptName = involvedDeptName;
	}

	public String getInvolvedOrgId() {
		return involvedOrgId;
	}

	public void setInvolvedOrgId(String involvedOrgId) {
		this.involvedOrgId = involvedOrgId;
	}

	public String getInvolvedOrgName() {
		return involvedOrgName;
	}

	public void setInvolvedOrgName(String involvedOrgName) {
		this.involvedOrgName = involvedOrgName;
	}

	public String getIsUnView() {
		return isUnView;
	}

	public void setIsUnView(String isUnView) {
		this.isUnView = isUnView;
	}


	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getAssignId() {
		return assignId;
	}

	public void setAssignId(String assignId) {
		this.assignId = assignId;
	}

	public String getAssignCode() {
		return assignCode;
	}

	public void setAssignCode(String assignCode) {
		this.assignCode = assignCode;
	}

	public String getAssignType() {
		return assignType;
	}

	public void setAssignType(String assignType) {
		this.assignType = assignType;
	}

	public String getAssignTitle() {
		return assignTitle;
	}

	public void setAssignTitle(String assignTitle) {
		this.assignTitle = assignTitle;
	}

	public String getApproveState() {
		return approveState;
	}

	public void setApproveState(String approveState) {
		this.approveState = approveState;
	}

	public String getApproveCity() {
		return approveCity;
	}

	public void setApproveCity(String approveCity) {
		this.approveCity = approveCity;
	}

	public String getApproveOrg() {
		return approveOrg;
	}

	public void setApproveOrg(String approveOrg) {
		this.approveOrg = approveOrg;
	}

	public Date getCaseCreateTime() {
		return caseCreateTime;
	}

	public void setCaseCreateTime(Date caseCreateTime) {
		this.caseCreateTime = caseCreateTime;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getCaseLine() {
		return caseLine;
	}

	public void setCaseLine(String caseLine) {
		this.caseLine = caseLine;
	}

	public String getCaseCause() {
		return caseCause;
	}

	public void setCaseCause(String caseCause) {
		this.caseCause = caseCause;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getLawsuitSize() {
		return lawsuitSize;
	}

	public void setLawsuitSize(String lawsuitSize) {
		this.lawsuitSize = lawsuitSize;
	}

	public Double getLawsuitMoney() {
		return lawsuitMoney;
	}

	public void setLawsuitMoney(Double lawsuitMoney) {
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

	public String getOtherRelated() {
		return otherRelated;
	}

	public void setOtherRelated(String otherRelated) {
		this.otherRelated = otherRelated;
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

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getPlaintiffRequest() {
		return plaintiffRequest;
	}

	public void setPlaintiffRequest(String plaintiffRequest) {
		this.plaintiffRequest = plaintiffRequest;
	}

	public String getCaseFile() {
		return caseFile;
	}

	public void setCaseFile(String caseFile) {
		this.caseFile = caseFile;
	}

	public String getAssignOrg() {
		return assignOrg;
	}

	public void setAssignOrg(String assignOrg) {
		this.assignOrg = assignOrg;
	}

	public String getMainSeedOrg() {
		return mainSeedOrg;
	}

	public void setMainSeedOrg(String mainSeedOrg) {
		this.mainSeedOrg = mainSeedOrg;
	}

	public String getAssignItem() {
		return assignItem;
	}

	public void setAssignItem(String assignItem) {
		this.assignItem = assignItem;
	}

	public String getAssignFile() {
		return assignFile;
	}

	public void setAssignFile(String assignFile) {
		this.assignFile = assignFile;
	}

	public String getSignDept() {
		return signDept;
	}

	public void setSignDept(String signDept) {
		this.signDept = signDept;
	}

	public String getAssignStatus() {
		return assignStatus;
	}

	public void setAssignStatus(String assignStatus) {
		this.assignStatus = assignStatus;
	}

	public List<Map<String, Object>> getFiles() {
		return files;
	}

	public void setFiles(List<Map<String, Object>> files) {
		this.files = files;
	}

	public TFlowMainVO getMian() {
		return mian;
	}

	public void setMian(TFlowMainVO mian) {
		this.mian = mian;
	}

	public String getCurrActivityDefId() {
		return currActivityDefId;
	}

	public TCaseAssignVO setCurrActivityDefId(String currActivityDefId) {
		this.currActivityDefId = currActivityDefId;
		return this;
	}

	public String getCurrActivityDefName() {
		return currActivityDefName;
	}

	public TCaseAssignVO setCurrActivityDefName(String currActivityDefName) {
		this.currActivityDefName = currActivityDefName;
		return this;
	}

	public String getOurLawsuitBodyName() {
		return ourLawsuitBodyName;
	}

	public void setOurLawsuitBodyName(String ourLawsuitBodyName) {
		this.ourLawsuitBodyName = ourLawsuitBodyName;
	}

	public String getMainSeedOrgId() {
		return mainSeedOrgId;
	}

	public void setMainSeedOrgId(String mainSeedOrgId) {
		this.mainSeedOrgId = mainSeedOrgId;
	}

	public String getApproveStateName() {
		return approveStateName;
	}

	public void setApproveStateName(String approveStateName) {
		this.approveStateName = approveStateName;
	}

	public String getApproveCityName() {
		return approveCityName;
	}

	public void setApproveCityName(String approveCityName) {
		this.approveCityName = approveCityName;
	}

	@Override
	public String toString() {
		return "TCaseAssignVO [assignId=" + assignId + ", assignCode=" + assignCode + ", assignType=" + assignType
				+ ", assignTitle=" + assignTitle + ", approveStateName=" + approveStateName + ", approveCityName="
				+ approveCityName + ", approveState=" + approveState + ", approveCity=" + approveCity + ", approveOrg="
				+ approveOrg + ", caseCreateTime=" + caseCreateTime + ", caseCode=" + caseCode + ", caseLine="
				+ caseLine + ", caseCause=" + caseCause + ", caseType=" + caseType + ", lawsuitSize=" + lawsuitSize
				+ ", lawsuitMoney=" + lawsuitMoney + ", ourLawsuitBody=" + ourLawsuitBody + ", ourLawsuitBodyName="
				+ ourLawsuitBodyName + ", theyLawsuitBody=" + theyLawsuitBody + ", ourLawsuitStatus=" + ourLawsuitStatus
				+ ", otherRelated=" + otherRelated + ", thirdPerson=" + thirdPerson + ", plaintiff=" + plaintiff
				+ ", defendant=" + defendant + ", sendTime=" + sendTime + ", plaintiffRequest=" + plaintiffRequest
				+ ", caseFile=" + caseFile + ", assignOrg=" + assignOrg + ", mainSeedOrg=" + mainSeedOrg
				+ ", mainSeedOrgId=" + mainSeedOrgId + ", assignItem=" + assignItem + ", assignFile=" + assignFile
				+ ", signDept=" + signDept + ", assignStatus=" + assignStatus + ", files=" + files + ", mian=" + mian
				+ ", currActivityDefId=" + currActivityDefId + ", currActivityDefName=" + currActivityDefName
				+ ", moduleName=" + moduleName + ", isUnView=" + isUnView + ", involvedAccountId=" + involvedAccountId
				+ ", involvedAccountName=" + involvedAccountName + ", involvedDeptId=" + involvedDeptId
				+ ", involvedDeptName=" + involvedDeptName + ", involvedOrgId=" + involvedOrgId + ", involvedOrgName="
				+ involvedOrgName + "]";
	}

}
