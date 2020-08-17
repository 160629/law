package com.chinatower.product.legalms.modules.flow.vo.common;

public class CacheModel {
    private String key;
    private Object value;
    private long expireTime;
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
	public long getExpireTime() {
		return expireTime;
	}
	public CacheModel setExpireTime(long expireTime) {
		this.expireTime = expireTime;
		return this;
	}
    
    
}
