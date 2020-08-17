package com.chinatower.product.legalms.modules.dispute.entity;

/**
 * @Date: 2019/12/16 15:35
 * @Description:
 */
public class WhereParam {

    private String mainSeedOrgId ;//案件交办的主送单位
    private String jointlyOrgId;//案件协办的协办部门
    private String executiveArmId; //法律文书的执行部门
    private String unitCode;//执行单位(用于文书和协办)

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getMainSeedOrgId() {
        return mainSeedOrgId;
    }

    public void setMainSeedOrgId(String mainSeedOrgId) {
        this.mainSeedOrgId = mainSeedOrgId;
    }

    public String getJointlyOrgId() {
        return jointlyOrgId;
    }

    public void setJointlyOrgId(String jointlyOrgId) {
        this.jointlyOrgId = jointlyOrgId;
    }

    public String getExecutiveArmId() {
        return executiveArmId;
    }

    public void setExecutiveArmId(String executiveArmId) {
        this.executiveArmId = executiveArmId;
    }
}
