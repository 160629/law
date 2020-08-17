package com.chinatower.product.legalms.modules.flow.entity.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Table(name="t_sys_ourmainbody")
@ApiModel(value="争议主体配置主Table")
public class SysOurmainbodyVO {
    @Id
    @Column(name = "unit_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(name="unitId",value = "自身公司ID",required = true)
    private BigInteger unitId;  //自身公司ID

    @Column(name = "unit_code")
    @ApiModelProperty(name="unitCode",value = "公司编号")
    private String unitCode;   //公司编号

    @Id
    @Column(name = "body_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(name="bodyId",value = "关联公司ID",required = true)
    private BigInteger bodyId;  //关联公司ID

    @Column(name = "body_code")
    @ApiModelProperty(name="bodyCode",value = "关联公司编号")
    private String bodyCode;   //关联公司编号

    @Column(name = "body_name")
    @ApiModelProperty(name="bodyName",value = "我方主体名称")
    private String bodyName;   //我方主体名称

    @Column(name = "status")
    @ApiModelProperty(name="status",value = "状态")
    private String status;   //状态 normal:正常delete:删除

    @Column(name = "remark")
    @ApiModelProperty(name="remark",value = "备注说明")
    private String remark;   //备注说明

    @Column(name = "create_time")
    @ApiModelProperty(name="createTime",value = "创建时间")
    private Date createTime;   //创建时间

    @Column(name = "update_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(name="updateTime",value = "最后更新时间")
    private Date updateTime;   //最后更新时间

    @Column(name = "last_modify_user")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(name="lastModifyUser",value = "最后修改人")
    private String lastModifyUser;   //最后修改人

    @Column(name = "enterp_type")
    @ApiModelProperty(name="enterpType",value = "企业类型")
    private String enterpType;

    private String approveItemId;

    private String approveItemType;

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

    public String getEnterpType() {
        return enterpType;
    }

    public void setEnterpType(String enterpType) {
        this.enterpType = enterpType;
    }

    public BigInteger getUnitId() {
        return unitId;
    }

    public void setUnitId(BigInteger unitId) {
        this.unitId = unitId;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public BigInteger getBodyId() {
        return bodyId;
    }

    public void setBodyId(BigInteger bodyId) {
        this.bodyId = bodyId;
    }

    public String getBodyCode() {
        return bodyCode;
    }

    public void setBodyCode(String bodyCode) {
        this.bodyCode = bodyCode;
    }

    public String getBodyName() {
        return bodyName;
    }

    public void setBodyName(String bodyName) {
        this.bodyName = bodyName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getLastModifyUser() {
        return lastModifyUser;
    }

    public void setLastModifyUser(String lastModifyUser) {
        this.lastModifyUser = lastModifyUser;
    }
}
