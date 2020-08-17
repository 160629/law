package com.chinatower.product.legalms.modules.dispute.vo.jointly;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

public class JointlyParam {
    @ApiModelProperty(value = "页码", name = "pageNum")
    public Integer pageNum;
    @ApiModelProperty(value = "单页数量", name = "pageSize")
    public Integer pageSize;
    @ApiModelProperty(value = "标题", name = "jointlyTitle")
    public String jointlyTitle;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "开始时间", name = "startDate", example = "2000-10-10 00:00:00")
    public Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "结束时间", name = "endDate", example = "2000-10-10 00:00:00")
    public Date endDate;
    @ApiModelProperty(value = "编号", name = "code")
    public String jointlyCode;
    @ApiModelProperty(value = "协办部门",name = "jointlyOrg")
    public String jointlyOrg;
    @ApiModelProperty(value = "关联案件",name = "caseTitle")
    private String caseTitle;
    @ApiModelProperty(value="1 =关联页面查询",name="correlate")
    private Integer correlate;
    @ApiModelProperty(value="状态",name="jointlyStatus")
    private String jointlyStatus;
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
    @ApiModelProperty(value="组织机构类型",name="type")
    private String type;
    @ApiModelProperty(value="组织机构树的id",name="id")
    private String id;

    private List<String> orgList;

    @ApiModelProperty("协办单位")
    private String  jointlyUnitName;

    @ApiModelProperty("协办单位id")
    private String jointlyUnitId;

    @ApiModelProperty("执行单位层级")
    private String unitLevel;

    @ApiModelProperty(value = "",name = "")
    private List<String> orgCodeList;

    public List<String> getOrgCodeList() {
        return orgCodeList;
    }

    public void setOrgCodeList(List<String> orgCodeList) {
        this.orgCodeList = orgCodeList;
    }

    public String getUnitLevel() {
        return unitLevel;
    }

    public void setUnitLevel(String unitLevel) {
        this.unitLevel = unitLevel;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getOrgList() {
        return orgList;
    }

    public void setOrgList(List<String> orgList) {
        this.orgList = orgList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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


    public String getJointlyOrg() {
        return jointlyOrg;
    }

    public void setJointlyOrg(String jointlyOrg) {
        this.jointlyOrg = jointlyOrg;
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

    public String getJointlyTitle() {
        return jointlyTitle;
    }

    public void setJointlyTitle(String jointlyTitle) {
        this.jointlyTitle = jointlyTitle;
    }

    public String getJointlyCode() {
        return jointlyCode;
    }

    public void setJointlyCode(String jointlyCode) {
        this.jointlyCode = jointlyCode;
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

    public String getCaseTitle() {
        return caseTitle;
    }

    public void setCaseTitle(String caseTitle) {
        this.caseTitle = caseTitle;
    }

    public Integer getCorrelate() {
        return correlate;
    }

    public void setCorrelate(Integer correlate) {
        this.correlate = correlate;
    }

    public String getJointlyStatus() {
        return jointlyStatus;
    }

    public void setJointlyStatus(String jointlyStatus) {
        this.jointlyStatus = jointlyStatus;
    }

}
