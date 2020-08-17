package com.chinatower.product.legalms.modules.system.entity;

import java.util.Date;

public class UserMsg {
    private String userId;

    private String flag;

    private String userCode;

    private String userName;

    private String empNo;

    private String sortNo;

    private String userLevel;

    private String orgCode;

    private String createDate;

    private String sex;

    private String birthday;

    private String nativePlace;

    private String identityCard;

    private String degree;

    private String finishSchool;

    private String mobile;

    private String email;

    private String userMisCode;

    private String userPwd;

    private String userOrgIdOld;

    private String userDeptId;

    private String userDeptIdOld;

    private String userDutyId;

    private String userDutyName;

    private Long userOrder;

    private String userStatus;

    private Date userStatusUpdateTime;

    private Date userCreateTime;

    private String userRemark;

    private String userInitial;


    /*用户返回的信息*/
    private String userRank;

    private String userUnitId;

    private String areaEn;

    private String orgParentCode;

    private String orgName;

    private String userEnterpType;

    private String isJiYueHua;

    private String parentOrgName;

    private String orgLevel;

    public String getParentOrgName() {
        return parentOrgName;
    }

    public void setParentOrgName(String parentOrgName) {
        this.parentOrgName = parentOrgName;
    }

    public String getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(String orgLevel) {
        this.orgLevel = orgLevel;
    }

    public String getIsJiYueHua() {
        return isJiYueHua;
    }

    public void setIsJiYueHua(String isJiYueHua) {
        this.isJiYueHua = isJiYueHua;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgParentCode() {
        return orgParentCode;
    }

    public void setOrgParentCode(String orgParentCode) {
        this.orgParentCode = orgParentCode;
    }

    public String getAreaEn() {
        return areaEn;
    }

    public void setAreaEn(String areaEn) {
        this.areaEn = areaEn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo == null ? null : empNo.trim();
    }

    public String getUserRank() {
        return userRank;
    }

    public void setUserRank(String userRank) {
        this.userRank = userRank == null ? null : userRank.trim();
    }

    public String getUserEnterpType() {
        return userEnterpType;
    }

    public void setUserEnterpType(String userEnterpType) {
        this.userEnterpType = userEnterpType;
    }

    public String getSortNo() {
        return sortNo;
    }

    public void setSortNo(String sortNo) {
        this.sortNo = sortNo == null ? null : sortNo.trim();
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel == null ? null : userLevel.trim();
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate == null ? null : createDate.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace == null ? null : nativePlace.trim();
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard == null ? null : identityCard.trim();
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree == null ? null : degree.trim();
    }

    public String getFinishSchool() {
        return finishSchool;
    }

    public void setFinishSchool(String finishSchool) {
        this.finishSchool = finishSchool == null ? null : finishSchool.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getUserMisCode() {
        return userMisCode;
    }

    public void setUserMisCode(String userMisCode) {
        this.userMisCode = userMisCode == null ? null : userMisCode.trim();
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd == null ? null : userPwd.trim();
    }

    public String getUserUnitId() {
        return userUnitId;
    }

    public void setUserUnitId(String userUnitId) {
        this.userUnitId = userUnitId == null ? null : userUnitId.trim();
    }

    public String getUserOrgIdOld() {
        return userOrgIdOld;
    }

    public void setUserOrgIdOld(String userOrgIdOld) {
        this.userOrgIdOld = userOrgIdOld == null ? null : userOrgIdOld.trim();
    }

    public String getUserDeptId() {
        return userDeptId;
    }

    public void setUserDeptId(String userDeptId) {
        this.userDeptId = userDeptId == null ? null : userDeptId.trim();
    }

    public String getUserDeptIdOld() {
        return userDeptIdOld;
    }

    public void setUserDeptIdOld(String userDeptIdOld) {
        this.userDeptIdOld = userDeptIdOld == null ? null : userDeptIdOld.trim();
    }

    public String getUserDutyId() {
        return userDutyId;
    }

    public void setUserDutyId(String userDutyId) {
        this.userDutyId = userDutyId == null ? null : userDutyId.trim();
    }

    public String getUserDutyName() {
        return userDutyName;
    }

    public void setUserDutyName(String userDutyName) {
        this.userDutyName = userDutyName == null ? null : userDutyName.trim();
    }

    public Long getUserOrder() {
        return userOrder;
    }

    public void setUserOrder(Long userOrder) {
        this.userOrder = userOrder;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus == null ? null : userStatus.trim();
    }

    public Date getUserStatusUpdateTime() {
        return userStatusUpdateTime;
    }

    public void setUserStatusUpdateTime(Date userStatusUpdateTime) {
        this.userStatusUpdateTime = userStatusUpdateTime;
    }

    public Date getUserCreateTime() {
        return userCreateTime;
    }

    public void setUserCreateTime(Date userCreateTime) {
        this.userCreateTime = userCreateTime;
    }

    public String getUserRemark() {
        return userRemark;
    }

    public void setUserRemark(String userRemark) {
        this.userRemark = userRemark == null ? null : userRemark.trim();
    }

    public String getUserInitial() {
        return userInitial;
    }

    public void setUserInitial(String userInitial) {
        this.userInitial = userInitial == null ? null : userInitial.trim();
    }
}