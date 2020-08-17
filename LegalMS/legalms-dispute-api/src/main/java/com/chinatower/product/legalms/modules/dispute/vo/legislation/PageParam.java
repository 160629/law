package com.chinatower.product.legalms.modules.dispute.vo.legislation;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel("法律文书列表查询参数")
public class PageParam {
    @ApiModelProperty(value = "页码", name = "pageNum")
    public Integer pageNum;
    @ApiModelProperty(value = "单页数量", name = "pageSize")
    public Integer pageSize;
    @ApiModelProperty(value = "标题", name = "title")
    public String title;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "开始时间", name = "startDate", example = "2000-10-10 00:00:00")
    public Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "结束时间", name = "endDate", example = "2000-10-10 00:00:00")
    public Date endDate;
    @ApiModelProperty(value ="单号",name = "odd")
    public String odd;
    @ApiModelProperty(value = "最小金额",name = "minAmount")
    public Double minAmount;
    @ApiModelProperty(value = "最大金额",name = "maxAmount")
    public Double maxAmount;
    @ApiModelProperty(value = "执行部门",name="executiveArm")
    public String executiveArm;
    @ApiModelProperty(value = "关联案件",name = "caseTitle")
    public String caseTitle;
    @ApiModelProperty(value = "状态",name = "state")
    public String state;
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
    @ApiModelProperty(value = "组织机构树的id")
    private String id;
    @ApiModelProperty("组织机构树的type")
    private String type;

    @ApiModelProperty("执行单位")
    private String  executeUnitName;

    @ApiModelProperty("执行单位id")
    private String executeUnitId;

    @ApiModelProperty(value = "",name = "")
    private List<String> orgCodeList;

    @ApiModelProperty("执行单位层级")
    private String unitLevel;

    public String getUnitLevel() {
        return unitLevel;
    }

    public void setUnitLevel(String unitLevel) {
        this.unitLevel = unitLevel;
    }

    public List<String> getOrgCodeList() {
        return orgCodeList;
    }

    public void setOrgCodeList(List<String> orgCodeList) {
        this.orgCodeList = orgCodeList;
    }

    public String getExecuteUnitName() {
        return executeUnitName;
    }

    public void setExecuteUnitName(String executeUnitName) {
        this.executeUnitName = executeUnitName;
    }

    public String getExecuteUnitId() {
        return executeUnitId;
    }

    public void setExecuteUnitId(String executeUnitId) {
        this.executeUnitId = executeUnitId;
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

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getOdd() {
        return odd;
    }

    public void setOdd(String odd) {
        this.odd = odd;
    }

    public Double getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Double minAmount) {
        this.minAmount = minAmount;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getExecutiveArm() {
        return executiveArm;
    }

    public void setExecutiveArm(String executiveArm) {
        this.executiveArm = executiveArm;
    }

    public String getCaseTitle() {
        return caseTitle;
    }

    public void setCaseTitle(String caseTitle) {
        this.caseTitle = caseTitle;
    }
}
