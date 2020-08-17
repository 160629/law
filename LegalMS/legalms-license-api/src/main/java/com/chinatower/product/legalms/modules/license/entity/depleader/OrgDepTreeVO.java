package com.chinatower.product.legalms.modules.license.entity.depleader;

/**
 * @Date: 2019/11/20 16:50
 * @Description:
 */
public class OrgDepTreeVO {

    private  String orgName;

    private  String orgCode;

    private  String orgParentCode;

    private  String userCode;

    private  String userName;

    /*组织机构等级(00--中国铁塔集团；01--总部；02--省分；03--地市；04--部门；)*/
    private  String orgLevel;

    /*判断是否是公司1是2不是*/
    private  String isUnit;

    private String companyCode;

    private String orgPath;

    public String getOrgPath() {
        return orgPath;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setOrgPath(String orgPath) {
        this.orgPath = orgPath;
    }

    public String getCompanyCode() {

        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(String orgLevel) {
        this.orgLevel = orgLevel;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }



    public String getIsUnit() {
        return isUnit;
    }

    public void setIsUnit(String isUnit) {
        this.isUnit = isUnit;
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

    public String getOrgParentCode() {
        return orgParentCode;
    }

    public void setOrgParentCode(String orgParentCode) {
        this.orgParentCode = orgParentCode;
    }
}
