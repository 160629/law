package com.chinatower.product.legalms.modules.license.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class ListParam {
    @ApiModelProperty(value = "页码", name = "pageNum")
    public Integer pageNum;
    @ApiModelProperty(value = "单页数量", name = "pageSize")
    public Integer pageSize;
    @ApiModelProperty
    private String roleCode;
    @ApiModelProperty
    private String roleName;
    @ApiModelProperty
    private List<RoleVo> roleCodeList;

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

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<RoleVo> getRoleCodeList() {
        return roleCodeList;
    }

    public void setRoleCodeList(List<RoleVo> roleCodeList) {
        this.roleCodeList = roleCodeList;
    }
}
