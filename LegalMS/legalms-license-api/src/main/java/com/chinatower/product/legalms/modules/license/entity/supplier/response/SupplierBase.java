package com.chinatower.product.legalms.modules.license.entity.supplier.response;

import java.io.Serializable;
import java.util.List;


public class SupplierBase implements Serializable {


    private String creditRating;


    private String supplierAccountsType;

    private String supplierProductType;

    private String reservedText1;

    private String reservedText2;

    private String reservedText3;

    private String reservedText4;

    private String reservedText5;

    private String supBaseId;

    private String supplierParentName;

    private String supplierParentCode;

    private String supplierCode;

    private String supplierName;

    private String supplierTelephone;

    private String supplierEmail;

    private String supplierCategory;

    private String supplierStatus;

    private String associativeRelationship;

    private String associativeUnit;

    private String documentType;

    private String documentCoding;

    private String residence;

    private String legalRepresentative;

    private String registeredCapital;

    private String registeredCurrency;

    private String establishmentDate;

    private String timeLimitBusiness;

    private String scopeOperation;

    private String registeredCountry;

    private String registeredProv;

    private String registeredCity;

    private String payTaxesType;

    private String applicationUnit;

    private String applicantName;

    private String applicantInt;

    private String reservedText6;

    private String reservedText7;

    private String reservedText10;

    private String reservedText8;

    private String reservedText9;

    private String supplierRemarks;

    private String lastUpdateDate;

    private String lastUpdatedBy;

    private String creationDate;

    private String supplierRank;


    private String createdBy;


    private Integer lastUpdateLogin;


    private String orgId;


    private String auditStatus;


    private Integer companyId;


    private String versionM;

    private List<SupplierContact> supplierContactInfo;

    private List<SupplierBank> supplierBankInfo;

    private List<SupplierCertificate> supplierCertificateInfo;

    private Integer pageNum;

    private Integer pageSize;

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

    public String getSupplierRank() {
        return supplierRank;
    }

    public void setSupplierRank(String supplierRank) {
        this.supplierRank = supplierRank;
    }

    public List<SupplierContact> getSupplierContactInfo() {
        return supplierContactInfo;
    }

    public void setSupplierContactInfo(List<SupplierContact> supplierContactInfo) {
        this.supplierContactInfo = supplierContactInfo;
    }

    public List<SupplierBank> getSupplierBankInfo() {
        return supplierBankInfo;
    }

    public void setSupplierBankInfo(List<SupplierBank> supplierBankInfo) {
        this.supplierBankInfo = supplierBankInfo;
    }

    public List<SupplierCertificate> getSupplierCertificateInfo() {
        return supplierCertificateInfo;
    }

    public void setSupplierCertificateInfo(List<SupplierCertificate> supplierCertificateInfo) {
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