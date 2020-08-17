package com.chinatower.product.legalms.modules.dispute.vo.issue;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("列表查询对象")
public class ListParam {
	@ApiModelProperty(value = "页码", name = "pageNum")
	public Integer pageNum;
	@ApiModelProperty(value = "单页数量", name = "pageSize")
	public Integer pageSize;
	@ApiModelProperty(value = "标题", name = "title")
	public String title;
	@ApiModelProperty(value = "编号", name = "code")
	public String code;
	@ApiModelProperty(value = "审理机构", name = "approveOrg")
	public String approveOrg;
	@ApiModelProperty(value = "案件类型", name = "caseType")
	public String caseType;
	@ApiModelProperty(value = "主送单位", name = "mainSeedOrg")
	public String mainSeedOrg;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
	@ApiModelProperty(value = "开始时间", name = "startDate", example = "2000-10-10 00:00:00")
	public Date startDate;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
	@ApiModelProperty(value = "结束时间", name = "endDate", example = "2000-10-10 00:00:00")
	public Date endDate;
    @ApiModelProperty(value="我方争议主体名称",name="ourLawsuitBodyName")
    private String ourLawsuitBodyName;
    @ApiModelProperty(value="对方争议主体名称",name="otherDeputeBody")
    private String otherDeputeBody;
	@ApiModelProperty(value="对方诉讼主体名称",name="theyLawsuitBody")
	private String theyLawsuitBody;
    @ApiModelProperty(value="1 =关联页面查询",name="correlate")
    private Integer correlate;

    @ApiModelProperty(value="执行结果",name="result")
    private String result;
    
    @ApiModelProperty(value="案件ID",name="caseId")
    private String caseId;
    @ApiModelProperty(value="状态",name="status")
    private String status;
	@ApiModelProperty(value="公司ID",name="orgId")
	private String orgId;
	@ApiModelProperty(value="公司名称",name="orgName")
	private String orgName;
	@ApiModelProperty(value="企业类型",name="enterpType")
	private String enterpType;/*单位编码 例：CT 代表铁塔 TE 能源 TZ 智联*/
	@ApiModelProperty(value="部门ID",name="deptId")
	private String deptId;
	@ApiModelProperty(value="起草人",name="createAcct")
	private String loginAcct;
	@ApiModelProperty(value="组织机构树的类型",name="type")
	private String type;
	@ApiModelProperty(value="组织机构树的id",name="type")
	private String id;

    private List<String> orgList;

	public String getTheyLawsuitBody() {
		return theyLawsuitBody;
	}

	public void setTheyLawsuitBody(String theyLawsuitBody) {
		this.theyLawsuitBody = theyLawsuitBody;
	}

	public List<String> getOrgList() {
		return orgList;
	}

	public void setOrgList(List<String> orgList) {
		this.orgList = orgList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getEnterpType() {
		return enterpType;
	}

	public void setEnterpType(String enterpType) {
		this.enterpType = enterpType;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getLoginAcct() {
		return loginAcct;
	}

	public void setLoginAcct(String loginAcct) {
		this.loginAcct = loginAcct;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getCorrelate() {
		return correlate;
	}

	public void setCorrelate(Integer correlate) {
		this.correlate = correlate;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getOurLawsuitBodyName() {
		return ourLawsuitBodyName;
	}

	public void setOurLawsuitBodyName(String ourLawsuitBodyName) {
		this.ourLawsuitBodyName = ourLawsuitBodyName;
	}

	public String getOtherDeputeBody() {
		return otherDeputeBody;
	}

	public void setOtherDeputeBody(String otherDeputeBody) {
		this.otherDeputeBody = otherDeputeBody;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getApproveOrg() {
		return approveOrg;
	}

	public void setApproveOrg(String approveOrg) {
		this.approveOrg = approveOrg;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getMainSeedOrg() {
		return mainSeedOrg;
	}

	public void setMainSeedOrg(String mainSeedOrg) {
		this.mainSeedOrg = mainSeedOrg;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	@Override
	public String toString() {
		return "ListParam [pageNum=" + pageNum + ", pageSize=" + pageSize + ", title=" + title + ", code=" + code
				+ ", approveOrg=" + approveOrg + ", caseType=" + caseType + ", mainSeedOrg=" + mainSeedOrg
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", ourLawsuitBodyName=" + ourLawsuitBodyName
				+ ", otherDeputeBody=" + otherDeputeBody + ", correlate=" + correlate + ", result=" + result
				+ ", caseId=" + caseId + ", status=" + status + "]";
	}


}
