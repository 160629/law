package com.chinatower.product.legalms.modules.flow.entity.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

public class SysQuickButtonVO implements Serializable {

    @Id
    @Column(name = "id")
    @JsonProperty("id")
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

    @JsonProperty("flowStartActName")
    @Column(name = "flow_start_act_name")
    private String flowStartActName;  //流程开始节点名称

    @JsonProperty("level")
    @Column(name = "level")
    private String level;  //层级(总部 01 省分 02 地市 03)

    @JsonProperty("importantLevel")
    @Column(name = "important_level")
    private String importantLevel;  // 普通 normal 一般 commonly 重大 weighty

    private List<String> roleCodes;

    private String isJiyuehua;

    private Integer versionId;

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getImportantLevel() {
        return importantLevel;
    }

    public void setImportantLevel(String importantLevel) {
        this.importantLevel = importantLevel;
    }

    public List<String> getRoleCodes() {
        return roleCodes;
    }

    public void setRoleCodes(List<String> roleCodes) {
        this.roleCodes = roleCodes;
    }

    public String getFlowStartActName() {
        return flowStartActName;
    }

    public void setFlowStartActName(String flowStartActName) {
        this.flowStartActName = flowStartActName;
    }

    public String getIsJiyuehua() {
        return isJiyuehua;
    }

    public void setIsJiyuehua(String isJiyuehua) {
        this.isJiyuehua = isJiyuehua;
    }
}
