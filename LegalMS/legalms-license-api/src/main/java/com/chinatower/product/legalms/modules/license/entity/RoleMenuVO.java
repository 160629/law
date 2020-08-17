package com.chinatower.product.legalms.modules.license.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.Date;


@Table(name = "t_pub_rolemenu")
public class RoleMenuVO {
    @JsonProperty("flag")
    @Column(name = "flag")
    private String flag;

    @JsonProperty("serviceId")
    @NotEmpty(message = "应用标识不能为空")
    @Column(name = "service_id")
    private String serviceId;

    @JsonProperty("menuCode")
    @NotEmpty(message = "菜单编码不能为空")
    @Column(name = "menu_code")
    private String menuCode;

    @JsonProperty("roleCode")
    @NotEmpty(message = "角色编码不能为空")
    @Column(name = "role_code")
    private String roleCode;

    private Date createTime;
    @ApiModelProperty(value = "页码", name = "pageNum")
    public Integer pageNum;
    @ApiModelProperty(value = "单页数量", name = "pageSize")
    public Integer pageSize;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
