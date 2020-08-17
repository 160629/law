package com.chinatower.product.legalms.modules.cases.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;


@ApiModel(value = "卷宗移交")
public class CaseTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(name = "caseId", value = "案件ID", required = true)
    private String caseId;
    @ApiModelProperty(name = "caseReceptUser", value = "案件承接人")
    private String caseReceptUser;
    @ApiModelProperty(name = "caseTransferUser", value = "案件移交人")
    private String caseTransferUser;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(name = "caseTransferTime", value = "案件移交时间")
    private Date caseTransferTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(name = "startDate", value = "startDate", hidden = true)
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(name = "endDate", value = "endDate", hidden = true)
    private Date endDate;
    @ApiModelProperty(name = "receptUserId", value = "案件承接人id")
    private String receptUserId;
    @ApiModelProperty(name = "transferUserId", value = "案件移交人")
    private String transferUserId;
    @ApiModelProperty(name = "caseCode", value = "案件编号")
    private String caseCode;
    @ApiModelProperty(name = "caseTitle", value = "案件标题")
    private String caseTitle;
    @ApiModelProperty(name = "caseStatus", value = "案件状态")
    private String caseStatus;
    @ApiModelProperty(name = "caseIdList",value = "caseIdList")
    private List<String> caseIdList;
    @ApiModelProperty(name = "accountId",value = "accountId", hidden = true)
    private String accountId;
    @ApiModelProperty(name = "accountName",value = "accountName", hidden = true)
    private String accountName;
    @ApiModelProperty(name = "deptId",value = "deptId", hidden = true)
    private String deptId;
    @ApiModelProperty(name = "unitIdList",value = "unitIdList", hidden = true)
    private List<String> unitIdList;

    private String receptorUnitId;//承接人公司code

    private String transferUnitId;//承接人公司code

    private List<String> caseStatusList;

    private List<String> caseCodeList;

    /*勿动 用于权限查询*/
    private List<String> companyCodeList;

    private String superRole;


    public String getSuperRole() {
        return superRole;
    }

    public void setSuperRole(String superRole) {
        this.superRole = superRole;
    }

    private String tansferReason;

    public String getTransferUnitId() {
        return transferUnitId;
    }

    public void setTransferUnitId(String transferUnitId) {
        this.transferUnitId = transferUnitId;
    }

    @ApiModelProperty(value = "页码", name = "pageNum", hidden = true)
    private Integer pageNum;

    @ApiModelProperty(value = "单页数量", name = "pageSize", hidden = true)
    private Integer pageSize;

    public List<String> getUnitIdList() {
        return unitIdList;
    }

    public void setUnitIdList(List<String> unitIdList) {
        this.unitIdList = unitIdList;
    }

    public String getReceptorUnitId() {
        return receptorUnitId;
    }

    public void setReceptorUnitId(String receptorUnitId) {
        this.receptorUnitId = receptorUnitId;
    }

    public List<String> getCaseStatusList() {
        return caseStatusList;
    }

    public void setCaseStatusList(List<String> caseStatusList) {
        this.caseStatusList = caseStatusList;
    }

    public List<String> getCaseCodeList() {
        return caseCodeList;
    }

    public void setCaseCodeList(List<String> caseCodeList) {
        this.caseCodeList = caseCodeList;
    }

    public List<String> getCompanyCodeList() {
        return companyCodeList;
    }

    public void setCompanyCodeList(List<String> companyCodeList) {
        this.companyCodeList = companyCodeList;
    }

    public String getTansferReason() {
        return tansferReason;
    }

    public void setTansferReason(String tansferReason) {
        this.tansferReason = tansferReason;
    }



    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public List<String> getCaseIdList() {
        return caseIdList;
    }

    public void setCaseIdList(List<String> caseIdList) {
        this.caseIdList = caseIdList;
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

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId == null ? null : caseId.trim();
    }

    public String getCaseReceptUser() {
        return caseReceptUser;
    }

    public void setCaseReceptUser(String caseReceptUser) {
        this.caseReceptUser = caseReceptUser == null ? null : caseReceptUser.trim();
    }

    public String getCaseTransferUser() {
        return caseTransferUser;
    }

    public void setCaseTransferUser(String caseTransferUser) {
        this.caseTransferUser = caseTransferUser == null ? null : caseTransferUser.trim();
    }

    public Date getCaseTransferTime() {
        return caseTransferTime;
    }

    public void setCaseTransferTime(Date caseTransferTime) {
        this.caseTransferTime = caseTransferTime;
    }

    public String getReceptUserId() {
        return receptUserId;
    }

    public void setReceptUserId(String receptUserId) {
        this.receptUserId = receptUserId == null ? null : receptUserId.trim();
    }

    public String getTransferUserId() {
        return transferUserId;
    }

    public void setTransferUserId(String transferUserId) {
        this.transferUserId = transferUserId == null ? null : transferUserId.trim();
    }
}