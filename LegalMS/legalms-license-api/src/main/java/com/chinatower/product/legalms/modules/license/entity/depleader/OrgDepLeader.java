package com.chinatower.product.legalms.modules.license.entity.depleader;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Table(name="t_orgdep_leader")
@ApiModel("分管领导实体类")
public class OrgDepLeader {

    @Id
    @ApiModelProperty(value="主键",name="id" ,hidden = true)
    private Integer id;

    /*t_deporg_leader*/
    @ApiModelProperty(value="组织code",name="orgCode" )
    private String orgCode;

    /*用于限制单位类型*/
    @ApiModelProperty(value="currOrgCode",name="currOrgCode",hidden = true )
    private String currOrgCode;
    /*过滤组织表公司领导部门*/
    @ApiModelProperty(value="filterOrgName",name="filterOrgName",hidden = true )
    private String filterOrgName;

    private String superRole;//超级管理员

    private String enterpType;

    @ApiModelProperty(value="组织ID",name="orgId",hidden = true )
    private Long orgId;

    @ApiModelProperty(value="部门名称",name="orgName")
    private String orgName;

    @ApiModelProperty(value="公司名称",name="compName")
    private String compName;

    @ApiModelProperty(value="分管部门领导部门code",name="accountId",hidden = true )
    private String accountId;

    @ApiModelProperty(value="分管领导ID",name="userId" ,hidden = true)
    private String userId;

    @ApiModelProperty(value="分管领导名称",name="empName" )
    private String empName;

    @ApiModelProperty(value="创建时间",name="createTime" ,hidden = true)
    private Date createTime;

    @ApiModelProperty(value="更新时间",name="lastUpdate" ,hidden = true)
    private Date lastUpdate;

    @ApiModelProperty(value="更新用户ID",name="updateUserId" ,hidden = true)
    private String updateUserId;

    @ApiModelProperty(value="是否设置(1是0否)",name="isSetup")
    private String isSetup;

    @ApiModelProperty(value="分管领导名称（字符串以','分割)",name="empNameStr")
    private String  empNameStr;

    /*分管领导账号*/
    @ApiModelProperty(value="分管领导账号",name="userCode",hidden = true)
    private String  userCode;

    private String  leadeOrgCode;

    /*分管领导账号拼接*/
    private String userCodeStr;

    /*分管领导组织code(t_pub_account_tab)*/
    @ApiModelProperty(value="领导orgID",name="accountOrgId")
    private String  accountOrgId;

    @ApiModelProperty(value="父级code",name="orgParentCode",hidden = true)
    private String  orgParentCode;

    private String  orgLevel;

    @ApiModelProperty(value="orgPath",name="orgPath",hidden = true)
    private String  orgPath;

    private List<String> orgCodeList;


    @ApiModelProperty(value="页码",name="pageNum",hidden = true)
    private Integer pageNum;

    @ApiModelProperty(value="单页数量",name="pageSize",hidden = true)
    private Integer pageSize;


    public String getLeadeOrgCode() {
        return leadeOrgCode;
    }

    public void setLeadeOrgCode(String leadeOrgCode) {
        this.leadeOrgCode = leadeOrgCode;
    }

    public String getEnterpType() {
        return enterpType;
    }

    public void setEnterpType(String enterpType) {
        this.enterpType = enterpType;
    }

    public List<String> getOrgCodeList() {
        return orgCodeList;
    }

    public void setOrgCodeList(List<String> orgCodeList) {
        this.orgCodeList = orgCodeList;
    }

    public String getSuperRole() {
        return superRole;
    }

    public void setSuperRole(String superRole) {
        this.superRole = superRole;
    }

    public String getFilterOrgName() {
        return filterOrgName;
    }

    public void setFilterOrgName(String filterOrgName) {
        this.filterOrgName = filterOrgName;
    }

    public String getCurrOrgCode() {
        return currOrgCode;
    }

    public void setCurrOrgCode(String currOrgCode) {
        this.currOrgCode = currOrgCode;
    }

    public String getUserCodeStr() {
        return userCodeStr;
    }

    public void setUserCodeStr(String userCodeStr) {
        this.userCodeStr = userCodeStr;
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
    public String getOrgParentCode() {
        return orgParentCode;
    }

    public void setOrgParentCode(String orgParentCode) {
        this.orgParentCode = orgParentCode;
    }

    public String getOrgPath() {
        return orgPath;
    }

    public void setOrgPath(String orgPath) {
        this.orgPath = orgPath;
    }

    public String getAccountOrgId() {
        return accountOrgId;
    }

    public void setAccountOrgId(String accountOrgId) {
        this.accountOrgId = accountOrgId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getEmpNameStr() {
        return empNameStr;
    }

    public void setEmpNameStr(String empNameStr) {
        this.empNameStr = empNameStr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName == null ? null : compName.trim();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName == null ? null : empName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId == null ? null : updateUserId.trim();
    }

    public String getIsSetup() {
        return isSetup;
    }

    public void setIsSetup(String isSetup) {
        this.isSetup = isSetup;
    }


    public String getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(String orgLevel) {
        this.orgLevel = orgLevel;
    }
}