package com.chinatower.product.legalms.modules.dispute.vo.push;

import java.util.List;

public class NotifAndUndone<T> {
	private String serviceId;
	private String userId;
	private Integer bereadSum;
	private String flag;
	private List<T> bereadMessage;
	public String getServiceId() {
		return serviceId;
	}
	public NotifAndUndone<T> setServiceId(String serviceId) {
		this.serviceId = serviceId;
		return this;
	}
	public String getUserId() {
		return userId;
	}
	public NotifAndUndone<T> setUserId(String userId) {
		this.userId = userId;
		return this;
	}

	public Integer getBereadSum() {
		return bereadSum;
	}
	public NotifAndUndone setBereadSum(Integer bereadSum) {
		this.bereadSum = bereadSum;
		return this;
	}
	public String getFlag() {
		return flag;
	}
	public NotifAndUndone<T> setFlag(String flag) {
		this.flag = flag;
		return this;
	}
	public List<T> getBereadMessage() {
		return bereadMessage;
	}
	public NotifAndUndone<T> setBereadMessage(List<T> bereadMessage) {
		this.bereadMessage = bereadMessage;
		return this;
	}
	
	
}
