package com.chinatower.product.legalms.modules.license.entity.jiyuehua;

import java.util.List;

/**
 * @Date: 2020/4/10 11:50
 * @Description:
 */
public class OrgIntensives extends OrgIntensivesVO{



    private Integer pageNum;

    private Integer pageSize;

    private List<String> orgCodeList;

    private String superRole;//超级管理员

    private String enterpType;

    private String currOrgCode;

    private String orgLevel;


    public String getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(String orgLevel) {
        this.orgLevel = orgLevel;
    }

    public String getCurrOrgCode() {
        return currOrgCode;
    }

    public void setCurrOrgCode(String currOrgCode) {
        this.currOrgCode = currOrgCode;
    }

    public String getEnterpType() {
        return enterpType;
    }

    public void setEnterpType(String enterpType) {
        this.enterpType = enterpType;
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
}
