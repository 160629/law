package com.chinatower.product.legalms.modules.dispute.vo.assist;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author 刘晓亮
 * @date 2020/7/24 15:18
 */
public class SelectRelationshipListInCreateParamVO {
    @ApiModelProperty(value="标题",name="title")
    private String title;

    @ApiModelProperty(value = "编号", name = "code")
    private String code;

    @ApiModelProperty(value="执行类型",name="assistType")
    private String assistType;

    @ApiModelProperty(value = "执行法院名称", name = "courtName")
    private String courtName;

    @ApiModelProperty(value = "被执行人(供应商)名称", name = "supplierName")
    private String supplierName;

    @ApiModelProperty(value = "起草单位", name = "orgName")
    private String orgName;

    @ApiModelProperty(value = "页码", name = "pageNum")
    private Integer pageNum;

    @ApiModelProperty(value = "单页数量", name = "pageSize")
    private Integer pageSize;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAssistType() {
        return assistType;
    }

    public void setAssistType(String assistType) {
        this.assistType = assistType;
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
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

    @Override
    public String toString() {
        return "SelectRelationshipListInCreateParamVO{" +
                "title='" + title + '\'' +
                ", code='" + code + '\'' +
                ", assistType='" + assistType + '\'' +
                ", courtName='" + courtName + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", orgName='" + orgName + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
