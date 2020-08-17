package com.chinatower.product.legalms.modules.dispute.vo.assist;

import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

public class SelectRelationshipListParamVO {
    @ApiModelProperty(value="标题",name="title")
    private String title;

    @ApiModelProperty(value = "编号", name = "code")
    private String code;

    @ApiModelProperty(value="执行类型",name="assistType")
    private String assistType;

    @ApiModelProperty(value = "单据状态", name = "state")
    private String state;

    @ApiModelProperty(value="执行单位名称",name="jointlyUnitName")
    private String jointlyUnitName;

    @ApiModelProperty(value = "承办部门名称", name = "jointlyDeptName")
    private String jointlyDeptName;

    @ApiModelProperty(value = "执行法院名称", name = "courtName")
    private String courtName;

    @ApiModelProperty(value = "被执行人(供应商)名称", name = "supplierName")
    private String supplierName;

    @ApiModelProperty(value = "被执行人(供应商)名称", name = "supplierName")
    private String processInstId;

    @ApiModelProperty(value = "页码", name = "pageNum")
    public Integer pageNum;

    @ApiModelProperty(value = "单页数量", name = "pageSize")
    public Integer pageSize;

    public String getProcessInstId() {
        return processInstId;
    }

    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getJointlyUnitName() {
        return jointlyUnitName;
    }

    public void setJointlyUnitName(String jointlyUnitName) {
        this.jointlyUnitName = jointlyUnitName;
    }

    public String getJointlyDeptName() {
        return jointlyDeptName;
    }

    public void setJointlyDeptName(String jointlyDeptName) {
        this.jointlyDeptName = jointlyDeptName;
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

    @Override
    public String toString() {
        return "SelectRelationshipListParamVO{" +
                "title='" + title + '\'' +
                ", code='" + code + '\'' +
                ", assistType='" + assistType + '\'' +
                ", state='" + state + '\'' +
                ", jointlyUnitName='" + jointlyUnitName + '\'' +
                ", jointlyDeptName='" + jointlyDeptName + '\'' +
                ", courtName='" + courtName + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", processInstId='" + processInstId + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SelectRelationshipListParamVO that = (SelectRelationshipListParamVO) o;
        return title.equals(that.title) &&
                code.equals(that.code) &&
                assistType.equals(that.assistType) &&
                state.equals(that.state) &&
                jointlyUnitName.equals(that.jointlyUnitName) &&
                jointlyDeptName.equals(that.jointlyDeptName) &&
                courtName.equals(that.courtName) &&
                supplierName.equals(that.supplierName) &&
                pageNum.equals(that.pageNum) &&
                pageSize.equals(that.pageSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, code, assistType, state, jointlyUnitName, jointlyDeptName, courtName, supplierName, pageNum, pageSize);
    }
}
