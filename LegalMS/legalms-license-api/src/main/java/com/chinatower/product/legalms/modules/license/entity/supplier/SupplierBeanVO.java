package com.chinatower.product.legalms.modules.license.entity.supplier;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @Date: 2020/7/2 16:43
 * @Description:
 */
public class SupplierBeanVO implements Serializable {
    @JsonProperty("flag")
    private String flag;

    @JsonProperty("listJson")
    private List<SupplierBaseVO> listJson;


    public void setFlag(String flag) {
        this.flag = flag;
    }
    public String getFlag() {
        return flag;
    }

    public void setListJson(List<SupplierBaseVO> listJson) {
        this.listJson = listJson;
    }
    public List<SupplierBaseVO> getListJson() {
        return listJson;
    }
}
