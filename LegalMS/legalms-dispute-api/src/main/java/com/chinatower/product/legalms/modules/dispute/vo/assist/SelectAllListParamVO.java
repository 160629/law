package com.chinatower.product.legalms.modules.dispute.vo.assist;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.Objects;

public class SelectAllListParamVO {

    @ApiModelProperty(value="标题",name="title")
    private String title;

    @ApiModelProperty(value="执行单位名称",name="jointlyUnitName")
    private String jointlyUnitName;

    @ApiModelProperty(value="执行类型",name="assistType")
    private String assistType;

    @ApiModelProperty(value = "执行法院名称", name = "courtName")
    private String courtName;

    @ApiModelProperty(value = "编号", name = "code")
    private String code;

    @ApiModelProperty(value = "承办部门名称", name = "jointlyDeptName")
    private String jointlyDeptName;

    @ApiModelProperty(value = "提交时间-开始时间", name = "startTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty(value = "提交时间-结束时间", name = "endTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    @ApiModelProperty(value = "被执行人(供应商)名称", name = "supplierName")
    private String supplierName;

    @ApiModelProperty(value = "页码", name = "pageNum")
    public Integer pageNum;

    @ApiModelProperty(value = "单页数量", name = "pageSize")
    public Integer pageSize;

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

    public String getJointlyUnitName() {
        return jointlyUnitName;
    }

    public void setJointlyUnitName(String jointlyUnitName) {
        this.jointlyUnitName = jointlyUnitName;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getJointlyDeptName() {
        return jointlyDeptName;
    }

    public void setJointlyDeptName(String jointlyDeptName) {
        this.jointlyDeptName = jointlyDeptName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SelectAllListParamVO that = (SelectAllListParamVO) o;
        return title.equals(that.title) &&
                jointlyUnitName.equals(that.jointlyUnitName) &&
                assistType.equals(that.assistType) &&
                courtName.equals(that.courtName) &&
                code.equals(that.code) &&
                jointlyDeptName.equals(that.jointlyDeptName) &&
                startTime.equals(that.startTime) &&
                endTime.equals(that.endTime) &&
                supplierName.equals(that.supplierName) &&
                pageNum.equals(that.pageNum) &&
                pageSize.equals(that.pageSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, jointlyUnitName, assistType, courtName, code, jointlyDeptName, startTime, endTime, supplierName, pageNum, pageSize);
    }

    @Override
    public String toString() {
        return "SelectAllListParamVO{" +
                "title='" + title + '\'' +
                ", jointlyUnitName='" + jointlyUnitName + '\'' +
                ", assistType='" + assistType + '\'' +
                ", courtName='" + courtName + '\'' +
                ", code='" + code + '\'' +
                ", jointlyDeptName='" + jointlyDeptName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", supplierName='" + supplierName + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
