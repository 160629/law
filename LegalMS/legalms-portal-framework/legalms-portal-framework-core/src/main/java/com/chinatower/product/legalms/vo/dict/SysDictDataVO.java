package com.chinatower.product.legalms.vo.dict;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;


import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "t_sys_dict_data")
@ToString
public class SysDictDataVO {
    @Id
    @Column(name = "dict_code")
    @JsonProperty("dictCode")
    @GeneratedValue(strategy = GenerationType.IDENTITY)    //字典编码
    private String dictCode;

    @JsonProperty("dictParentCode")
    @Column(name = "dict_parent_code")
    private String dictParentCode;

    @JsonProperty("dictSort")
    @Column(name = "dict_sort")
    private  Integer dictSort;             //字典排序

    @JsonProperty("dictCabel")
    @Column(name = "dict_cabel")
    private String dictCabel;              //字典标签

    @JsonProperty("dictValue")
    @Column(name = "dict_value")
    private String dictValue;            //字典键值

    @JsonProperty("dictType")
    @Column(name = "dict_type")
    private String dictType;           //字典类型

    @JsonProperty("cssClass")
    @Column(name = "css_class")
    private String  cssClass;         //样式属性

    @JsonProperty("isDefault")
    @Column(name = "is_default")
    private String isDefault;       //是否默认（Y是 N否）

    @JsonProperty("status")
    @Column(name = "status")
    private String status;       //状态（0正常 1停用）

    @JsonProperty("createBy")
    @Column(name = "create_by")
    private String createBy;             //创建者

    @JsonProperty("createTime")
    @Column(name = "create_time")
    private Date createTime;            //创建时间

    @JsonProperty("updateBy")
    @Column(name = "update_by")
    private String updateBy;            //更新者

    @JsonProperty("updateTime")
    @Column(name = "update_time")
    private Date updateTime;          //更新时间

    @JsonProperty("remark")
    @Column(name = "remark")
    private String remark;         //备注

    private Boolean isParent;         //是不是父节点

    private String type;         //1可选2不可选

    private Integer pageSize;

    private Integer pageNum;

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
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
        this.dictCabel = dictCabel;
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
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
        this.updateBy = updateBy;
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
        this.remark = remark;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public String getDictParentCode() {
        return dictParentCode;
    }

    public void setDictParentCode(String dictParentCode) {
        this.dictParentCode = dictParentCode;
    }

    public Boolean getParent() {
        return isParent;
    }

    public void setParent(Boolean parent) {
        isParent = parent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
