package com.chinatower.product.legalms.modules.flow.vo.unview;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 已阅待阅表
 *
 * @author wangyong
 * @date   2019/11/22
 */
public class TFlowUnviewParam {
    private String viewId;

    private String viewType;

    private String viewTitle;

    private String viewStatus;

    private String loginAcct;
    
    private String toerId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    private Date endTime;

	private Integer pageNum;

	private Integer pageSize;


	
	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
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

	public String getLoginAcct() {
		return loginAcct;
	}

	public void setLoginAcct(String loginAcct) {
		this.loginAcct = loginAcct;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getToerId() {
		return toerId;
	}

	public void setToerId(String toerId) {
		this.toerId = toerId;
	}

}