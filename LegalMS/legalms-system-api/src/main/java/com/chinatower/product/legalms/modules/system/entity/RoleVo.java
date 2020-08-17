package com.chinatower.product.legalms.modules.system.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "t_role_table")
@ToString
public class RoleVo {

    @Column(name = "flag")
    private String  flag;

    @Column(name = "serviceId")
    private String serviceId;

    @Column(name = "roleId")
    private Integer roleId;

    @Column(name = "roleName")
    private String roleName;

    @Column(name = "roleLevel")
    private Integer roleLevel;

    @Id
    @Column(name = "roleCode")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String roleCode;

    @Column(name = "roleState")
    private Integer roleState;

    @Column(name = "createDate")
    private Date createDate;

    @Column(name = "statusCd")
    private Integer statusCd;

    private String remark;

    /* @JsonProperty("url")
    @NotEmpty(message = "此路径为ESB转换服务调用业务系统的路径不能为空")
    @Column(name = "url")
    private String url;*/

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

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getRoleLevel() {
        return roleLevel;
    }

    public void setRoleLevel(Integer roleLevel) {
        this.roleLevel = roleLevel;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public Integer getRoleState() {
        return roleState;
    }

    public void setRoleState(Integer roleState) {
        this.roleState = roleState;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(Integer statusCd) {
        this.statusCd = statusCd;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
