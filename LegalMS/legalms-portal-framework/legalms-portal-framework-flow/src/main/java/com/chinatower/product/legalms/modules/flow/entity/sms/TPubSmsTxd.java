package com.chinatower.product.legalms.modules.flow.entity.sms;

import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 短信发送记录表
 *
 * @author wangyong
 * @date   2020/06/22
 */
@Table(name="t_pub_sms_txd")
@ApiModel("短信发送记录表")
public class TPubSmsTxd {
    @Id
    @ApiModelProperty(value="主键",name="txdId",example="0")
    private Long txdId;

    @ApiModelProperty(value="发送时间",name="mttime")
    private String mttime;

    @ApiModelProperty(value="签名",name="sign")
    private String sign;

    @ApiModelProperty(value="发送状态",name="txdCode")
    private String txdCode;

    @ApiModelProperty(value="提交批次id",name="sendId")
    private String sendId;

    @ApiModelProperty(value="发送账号",name="loginAcct")
    private String loginAcct;

    @ApiModelProperty(value="发送账号名称",name="loginName")
    private String loginName;

    @ApiModelProperty(value="提交批次pid",name="sendPid")
    private String sendPid;

    @ApiModelProperty(value="重发次数",name="retry",example="0")
    private Integer retry;

    @ApiModelProperty(value="业务类型",name="businessType")
    private String businessType;

    @ApiModelProperty(value="业务id",name="businessId")
    private String businessId;

    @ApiModelProperty(value="手机号(多个逗号分隔)",name="phones")
    private String phones;

    @ApiModelProperty(value="内容",name="content")
    private String content;

    public Long getTxdId() {
        return txdId;
    }

    public void setTxdId(Long txdId) {
        this.txdId = txdId;
    }

    public String getMttime() {
        return mttime;
    }

    public void setMttime(String mttime) {
        this.mttime = mttime == null ? null : mttime.trim();
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign == null ? null : sign.trim();
    }

    public String getTxdCode() {
        return txdCode;
    }

    public void setTxdCode(String txdCode) {
        this.txdCode = txdCode == null ? null : txdCode.trim();
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId == null ? null : sendId.trim();
    }

    public String getLoginAcct() {
        return loginAcct;
    }

    public void setLoginAcct(String loginAcct) {
        this.loginAcct = loginAcct == null ? null : loginAcct.trim();
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getSendPid() {
        return sendPid;
    }

    public void setSendPid(String sendPid) {
        this.sendPid = sendPid == null ? null : sendPid.trim();
    }

    public Integer getRetry() {
        return retry;
    }

    public void setRetry(Integer retry) {
        this.retry = retry;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType == null ? null : businessType.trim();
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId == null ? null : businessId.trim();
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones == null ? null : phones.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	@Override
	public String toString() {
		return "TPubSmsTxd [txdId=" + txdId + ", mttime=" + mttime + ", sign=" + sign + ", txdCode=" + txdCode
				+ ", sendId=" + sendId + ", loginAcct=" + loginAcct + ", loginName=" + loginName + ", sendPid="
				+ sendPid + ", retry=" + retry + ", businessType=" + businessType + ", businessId=" + businessId
				+ ", phones=" + phones + ", content=" + content + "]";
	}

	public TPubSmsTxd() {
		super();
	}
    
	public TPubSmsTxd(String phones, String businessId, String businessType,String loginAcct,String loginName) {
		super();
		this.phones = phones;
		this.businessType = businessType;
		this.businessId = businessId;
		this.loginAcct = loginAcct;
		this.loginName = loginName;
	}
}