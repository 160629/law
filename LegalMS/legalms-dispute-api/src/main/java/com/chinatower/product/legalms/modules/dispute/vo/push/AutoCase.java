package com.chinatower.product.legalms.modules.dispute.vo.push;

import java.util.Date;

import com.chinatower.product.legalms.common.CoreConstClass;

public class AutoCase {
    private String caseId;  //案件ID

    private String caseTitle;  //案件标题

    private String caseSpecialLine; //案件所属专业线

    private Date createTime; //创建时间

    private String moduleName;

    private String caseReason; //案由

    private String largeLawsuitMark; //重大诉讼标识

    private String deputeType; //案件类型

    private String caseDeputeMoney; //争议金额

    private String ourLawsuitBody; //我方诉讼主体Id

    private String ourLawsuitBodyName; //我方诉讼主体名称

    private String ourLawsuitIdentity; //我方诉讼地位

    private String otherLawsuitBody; //诉讼相对方主体

    private String caseTheThird; //第三人

    private String caseSamePlaintiff;   //同案原告

    private String caseSameDefendant;   //同案被告

    private String caseTruth;   //案件事实

    private String caseFile;   //案件材料

    private String drafterUnit; //起草单位

    private String creatorDeptId;   //creator_dept_id

    private String creatorDeptName;     //用户部门名称

    private String creatorUnitId;   //creator_unit_id

    private String creatorUnitName; 
    
    private String creatorAccountId; //creator_account_id

    private String creatorAccountName; //起草人

    private String creatorCell;   //联系电话

    private String isAuto;     //是否为自动生成

    private String businessId;

    private String businessType;
    
    private Date caseRecordTime;
    
    private String institutionCaseCode;
    
    //涉案人ID")
    private String involvedAccountId;

    //涉案人名称")
    private String involvedAccountName;

    //涉案部门ID")
    private String involvedDeptId;

    //涉案部门名称")
    private String involvedDeptName;

    //涉案单位ID")
    private String involvedOrgId;

    //涉案单位名称")
    private String involvedOrgName;
    //编号
    private String code;  


	public String getCode() {
		return code;
	}

	public AutoCase setCode(String code) {
		this.code = code;
		return this;
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

	public Date getCaseRecordTime() {
		return caseRecordTime;
	}

	public AutoCase setCaseRecordTime(Date caseRecordTime) {
		this.caseRecordTime = caseRecordTime;
		return this;
	}

	public String getInstitutionCaseCode() {
		return institutionCaseCode;
	}

	public AutoCase setInstitutionCaseCode(String institutionCaseCode) {
		this.institutionCaseCode = institutionCaseCode;
		return this;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getCaseTitle() {
		return caseTitle;
	}

	public AutoCase setCaseTitle(String caseTitle) {
		this.caseTitle = caseTitle;
		return this;
	}

	public String getCaseSpecialLine() {
		return caseSpecialLine;
	}

	public AutoCase setCaseSpecialLine(String caseSpecialLine) {
		this.caseSpecialLine = caseSpecialLine;
		return this;
	}

	public Date getCreateTime() {
		return new Date();
	}

	public AutoCase setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}

	public String getModuleName() {
		return moduleName;
	}

	public AutoCase setModuleName(String moduleName) {
		this.moduleName = moduleName;
		return this;
	}

	public String getCaseReason() {
		return caseReason;
	}

	public AutoCase setCaseReason(String caseReason) {
		this.caseReason = caseReason;
		return this;
	}

	public String getLargeLawsuitMark() {
		return largeLawsuitMark;
	}

	public AutoCase setLargeLawsuitMark(String largeLawsuitMark) {
		this.largeLawsuitMark = largeLawsuitMark;
		return this;
	}

	public String getDeputeType() {
		return deputeType;
	}

	public AutoCase setDeputeType(String deputeType) {
		this.deputeType = deputeType;
		return this;
	}

	public String getCaseDeputeMoney() {
		return caseDeputeMoney;
	}

	public AutoCase setCaseDeputeMoney(String caseDeputeMoney) {
		this.caseDeputeMoney = caseDeputeMoney;
		return this;
	}

	public String getOurLawsuitBody() {
		return ourLawsuitBody;
	}

	public AutoCase setOurLawsuitBody(String ourLawsuitBody) {
		this.ourLawsuitBody = ourLawsuitBody;
		return this;
	}

	public String getOurLawsuitBodyName() {
		return ourLawsuitBodyName;
	}

	public AutoCase setOurLawsuitBodyName(String ourLawsuitBodyName) {
		this.ourLawsuitBodyName = ourLawsuitBodyName;
		return this;
	}

	public String getOurLawsuitIdentity() {
		return ourLawsuitIdentity;
	}

	public AutoCase setOurLawsuitIdentity(String ourLawsuitIdentity) {
		this.ourLawsuitIdentity = ourLawsuitIdentity;
		return this;
	}

	public String getOtherLawsuitBody() {
		return otherLawsuitBody;
	}

	public AutoCase setOtherLawsuitBody(String otherLawsuitBody) {
		this.otherLawsuitBody = otherLawsuitBody;
		return this;
	}

	public String getCaseTheThird() {
		return caseTheThird;
	}

	public AutoCase setCaseTheThird(String caseTheThird) {
		this.caseTheThird = caseTheThird;
		return this;
	}

	public String getCaseSamePlaintiff() {
		return caseSamePlaintiff;
	}

	public AutoCase setCaseSamePlaintiff(String caseSamePlaintiff) {
		this.caseSamePlaintiff = caseSamePlaintiff;
		return this;
	}

	public String getCaseSameDefendant() {
		return caseSameDefendant;
	}

	public AutoCase setCaseSameDefendant(String caseSameDefendant) {
		this.caseSameDefendant = caseSameDefendant;
		return this;
	}

	public String getCaseTruth() {
		return caseTruth;
	}

	public AutoCase setCaseTruth(String caseTruth) {
		this.caseTruth = caseTruth;
		return this;
	}

	public String getCaseFile() {
		return caseFile;
	}

	public AutoCase setCaseFile(String caseFile) {
		this.caseFile = caseFile;
		return this;
	}

	public String getDrafterUnit() {
		return drafterUnit;
	}

	public AutoCase setDrafterUnit(String drafterUnit) {
		this.drafterUnit = drafterUnit;
		return this;
	}

	public String getCreatorDeptId() {
		return creatorDeptId;
	}

	public AutoCase setCreatorDeptId(String creatorDeptId) {
		this.creatorDeptId = creatorDeptId;
		return this;
	}

	public String getCreatorDeptName() {
		return creatorDeptName;
	}

	public AutoCase setCreatorDeptName(String creatorDeptName) {
		this.creatorDeptName = creatorDeptName;
		return this;
	}

	public String getCreatorUnitId() {
		return creatorUnitId;
	}

	public AutoCase setCreatorUnitId(String creatorUnitId) {
		this.creatorUnitId = creatorUnitId;
		return this;
	}

	public String getCreatorAccountId() {
		return creatorAccountId;
	}

	public AutoCase setCreatorAccountId(String creatorAccountId) {
		this.creatorAccountId = creatorAccountId;
		return this;
	}

	public String getCreatorAccountName() {
		return creatorAccountName;
	}

	public AutoCase setCreatorAccountName(String creatorAccountName) {
		this.creatorAccountName = creatorAccountName;
		return this;
	}

	public String getCreatorCell() {
		return creatorCell;
	}

	public AutoCase setCreatorCell(String creatorCell) {
		this.creatorCell = creatorCell;
		return this;
	}

	public String getIsAuto() {
		this.isAuto = CoreConstClass.IS_AUTO_ONE;
		return isAuto;
	}

	public AutoCase setIsAuto(String isAuto) {
		this.isAuto = isAuto;
		return this;
	}

	public String getBusinessId() {
		return businessId;
	}


	public AutoCase setBusinessId(String businessId) {
		this.businessId = businessId;
		return this;
	}

	public String getBusinessType() {
		return businessType;
	}

	public AutoCase setBusinessType(String businessType) {
		this.businessType = businessType;
		return this;
	}

	


	public AutoCase(String caseId, String involvedAccountId, String involvedAccountName,
			String involvedDeptId, String involvedDeptName, String involvedOrgId, String involvedOrgName) {
		super();
		this.caseId = caseId;
		this.involvedAccountId = involvedAccountId;
		this.involvedAccountName = involvedAccountName;
		this.involvedDeptId = involvedDeptId;
		this.involvedDeptName = involvedDeptName;
		this.involvedOrgId = involvedOrgId;
		this.involvedOrgName = involvedOrgName;
	}

	public AutoCase() {
		super();
	}

	public String getCreatorUnitName() {
		return creatorUnitName;
	}

	public AutoCase setCreatorUnitName(String creatorUnitName) {
		this.creatorUnitName = creatorUnitName;
		return this;
	}

	@Override
	public String toString() {
		return "AutoCase [caseId=" + caseId + ", caseTitle=" + caseTitle + ", caseSpecialLine=" + caseSpecialLine
				+ ", createTime=" + createTime + ", moduleName=" + moduleName + ", caseReason=" + caseReason
				+ ", largeLawsuitMark=" + largeLawsuitMark + ", deputeType=" + deputeType + ", caseDeputeMoney="
				+ caseDeputeMoney + ", ourLawsuitBody=" + ourLawsuitBody + ", ourLawsuitBodyName=" + ourLawsuitBodyName
				+ ", ourLawsuitIdentity=" + ourLawsuitIdentity + ", otherLawsuitBody=" + otherLawsuitBody
				+ ", caseTheThird=" + caseTheThird + ", caseSamePlaintiff=" + caseSamePlaintiff + ", caseSameDefendant="
				+ caseSameDefendant + ", caseTruth=" + caseTruth + ", caseFile=" + caseFile + ", drafterUnit="
				+ drafterUnit + ", creatorDeptId=" + creatorDeptId + ", creatorDeptName=" + creatorDeptName
				+ ", creatorUnitId=" + creatorUnitId + ", creatorUnitName=" + creatorUnitName + ", creatorAccountId="
				+ creatorAccountId + ", creatorAccountName=" + creatorAccountName + ", creatorCell=" + creatorCell
				+ ", isAuto=" + isAuto + ", businessId=" + businessId + ", businessType=" + businessType
				+ ", caseRecordTime=" + caseRecordTime + ", institutionCaseCode=" + institutionCaseCode
				+ ", involvedAccountId=" + involvedAccountId + ", involvedAccountName=" + involvedAccountName
				+ ", involvedDeptId=" + involvedDeptId + ", involvedDeptName=" + involvedDeptName + ", involvedOrgId="
				+ involvedOrgId + ", involvedOrgName=" + involvedOrgName + ", code=" + code + "]";
	}
    
    
}
