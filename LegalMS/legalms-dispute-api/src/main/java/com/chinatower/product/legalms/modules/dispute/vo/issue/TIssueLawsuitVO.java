package com.chinatower.product.legalms.modules.dispute.vo.issue;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.chinatower.product.legalms.modules.flow.entity.unview.Tbase;
import com.chinatower.product.legalms.modules.flow.vo.flow.TFlowMainVO;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("诉讼")
public class TIssueLawsuitVO extends Tbase{
	@ApiModelProperty(value = "诉讼ID", name = "lawsuitId")
	private String lawsuitId;

	@ApiModelProperty(value = "诉讼编码", name = "lawsuitCode")
	private String lawsuitCode;

	@ApiModelProperty(value = "标题", name = "lawsuitTitle")
	private String lawsuitTitle;

	@ApiModelProperty(value = "关联引诉纠纷ID", name = "guideId")
	private String guideId;

	@ApiModelProperty(value = "关联案件交办", name = "caseId")
	private String caseId;

	@ApiModelProperty(value = "审理机构省份", name = "approveState")
	private String approveState;

	@ApiModelProperty(value = "审理机构地市", name = "approveCity")
	private String approveCity;

	@ApiModelProperty(value = "审理机构名称", name = "approveOrg")
	private String approveOrg;

	@ApiModelProperty(value = "案件审级", name = "approveLevel")
	private String approveLevel;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
	@ApiModelProperty(value = "立案时间", name = "caseCreateTime")
	private Date caseCreateTime;


	@ApiModelProperty(value = "案件类型", name = "caseType")
	private String caseType;

	@ApiModelProperty(value = "起诉/应诉", name = "indictOrUnindict")
	private String indictOrUnindict;

	@ApiModelProperty(value = "案件所属专业线", name = "caseLine")
	private String caseLine;

	@ApiModelProperty(value = "案由", name = "caseCause")
	private String caseCause;

	@ApiModelProperty(value = "我方诉讼主体", name = "ourLawsuitBody")
	private String ourLawsuitBody;

	@ApiModelProperty(value="我方诉讼主体名称",name="ourLawsuitBodyName")
	private String ourLawsuitBodyName;
	
	@ApiModelProperty(value = "对方诉讼主体", name = "theyLawsuitBody")
	private String theyLawsuitBody;

	@ApiModelProperty(value = "我方诉讼地位", name = "ourLawsuitStatus")
	private String ourLawsuitStatus;

	@ApiModelProperty(value = "争议金额", name = "lawsuitMoney")
	private String lawsuitMoney;

	@ApiModelProperty(value = "是否是重大诉讼", name = "lawsuitSize")
	private String lawsuitSize;

	@ApiModelProperty(value = "案件基本事实", name = "lawsuitDetail")
	private String lawsuitDetail;

	@ApiModelProperty(value = "附件", name = "fileUrl")
	private String fileUrl;

    @ApiModelProperty(value="附件信息",name="files")
    private List<Map<String, Object>> files;
    
	@ApiModelProperty(value = "其他涉案方", name = "otherRelated")
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
    
	@ApiModelProperty(value = "是否会签", name = "signDept")
	private String signDept;

	@ApiModelProperty(value = "状态", name = "lawsuitStatus")
	private String lawsuitStatus;

	@ApiModelProperty("审批主对象")
	private TFlowMainVO mian;

	@ApiModelProperty("当前活动ID")
	private String currActivityDefId;

	@ApiModelProperty("当前活动名称")
	private String currActivityDefName;

	@ApiModelProperty("moduleName")
	private String moduleName;

    @ApiModelProperty(value="案件分析",name="lawsuitAnalyze")
    private String lawsuitAnalyze;
    
    @ApiModelProperty(value="案件建议",name="lawsuitAdvise")
    private String lawsuitAdvise;

    
    @ApiModelProperty(value = "处理建议", name = "businessAdvice")
    public String businessAdvice;

    @ApiModelProperty(value = "是否已读", name = "isUnView")
    private String isUnView;
    
	@ApiModelProperty(name = "createCaseType", value = "生成卷宗类型 自动:0,关联:1")
	private Integer createCaseType;
	
	@ApiModelProperty(name = "caseTitle", value = "关联卷宗标题")
	private String caseTitle;
	
	
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

	public String getIsUnView() {
		return isUnView;
	}

	public void setIsUnView(String isUnView) {
		this.isUnView = isUnView;
	}

	public String getBusinessAdvice() {
		return businessAdvice;
	}

	public void setBusinessAdvice(String businessAdvice) {
		this.businessAdvice = businessAdvice;
	}

    public String getLawsuitAnalyze() {
		return lawsuitAnalyze;
	}

	public void setLawsuitAnalyze(String lawsuitAnalyze) {
		this.lawsuitAnalyze = lawsuitAnalyze;
	}

	public String getLawsuitAdvise() {
		return lawsuitAdvise;
	}

	public void setLawsuitAdvise(String lawsuitAdvise) {
		this.lawsuitAdvise = lawsuitAdvise;
	}
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getLawsuitId() {
		return lawsuitId;
	}


	public void setLawsuitId(String lawsuitId) {
		this.lawsuitId = lawsuitId;
	}

	public String getLawsuitCode() {
		return lawsuitCode;
	}

	public void setLawsuitCode(String lawsuitCode) {
		this.lawsuitCode = lawsuitCode;
	}

	public String getLawsuitTitle() {
		return lawsuitTitle;
	}

	public void setLawsuitTitle(String lawsuitTitle) {
		this.lawsuitTitle = lawsuitTitle;
	}

	public String getGuideId() {
		return guideId;
	}

	public void setGuideId(String guideId) {
		this.guideId = guideId;
	}

	public String getCaseId() {
		return caseId;
	}


	public void setCaseId(String caseId) {
		this.caseId = caseId;
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

	public String getApproveLevel() {
		return approveLevel;
	}

	public void setApproveLevel(String approveLevel) {
		this.approveLevel = approveLevel;
	}

	public Date getCaseCreateTime() {
		return caseCreateTime;
	}

	public void setCaseCreateTime(Date caseCreateTime) {
		this.caseCreateTime = caseCreateTime;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getIndictOrUnindict() {
		return indictOrUnindict;
	}

	public void setIndictOrUnindict(String indictOrUnindict) {
		this.indictOrUnindict = indictOrUnindict;
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

	public String getLawsuitMoney() {
		return lawsuitMoney;
	}

	public void setLawsuitMoney(String lawsuitMoney) {
		this.lawsuitMoney = lawsuitMoney;
	}

	public String getLawsuitSize() {
		return lawsuitSize;
	}

	public void setLawsuitSize(String lawsuitSize) {
		this.lawsuitSize = lawsuitSize;
	}

	public String getLawsuitDetail() {
		return lawsuitDetail;
	}

	public void setLawsuitDetail(String lawsuitDetail) {
		this.lawsuitDetail = lawsuitDetail;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getOtherRelated() {
		return otherRelated;
	}

	public void setOtherRelated(String otherRelated) {
		this.otherRelated = otherRelated;
	}

	public String getSignDept() {
		return signDept;
	}

	public void setSignDept(String signDept) {
		this.signDept = signDept;
	}

	public String getLawsuitStatus() {
		return lawsuitStatus;
	}

	public void setLawsuitStatus(String lawsuitStatus) {
		this.lawsuitStatus = lawsuitStatus;
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

	public TIssueLawsuitVO setCurrActivityDefId(String currActivityDefId) {
		this.currActivityDefId = currActivityDefId;
		return this;
	}

	public String getCurrActivityDefName() {
		return currActivityDefName;
	}

	public TIssueLawsuitVO setCurrActivityDefName(String currActivityDefName) {
		this.currActivityDefName = currActivityDefName;
		return this;
	}

	public List<Map<String, Object>> getFiles() {
		return files;
	}

	public void setFiles(List<Map<String, Object>> files) {
		this.files = files;
	}

	public String getOurLawsuitBodyName() {
		return ourLawsuitBodyName;
	}

	public void setOurLawsuitBodyName(String ourLawsuitBodyName) {
		this.ourLawsuitBodyName = ourLawsuitBodyName;
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







}
