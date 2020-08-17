package com.chinatower.product.legalms.modules.consult.entity.lawcase;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import com.chinatower.product.legalms.modules.flow.entity.unview.Tbase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 案件交办信息表
 *
 * @author wangyong
 * @date   2019/10/16
 */
@Table(name="t_case_assign")
@ApiModel("案件交办信息表")
public class TCaseAssign extends Tbase{
    // id
    @Id
    @ApiModelProperty(value="id",name="assignId")
    private String assignId;
    
    @ApiModelProperty(value="编码",name="assignCode")
    private String assignCode;
    
    @ApiModelProperty(value="交办类型",name="assignType")
    private String assignType;

    // 标题信息
    @ApiModelProperty(value="标题信息",name="assignTitle")
    private String assignTitle;

    // 审理机构省份
    @ApiModelProperty(value="审理机构省份",name="approveStateName")
    private String approveStateName;

    // 审理机构地市
    @ApiModelProperty(value="审理机构地市",name="approveCityName")
    private String approveCityName;
    // 审理机构省份
    @ApiModelProperty(value="审理机构省份",name="approveState")
    private String approveState;
    
    // 审理机构地市
    @ApiModelProperty(value="审理机构地市",name="approveCity")
    private String approveCity;

    // 审理机构名称
    @ApiModelProperty(value="审理机构名称",name="approveOrg")
    private String approveOrg;

    // 立案时间
    @ApiModelProperty(value="立案时间",name="caseCreateTime")
    private Date caseCreateTime;
    

	
    // 审理机关案件编号
    @ApiModelProperty(value="审理机关案件编号",name="caseCode")
    private String caseCode;

    // 案件所属专业线
    @ApiModelProperty(value="案件所属专业线",name="caseLine")
    private String caseLine;

    // 案由
    @ApiModelProperty(value="案由",name="caseCause")
    private String caseCause;

    // 案件类型
    @ApiModelProperty(value="案件类型",name="caseType")
    private String caseType;

    // 是否是重大诉讼
    @ApiModelProperty(value="是否是重大诉讼",name="lawsuitSize")
    private String lawsuitSize;

    // 争议金额
    @ApiModelProperty(value="争议金额",name="lawsuitMoney",example="0")
    private Double lawsuitMoney;

    // 我方诉讼主体
    @ApiModelProperty(value="我方诉讼主体",name="ourLawsuitBody")
    private String ourLawsuitBody;
    
	@ApiModelProperty(value="我方诉讼主体名称",name="ourLawsuitBodyName")
	private String ourLawsuitBodyName;

    // 对方诉讼主体
    @ApiModelProperty(value="对方诉讼主体",name="theyLawsuitBody")
    private String theyLawsuitBody;

    // 我方诉讼地位
    @ApiModelProperty(value="我方诉讼地位",name="ourLawsuitStatus")
    private String ourLawsuitStatus;

    // 其他涉案方
    @ApiModelProperty(value="其他涉案方",name="otherRelated")
    private String otherRelated;

    // 第三人
    @ApiModelProperty(value="第三人",name="thirdPerson")
    private String thirdPerson;

    // 同案原告
    @ApiModelProperty(value="同案原告",name="plaintiff")
    private String plaintiff;

    // 同案被告
    @ApiModelProperty(value="同案被告",name="defendant")
    private String defendant;

    @ApiModelProperty(value="送达时间",name="sendTime")
    private Date sendTime;

    // 原告诉讼请求
    @ApiModelProperty(value="原告诉讼请求",name="plaintiffRequest")
    private String plaintiffRequest;

    // 案件材料/附件
    @ApiModelProperty(value="案件材料/附件",name="caseFile")
    private String caseFile;

    // 转出单位
    @ApiModelProperty(value="转出单位",name="assignOrg")
    private String assignOrg;

    // 主送单位
    @ApiModelProperty(value="主送单位",name="mainSeedOrg")
    private String mainSeedOrg;
    // 主送单位
    @ApiModelProperty(value="主送单位",name="mainSeedOrgId")
    private String mainSeedOrgId;

    // 交办事项
    @ApiModelProperty(value="交办事项",name="assignItem")
    private String assignItem;

    // 上传附件
    @ApiModelProperty(value="上传附件",name="assignFile")
    private String assignFile;

    @ApiModelProperty(value="是否会签",name="signDept")
    private String signDept;
    
    @ApiModelProperty(value="状态",name="assignStatus")
    private String assignStatus;

    @ApiModelProperty("moduleName")
    private String moduleName;
    
    @ApiModelProperty("copyOrgId")
    private String copyOrgId;
    
    @ApiModelProperty("copyOrgName")
    private String copyOrgName;

    
    public String getCopyOrgId() {
		return copyOrgId;
	}

	public void setCopyOrgId(String copyOrgId) {
		this.copyOrgId = copyOrgId;
	}

	public String getCopyOrgName() {
		return copyOrgName;
	}

	public void setCopyOrgName(String copyOrgName) {
		this.copyOrgName = copyOrgName;
	}

	public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
    

	public String getAssignStatus() {
		return assignStatus;
	}

	public void setAssignStatus(String assignStatus) {
		this.assignStatus = assignStatus;
	}

	public String getSignDept() {
		return signDept;
	}

	public void setSignDept(String signDept) {
		this.signDept = signDept;
	}

	public String getAssignId() {
        return assignId;
    }

    public void setAssignId(String assignId) {
        this.assignId = assignId == null ? null : assignId.trim();
    }

    public String getAssignTitle() {
        return assignTitle;
    }

    public void setAssignTitle(String assignTitle) {
        this.assignTitle = assignTitle == null ? null : assignTitle.trim();
    }

    public String getApproveState() {
        return approveState;
    }

    public void setApproveState(String approveState) {
        this.approveState = approveState == null ? null : approveState.trim();
    }

    public String getApproveCity() {
        return approveCity;
    }

    public void setApproveCity(String approveCity) {
        this.approveCity = approveCity == null ? null : approveCity.trim();
    }

    public String getApproveOrg() {
        return approveOrg;
    }

    public void setApproveOrg(String approveOrg) {
        this.approveOrg = approveOrg == null ? null : approveOrg.trim();
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
        this.caseCode = caseCode == null ? null : caseCode.trim();
    }

    public String getCaseLine() {
        return caseLine;
    }

    public void setCaseLine(String caseLine) {
        this.caseLine = caseLine == null ? null : caseLine.trim();
    }

    public String getCaseCause() {
        return caseCause;
    }

    public void setCaseCause(String caseCause) {
        this.caseCause = caseCause == null ? null : caseCause.trim();
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType == null ? null : caseType.trim();
    }

    public String getLawsuitSize() {
        return lawsuitSize;
    }

    public void setLawsuitSize(String lawsuitSize) {
        this.lawsuitSize = lawsuitSize == null ? null : lawsuitSize.trim();
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
        this.ourLawsuitBody = ourLawsuitBody == null ? null : ourLawsuitBody.trim();
    }

    public String getTheyLawsuitBody() {
        return theyLawsuitBody;
    }

    public void setTheyLawsuitBody(String theyLawsuitBody) {
        this.theyLawsuitBody = theyLawsuitBody == null ? null : theyLawsuitBody.trim();
    }

    public String getOurLawsuitStatus() {
        return ourLawsuitStatus;
    }

    public void setOurLawsuitStatus(String ourLawsuitStatus) {
        this.ourLawsuitStatus = ourLawsuitStatus == null ? null : ourLawsuitStatus.trim();
    }

    public String getOtherRelated() {
        return otherRelated;
    }

    public void setOtherRelated(String otherRelated) {
        this.otherRelated = otherRelated == null ? null : otherRelated.trim();
    }

    public String getThirdPerson() {
        return thirdPerson;
    }

    public void setThirdPerson(String thirdPerson) {
        this.thirdPerson = thirdPerson == null ? null : thirdPerson.trim();
    }

    public String getPlaintiff() {
        return plaintiff;
    }

    public void setPlaintiff(String plaintiff) {
        this.plaintiff = plaintiff == null ? null : plaintiff.trim();
    }

    public String getDefendant() {
        return defendant;
    }

    public void setDefendant(String defendant) {
        this.defendant = defendant == null ? null : defendant.trim();
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
        this.plaintiffRequest = plaintiffRequest == null ? null : plaintiffRequest.trim();
    }

    public String getCaseFile() {
        return caseFile;
    }

    public void setCaseFile(String caseFile) {
        this.caseFile = caseFile == null ? null : caseFile.trim();
    }

    public String getAssignOrg() {
        return assignOrg;
    }

    public void setAssignOrg(String assignOrg) {
        this.assignOrg = assignOrg == null ? null : assignOrg.trim();
    }

    public String getMainSeedOrg() {
        return mainSeedOrg;
    }

    public void setMainSeedOrg(String mainSeedOrg) {
        this.mainSeedOrg = mainSeedOrg == null ? null : mainSeedOrg.trim();
    }

    public String getAssignItem() {
        return assignItem;
    }

    public void setAssignItem(String assignItem) {
        this.assignItem = assignItem == null ? null : assignItem.trim();
    }

    public String getAssignFile() {
        return assignFile;
    }

    public void setAssignFile(String assignFile) {
        this.assignFile = assignFile == null ? null : assignFile.trim();
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
		return "TCaseAssign [assignId=" + assignId + ", assignCode=" + assignCode + ", assignType=" + assignType
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
				+ ", signDept=" + signDept + ", assignStatus=" + assignStatus + ", moduleName=" + moduleName
				+ ", copyOrgId=" + copyOrgId + ", copyOrgName=" + copyOrgName + "]";
	}



    
}