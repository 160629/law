package com.chinatower.product.legalms.modules.flow.vo.flow;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("业务冗余字段")
public class BusinessFields {
    @ApiModelProperty(name = "businessTitle", value = "标题")
    private String businessTitle;
    @ApiModelProperty(name = "businessCode", value = "编号")
    private String businessCode;
    @ApiModelProperty(name = "businessType", value = "业务类型")
    private String businessType;
    @ApiModelProperty(name = "businessTableName", value = "业务表名")
    private String businessTableName;
    @ApiModelProperty(name = "businessId", value = "业务id")
    private String businessId;
    @ApiModelProperty(name = "businessIdName", value = "业务id字段名称")
    private String businessIdName;
    @ApiModelProperty(name = "curState", value = "当前状态")
    private String curState;
    @ApiModelProperty(name = "accountId", value = "当前人id")
    private String accountId;
    @ApiModelProperty(name = "moduleName", value = "页面标识")
    private String moduleName;

    public BusinessFields setBusinessTitle(String businessTitle) {
        this.businessTitle = businessTitle;
        return this;
    }

    public BusinessFields setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
        return this;
    }

    public BusinessFields setBusinessType(String businessType) {
        this.businessType = businessType;
        return this;
    }

    public BusinessFields setBusinessTableName(String businessTableName) {
        this.businessTableName = businessTableName;
        return this;
    }

    public BusinessFields setBusinessId(String businessId) {
        this.businessId = businessId;
        return this;
    }

    public BusinessFields setBusinessIdName(String businessIdName) {
        this.businessIdName = businessIdName;
        return this;
    }

    public BusinessFields setCurState(String curState) {
        this.curState = curState;
        return this;
    }

    public BusinessFields setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public BusinessFields setModuleName(String moduleName) {
        this.moduleName = moduleName;
        return this;
    }

    public String getBusinessTitle() {
        return businessTitle;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public String getBusinessType() {
        return businessType;
    }

    public String getBusinessTableName() {
        return businessTableName;
    }

    public String getBusinessId() {
        return businessId;
    }

    public String getBusinessIdName() {
        return businessIdName;
    }

    public String getCurState() {
        return curState;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getModuleName() {
        return moduleName;
    }
}
