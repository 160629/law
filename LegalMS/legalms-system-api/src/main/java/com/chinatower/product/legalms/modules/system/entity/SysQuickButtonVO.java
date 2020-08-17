package com.chinatower.product.legalms.modules.system.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
@Data
@Table(name = "t_sys_quick_button")
@ToString
public class SysQuickButtonVO implements Serializable {
    @Id
    @Column(name = "id")
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonProperty("flowId")
    @Column(name = "flow_id")
    private  String flowId;             //流程定义ID

    @JsonProperty("iconImg")
    @Column(name = "icon_img")
    private String iconImg;              //图片

    @JsonProperty("displayName")
    @Column(name = "display_name")
    private String displayName;            //陈列名字

    @JsonProperty("sort")
    @Column(name = "sort")
    private Integer sort;           //排序

    @JsonProperty("moduleName")
    @Column(name = "module_name")
    private String moduleName;  //对应的页面模块名称


    @JsonProperty("flowStartAct")
    @Column(name = "flow_start_act")
    private String flowStartAct;

    @JsonProperty("businessType")
    @Column(name = "business_type")
    private String businessType;

    @JsonProperty("roleCode")
    @Column(name = "role_code")
    private String roleCode;

    @JsonProperty("flag")
    @Column(name = "flag")
    private String flag;

    @JsonProperty("path")
    @Column(name = "path")
    private String path;

    @JsonProperty("openType")
    @Column(name = "open_type")
    private String openType;

    @JsonProperty("pageKey")
    @Column(name = "page_key")
    private String pageKey;

    @JsonProperty("isJiyuehua")
    @Column(name = "is_jiyuehua")
    private String isJiyuehua;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getIconImg() {
        return iconImg;
    }

    public void setIconImg(String iconImg) {
        this.iconImg = iconImg;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getFlowStartAct() {
        return flowStartAct;
    }

    public void setFlowStartAct(String flowStartAct) {
        this.flowStartAct = flowStartAct;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getOpenType() {
        return openType;
    }

    public void setOpenType(String openType) {
        this.openType = openType;
    }

    public String getPageKey() {
        return pageKey;
    }

    public void setPageKey(String pageKey) {
        this.pageKey = pageKey;
    }

    public String getIsJiyuehua() {
        return isJiyuehua;
    }

    public void setIsJiyuehua(String isJiyuehua) {
        this.isJiyuehua = isJiyuehua;
    }
}
