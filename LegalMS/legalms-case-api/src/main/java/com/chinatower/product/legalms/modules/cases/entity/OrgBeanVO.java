package com.chinatower.product.legalms.modules.cases.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;



@Table(name = "t_pub_org")
@ToString
@ApiModel(value="组织Table")
public class OrgBeanVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "org_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(name="orgId",value = "组织id",required = true)
    private String orgId;	    	//组织id

	@Column(name = "org_id_old")
    @ApiModelProperty(name="orgIdOld",value = "组织id(旧)")
    private String orgIdOld;		//组织id(旧)

    @ApiModelProperty(name="orgName",value = "组织机构名称")
    @Column(name = "org_name")
    private String orgName;		//组织机构名称

    @Column(name = "org_vulgo")
    @ApiModelProperty(name="orgVulgo",value = "组织俗称")
    private String orgVulgo;		//组织俗称

	@Column(name = "org_code")
    @ApiModelProperty(name="orgCode",value = "组织机构编码")
    private String orgCode;		//组织机构编码

	@Column(name = "org_parent_id")
    @ApiModelProperty(name="orgParentId",value = "父组织id")
    private String orgParentId;		//父组织id

	@Column(name = "org_parent_id_old")
    @ApiModelProperty(name="orgParentIdOld",value = "父组织id(旧)")
    private String orgParentIdOld;		//父组织id(旧)

	@Column(name = "org_tree_level")
    @ApiModelProperty(name="orgTreeLevel",value = "组织树级别")
    private String orgTreeLevel;		//组织树级别

	@Column(name = "org_type")
    @ApiModelProperty(name="orgType",value = "组织类型")
    private String orgType;		//组织类型

	@Column(name = "org_unit_id")
    @ApiModelProperty(name="orgUnitId",value = "组织所属单位id")
    private String orgUnitId;		//组织所属单位id

	@Column(name = "org_dept_id")
    @ApiModelProperty(name="orgDeptId",value = "组织所属部门id")
    private String orgDeptId;		//组织所属部门id

	@Column(name = "org_dept_id_old")
    @ApiModelProperty(name="orgDeptIdOld",value = "组织所属部门id(旧)")
    private String orgDeptIdOld;		//组织所属部门id(旧)

	@Column(name = "org_abbreviation_cn")
    @ApiModelProperty(name="orgAbbreviationCn",value = "组织简称（中文）")
    private String orgAbbreviationCn;		//组织简称（中文）

	@Column(name = "org_abbreviation_en")
    @ApiModelProperty(name="orgAbbreviationEn",value = "组织简称（英文）")
    private String orgAbbreviationEn;		//组织简称（英文）

	@Column(name = "org_station_cn")
    @ApiModelProperty(name="orgStationCn",value = "组织驻地简称（中文）")
    private String orgStationCn;		//组织驻地简称（中文）

	@Column(name = "org_station_en")
    @ApiModelProperty(name="orgStationEn",value = "组织驻地简称（英文）")
    private String orgStationEn;		//组织驻地简称（英文）

	@Column(name = "org_email")
    @ApiModelProperty(name="orgEmail",value = "组织邮箱")
    private String orgEmail;		//组织邮箱

	@Column(name = "org_fax")
    @ApiModelProperty(name="orgFax",value = "组织传真")
    private String orgFax;		//组织传真

	@Column(name = "org_phone")
    @ApiModelProperty(name="orgPhone",value = "组织电话")
    private String orgPhone;		//组织电话

	@Column(name = "org_order")
    @ApiModelProperty(name="orgOrder",value = "组织顺序号")
    private Integer orgOrder;		//组织顺序号

	@Column(name = "org_status")
    @ApiModelProperty(name="orgStatus",value = "组织状态")
    private String orgStatus;		//组织状态

	@Column(name = "org_status_update_time")
    @ApiModelProperty(name="orgStatusUpdateTime",value = "组织最后变更时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date orgStatusUpdateTime;		//组织最后变更时间

	@Column(name = "org_create_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(name="orgCreateTime",value = "组织创建时间")
    private Date orgCreateTime;		//组织创建时间

	@Column(name = "org_remarks")
    @ApiModelProperty(name="orgRemarks",value = "备注")
    private String orgRemarks;		//备注

	@Column(name = "enterp_type")
    @ApiModelProperty(name="enterpType",value = "企业类型")
    private String enterpType;		//企业类型

	@Column(name = "org_level")
    @ApiModelProperty(name="orgLevel",value = "组织机构等级")
    private String orgLevel;		//组织机构等级

	@Column(name = "org_parent_code")
    @ApiModelProperty(name="orgParentCode",value = "上级组织机构")
    private String orgParentCode;		//上级组织机构

	@Column(name = "org_change")
    @ApiModelProperty(name="orgChange",value = "组织机构变更事项")
    private String orgChange;		//组织机构变更事项

	@Column(name = "org_path")
    @ApiModelProperty(name="orgPath",value = "组织机构路径")
    private String orgPath;		//组织机构路径

    @Column(name = "mdm_org_code")
    @ApiModelProperty(name="mdmOrgCode",value = "mdmOrgCode")
    private String mdmOrgCode;

    @Column(name = "org_base_id")
    @ApiModelProperty(name="orgBaseId",value = "orgBaseId")
    private Integer orgBaseId;

    @Column(name = "org_reserved_text1")
    @ApiModelProperty(name="orgReservedText1",value = "orgReservedText1")
    private String orgReservedText1;

    @Column(name = "area_name")
    @ApiModelProperty(name="areaName",value = "areaName")
    private String areaName;

    @Column(name = "is_unit")
    @ApiModelProperty(name="isUnit",value = "isUnit")
    private String isUnit;

    @ApiModelProperty(name="companyCode",value = "companyCode")
    private String companyCode;


    /*增加数据*/
    private String userName;

    private String accountId;


    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgIdOld() {
        return orgIdOld;
    }

    public void setOrgIdOld(String orgIdOld) {
        this.orgIdOld = orgIdOld;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgVulgo() {
        return orgVulgo;
    }

    public void setOrgVulgo(String orgVulgo) {
        this.orgVulgo = orgVulgo;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgParentId() {
        return orgParentId;
    }

    public void setOrgParentId(String orgParentId) {
        this.orgParentId = orgParentId;
    }

    public String getOrgParentIdOld() {
        return orgParentIdOld;
    }

    public void setOrgParentIdOld(String orgParentIdOld) {
        this.orgParentIdOld = orgParentIdOld;
    }

    public String getOrgTreeLevel() {
        return orgTreeLevel;
    }

    public void setOrgTreeLevel(String orgTreeLevel) {
        this.orgTreeLevel = orgTreeLevel;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getOrgUnitId() {
        return orgUnitId;
    }

    public void setOrgUnitId(String orgUnitId) {
        this.orgUnitId = orgUnitId;
    }

    public String getOrgDeptId() {
        return orgDeptId;
    }

    public void setOrgDeptId(String orgDeptId) {
        this.orgDeptId = orgDeptId;
    }

    public String getOrgDeptIdOld() {
        return orgDeptIdOld;
    }

    public void setOrgDeptIdOld(String orgDeptIdOld) {
        this.orgDeptIdOld = orgDeptIdOld;
    }

    public String getOrgAbbreviationCn() {
        return orgAbbreviationCn;
    }

    public void setOrgAbbreviationCn(String orgAbbreviationCn) {
        this.orgAbbreviationCn = orgAbbreviationCn;
    }

    public String getOrgAbbreviationEn() {
        return orgAbbreviationEn;
    }

    public void setOrgAbbreviationEn(String orgAbbreviationEn) {
        this.orgAbbreviationEn = orgAbbreviationEn;
    }

    public String getOrgStationCn() {
        return orgStationCn;
    }

    public void setOrgStationCn(String orgStationCn) {
        this.orgStationCn = orgStationCn;
    }

    public String getOrgStationEn() {
        return orgStationEn;
    }

    public void setOrgStationEn(String orgStationEn) {
        this.orgStationEn = orgStationEn;
    }

    public String getOrgEmail() {
        return orgEmail;
    }

    public void setOrgEmail(String orgEmail) {
        this.orgEmail = orgEmail;
    }

    public String getOrgFax() {
        return orgFax;
    }

    public void setOrgFax(String orgFax) {
        this.orgFax = orgFax;
    }

    public String getOrgPhone() {
        return orgPhone;
    }

    public void setOrgPhone(String orgPhone) {
        this.orgPhone = orgPhone;
    }

    public Integer getOrgOrder() {
        return orgOrder;
    }

    public void setOrgOrder(Integer orgOrder) {
        this.orgOrder = orgOrder;
    }

    public String getOrgStatus() {
        return orgStatus;
    }

    public void setOrgStatus(String orgStatus) {
        this.orgStatus = orgStatus;
    }

    public Date getOrgStatusUpdateTime() {
        return orgStatusUpdateTime;
    }

    public void setOrgStatusUpdateTime(Date orgStatusUpdateTime) {
        this.orgStatusUpdateTime = orgStatusUpdateTime;
    }

    public Date getOrgCreateTime() {
        return orgCreateTime;
    }

    public void setOrgCreateTime(Date orgCreateTime) {
        this.orgCreateTime = orgCreateTime;
    }

    public String getOrgRemarks() {
        return orgRemarks;
    }

    public void setOrgRemarks(String orgRemarks) {
        this.orgRemarks = orgRemarks;
    }

    public String getEnterpType() {
        return enterpType;
    }

    public void setEnterpType(String enterpType) {
        this.enterpType = enterpType;
    }

    public String getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(String orgLevel) {
        this.orgLevel = orgLevel;
    }

    public String getOrgParentCode() {
        return orgParentCode;
    }

    public void setOrgParentCode(String orgParentCode) {
        this.orgParentCode = orgParentCode;
    }

    public String getOrgChange() {
        return orgChange;
    }

    public void setOrgChange(String orgChange) {
        this.orgChange = orgChange;
    }

    public String getOrgPath() {
        return orgPath;
    }

    public void setOrgPath(String orgPath) {
        this.orgPath = orgPath;
    }

    public String getMdmOrgCode() {
        return mdmOrgCode;
    }

    public void setMdmOrgCode(String mdmOrgCode) {
        this.mdmOrgCode = mdmOrgCode;
    }

    public Integer getOrgBaseId() {
        return orgBaseId;
    }

    public void setOrgBaseId(Integer orgBaseId) {
        this.orgBaseId = orgBaseId;
    }

    public String getOrgReservedText1() {
        return orgReservedText1;
    }

    public void setOrgReservedText1(String orgReservedText1) {
        this.orgReservedText1 = orgReservedText1;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getIsUnit() {
        return isUnit;
    }

    public void setIsUnit(String isUnit) {
        this.isUnit = isUnit;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}