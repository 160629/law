package com.chinatower.product.legalms.modules.flow.entity.sms;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 字典数据表
 *
 * @author wangyong
 * @date   2020/07/01
 */
@Table(name="t_sys_dict_data")
@ApiModel("字典数据表")
public class TSysDictData {
    @Id
    @ApiModelProperty(value="字典编码",name="dictCode")
    private String dictCode;

    @ApiModelProperty(value="字典排序",name="dictSort",example="0")
    private Integer dictSort;

    @ApiModelProperty(value="字典标签",name="dictCabel")
    private String dictCabel;

    @ApiModelProperty(value="字典键值",name="dictValue")
    private String dictValue;

    @ApiModelProperty(value="字典类型",name="dictType")
    private String dictType;

    @ApiModelProperty(value="样式属性",name="cssClass")
    private String cssClass;

    @ApiModelProperty(value="是否默认（Y是 N否）",name="isDefault")
    private String isDefault;

    @ApiModelProperty(value="状态（0正常 1停用）",name="status")
    private String status;

    @ApiModelProperty(value="创建者",name="createBy")
    private String createBy;

    @ApiModelProperty(value="创建时间",name="createTime")
    private Date createTime;

    @ApiModelProperty(value="更新者",name="updateBy")
    private String updateBy;

    @ApiModelProperty(value="更新时间",name="updateTime")
    private Date updateTime;

    @ApiModelProperty(value="备注",name="remark")
    private String remark;

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode == null ? null : dictCode.trim();
    }

    public Integer getDictSort() {
        return dictSort;
    }

    public void setDictSort(Integer dictSort) {
        this.dictSort = dictSort;
    }

    public String getDictCabel() {
        return dictCabel;
    }

    public void setDictCabel(String dictCabel) {
        this.dictCabel = dictCabel == null ? null : dictCabel.trim();
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue == null ? null : dictValue.trim();
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType == null ? null : dictType.trim();
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass == null ? null : cssClass.trim();
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault == null ? null : isDefault.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}