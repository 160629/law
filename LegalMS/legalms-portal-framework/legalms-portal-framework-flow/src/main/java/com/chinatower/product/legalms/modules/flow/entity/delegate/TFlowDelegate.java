package com.chinatower.product.legalms.modules.flow.entity.delegate;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import com.chinatower.product.legalms.modules.flow.entity.unview.Tbase;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 流程代办表
 *
 * @author wangyong
 * @date   2019/11/18
 */
@Table(name="t_flow_delegate")
public class TFlowDelegate extends Tbase{
    @Id
    private Long delegateId;
    
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date startTime;
    
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date endTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date realEndTime;

    private String delegateStatus;

    private String toerId;

    private String toerName;

    private String approveItemType;

    public Long getDelegateId() {
        return delegateId;
    }

    public TFlowDelegate setDelegateId(Long delegateId) {
        this.delegateId = delegateId;
        return this;
    }

    public Date getStartTime() {
        return startTime;
    }

    public TFlowDelegate setStartTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }

    public Date getEndTime() {
        return endTime;
    }

    public TFlowDelegate setEndTime(Date endTime) {
        this.endTime = endTime;
        return this;
    }

    public Date getRealEndTime() {
        return realEndTime;
    }

    public TFlowDelegate setRealEndTime(Date realEndTime) {
        this.realEndTime = realEndTime;
        return this;
    }

    public String getDelegateStatus() {
        return delegateStatus;
    }

    public TFlowDelegate setDelegateStatus(String delegateStatus) {
        this.delegateStatus = delegateStatus == null ? null : delegateStatus.trim();
        return this;
    }

    public String getToerId() {
        return toerId;
    }

    public TFlowDelegate setToerId(String toerId) {
        this.toerId = toerId == null ? null : toerId.trim();
        return this;
    }

    public String getToerName() {
        return toerName;
    }

    public TFlowDelegate setToerName(String toerName) {
        this.toerName = toerName == null ? null : toerName.trim();
        return this;
    }

    public String getApproveItemType() {
        return approveItemType;
    }

    public TFlowDelegate setApproveItemType(String approveItemType) {
        this.approveItemType = approveItemType == null ? null : approveItemType.trim();
        return this;
    }

	public TFlowDelegate() {
		super();
	}
    
	public TFlowDelegate(String loginAcct) {
		super.loginAcct=loginAcct;

	}
}