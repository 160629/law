package com.chinatower.product.legalms.modules.consult.vo.issue;

import com.chinatower.product.legalms.modules.flow.entity.unview.Tbase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("法律支撑列表参数")
public class ConsultListParam extends Tbase {
    @ApiModelProperty(value="编号",name="code")
    private String code;

    @ApiModelProperty(value="标题",name="title")
    private String title;

    @ApiModelProperty(value = "业务类别", name = "businessType")
    private String businessType;

    @ApiModelProperty(value = "支撑类别", name = "supportType")
    private String supportType;

    @ApiModelProperty(value = "页码", name = "pageNum")
    public Integer pageNum;

    @ApiModelProperty(value = "单页数量", name = "pageSize")
    public Integer pageSize;

    @Override
    public String toString() {
        return "ConsultListParam{" +
                "code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", businessType='" + businessType + '\'' +
                ", supportType='" + supportType + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getSupportType() {
        return supportType;
    }

    public void setSupportType(String supportType) {
        this.supportType = supportType;
    }

}
