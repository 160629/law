package com.chinatower.product.legalms.modules.license.entity.depleader;

public class OrgDepLeaderVO {

    private Integer id;

    private String orgCode;

    private String orgName;

    private String compName;

    private String empName;


    public Integer getId() {
        return id;
    }

    public OrgDepLeaderVO setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public OrgDepLeaderVO setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
        return this;
    }


    public String getOrgName() {
        return orgName;
    }

    public OrgDepLeaderVO setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
        return this;
    }

    public String getCompName() {
        return compName;
    }

    public OrgDepLeaderVO setCompName(String compName) {
        this.compName = compName == null ? null : compName.trim();
        return this;
    }


    public String getEmpName() {
        return empName;
    }

    public OrgDepLeaderVO setEmpName(String empName) {
        this.empName = empName == null ? null : empName.trim();
        return this;
    }
}