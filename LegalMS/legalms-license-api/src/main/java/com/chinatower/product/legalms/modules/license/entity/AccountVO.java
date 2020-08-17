package com.chinatower.product.legalms.modules.license.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "t_pub_usera")
public class AccountVO implements Serializable {

    private static final long serialVersionUID = -5001304893036482388L;


    @NotEmpty(message = "执行标识")

    private String flag;


    @NotEmpty(message = "用户编码不能为空")

    private String userCode;


    @NotEmpty(message = "用户名不能为空")

    private String userName;


    @NotEmpty(message = "工号不能为空")

    private String empNo;


    @NotEmpty(message = "职级不能为空")

    private String userRank;


    @NotEmpty(message = "人员性质不能为空")

    private String userNature;


    @NotEmpty(message = "人员层级不能为空")

    private String userLevel;


    @Column(name = "sort_no")
    private String sortNo;


    @NotEmpty(message = "所属组织编码不能为空")

    private String orgCode;


    @NotEmpty(message = "创建日期不能为空")

    private String createDate;


    @NotEmpty(message = "性别不能为空")

    private String sex;


    @Column(name = "birthday")
    private String birthday;


    @Column(name = "native_place")
    private String nativePlace;


    @Column(name = "identity_card")
    private String identityCard;


    @Column(name = "degree")
    private String degree;


    @Column(name = "finish_school")
    private String finishSchool;


    @Column(name = "mobile")
    private String mobile;

    private String serviceCode;

    private String accountRemark;


    private String email;


    private Date createTime;


    private Date updateTime;


    private String syncStatus;


    public String getAccountRemark() {
        return accountRemark;
    }

    public void setAccountRemark(String accountRemark) {
        this.accountRemark = accountRemark;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getUserRank() {
        return userRank;
    }

    public void setUserRank(String userRank) {
        this.userRank = userRank;
    }

    public String getUserNature() {
        return userNature;
    }

    public void setUserNature(String userNature) {
        this.userNature = userNature;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public String getSortNo() {
        return sortNo;
    }

    public void setSortNo(String sortNo) {
        this.sortNo = sortNo;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getFinishSchool() {
        return finishSchool;
    }

    public void setFinishSchool(String finishSchool) {
        this.finishSchool = finishSchool;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }
}
