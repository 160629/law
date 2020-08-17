package com.chinatower.product.legalms.modules.flow.entity.sms;

import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 短信发送确认信息表
 *
 * @author wangyong
 * @date   2020/06/22
 */
@Table(name="t_pub_sms_ack")
@ApiModel("短信发送确认信息表")
public class TPubSmsAck {
    @Id
    @ApiModelProperty(value="手机号",name="phone")
    private String phone;

    @ApiModelProperty(value="提交批次id",name="sendId")
    private String sendId;

    @ApiModelProperty(value="发送确认状态",name="ackStatus")
    private String ackStatus;

    @ApiModelProperty(value="接收时间",name="receiveTime")
    private String receiveTime;
    
    @ApiModelProperty(value="接收地址",name="receiveUrl")
    private String receiveUrl;

    
    
    public String getReceiveUrl() {
		return receiveUrl;
	}

	public void setReceiveUrl(String receiveUrl) {
		this.receiveUrl = receiveUrl;
	}

	public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId == null ? null : sendId.trim();
    }

    public String getAckStatus() {
        return ackStatus;
    }

    public void setAckStatus(String ackStatus) {
        this.ackStatus = ackStatus == null ? null : ackStatus.trim();
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime == null ? null : receiveTime.trim();
    }

	@Override
	public String toString() {
		return "TPubSmsAck [phone=" + phone + ", sendId=" + sendId + ", ackStatus=" + ackStatus + ", receiveTime="
				+ receiveTime + ", receiveUrl=" + receiveUrl + "]";
	}

	public TPubSmsAck(String phone, String sendId, String ackStatus, String receiveTime) {
		super();
		this.phone = phone;
		this.sendId = sendId;
		this.ackStatus = ackStatus;
		this.receiveTime = receiveTime;
	}

	public TPubSmsAck() {
		super();
	}
    
    
}