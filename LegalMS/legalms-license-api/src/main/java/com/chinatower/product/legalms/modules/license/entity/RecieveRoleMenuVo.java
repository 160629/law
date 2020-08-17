package com.chinatower.product.legalms.modules.license.entity;

import java.util.Date;
import java.util.List;

public class RecieveRoleMenuVo {
    private String flag;
    private String serviceId;
    private String roleCode;
    private List<String> menuCodes;




    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    private Date createTime;


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

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public List<String> getMenuCodes() {
        return menuCodes;
    }

    public void setMenuCodes(List<String> menuCodes) {
        this.menuCodes = menuCodes;
    }


}
