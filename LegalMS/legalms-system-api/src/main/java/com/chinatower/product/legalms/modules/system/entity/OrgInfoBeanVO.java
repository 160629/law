package com.chinatower.product.legalms.modules.system.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "t_pub_org_between")
@ToString
public class OrgInfoBeanVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "org_name")
    private String orgName;		//组织机构名称
	@Id
	@Column(name = "org_code")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String orgCode;			//组织机构编码

	@Column(name = "org_status")
    private String orgStatus;		//组织状态

	@Column(name = "org_remarks")
    private String orgRemarks;		//备注

	@Column(name = "enterp_type")
    private String enterpType;		//企业类型

	@Column(name = "org_level")
    private String orgLevel;		//组织机构等级

	@Column(name = "org_parent_code")
    private String orgParentCode;		//上级组织机构

	@Column(name = "org_change")
    private String orgChange;		//组织机构变更事项

	@Column(name = "org_path")
    private String orgPath;		//组织机构路径

	@Column(name = "created_by")
    private String createdBy;		//创建人员

	@Column(name = "creation_date")
    private Date creationDate;		//创建时间

	@Column(name = "last_updated_by")
    private String lastUpdatedBy;		//最后修改人

	@Column(name = "last_update_date")
    private Date lastUpdateDate;		//最后修改时间

	@Column(name = "org_reserved_text1")
    private String orgReservedText1;		//排序字段

	@Column(name = "org_relation")
    private String orgRelation;		//关联组织机构

	@Column(name = "flag")
    private String flag;		//新增/修改标记

	@Column(name = "url")
    private String url;		//此路径为ESB转换服务调用主数据的路径

	@Column(name = "mdm_org_code")
	private String mdmOrgCode;

	@Column(name = "org_base_id")
	private Integer orgBaseId;

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

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgStatus() {
		return orgStatus;
	}

	public void setOrgStatus(String orgStatus) {
		this.orgStatus = orgStatus;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getOrgReservedText1() {
		return orgReservedText1;
	}

	public void setOrgReservedText1(String orgReservedText1) {
		this.orgReservedText1 = orgReservedText1;
	}

	public String getOrgRelation() {
		return orgRelation;
	}

	public void setOrgRelation(String orgRelation) {
		this.orgRelation = orgRelation;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}