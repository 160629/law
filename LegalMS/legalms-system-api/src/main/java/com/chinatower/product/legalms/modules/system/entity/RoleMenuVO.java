package com.chinatower.product.legalms.modules.system.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "t_roelmenu_table")
@ToString
public class RoleMenuVO {
    @Column(name = "flag")
    private String flag;

    @Column(name = "serviceId")
    private String serviceId;

    @Id
    @Column(name = "menuCode")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String menuCode;

    @Id
    @Column(name = "roleCode")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String roleCode;

    @Column(name = "createDate")
    private Date createDate;

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

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
