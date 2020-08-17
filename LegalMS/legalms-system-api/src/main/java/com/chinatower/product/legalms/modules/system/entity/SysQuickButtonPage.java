package com.chinatower.product.legalms.modules.system.entity;
import lombok.Data;


@Data
public class SysQuickButtonPage {
    private Integer pageSize; //页码

    private Integer pageNum;  //每页条数

    private  String flowId;  //流程定义ID

    private String displayName; //陈列名字

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

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
