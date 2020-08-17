package com.chinatower.product.legalms.modules.flow.entity.common;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Table(name = "t_case_main")
@ApiModel(value="案宗Main")
public class CaseMainVO {
    /*
     * 案宗主表
     */
    @Id
    @Column(name = "case_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(name="caseId",value = "案件ID",required = true)
    private String caseId;  //案件ID

    @Column(name = "case_code")
    @ApiModelProperty(name="caseCode",value = "案件编号")
    private String caseCode;   //案件编号

    @Column(name = "case_title")
    @ApiModelProperty(name="caseTitle",value = "案件标题",required = true)
    private String caseTitle;  //案件标题

    @Column(name = "case_status")
    @ApiModelProperty(name="caseStatus",value = "案件状态")
    private String caseStatus;  //案件状态

    @Column(name = "drafter_unit")
    @ApiModelProperty(name="drafterUnit",value = "起草单位")
    private String drafterUnit; //起草单位

    @Column(name = "case_record_time")
    @ApiModelProperty(name="caseRecordTime",value = "立案时间",required = true)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date caseRecordTime;  //立案时间

    @Column(name = "institution_case_code")
    @ApiModelProperty(name="institutionCaseCode",value = "审理机关案件编号",required = true)
    private String institutionCaseCode; //审理机关案件编号

    @Column(name = "lawsuit_type")
    @ApiModelProperty(name="lawsuitType",value = "诉讼类型",required = true)
    private String lawsuitType; //诉讼类型

    @Column(name = "our_lawsuit_body")
    @ApiModelProperty(name="ourLawsuitBody",value = "我方诉讼主体",required = true)
    private String ourLawsuitBody; //我方诉讼主体
    
    @Column(name = "our_lawsuit_body_name")
    @ApiModelProperty(name="ourLawsuitBodyName",value = "我方诉讼主体名称",required = true)
    private String ourLawsuitBodyName; //我方诉讼主体

    @Column(name = "our_lawsuit_identity")
    @ApiModelProperty(name="ourLawsuitIdentity",value = "我方诉讼地位",required = true)
    private String ourLawsuitIdentity; //我方诉讼地位

    @Column(name = "other_lawsuit_body")
    @ApiModelProperty(name="otherLawsuitBody",value = "诉讼相对方主体",required = true)
    private String otherLawsuitBody; //诉讼相对方主体

    @Column(name = "case_the_third")
    @ApiModelProperty(name="caseTheThird",value = "第三人")
    private String caseTheThird; //第三人

    @Column(name = "case_depute_money")
    @ApiModelProperty(name="caseDeputeMoney",value = "争议金额")
    private BigDecimal caseDeputeMoney; //争议金额

    @Column(name = "case_special_line")
    @ApiModelProperty(name="caseSpecialLine",value = "案件所属专业线")
    private String caseSpecialLine; //案件所属专业线

    @Column(name = "related_body_attribution")
    @ApiModelProperty(name="relatedBodyAttribution",value = "我方涉诉公司主体归属")
    private String relatedBodyAttribution; //我方涉诉公司主体归属

    @Column(name = "case_review_grade")
    @ApiModelProperty(name="caseReviewGrade",value = "案件审级",required = true)
    private String caseReviewGrade; //案件审级

    @Column(name = "case_reason")
    @ApiModelProperty(name="caseReason",value = "案由")
    private String caseReason; //案由

    @Column(name = "depute_type")
    @ApiModelProperty(name="deputeType",value = "案件类型",required = true)
    private String deputeType; //案件类型

    @Column(name = "create_time")
    @ApiModelProperty(name="createTime",value = "创建时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime; //创建时间

    @Column(name = "last_update_time")
    @ApiModelProperty(name="lastUpdateTime",value = "最后更新时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date lastUpdateTime; //最后更新时间

    @Column(name = "case_is_finish")
    @ApiModelProperty(name="caseIsFinish",value = "案件是否结案",required = true)
    private Integer caseIsFinish   ; //案件是否结案

    @Column(name = "case_finish_time")
    @ApiModelProperty(name="caseFinishTime",value = "结案时间",required = true)
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date caseFinishTime; //结案时间

    @Column(name = "case_finish_way")
    @ApiModelProperty(name="caseFinishWay",value = "结案方式",required = true)
    private String caseFinishWay; //结案方式

    @Column(name = "large_lawsuit_mark")
    @ApiModelProperty(name="largeLawsuitMark",value = "重大诉讼标识")
    private String largeLawsuitMark; //重大诉讼标识

    @Column(name = "creator_account_id")
    @ApiModelProperty(name="creatorAccountId",value = "creator_account_id")
    private String creatorAccountId; //creator_account_id

    @Column(name = "creator_account_name")
    @ApiModelProperty(name="creatorAccountName",value = "起草人")
    private String creatorAccountName; //起草人

    @Column(name = "creator_cell")
    @ApiModelProperty(name="creatorCell",value = "联系电话")
    private String creatorCell;   //联系电话

    @Column(name = "case_truth")
    @ApiModelProperty(name="caseTruth",value = "案件事实",required = true)
    private String caseTruth;   //案件事实

    @Column(name = "plaintiff_lawsuit_request")
    @ApiModelProperty(name="plaintiffLawsuitRequest",value = "原告诉讼请求",required = true)
    private String plaintiffLawsuitRequest;   //原告诉讼请求

    @Column(name = "case_file")
    @ApiModelProperty(name="caseFile",value = "案件材料")
    private String caseFile;   //案件材料

    @Column(name = "case_keyword")
    @ApiModelProperty(name="caseKeyword",value = "案件关键字")
    private String caseKeyword;   //案件关键字

    @Column(name = "case_same_plaintiff")
    @ApiModelProperty(name="caseSamePlaintiff",value = "同案原告")
    private String caseSamePlaintiff;   //同案原告

    @Column(name = "case_same_defendant")
    @ApiModelProperty(name="caseSameDefendant",value = "同案被告")
    private String caseSameDefendant;   //同案被告

    @Column(name = "creator_dept_id")
    @ApiModelProperty(name="creatorDeptId",value = "creator_dept_id")
    private String creatorDeptId;   //creator_dept_id

    @Column(name = "creator_unit_id")
    @ApiModelProperty(name="creatorUnitId",value = "creator_unit_id")
    private String creatorUnitId;   //creator_unit_id

    @Column(name = "creator_unit_name")
    @ApiModelProperty(name="creatorUnitName",value = "creator_unit_name")
    private String creatorUnitName;   //creator_unit_name

    @Column(name="case_explain")
    @ApiModelProperty(name="caseExplain",value = "结案说明")
    private String caseExplain;   //结案说明

    @Column(name = "ruling_classes")
    @ApiModelProperty(name="rulingClasses",value = "裁决类别",required = true)
    private String rulingClasses;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode;
    }

    public String getCaseTitle() {
        return caseTitle;
    }

    public void setCaseTitle(String caseTitle) {
        this.caseTitle = caseTitle;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public String getDrafterUnit() {
        return drafterUnit;
    }

    public void setDrafterUnit(String drafterUnit) {
        this.drafterUnit = drafterUnit;
    }

    public Date getCaseRecordTime() {
        return caseRecordTime;
    }

    public void setCaseRecordTime(Date caseRecordTime) {
        this.caseRecordTime = caseRecordTime;
    }

    public String getInstitutionCaseCode() {
        return institutionCaseCode;
    }

    public void setInstitutionCaseCode(String institutionCaseCode) {
        this.institutionCaseCode = institutionCaseCode;
    }

    public String getLawsuitType() {
        return lawsuitType;
    }

    public void setLawsuitType(String lawsuitType) {
        this.lawsuitType = lawsuitType;
    }

    public String getOurLawsuitBody() {
        return ourLawsuitBody;
    }

    public void setOurLawsuitBody(String ourLawsuitBody) {
        this.ourLawsuitBody = ourLawsuitBody;
    }

    public String getOurLawsuitIdentity() {
        return ourLawsuitIdentity;
    }

    public void setOurLawsuitIdentity(String ourLawsuitIdentity) {
        this.ourLawsuitIdentity = ourLawsuitIdentity;
    }

    public String getOtherLawsuitBody() {
        return otherLawsuitBody;
    }

    public void setOtherLawsuitBody(String otherLawsuitBody) {
        this.otherLawsuitBody = otherLawsuitBody;
    }

    public String getCaseTheThird() {
        return caseTheThird;
    }

    public void setCaseTheThird(String caseTheThird) {
        this.caseTheThird = caseTheThird;
    }

    public BigDecimal getCaseDeputeMoney() {
        return caseDeputeMoney;
    }

    public void setCaseDeputeMoney(BigDecimal caseDeputeMoney) {
        this.caseDeputeMoney = caseDeputeMoney;
    }

    public String getCaseSpecialLine() {
        return caseSpecialLine;
    }

    public void setCaseSpecialLine(String caseSpecialLine) {
        this.caseSpecialLine = caseSpecialLine;
    }

    public String getRelatedBodyAttribution() {
        return relatedBodyAttribution;
    }

    public void setRelatedBodyAttribution(String relatedBodyAttribution) {
        this.relatedBodyAttribution = relatedBodyAttribution;
    }

    public String getCaseReviewGrade() {
        return caseReviewGrade;
    }

    public void setCaseReviewGrade(String caseReviewGrade) {
        this.caseReviewGrade = caseReviewGrade;
    }

    public String getCaseReason() {
        return caseReason;
    }

    public void setCaseReason(String caseReason) {
        this.caseReason = caseReason;
    }

    public String getDeputeType() {
        return deputeType;
    }

    public void setDeputeType(String deputeType) {
        this.deputeType = deputeType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getCaseIsFinish() {
        return caseIsFinish;
    }

    public void setCaseIsFinish(Integer caseIsFinish) {
        this.caseIsFinish = caseIsFinish;
    }

    public Date getCaseFinishTime() {
        return caseFinishTime;
    }

    public void setCaseFinishTime(Date caseFinishTime) {
        this.caseFinishTime = caseFinishTime;
    }

    public String getCaseFinishWay() {
        return caseFinishWay;
    }

    public void setCaseFinishWay(String caseFinishWay) {
        this.caseFinishWay = caseFinishWay;
    }

    public String getLargeLawsuitMark() {
        return largeLawsuitMark;
    }

    public void setLargeLawsuitMark(String largeLawsuitMark) {
        this.largeLawsuitMark = largeLawsuitMark;
    }

    public String getCreatorAccountId() {
        return creatorAccountId;
    }

    public void setCreatorAccountId(String creatorAccountId) {
        this.creatorAccountId = creatorAccountId;
    }

    public String getCreatorAccountName() {
        return creatorAccountName;
    }

    public void setCreatorAccountName(String creatorAccountName) {
        this.creatorAccountName = creatorAccountName;
    }

    public String getCreatorCell() {
        return creatorCell;
    }

    public void setCreatorCell(String creatorCell) {
        this.creatorCell = creatorCell;
    }

    public String getCaseTruth() {
        return caseTruth;
    }

    public void setCaseTruth(String caseTruth) {
        this.caseTruth = caseTruth;
    }

    public String getPlaintiffLawsuitRequest() {
        return plaintiffLawsuitRequest;
    }

    public void setPlaintiffLawsuitRequest(String plaintiffLawsuitRequest) {
        this.plaintiffLawsuitRequest = plaintiffLawsuitRequest;
    }

    public String getCaseFile() {
        return caseFile;
    }

    public void setCaseFile(String caseFile) {
        this.caseFile = caseFile;
    }

    public String getCaseKeyword() {
        return caseKeyword;
    }

    public void setCaseKeyword(String caseKeyword) {
        this.caseKeyword = caseKeyword;
    }

    public String getCaseSamePlaintiff() {
        return caseSamePlaintiff;
    }

    public void setCaseSamePlaintiff(String caseSamePlaintiff) {
        this.caseSamePlaintiff = caseSamePlaintiff;
    }

    public String getCaseSameDefendant() {
        return caseSameDefendant;
    }

    public void setCaseSameDefendant(String caseSameDefendant) {
        this.caseSameDefendant = caseSameDefendant;
    }

    public String getCreatorDeptId() {
        return creatorDeptId;
    }

    public void setCreatorDeptId(String creatorDeptId) {
        this.creatorDeptId = creatorDeptId;
    }

    public String getCreatorUnitId() {
        return creatorUnitId;
    }

    public void setCreatorUnitId(String creatorUnitId) {
        this.creatorUnitId = creatorUnitId;
    }

    public String getCaseExplain() {
        return caseExplain;
    }

    public void setCaseExplain(String caseExplain) {
        this.caseExplain = caseExplain;
    }

    public String getRulingClasses() {
        return rulingClasses;
    }

    public void setRulingClasses(String rulingClasses) {
        this.rulingClasses = rulingClasses;
    }

	public String getOurLawsuitBodyName() {
		return ourLawsuitBodyName;
	}

	public void setOurLawsuitBodyName(String ourLawsuitBodyName) {
		this.ourLawsuitBodyName = ourLawsuitBodyName;
	}
    
    
}
