package com.chinatower.product.legalms.modules.license.entity.supplier;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;


public class SupplierBaseVO implements Serializable {


    @JsonProperty("credit_rating")
    private String creditRating;


    @JsonProperty("supplier_accounts_type")
    private String supplierAccountsType;

    @JsonProperty("supplier_product_type")
    private String supplierProductType;

    @JsonProperty("reserved_text_1")
    private String reservedText1;

    @JsonProperty("reserved_text_2")
    private String reservedText2;

    @JsonProperty("reserved_text_3")
    private String reservedText3;

    @JsonProperty("reserved_text_4")
    private String reservedText4;

    @JsonProperty("reserved_text_5")
    private String reservedText5;

    //@JsonProperty("supplier_product_type")
    private String supBaseId;

    @JsonProperty("supplier_parent_name")
    private String supplierParentName;

    @JsonProperty("supplier_parent_code")
    private String supplierParentCode;

    @JsonProperty("supplier_code")
    private String supplierCode;

    @JsonProperty("supplier_name")
    private String supplierName;

    @JsonProperty("supplier_telephone")
    private String supplierTelephone;

    @JsonProperty("supplier_email")
    private String supplierEmail;

    @JsonProperty("supplier_category")
    private String supplierCategory;

    @JsonProperty("supplier_status")
    private String supplierStatus;

    @JsonProperty("associative_relationship")
    private String associativeRelationship;

    @JsonProperty("associative_unit")
    private String associativeUnit;

    @JsonProperty("document_type")
    private String documentType;

    @JsonProperty("document_coding")
    private String documentCoding;

    @JsonProperty("residence")
    private String residence;

    @JsonProperty("legal_representative")
    private String legalRepresentative;

    @JsonProperty("registered_capital")
    private String registeredCapital;

    @JsonProperty("registered_currency")
    private String registeredCurrency;

    @JsonProperty("establishment_date")
    //@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private String establishmentDate;

    @JsonProperty("time_limit_business")
    //@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private String timeLimitBusiness;

    @JsonProperty("scope_operation")
    private String scopeOperation;

    @JsonProperty("registered_country")
    private String registeredCountry;

    @JsonProperty("registered_prov")
    private String registeredProv;

    @JsonProperty("registered_city")
    private String registeredCity;

    @JsonProperty("pay_taxes_type")
    private String payTaxesType;

    @JsonProperty("application_unit")
    private String applicationUnit;

    @JsonProperty("applicant_name")
    private String applicantName;

    @JsonProperty("applicant_number")
    private String applicantInt;

    @JsonProperty("reserved_text_6")
    private String reservedText6;

    @JsonProperty("reserved_text_7")
    private String reservedText7;

    @JsonProperty("reserved_text_10")
    private String reservedText10;

    @JsonProperty("reserved_text_8")
    private String reservedText8;

    @JsonProperty("reserved_text_9")
    private String reservedText9;

    @JsonProperty("supplier_remarks")
    private String supplierRemarks;

    @JsonProperty("last_update_date")
   // @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private String lastUpdateDate;

    @JsonProperty("last_updated_by")
    private String lastUpdatedBy;

    @JsonProperty("creation_date")
   // @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private String creationDate;

    @JsonProperty("supplier_rank")
    private String supplierRank;


    private String createdBy;


    private Integer lastUpdateLogin;


    private String orgId;


    private String auditStatus;


    private Integer companyId;


    private String versionM;

    @JsonProperty("supplier_contact_info")
    private List<SupplierContactVO> supplierContactInfo;

    @JsonProperty("supplier_bank_info")
    private List<SupplierBankVO> supplierBankInfo;

    @JsonProperty("supplier_certificate_info")
    private List<SupplierCertificateVO> supplierCertificateInfo;



    public String getSupplierRank() {
        return supplierRank;
    }

    public void setSupplierRank(String supplierRank) {
        this.supplierRank = supplierRank;
    }

    public List<SupplierContactVO> getSupplierContactInfo() {
        return supplierContactInfo;
    }

    public void setSupplierContactInfo(List<SupplierContactVO> supplierContactInfo) {
        this.supplierContactInfo = supplierContactInfo;
    }

    public List<SupplierBankVO> getSupplierBankInfo() {
        return supplierBankInfo;
    }

    public void setSupplierBankInfo(List<SupplierBankVO> supplierBankInfo) {
        this.supplierBankInfo = supplierBankInfo;
    }

    public List<SupplierCertificateVO> getSupplierCertificateInfo() {
        return supplierCertificateInfo;
    }

    public void setSupplierCertificateInfo(List<SupplierCertificateVO> supplierCertificateInfo) {
        this.supplierCertificateInfo = supplierCertificateInfo;
    }

    public String getCreditRating() {
        return creditRating;
    }

    public void setCreditRating(String creditRating) {
        this.creditRating = creditRating == null ? null : creditRating.trim();
    }

    public String getSupplierAccountsType() {
        return supplierAccountsType;
    }

    public void setSupplierAccountsType(String supplierAccountsType) {
        this.supplierAccountsType = supplierAccountsType == null ? null : supplierAccountsType.trim();
    }

    public String getSupplierProductType() {
        return supplierProductType;
    }

    public void setSupplierProductType(String supplierProductType) {
        this.supplierProductType = supplierProductType == null ? null : supplierProductType.trim();
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

    public String getSupplierParentName() {
        return supplierParentName;
    }

    public void setSupplierParentName(String supplierParentName) {
        this.supplierParentName = supplierParentName == null ? null : supplierParentName.trim();
    }

    public String getSupplierParentCode() {
        return supplierParentCode;
    }

    public void setSupplierParentCode(String supplierParentCode) {
        this.supplierParentCode = supplierParentCode == null ? null : supplierParentCode.trim();
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode == null ? null : supplierCode.trim();
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    public String getSupplierTelephone() {
        return supplierTelephone;
    }

    public void setSupplierTelephone(String supplierTelephone) {
        this.supplierTelephone = supplierTelephone == null ? null : supplierTelephone.trim();
    }

    public String getSupplierEmail() {
        return supplierEmail;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail == null ? null : supplierEmail.trim();
    }

    public String getSupplierCategory() {
        return supplierCategory;
    }

    public void setSupplierCategory(String supplierCategory) {
        this.supplierCategory = supplierCategory == null ? null : supplierCategory.trim();
    }

    public String getSupplierStatus() {
        return supplierStatus;
    }

    public void setSupplierStatus(String supplierStatus) {
        this.supplierStatus = supplierStatus == null ? null : supplierStatus.trim();
    }

    public String getAssociativeRelationship() {
        return associativeRelationship;
    }

    public void setAssociativeRelationship(String associativeRelationship) {
        this.associativeRelationship = associativeRelationship == null ? null : associativeRelationship.trim();
    }

    public String getAssociativeUnit() {
        return associativeUnit;
    }

    public void setAssociativeUnit(String associativeUnit) {
        this.associativeUnit = associativeUnit == null ? null : associativeUnit.trim();
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType == null ? null : documentType.trim();
    }

    public String getDocumentCoding() {
        return documentCoding;
    }

    public void setDocumentCoding(String documentCoding) {
        this.documentCoding = documentCoding == null ? null : documentCoding.trim();
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence == null ? null : residence.trim();
    }

    public String getLegalRepresentative() {
        return legalRepresentative;
    }

    public void setLegalRepresentative(String legalRepresentative) {
        this.legalRepresentative = legalRepresentative == null ? null : legalRepresentative.trim();
    }

    public String getRegisteredCapital() {
        return registeredCapital;
    }

    public void setRegisteredCapital(String registeredCapital) {
        this.registeredCapital = registeredCapital == null ? null : registeredCapital.trim();
    }

    public String getRegisteredCurrency() {
        return registeredCurrency;
    }

    public void setRegisteredCurrency(String registeredCurrency) {
        this.registeredCurrency = registeredCurrency == null ? null : registeredCurrency.trim();
    }



    public String getScopeOperation() {
        return scopeOperation;
    }

    public void setScopeOperation(String scopeOperation) {
        this.scopeOperation = scopeOperation == null ? null : scopeOperation.trim();
    }

    public String getRegisteredCountry() {
        return registeredCountry;
    }

    public void setRegisteredCountry(String registeredCountry) {
        this.registeredCountry = registeredCountry == null ? null : registeredCountry.trim();
    }

    public String getRegisteredProv() {
        return registeredProv;
    }

    public void setRegisteredProv(String registeredProv) {
        this.registeredProv = registeredProv == null ? null : registeredProv.trim();
    }

    public String getRegisteredCity() {
        return registeredCity;
    }

    public void setRegisteredCity(String registeredCity) {
        this.registeredCity = registeredCity == null ? null : registeredCity.trim();
    }

    public String getPayTaxesType() {
        return payTaxesType;
    }

    public void setPayTaxesType(String payTaxesType) {
        this.payTaxesType = payTaxesType == null ? null : payTaxesType.trim();
    }

    public String getApplicationUnit() {
        return applicationUnit;
    }

    public void setApplicationUnit(String applicationUnit) {
        this.applicationUnit = applicationUnit == null ? null : applicationUnit.trim();
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName == null ? null : applicantName.trim();
    }

    public String getApplicantInt() {
        return applicantInt;
    }

    public void setApplicantInt(String applicantInt) {
        this.applicantInt = applicantInt == null ? null : applicantInt.trim();
    }

    public String getReservedText6() {
        return reservedText6;
    }

    public void setReservedText6(String reservedText6) {
        this.reservedText6 = reservedText6 == null ? null : reservedText6.trim();
    }

    public String getReservedText7() {
        return reservedText7;
    }

    public void setReservedText7(String reservedText7) {
        this.reservedText7 = reservedText7 == null ? null : reservedText7.trim();
    }

    public String getReservedText10() {
        return reservedText10;
    }

    public void setReservedText10(String reservedText10) {
        this.reservedText10 = reservedText10 == null ? null : reservedText10.trim();
    }

    public String getReservedText8() {
        return reservedText8;
    }

    public void setReservedText8(String reservedText8) {
        this.reservedText8 = reservedText8 == null ? null : reservedText8.trim();
    }

    public String getReservedText9() {
        return reservedText9;
    }

    public void setReservedText9(String reservedText9) {
        this.reservedText9 = reservedText9 == null ? null : reservedText9.trim();
    }

    public String getSupplierRemarks() {
        return supplierRemarks;
    }

    public void setSupplierRemarks(String supplierRemarks) {
        this.supplierRemarks = supplierRemarks == null ? null : supplierRemarks.trim();
    }



    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy == null ? null : lastUpdatedBy.trim();
    }

    public String getEstablishmentDate() {
        return establishmentDate;
    }

    public void setEstablishmentDate(String establishmentDate) {
        this.establishmentDate = establishmentDate;
    }

    public String getTimeLimitBusiness() {
        return timeLimitBusiness;
    }

    public void setTimeLimitBusiness(String timeLimitBusiness) {
        this.timeLimitBusiness = timeLimitBusiness;
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

    public String getVersionM() {
        return versionM;
    }

    public void setVersionM(String versionM) {
        this.versionM = versionM == null ? null : versionM.trim();
    }
}