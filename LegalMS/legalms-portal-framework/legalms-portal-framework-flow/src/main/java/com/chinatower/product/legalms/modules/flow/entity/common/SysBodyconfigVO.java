package com.chinatower.product.legalms.modules.flow.entity.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Table(name="t_sys_bodyconfig")
@ApiModel(value="我方争议主体和业务关联关系表")
public class SysBodyconfigVO {

    @Id
    @Column(name = "approve_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(name="approveItemId",value = "业务ID",required = true)
    private String approveItemId;  //业务ID


    @Column(name = "approve_item_type")
    @ApiModelProperty(name="approveItemType",value = "业务事项类型")
    private String approveItemType;   //业务事项类型

    @Column(name = "unit_code")
    @ApiModelProperty(name="unitCode",value = "公司编号")
    private String unitCode;   //公司编号

    @Column(name = "body_code")
    @ApiModelProperty(name="bodyCode",value = "关联公司编号")
    private String bodyCode;   //关联公司编号

    @Column(name = "our_sign_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(name="ourSignDate",value = "ourSignDate")
    private Date ourSignDate;   //ourSignDate

    public String getApproveItemId() {
        return approveItemId;
    }

    public void setApproveItemId(String approveItemId) {
        this.approveItemId = approveItemId;
    }

    public String getApproveItemType() {
        return approveItemType;
    }

    public void setApproveItemType(String approveItemType) {
        this.approveItemType = approveItemType;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getBodyCode() {
        return bodyCode;
    }

    public void setBodyCode(String bodyCode) {
        this.bodyCode = bodyCode;
    }

    public Date getOurSignDate() {
        return ourSignDate;
    }

    public void setOurSignDate(Date ourSignDate) {
        this.ourSignDate = ourSignDate;
    }
}
