package com.chinatower.product.legalms.modules.flow.entity.common;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Table(name = "t_pub_org")
public class OrgBeanVO implements Serializable {
	private static final long serialVersionUID = 1L;


    @Column(name = "org_code")
    private String orgCode;		//组织机构编码

    @Column(name = "org_level")
    private String orgLevel;		//组织机构等级


    @Column(name = "org_name")
    private String orgName;		//组织机构名称
    @Id
	@Column(name = "org_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String orgId;		//组织id

	@Column(name = "org_id_old")
    private String orgIdOld;		//组织id(旧)




    @Column(name = "org_vulgo")
    private String orgVulgo;		//组织俗称



	@Column(name = "org_parent_id")
    private String orgParentId;		//父组织id

	@Column(name = "org_parent_id_old")
    private String orgParentIdOld;		//父组织id(旧)

	@Column(name = "org_tree_level")
    private String orgTreeLevel;		//组织树级别

	@Column(name = "org_type")
    private String orgType;		//组织类型

	@Column(name = "org_unit_id")
    private String orgUnitId;		//组织所属单位id

	@Column(name = "org_dept_id")
    private String orgDeptId;		//组织所属部门id

	@Column(name = "org_dept_id_old")
    private String orgDeptIdOld;		//组织所属部门id(旧)

	@Column(name = "org_abbreviation_cn")
    private String orgAbbreviationCn;		//组织简称（中文）

	@Column(name = "org_abbreviation_en")
    private String orgAbbreviationEn;		//组织简称（英文）

	@Column(name = "org_station_cn")
    private String orgStationCn;		//组织驻地简称（中文）

	@Column(name = "org_station_en")
    private String orgStationEn;		//组织驻地简称（英文）

	@Column(name = "org_email")
    private String orgEmail;		//组织邮箱

	@Column(name = "org_fax")
    private String orgFax;		//组织传真

	@Column(name = "org_phone")
    private String orgPhone;		//组织电话

	@Column(name = "org_order")
    private Integer orgOrder;		//组织顺序号

	@Column(name = "org_status")
    private String orgStatus;		//组织状态

	@Column(name = "org_status_update_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date orgStatusUpdateTime;		//组织最后变更时间

	@Column(name = "org_create_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date orgCreateTime;		//组织创建时间

	@Column(name = "org_remarks")
    private String orgRemarks;		//备注

	@Column(name = "enterp_type")
    private String enterpType;		//企业类型


	@Column(name = "org_parent_code")
    private String orgParentCode;		//上级组织机构

	@Column(name = "org_change")
    private String orgChange;		//组织机构变更事项

	@Column(name = "org_path")
    private String orgPath;		//组织机构路径

    @Column(name = "mdm_org_code")
    private String mdmOrgCode;

    @Column(name = "org_base_id")
    private Integer orgBaseId;

    @Column(name = "org_reserved_text1")
    private String orgReservedText1;

    @Column(name = "area_name")
    private String areaName;

    @Column(name = "is_unit")
    private String isUnit;

    private String companyCode;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(String orgLevel) {
        this.orgLevel = orgLevel;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

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

    public String getOrgVulgo() {
        return orgVulgo;
    }

    public void setOrgVulgo(String orgVulgo) {
        this.orgVulgo = orgVulgo;
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
}