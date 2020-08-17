package com.chinatower.product.legalms.modules.license.entity;

import lombok.Data;

import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Table(name = "t_pub_accroleimpower")
public class AccRoleImpowerVO implements Serializable {

    private static final long serialVersionUID = 8280071857849560762L;
    @NotEmpty(message = "执行标识")
    private String flag;

    @NotEmpty(message = "应用系统标识")
    private String serviceId;

    @NotEmpty(message = "账号编码")
    private String userCode;

    @NotEmpty(message = "组织编码")
    private String orgCode;

    @NotEmpty(message = "角色集合编码")
    private List<String> roleCodes;

    @NotEmpty(message = "角色集合编码")
    private String roleColeIds;

    private String userName;

    private String accountId;

    private Date createTime;

    private String permissionKey;

    private String mobile;

    private String orgPath;

    private String orgName;


    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgPath() {
        return orgPath;
    }

    public void setOrgPath(String orgPath) {
        this.orgPath = orgPath;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPermissionKey() {
        return permissionKey;
    }

    public void setPermissionKey(String permissionKey) {
        this.permissionKey = permissionKey;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    private String unitId;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
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

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public List<String> getRoleCodes() {
        return roleCodes;
    }

    public void setRoleCodes(List<String> roleCodes) {
        this.roleCodes = roleCodes;
    }

    public String getRoleColeIds() {
        return roleColeIds;
    }

    public void setRoleColeIds(String roleColeIds) {
        this.roleColeIds = roleColeIds;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
