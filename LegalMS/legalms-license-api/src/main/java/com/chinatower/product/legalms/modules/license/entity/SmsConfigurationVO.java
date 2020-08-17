package com.chinatower.product.legalms.modules.license.entity;

import java.util.List;

/**
 * @Date: 2020/7/12 14:03
 * @Description:
 */
public class SmsConfigurationVO extends AccountLogicVO {

    private String  orgCode;

    private List<String> orgCodeList;

    private List<String>  accountIdList;

    private String superRole;//超级管理员

    private String  accountPhoneStatus;

    private String  orgLevel;

    private String enterpType;

    private String orgPath;

    public String getOrgPath() {
        return orgPath;
    }

    public void setOrgPath(String orgPath) {
        this.orgPath = orgPath;
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

    public String getAccountPhoneStatus() {
        return accountPhoneStatus;
    }

    public void setAccountPhoneStatus(String accountPhoneStatus) {
        this.accountPhoneStatus = accountPhoneStatus;
    }

    public String getSuperRole() {
        return superRole;
    }

    public void setSuperRole(String superRole) {
        this.superRole = superRole;
    }

    public List<String> getOrgCodeList() {
        return orgCodeList;
    }

    public void setOrgCodeList(List<String> orgCodeList) {
        this.orgCodeList = orgCodeList;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }




    public List<String> getAccountIdList() {
        return accountIdList;
    }

    public void setAccountIdList(List<String> accountIdList) {
        this.accountIdList = accountIdList;
    }
}
