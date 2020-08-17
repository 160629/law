package com.chinatower.product.legalms.modules.license.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
@Data
@Table(name = "t_pub_auxiaccount")
public class AuxiAccountVO implements Serializable {

    private static final long serialVersionUID = 5963237758408834060L;


    @NotEmpty(message = "执行标识")
    private String flag;

    @NotEmpty(message = "用户编码")
    private String userCode;

    @NotEmpty(message = "附属组织编码")
    private String  partOrgCode ;

    @Column(name = "create_Time")
    private Date createTime;

    @Column(name = "update_Time")
    private Date updateTime;

    @Column(name = "sync_status")
    private String syncStatus;

    /*兼职授权标识*/
    private String permissionKey;

    public String getPermissionKey() {
        return permissionKey;
    }

    public void setPermissionKey(String permissionKey) {
        this.permissionKey = permissionKey;
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

    public String getPartOrgCode() {
        return partOrgCode;
    }

    public void setPartOrgCode(String partOrgCode) {
        this.partOrgCode = partOrgCode;
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
