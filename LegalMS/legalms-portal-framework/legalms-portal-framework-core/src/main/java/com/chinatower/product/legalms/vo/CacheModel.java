package com.chinatower.product.legalms.vo;

public class CacheModel {
    private String key;
    private Object value;
    private Long expireTime;
	public String getKey() {
		return key;
	}
	public CacheModel setKey(String key) {
		this.key = key;
		return this;
	}
	public Object getValue() {
		return value;
	}
	public CacheModel setValue(Object value) {
		this.value = value;
		return this;
	}
	public Long getExpireTime() {
		return expireTime;
	}
	public CacheModel setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
		return this;
	}
    
    
}
