package com.chinatower.product.legalms.modules.flow.entity.common;

import java.util.Date;

public class AccountLogic {


    private String accountName;

    private String accountCurUserId;

    private String accountOrgId;

    private String accountId;

    private String accountStatus;

    private String accountType;

    private String accountAppId;

    private String accountCurUserName;

    private String accountPreUserId;

    private String accountPreUserName;

    private String accountOrgIdOld;

    private String accountUnitId;

    private String accountDeptId;

    private String accountDeptIdOld;

    private String accountOrder;

    private Date accountStatusUpdateTime;

    private Date accountCreateTime;

    private String accountRemark;

    private Double accountDutyId;

    private String siteType;

    private String mobile;

    private String deptName;

    private String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }


    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountCurUserId() {
        return accountCurUserId;
    }

    public void setAccountCurUserId(String accountCurUserId) {
        this.accountCurUserId = accountCurUserId;
    }

    public String getAccountOrgId() {
        return accountOrgId;
    }

    public void setAccountOrgId(String accountOrgId) {
        this.accountOrgId = accountOrgId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus == null ? null : accountStatus.trim();
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType == null ? null : accountType.trim();
    }



    public String getAccountAppId() {
        return accountAppId;
    }

    public void setAccountAppId(String accountAppId) {
        this.accountAppId = accountAppId == null ? null : accountAppId.trim();
    }



    public String getAccountCurUserName() {
        return accountCurUserName;
    }

    public void setAccountCurUserName(String accountCurUserName) {
        this.accountCurUserName = accountCurUserName == null ? null : accountCurUserName.trim();
    }

    public String getAccountPreUserId() {
        return accountPreUserId;
    }

    public void setAccountPreUserId(String accountPreUserId) {
        this.accountPreUserId = accountPreUserId == null ? null : accountPreUserId.trim();
    }

    public String getAccountPreUserName() {
        return accountPreUserName;
    }

    public void setAccountPreUserName(String accountPreUserName) {
        this.accountPreUserName = accountPreUserName == null ? null : accountPreUserName.trim();
    }



    public String getAccountOrgIdOld() {
        return accountOrgIdOld;
    }

    public void setAccountOrgIdOld(String accountOrgIdOld) {
        this.accountOrgIdOld = accountOrgIdOld == null ? null : accountOrgIdOld.trim();
    }

    public String getAccountUnitId() {
        return accountUnitId;
    }

    public void setAccountUnitId(String accountUnitId) {
        this.accountUnitId = accountUnitId == null ? null : accountUnitId.trim();
    }

    public String getAccountDeptId() {
        return accountDeptId;
    }

    public void setAccountDeptId(String accountDeptId) {
        this.accountDeptId = accountDeptId == null ? null : accountDeptId.trim();
    }

    public String getAccountDeptIdOld() {
        return accountDeptIdOld;
    }

    public void setAccountDeptIdOld(String accountDeptIdOld) {
        this.accountDeptIdOld = accountDeptIdOld == null ? null : accountDeptIdOld.trim();
    }

    public String getAccountOrder() {
        return accountOrder;
    }

    public void setAccountOrder(String accountOrder) {
        this.accountOrder = accountOrder == null ? null : accountOrder.trim();
    }

    public Date getAccountStatusUpdateTime() {
        return accountStatusUpdateTime;
    }

    public void setAccountStatusUpdateTime(Date accountStatusUpdateTime) {
        this.accountStatusUpdateTime = accountStatusUpdateTime;
    }

    public Date getAccountCreateTime() {
        return accountCreateTime;
    }

    public void setAccountCreateTime(Date accountCreateTime) {
        this.accountCreateTime = accountCreateTime;
    }

    public String getAccountRemark() {
        return accountRemark;
    }

    public void setAccountRemark(String accountRemark) {
        this.accountRemark = accountRemark == null ? null : accountRemark.trim();
    }

    public Double getAccountDutyId() {
        return accountDutyId;
    }

    public void setAccountDutyId(Double accountDutyId) {
        this.accountDutyId = accountDutyId;
    }

    public String getSiteType() {
        return siteType;
    }

    public void setSiteType(String siteType) {
        this.siteType = siteType == null ? null : siteType.trim();
    }

	public AccountLogic() {
		super();
	}

	public AccountLogic( String accountId,String accountName) {
		super();
		this.accountId = accountId;
		this.accountName = accountName;
	}
    
}