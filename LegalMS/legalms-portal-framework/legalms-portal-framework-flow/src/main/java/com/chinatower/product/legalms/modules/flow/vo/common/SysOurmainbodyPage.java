package com.chinatower.product.legalms.modules.flow.vo.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.math.BigInteger;
import java.util.Date;

public class SysOurmainbodyPage {
    @ApiModelProperty(value = "页码", name = "pageNum")
    public Integer pageNum;

    @ApiModelProperty(value = "单页数量", name = "pageSize")
    public Integer pageSize;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(name="unitId",value = "自身公司ID",required = true)
    private BigInteger unitId;  //自身公司ID

    @ApiModelProperty(name="unitCode",value = "公司编号")
    private String unitCode;   //公司编号

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(name="bodyId",value = "主体ID",required = true)
    private BigInteger bodyId;  //主体ID

    @ApiModelProperty(name="bodyCode",value = "主体编号")
    private String bodyCode;   //主体编号

    @ApiModelProperty(name="bodyName",value = "我方主体名称")
    private String bodyName;   //我方主体名称

    @ApiModelProperty(name="status",value = "状态")
    private String status;   //状态 normal:正常delete:删除

    @ApiModelProperty(name="remark",value = "备注说明")
    private String remark;   //备注说明

    @ApiModelProperty(name="createTime",value = "创建时间")
    private Date createTime;   //创建时间

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(name="updateTime",value = "最后更新时间")
    private Date updateTime;   //最后更新时间

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(name="lastModifyUser",value = "最后修改人")
    private String lastModifyUser;   //最后修改人

    @ApiModelProperty(name="fromType",value = "表单类型,1.全集团（铁塔、智联、能源） 2全单位（用户所在单位下的总部、省分、地市主体）3.本机组织（当前用户所在层级的主体）")
    private String fromType;       //表单类型


    @ApiModelProperty(name="enterpType",value = "企业类型")
    private String enterpType;

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType;
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

    public String getEnterpType() {
        return enterpType;
    }

    public void setEnterpType(String enterpType) {
        this.enterpType = enterpType;
    }
}
