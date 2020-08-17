package com.chinatower.product.legalms.modules.license.entity.supplier;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class SupplierCertificateVO implements Serializable {
    @JsonProperty("certificate_type")
    private String certificateType;

    @JsonProperty("reserved_text_1")
    private String reservedText1;

    @JsonProperty("reserved_text_2")
    private String reservedText2;

    @JsonProperty("reserved_text_3")
    private String reservedText3;

    @JsonProperty("reserved_text_4")
    private String reservedText4;

    @JsonProperty("reserved_text_5 ")
    private String reservedText5;

    //@JsonProperty("listJson")
    private String supBaseId;

    //@JsonProperty("listJson")
    private Integer supCertificateId;

    @JsonProperty("certificate_child_id")
    private String certificateChildId;

    @JsonProperty("certificate_name")
    private String certificateName;

    @JsonProperty("certificate_cod")
    private String certificateCode;

    @JsonProperty("time_limit")
    //@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private String timeLimit;

    //@JsonProperty("listJson")
    private Integer pmsDeleteFlag;

    @JsonProperty("last_update_date")
    //@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private String lastUpdateDate;

    @JsonProperty("last_updated_by")
    private String lastUpdatedBy;

    @JsonProperty("creation_date")
    //@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private String creationDate;

    @JsonProperty("created_by")
    private String createdBy;

    //@JsonProperty("listJson")
    private Integer lastUpdateLogin;

    //@JsonProperty("listJson")
    private String orgId;

    //@JsonProperty("listJson")
    private String auditStatus;

    //@JsonProperty("listJson")
    private Integer companyId;

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType == null ? null : certificateType.trim();
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

    public String getSupBaseId() {
        return supBaseId;
    }

    public void setSupBaseId(String supBaseId) {
        this.supBaseId = supBaseId;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
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

    public Integer getSupCertificateId() {
        return supCertificateId;
    }

    public void setSupCertificateId(Integer supCertificateId) {
        this.supCertificateId = supCertificateId;
    }

    public String getCertificateChildId() {
        return certificateChildId;
    }

    public void setCertificateChildId(String certificateChildId) {
        this.certificateChildId = certificateChildId == null ? null : certificateChildId.trim();
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName == null ? null : certificateName.trim();
    }

    public String getCertificateCode() {
        return certificateCode;
    }

    public void setCertificateCode(String certificateCode) {
        this.certificateCode = certificateCode == null ? null : certificateCode.trim();
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