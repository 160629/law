package com.chinatower.product.legalms.modules.system.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
@Table(name = "t_pub_accroleimpower")
@ToString
public class AccRoleImpowerVO implements Serializable {

    private static final long serialVersionUID = 8280071857849560762L;
    @JsonProperty("flag")
    @NotEmpty(message = "执行标识")
    @Column(name = "flag")
    private String flag;


    @JsonProperty("serviceId")
    @NotEmpty(message = "应用系统标识")
    @Column(name = "service_id")
    private String serviceId;

    @JsonProperty("userCode")
    @NotEmpty(message = "账号编码")
    @Column(name = "user_code")
    private String userCode;

    @JsonProperty("orgCode")
    @NotEmpty(message = "组织编码")
    @Column(name = "org_code")
    private String orgCode;

    @JsonProperty("roleCodes")
    @NotEmpty(message = "角色集合编码")
    private List<String> roleCodes;

    @NotEmpty(message = "角色集合编码")
    @Column(name = "role_codes")
    private String roleColeIds;

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public List<String> getRoleCodes() {
        return roleCodes;
    }

    public void setRoleCodes(List<String> roleCodes) {
        this.roleCodes = roleCodes;
    }

    public String getRoleColeIds() {
        return roleColeIds;
    }

    public void setRoleColeIds(String roleColeIds) {
        this.roleColeIds = roleColeIds;
    }
}
