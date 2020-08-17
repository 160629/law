package com.chinatower.product.legalms.common;

import java.util.List;

public class UserInfo {

	/*单位编码 例：CT 代表铁塔 TE 能源 TZ 智联*/
	private String unitCode;
	/*单位编码 例：01 代表铁塔 02 能源 03 智联*/
	private String enterType;
	/*公司编码 例：LNLN*/
	private String orgCode;
	/*公司名称 例：辽宁省分公司*/
	private String orgName;
    /*公司id 例：210001*/
	private String orgId;
	/*公司等级 例：02 代表省份 01 代表总部 02 代表地市 04 代表部门*/
	private String orgLevel;
	/*部门名称 */
	private String deptName ;
	/*部门ID*/
	private String deptId;
    /*用户名称*/
	private String loginName ;
	/*用户账号*/
	private String loginAcct ;
	/*用户手机*/
	private String mobile;
	/*用户账号类型*/
	private String isMain;
    /*用户角色*/
	private String roles;
    /*用户角色编码集合*/
	private List<Object> roleCodeList;

	private String isJiYueHua;

	public String getIsJiYueHua() {
		return isJiYueHua;
	}

	public String getEnterType() {
		return enterType;
	}

	public void setEnterType(String enterType) {
		this.enterType = enterType;
	}

	public UserInfo setIsJiYueHua(String isJiYueHua) {
		this.isJiYueHua = isJiYueHua;
		return this;
	}

	public String getOrgLevel() {
		return orgLevel;
	}

	public UserInfo setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
		return this;
	}

	public String getOrgName() {
		return orgName;
	}

	public UserInfo setOrgName(String orgName) {
		this.orgName = orgName;
		return this;
	}

	public String getOrgCode() {
		return null==orgCode||orgCode.isEmpty()?"HNHY":orgCode;
	}

	public UserInfo setOrgCode(String orgCode) {
		this.orgCode = orgCode;
		return this;
	}

	public List<Object> getRoleCodeList() {
		return roleCodeList;
	}

	public UserInfo setRoleCodeList(List<Object> roleCodeList) {
		this.roleCodeList = roleCodeList;
		return this;
	}


	public String getUnitCode() {
		return null==unitCode||unitCode.isEmpty()?"CT":unitCode;
	}

	public UserInfo setUnitCode(String unitCode) {
		this.unitCode = unitCode;
		return this;
	}

	public String getMobile() {
		return mobile;
	}

	public UserInfo setMobile(String mobile) {
		this.mobile = mobile;
		return this;
	}

	public String getOrgId() {
		return orgId;
	}

	public UserInfo setOrgId(String orgId) {
		this.orgId = orgId;
		return this;
	}

	public String getLoginName() {
		return loginName;
	}
	public UserInfo setLoginName(String loginName) {
		this.loginName = loginName;
		return this;
	}
	public String getIsMain() {
		return isMain;
	}
	public UserInfo setIsMain(String isMain) {
		this.isMain = isMain;
		return this;
	}
	public String getDeptName() {
		return deptName;
	}
	public UserInfo setDeptName(String deptName) {
		this.deptName = deptName;
		return this;
	}
	public String getRoles() {
		return roles;
	}
	public UserInfo setRoles(String roles) {
		this.roles = roles;
		return this;
	}
	public String getLoginAcct() {
		return loginAcct;
	}
	public UserInfo setLoginAcct(String loginAcct) {
		this.loginAcct = loginAcct;
		return this;
	}
	public String getDeptId() {
		return deptId;
	}
	public UserInfo setDeptId(String deptId) {
		this.deptId = deptId;
		return this;
	}

	@Override
	public String toString() {
		return "UserInfo{" +
				"unitCode='" + unitCode + '\'' +
				", orgCode='" + orgCode + '\'' +
				", orgName='" + orgName + '\'' +
				", orgId='" + orgId + '\'' +
				", orgLevel='" + orgLevel + '\'' +
				", deptName='" + deptName + '\'' +
				", deptId='" + deptId + '\'' +
				", loginName='" + loginName + '\'' +
				", loginAcct='" + loginAcct + '\'' +
				", mobile='" + mobile + '\'' +
				", isMain='" + isMain + '\'' +
				", roles='" + roles + '\'' +
				", roleCodeList=" + roleCodeList +
				", isJiYueHua='" + isJiYueHua + '\'' +
				'}';
	}
}

