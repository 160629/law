package com.chinatower.product.legalms.modules.dispute.entity.issue;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import com.chinatower.product.legalms.modules.flow.entity.unview.Tbase;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 引诉信息表
 *
 * @author wangyong
 * @date   2019/10/22
 */
@Table(name="t_issue_guide")
@ApiModel("引诉信息表")
public class TIssueGuide extends Tbase{
    @Id
    @ApiModelProperty(value="引诉ID",name="guideId")
    private String guideId;

    @ApiModelProperty(value="编号",name="guideCode")
    private String guideCode;

    @ApiModelProperty(value="标题",name="guideTitle")
    private String guideTitle;
    
    @ApiModelProperty(value="执行结果",name="guideResult")
    private String guideResult;
    
    @ApiModelProperty(value="重要程度",name="guideSize")
    private String guideSize;
    @ApiModelProperty(value="建议解决方式",name="guideSolve")
    private String guideSolve;

    @ApiModelProperty(value="修改时间",name="modifyTime")
    private Date modifyTime;

    @ApiModelProperty(value="我方争议主体",name="ourLawsuitBody")
    private String ourLawsuitBody;

    @ApiModelProperty(value="我方争议主体名称",name="ourLawsuitBodyName")
    private String ourLawsuitBodyName;

    @ApiModelProperty(value="对方争议主体名称",name="otherDeputeBody")
    private String otherDeputeBody;

    @ApiModelProperty(value="案由",name="caseReason")
    private String caseReason;

    @ApiModelProperty(value="争议金额",name="caseDeputeMoney",example="0")
    private BigDecimal caseDeputeMoney;
    
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @ApiModelProperty(value="纠纷发生时间",name="caseHappenTime")
    private Date caseHappenTime;

    @ApiModelProperty(value="是否会签",name="signDept")
    private String signDept;

    @ApiModelProperty(value="附件",name="guideFile")
    private String guideFile;

    @ApiModelProperty(value="执行金额",name="implMoney",example="0")
    private BigDecimal implMoney;

    @ApiModelProperty(value="诉讼分类",name="guideMethod")
    private String guideMethod;

    @ApiModelProperty(value="状态",name="guideStatus")
    private String guideStatus;

    @ApiModelProperty(value="争议事实",name="caseDeputeTruth")
    private String caseDeputeTruth;

    @ApiModelProperty(value="纠纷处理建议",name="caseDeputeAdvice")
    private String caseDeputeAdvice;

    @ApiModelProperty(value="组织机构类型",name="type")
    private String type;
    
    @ApiModelProperty("moduleName")
    private String moduleName;
    
    @ApiModelProperty(value = "处理建议", name = "businessAdvice")
    public String businessAdvice;
    
    
    public String getBusinessAdvice() {
		return businessAdvice;
	}

	public void setBusinessAdvice(String businessAdvice) {
		this.businessAdvice = businessAdvice;
	}

	public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getGuideId() {
        return guideId;
    }

    public void setGuideId(String guideId) {
        this.guideId = guideId == null ? null : guideId.trim();
    }

    public String getGuideCode() {
        return guideCode;
    }

    public void setGuideCode(String guideCode) {
        this.guideCode = guideCode == null ? null : guideCode.trim();
    }

    public String getGuideTitle() {
        return guideTitle;
    }

    public void setGuideTitle(String guideTitle) {
        this.guideTitle = guideTitle == null ? null : guideTitle.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

	public String getOtherDeputeBody() {
        return otherDeputeBody;
    }

    public void setOtherDeputeBody(String otherDeputeBody) {
        this.otherDeputeBody = otherDeputeBody == null ? null : otherDeputeBody.trim();
    }

    public String getCaseReason() {
        return caseReason;
    }

    public void setCaseReason(String caseReason) {
        this.caseReason = caseReason == null ? null : caseReason.trim();
    }

    public BigDecimal getCaseDeputeMoney() {
        return caseDeputeMoney;
    }

    public void setCaseDeputeMoney(BigDecimal caseDeputeMoney) {
        this.caseDeputeMoney = caseDeputeMoney;
    }

    public Date getCaseHappenTime() {
        return caseHappenTime;
    }

    public void setCaseHappenTime(Date caseHappenTime) {
        this.caseHappenTime = caseHappenTime;
    }

    public String getSignDept() {
        return signDept;
    }

    public void setSignDept(String signDept) {
        this.signDept = signDept == null ? null : signDept.trim();
    }

    public String getGuideFile() {
        return guideFile;
    }

    public void setGuideFile(String guideFile) {
        this.guideFile = guideFile == null ? null : guideFile.trim();
    }

    public BigDecimal getImplMoney() {
        return implMoney;
    }

    public void setImplMoney(BigDecimal implMoney) {
        this.implMoney = implMoney;
    }

    public String getGuideMethod() {
        return guideMethod;
    }

    public void setGuideMethod(String guideMethod) {
        this.guideMethod = guideMethod;
    }

    public String getGuideStatus() {
        return guideStatus;
    }

    public void setGuideStatus(String guideStatus) {
        this.guideStatus = guideStatus == null ? null : guideStatus.trim();
    }

    public String getCaseDeputeTruth() {
        return caseDeputeTruth;
    }

    public void setCaseDeputeTruth(String caseDeputeTruth) {
        this.caseDeputeTruth = caseDeputeTruth == null ? null : caseDeputeTruth.trim();
    }

    public String getCaseDeputeAdvice() {
        return caseDeputeAdvice;
    }

    public void setCaseDeputeAdvice(String caseDeputeAdvice) {
        this.caseDeputeAdvice = caseDeputeAdvice == null ? null : caseDeputeAdvice.trim();
    }

	public String getGuideResult() {
		return guideResult;
	}

	public void setGuideResult(String guideResult) {
		this.guideResult = guideResult;
	}

	public String getGuideSize() {
		return guideSize;
	}

	public void setGuideSize(String guideSize) {
		this.guideSize = guideSize;
	}

	public String getOurLawsuitBody() {
		return ourLawsuitBody;
	}

	public void setOurLawsuitBody(String ourLawsuitBody) {
		this.ourLawsuitBody = ourLawsuitBody;
	}

	public String getOurLawsuitBodyName() {
		return ourLawsuitBodyName;
	}

	public void setOurLawsuitBodyName(String ourLawsuitBodyName) {
		this.ourLawsuitBodyName = ourLawsuitBodyName;
	}

	public String getGuideSolve() {
		return guideSolve;
	}

	public void setGuideSolve(String guideSolve) {
		this.guideSolve = guideSolve;
	}

	@Override
	public String toString() {
		return "TIssueGuide [guideId=" + guideId + ", guideCode=" + guideCode + ", guideTitle=" + guideTitle
				+ ", guideResult=" + guideResult + ", guideSize=" + guideSize + ", guideSolve=" + guideSolve
				+ ", modifyTime=" + modifyTime + ", ourLawsuitBody=" + ourLawsuitBody + ", ourLawsuitBodyName="
				+ ourLawsuitBodyName + ", otherDeputeBody=" + otherDeputeBody + ", caseReason=" + caseReason
				+ ", caseDeputeMoney=" + caseDeputeMoney + ", caseHappenTime=" + caseHappenTime + ", signDept="
				+ signDept + ", guideFile=" + guideFile + ", implMoney=" + implMoney + ", guideMethod=" + guideMethod
				+ ", guideStatus=" + guideStatus + ", caseDeputeTruth=" + caseDeputeTruth + ", caseDeputeAdvice="
				+ caseDeputeAdvice + ", type=" + type + ", moduleName=" + moduleName + ", businessAdvice="
				+ businessAdvice + "]";
	}


    
}