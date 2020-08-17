package com.chinatower.product.legalms.vo.dict;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
@Data
@Table(name = "t_sys_dict_type")
@ToString
public class SysDictTypeVO {
    @Id
    @Column(name = "dict_id")
    @JsonProperty("dictId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)    //字典主键
    private Integer dictId;

    @JsonProperty("dictName")
    @Column(name = "dict_name")       //字典名称
    private String dictName;


    @JsonProperty("dictType")
    @Column(name = "dict_type")
    private String dictType;    //字典类型

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
    private String remark;      //备注


    public Integer getDictId() {
        return dictId;
    }

    public void setDictId(Integer dictId) {
        this.dictId = dictId;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
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
}
