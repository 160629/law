package com.chinatower.product.legalms.modules.flow.vo.common;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;

public class PageLists {

    @ApiModelProperty(value = "页码", name = "pageNum")
    public Integer pageNum;

    @ApiModelProperty(value = "单页数量", name = "pageSize")
    public Integer pageSize;

    @ApiModelProperty(name="flowId",value = "流程定义ID",required = true)
    private String flowId;

    @ApiModelProperty(name="flowName",value = "流程定义名称",required = true)
    private String flowName;

    @ApiModelProperty(name="actId",value = "环节ID",required = true)
    private String actId;

    @ApiModelProperty(name="actName",value = "环节名称")
    private String actName;

    @Column(name = "permission_json")
    private String permissionJson;

    @ApiModelProperty(name="roleIds",value = "参与者角色  多个角色用,隔开")
    private String roleIds;

    @ApiModelProperty(name="orgIds",value = "参与者组织  多个组织用,隔开")
    private String orgIds;


    private Integer versionId;

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

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

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getPermissionJson() {
        return permissionJson;
    }

    public void setPermissionJson(String permissionJson) {
        this.permissionJson = permissionJson;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public String getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(String orgIds) {
        this.orgIds = orgIds;
    }
}
