package com.chinatower.product.legalms.modules.flow.entity.unview;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Tbase {
    protected String loginAcct;
    
    protected String loginName;

    protected String orgName;
    
    protected String orgId;

    protected String deptName ;
    
    protected String deptId;
    
    protected String mobile;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:dd",timezone = "GMT+8")
    protected Date createTime;

	protected String permissionJson;
    
    public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getLoginAcct() {
		return loginAcct;
	}

	public void setLoginAcct(String loginAcct) {
		this.loginAcct = loginAcct;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
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

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPermissionJson() {
		return permissionJson;
	}

	public void setPermissionJson(String permissionJson) {
		this.permissionJson = permissionJson;
	}
}
