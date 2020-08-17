package com.chinatower.product.legalms.modules.flow.entity.flow;


import java.util.Map;

public class FlowStartVO {
    String defName;
    String instName;
    String instDesc;
    boolean isTransactionSplit;
    Object[] params;
    String tableName;
    Map<String, Object> map;
    Map<String, Object> bizInfo;

    public String getDefName() {
        return defName;
    }

    public String getInstName() {
        return instName;
    }

    public String getInstDesc() {
        return instDesc;
    }

    public boolean isTransactionSplit() {
        return isTransactionSplit;
    }

    public Object[] getParams() {
        return params;
    }

    public String getTableName() {
        return tableName;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public Map<String, Object> getBizInfo() {
        return bizInfo;
    }

    public FlowStartVO setDefName(String defName) {
        this.defName = defName;
        return this;
    }

    public FlowStartVO setInstName(String instName) {
        this.instName = instName;
        return this;
    }

    public FlowStartVO setInstDesc(String instDesc) {
        this.instDesc = instDesc;
        return this;
    }

    public FlowStartVO setTransactionSplit(boolean transactionSplit) {
        isTransactionSplit = transactionSplit;
        return this;
    }

    public FlowStartVO setParams(Object[] params) {
        this.params = params;
        return this;
    }

    public FlowStartVO setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public FlowStartVO setMap(Map<String, Object> map) {
        this.map = map;
        return this;
    }

    public FlowStartVO setBizInfo(Map<String, Object> bizInfo) {
        this.bizInfo = bizInfo;
        return this;
    }
}
