package com.chinatower.product.legalms.modules.dispute.vo.push;

import java.io.Serializable;

public class LawSum implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String serviceId ="law";
	//本年案件数
	private Integer yearSumCase=0;
	//本年被诉案件数
	private Integer yearNumberIndictedCase=0;
	//累计重大案件数	
	private Integer sumMajorCase=0;
	//累计案件数
	private Integer sumCase=0;
	//案件累计金额
	private Double caseSumMoney=0.0;
	//被诉案件累计金额
	private Double indictedCaseMoney=0.0;
	//公司ID
	private String orgId="0";
	
	
	public String getOrgId() {
		return orgId;
	}


	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}


	public String getServiceId() {
		return serviceId;
	}


	public Integer getYearSumCase() {
		return yearSumCase;
	}


	public void setYearSumCase(Integer yearSumCase) {
		this.yearSumCase = yearSumCase;
	}


	public Integer getYearNumberIndictedCase() {
		return yearNumberIndictedCase;
	}
	public LawSum setYearNumberIndictedCase(Integer yearNumberIndictedCase) {
		this.yearNumberIndictedCase = yearNumberIndictedCase;
		return this;
	}
	public Integer getSumMajorCase() {
		return sumMajorCase;
	}
	public LawSum setSumMajorCase(Integer sumMajorCase) {
		this.sumMajorCase = sumMajorCase;
		return this;
	}
	public Integer getSumCase() {
		return sumCase;
	}
	public LawSum setSumCase(Integer sumCase) {
		this.sumCase = sumCase;
		return this;
	}
	public Double getCaseSumMoney() {
		return caseSumMoney;
	}
	public LawSum setCaseSumMoney(Double caseSumMoney) {
		this.caseSumMoney = caseSumMoney;
		return this;
	}
	public Double getIndictedCaseMoney() {
		return indictedCaseMoney;
	}
	public LawSum setIndictedCaseMoney(Double indictedCaseMoney) {
		this.indictedCaseMoney = indictedCaseMoney;
		return this;
	}


	public LawSum(Integer yearSumCase, Integer yearNumberIndictedCase, Integer sumMajorCase, Integer sumCase,
			Double caseSumMoney, Double indictedCaseMoney) {
		super();
		this.yearSumCase = yearSumCase;
		this.yearNumberIndictedCase = yearNumberIndictedCase;
		this.sumMajorCase = sumMajorCase;
		this.sumCase = sumCase;
		this.caseSumMoney = caseSumMoney;
		this.indictedCaseMoney = indictedCaseMoney;
	}


	public LawSum() {
		super();
	}
	
	
}
