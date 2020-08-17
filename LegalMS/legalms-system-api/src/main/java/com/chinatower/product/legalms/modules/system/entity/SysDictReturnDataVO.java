package com.chinatower.product.legalms.modules.system.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;

public class SysDictReturnDataVO {
    @JsonProperty("dictCabel")
    @Column(name = "dict_cabel")
    private String dictCabel;              //字典标签

    @JsonProperty("dictValue")
    @Column(name = "dict_value")
    private String dictValue;            //字典键值

    private String status;     //状态（0正常 1停用）

    public String getDictCabel() {
        return dictCabel;
    }

    public SysDictReturnDataVO setDictCabel(String dictCabel) {
        this.dictCabel = dictCabel;
        return this;
    }

    public String getDictValue() {
        return dictValue;
    }

    public SysDictReturnDataVO setDictValue(String dictValue) {
        this.dictValue = dictValue;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public SysDictReturnDataVO setStatus(String status) {
        this.status = status;
        return this;
    }
}
