package com.chinatower.product.legalms.modules.flow.entity.unview;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 已阅待阅表
 *
 * @author wangyong
 * @date   2019/12/02
 */
@Table(name="t_flow_unview")
public class TFlowUnview extends Tbase{
    @Id
    private String viewId;

    private String viewType;

    private String viewTitle;

    private String viewStatus;

    private String viewComent;

    private String toerId;

    private String toerName;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:dd",timezone = "GMT+8")
    private Date toerTime;

    private String viewUrl;
    
    private String viewUrlName;

    private String approveItemId;
    
    private Integer openType;
    
    private String pushFlag;
    
    
    public String getPushFlag() {
		return pushFlag;
	}

	public void setPushFlag(String pushFlag) {
		this.pushFlag = pushFlag;
	}

	public String getApproveItemId() {
		return approveItemId;
	}

	public void setApproveItemId(String approveItemId) {
		this.approveItemId = approveItemId;
	}

	public Integer getOpenType() {
		return openType;
	}

	public void setOpenType(Integer openType) {
		this.openType = openType;
	}
    public String getViewUrlName() {
		return viewUrlName;
	}

	public void setViewUrlName(String viewUrlName) {
		this.viewUrlName = viewUrlName;
	}

	public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId == null ? null : viewId.trim();
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType == null ? null : viewType.trim();
    }

    public String getViewTitle() {
        return viewTitle;
    }

    public void setViewTitle(String viewTitle) {
        this.viewTitle = viewTitle == null ? null : viewTitle.trim();
    }

    public String getViewStatus() {
        return viewStatus;
    }

    public void setViewStatus(String viewStatus) {
        this.viewStatus = viewStatus == null ? null : viewStatus.trim();
    }

    public String getViewComent() {
        return viewComent;
    }

    public void setViewComent(String viewComent) {
        this.viewComent = viewComent == null ? null : viewComent.trim();
    }

    public String getToerId() {
        return toerId;
    }

    public void setToerId(String toerId) {
        this.toerId = toerId == null ? null : toerId.trim();
    }

    public String getToerName() {
        return toerName;
    }

    public void setToerName(String toerName) {
        this.toerName = toerName == null ? null : toerName.trim();
    }

    public Date getToerTime() {
        return toerTime;
    }

    public void setToerTime(Date toerTime) {
        this.toerTime = toerTime;
    }

    public String getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl == null ? null : viewUrl.trim();
    }



	public TFlowUnview(String viewId, String viewType, String toerId, String toerName, 
			String viewUrlName,String loginAcct,String loginName) {
		super();
		this.viewId = viewId;
		this.viewType = viewType;
		this.toerId = toerId;
		this.toerName = toerName;
		this.viewUrlName = viewUrlName;
		super.loginAcct=loginAcct;
		super.loginName=loginName;
	}


	public TFlowUnview() {
		super();
	}
    
}