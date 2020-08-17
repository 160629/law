package com.chinatower.product.legalms.modules.flow.vo.flow;

import java.io.Serializable;

import com.eos.workflow.omservice.WFParticipant;

public class OrgParticipantVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orgName;
	private String orgCode;
	private WFParticipant depaInterPersonP;
	private String receiverCompanyId;
	private String receiverCompanyName;
	private String receiverOrgId;
	private String receiverOrgName;
	private String receiverId;
	private String receiverName;
	private String phone;


	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getReceiverCompanyId() {
		return receiverCompanyId;
	}

	public void setReceiverCompanyId(String receiverCompanyId) {
		this.receiverCompanyId = receiverCompanyId;
	}

	public String getReceiverCompanyName() {
		return receiverCompanyName;
	}

	public void setReceiverCompanyName(String receiverCompanyName) {
		this.receiverCompanyName = receiverCompanyName;
	}

	public String getReceiverOrgId() {
		return receiverOrgId;
	}

	public void setReceiverOrgId(String receiverOrgId) {
		this.receiverOrgId = receiverOrgId;
	}

	public String getReceiverOrgName() {
		return receiverOrgName;
	}

	public void setReceiverOrgName(String receiverOrgName) {
		this.receiverOrgName = receiverOrgName;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}



	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public WFParticipant getDepaInterPersonP() {
		return depaInterPersonP;
	}

	public void setDepaInterPersonP(WFParticipant depaInterPersonP) {
		this.depaInterPersonP = depaInterPersonP;
	}

	@Override
	public String toString() {
		return "OrgParticipantVO{" +
				"orgName='" + orgName + '\'' +
				", orgCode='" + orgCode + '\'' +
				", depaInterPersonP=" + depaInterPersonP +
				", receiverCompanyId='" + receiverCompanyId + '\'' +
				", receiverCompanyName='" + receiverCompanyName + '\'' +
				", receiverOrgId='" + receiverOrgId + '\'' +
				", receiverOrgName='" + receiverOrgName + '\'' +
				", receiverId='" + receiverId + '\'' +
				", receiverName='" + receiverName + '\'' +
				'}';
	}
}
