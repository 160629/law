package com.chinatower.product.legalms.modules.flow.entity.common;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@Table(name="t_sys_orgareamapper")
@ApiModel(value="争议主体配置Table")
public class SysOrgareamapperVO {
    @Id
    @JsonProperty("areaCode")
    @Column(name="area_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(name="areaCode",value = "地区编码 主键",required = true)
    private String areaCode;

    @JsonProperty("orgCode")
    @Column(name = "org_code")
	@ApiModelProperty(name="orgCode",value = "关联组织机构代码")
    private String orgCode;

    @JsonProperty("areaName")
    @Column(name = "area_name")
	@ApiModelProperty(name="areaName",value = "地区名称")
    private String areaName;

    @JsonProperty("areaEn")
    @Column(name = "area_en")
	@ApiModelProperty(name="areaEn",value = "字母简称")
    private String areaEn;

    @JsonProperty("logWorkdocId")
    @Column(name = "log_workdoc_id")
    private String logWorkdocId;

    @JsonProperty("areaCn")
    @Column(name = "area_cn")
	@ApiModelProperty(name="areaCn",value = "中文简称")
    private String areaCn;

    @JsonProperty("logTransactUserName")
    @Column(name = "log_transact_user_name")
    private String logTransactUserName;

    @JsonProperty("areaOrder")
    @Column(name = "area_order")
	@ApiModelProperty(name="areaOrder",value = "排序字段")
    private Integer areaOrder;

    @JsonProperty("tmp1")
    @Column(name = "tmp1")
	@ApiModelProperty(name="tmp1",value = "系统预留字段1")
    private String tmp1;

    @JsonProperty("tmp2")
    @Column(name = "tmp2")
	@ApiModelProperty(name="tmp2",value = "系统预留字段2")
    private String tmp2;

    @JsonProperty("tmp3")
    @Column(name = "tmp3")
	@ApiModelProperty(name="tmp3",value = "系统预留字段3")
    private String tmp3;

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaEn() {
		return areaEn;
	}

	public void setAreaEn(String areaEn) {
		this.areaEn = areaEn;
	}

	public String getLogWorkdocId() {
		return logWorkdocId;
	}

	public void setLogWorkdocId(String logWorkdocId) {
		this.logWorkdocId = logWorkdocId;
	}

	public String getAreaCn() {
		return areaCn;
	}

	public void setAreaCn(String areaCn) {
		this.areaCn = areaCn;
	}

	public String getLogTransactUserName() {
		return logTransactUserName;
	}

	public void setLogTransactUserName(String logTransactUserName) {
		this.logTransactUserName = logTransactUserName;
	}

	public Integer getAreaOrder() {
		return areaOrder;
	}

	public void setAreaOrder(Integer areaOrder) {
		this.areaOrder = areaOrder;
	}

	public String getTmp1() {
		return tmp1;
	}

	public void setTmp1(String tmp1) {
		this.tmp1 = tmp1;
	}

	public String getTmp2() {
		return tmp2;
	}

	public void setTmp2(String tmp2) {
		this.tmp2 = tmp2;
	}

	public String getTmp3() {
		return tmp3;
	}

	public void setTmp3(String tmp3) {
		this.tmp3 = tmp3;
	}


}
