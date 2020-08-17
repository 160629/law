package com.chinatower.product.legalms.modules.flow.entity.sms;

import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 短信回复信息表
 *
 * @author wangyong
 * @date   2020/06/22
 */
@Table(name="t_pub_sms_back")
@ApiModel("短信回复信息表")
public class TPubSmsBack {
    @Id
    @ApiModelProperty(value="手机号",name="phone")
    private String phone;

    @Id
    @ApiModelProperty(value="接收时间",name="receiveTime")
    private String receiveTime;

    @ApiModelProperty(value="内容",name="content")
    private String content;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime == null ? null : receiveTime.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	@Override
	public String toString() {
		return "TPubSmsBack [phone=" + phone + ", receiveTime=" + receiveTime + ", content=" + content + "]";
	}

	public TPubSmsBack(String phone, String receiveTime, String content) {
		super();
		this.phone = phone;
		this.receiveTime = receiveTime;
		this.content = content;
	}

	public TPubSmsBack() {
		super();
	}
    
    
}