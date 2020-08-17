package com.chinatower.product.legalms.modules.system.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@Table(name = "t_menu_table")
@ToString
public class MenuVO {

    @Column(name = "flag")
    private String flag;

    @Column(name = "serviceId")
    private String serviceId;

    @Id
    @Column(name = "menuId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer menuId;

    @Column(name = "menuName")
    private String menuName;

    @Column(name = "menuCode")
    private String menuCode;

    @Column(name = "menuLevel")
    private Integer menuLevel;

    @Column(name = "menuUrl")
    private String menuUrl;

    @Column(name = "parentMenuId")
    private Integer parentMenuId;

    @Column(name = "parentIds")
    private String parentIds;

    @Column(name = "paramName")
    private String paramName;

    @Column(name = "menuType")
    private Integer menuType;

    @Column(name = "menuDesc")
    private String menuDesc;

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

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public Integer getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(Integer menuLevel) {
        this.menuLevel = menuLevel;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public Integer getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(Integer parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public String getMenuDesc() {
        return menuDesc;
    }

    public void setMenuDesc(String menuDesc) {
        this.menuDesc = menuDesc;
    }

}
