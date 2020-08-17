package com.chinatower.product.legalms.modules.license.entity;

import lombok.ToString;


@ToString
public class ResponseVO {

	private   String serviceId;

	private   String rsp;

	private   String errDesc;

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getRsp() {
		return rsp;
	}

	public void setRsp(String rsp) {
		this.rsp = rsp;
	}

	public String getErrDesc() {
		return errDesc;
	}

	public void setErrDesc(String errDesc) {
		this.errDesc = errDesc;
	}
}