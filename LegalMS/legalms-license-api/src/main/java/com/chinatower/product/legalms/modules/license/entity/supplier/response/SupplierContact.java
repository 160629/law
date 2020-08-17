package com.chinatower.product.legalms.modules.license.entity.supplier.response;

import java.io.Serializable;

public class SupplierContact implements Serializable {

    private String contactResponsibleProv;

    private String contactName;

    private String contactTel;

    private String contactPosition;

    private String contactEmail;

    private String ifDefault;

    private String reservedText1;

    private String reservedText2;

    private String reservedText3;

    private String reservedText4;

    private String reservedText5;

    private String supBaseId;

    private Integer supContactId;



    private String contactChildId;

    private Integer pmsDeleteFlag;

    private String lastUpdateDate;

    private String lastUpdatedBy;

    private String creationDate;

    private String createdBy;
    private Integer lastUpdateLogin;

    private String orgId;

    private String auditStatus;

    private Integer companyId;

    public String getContactResponsibleProv() {
        return contactResponsibleProv;
    }

    public void setContactResponsibleProv(String contactResponsibleProv) {
        this.contactResponsibleProv = contactResponsibleProv == null ? null : contactResponsibleProv.trim();
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName == null ? null : contactName.trim();
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel == null ? null : contactTel.trim();
    }

    public String getContactPosition() {
        return contactPosition;
    }

    public void setContactPosition(String contactPosition) {
        this.contactPosition = contactPosition == null ? null : contactPosition.trim();
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail == null ? null : contactEmail.trim();
    }

    public String getIfDefault() {
        return ifDefault;
    }

    public void setIfDefault(String ifDefault) {
        this.ifDefault = ifDefault == null ? null : ifDefault.trim();
    }

    public String getReservedText1() {
        return reservedText1;
    }

    public void setReservedText1(String reservedText1) {
        this.reservedText1 = reservedText1 == null ? null : reservedText1.trim();
    }

    public String getReservedText2() {
        return reservedText2;
    }

    public void setReservedText2(String reservedText2) {
        this.reservedText2 = reservedText2 == null ? null : reservedText2.trim();
    }

    public String getReservedText3() {
        return reservedText3;
    }

    public void setReservedText3(String reservedText3) {
        this.reservedText3 = reservedText3 == null ? null : reservedText3.trim();
    }

    public String getReservedText4() {
        return reservedText4;
    }

    public void setReservedText4(String reservedText4) {
        this.reservedText4 = reservedText4 == null ? null : reservedText4.trim();
    }

    public String getReservedText5() {
        return reservedText5;
    }

    public void setReservedText5(String reservedText5) {
        this.reservedText5 = reservedText5 == null ? null : reservedText5.trim();
    }



    public Integer getSupContactId() {
        return supContactId;
    }

    public void setSupContactId(Integer supContactId) {
        this.supContactId = supContactId;
    }

    public String getContactChildId() {
        return contactChildId;
    }

    public void setContactChildId(String contactChildId) {
        this.contactChildId = contactChildId == null ? null : contactChildId.trim();
    }

    public Integer getPmsDeleteFlag() {
        return pmsDeleteFlag;
    }

    public void setPmsDeleteFlag(Integer pmsDeleteFlag) {
        this.pmsDeleteFlag = pmsDeleteFlag;
    }


    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy == null ? null : lastUpdatedBy.trim();
    }

    public String getSupBaseId() {
        return supBaseId;
    }

    public void setSupBaseId(String supBaseId) {
        this.supBaseId = supBaseId;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus == null ? null : auditStatus.trim();
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}