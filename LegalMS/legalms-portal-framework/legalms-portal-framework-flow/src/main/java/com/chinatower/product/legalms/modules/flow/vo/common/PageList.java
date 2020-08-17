package com.chinatower.product.legalms.modules.flow.vo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("列表查询")
public class PageList {
    @ApiModelProperty(value = "页码", name = "pageNum")
    public Integer pageNum;
    @ApiModelProperty(value = "单页数量", name = "pageSize")
    public Integer pageSize;
    @ApiModelProperty(value = "流程ID", name = "flowId")
    public String flowId;
    @ApiModelProperty(value = "开始ID", name = "beginId")
    public String beginId;

    @ApiModelProperty(name="endId",value = "结束环节ID")
    private String endId;
    @ApiModelProperty(name="endName",value = "结束环节")
    private String endName;

    @ApiModelProperty(value = "开始名称", name = "beginName")
    public String beginName;
    @ApiModelProperty(value = "重要程度", name = "importantLevel")
    public String importantLevel;

    private Integer versionId;

    public String getEndName() {
        return endName;
    }

    public void setEndName(String endName) {
        this.endName = endName;
    }

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

    public PageList() {
        //lsl
        super();
    }

    public PageList(String flowId, String beginId, String endId,Integer versionId ) {
        this.flowId = flowId;
        this.beginId = beginId;
        this.endId = endId;
        this.versionId = versionId;
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

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getBeginId() {
        return beginId;
    }

    public void setBeginId(String beginId) {
        this.beginId = beginId;
    }

    public String getBeginName() {
        return beginName;
    }

    public void setBeginName(String beginName) {
        this.beginName = beginName;
    }

    public String getImportantLevel() {
        return importantLevel;
    }

    public void setImportantLevel(String importantLevel) {
        this.importantLevel = importantLevel;
    }

    public String getEndId() {
        return endId;
    }

    public void setEndId(String endId) {
        this.endId = endId;
    }

    @Override
    public String toString() {
        return "PageList{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", flowId='" + flowId + '\'' +
                ", beginId='" + beginId + '\'' +
                ", endId='" + endId + '\'' +
                ", beginName='" + beginName + '\'' +
                ", importantLevel='" + importantLevel + '\'' +
                '}';
    }
}
