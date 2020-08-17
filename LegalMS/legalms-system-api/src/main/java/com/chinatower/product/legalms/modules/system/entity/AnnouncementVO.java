package com.chinatower.product.legalms.modules.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
//import com.chinatower.product.legalms.modules.filecommon.vo.filecommon.Filelist;

@Data
@Table(name = "t_sys_announce_base")
@ToString
public class AnnouncementVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "announcement_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String announcementId;		//id主键

	@Column(name = "announcement_order")
    private Integer announcementOrder;		//序号

	@Column(name = "announcement_name")
    private String announcementName;		//公告标题

	@Column(name = "announcement_content")
    private String announcementContent;		//公告内容

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "announcement_starttime")
    private Date announcementStarttime;		//开始时间

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "announcement_finishtime")
    private Date announcementFinishtime;		//结束时间

	@Column(name = "announcement_state")
    private String announcementState;		//公告状态 公告状态 0未发布 1发布中 2发布完毕

	@Column(name = "announcement_typeid")
    private Integer announcementTypeid;		//公告类型id

	@Column(name = "announcement_issuerid")
    private String announcementIssuerid;		//公告发布人id

	@Column(name = "announcement_issuername")
	private String announcementIssuername;		//公告发布人姓名

	@Column(name = "announcement_status")
	private Integer announcementStatus;     //公告删除状态 0为未删除 1为已删除

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "announcement_createtime")
	private Date announcementCreatetime;    //发布时间

	@Column(name="announcement_filestate")
	private String announcementFilestate;

	private String filedId;

	private Integer pageNum;

	private Integer pageSize;

//	private List<Filelist> filelist;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getAnnouncementId() {
		return announcementId;
	}

	public void setAnnouncementId(String announcementId) {
		this.announcementId = announcementId;
	}

	public Integer getAnnouncementOrder() {
		return announcementOrder;
	}

	public void setAnnouncementOrder(Integer announcementOrder) {
		this.announcementOrder = announcementOrder;
	}

	public String getAnnouncementName() {
		return announcementName;
	}

	public void setAnnouncementName(String announcementName) {
		this.announcementName = announcementName;
	}

	public String getAnnouncementContent() {
		return announcementContent;
	}

	public void setAnnouncementContent(String announcementContent) {
		this.announcementContent = announcementContent;
	}

	public Date getAnnouncementStarttime() {
		return announcementStarttime;
	}

	public void setAnnouncementStarttime(Date announcementStarttime) {
		this.announcementStarttime = announcementStarttime;
	}

	public Date getAnnouncementFinishtime() {
		return announcementFinishtime;
	}

	public void setAnnouncementFinishtime(Date announcementFinishtime) {
		this.announcementFinishtime = announcementFinishtime;
	}

	public String getAnnouncementState() {
		return announcementState;
	}

	public void setAnnouncementState(String announcementState) {
		this.announcementState = announcementState;
	}

	public Integer getAnnouncementTypeid() {
		return announcementTypeid;
	}

	public void setAnnouncementTypeid(Integer announcementTypeid) {
		this.announcementTypeid = announcementTypeid;
	}

	public String getAnnouncementIssuerid() {
		return announcementIssuerid;
	}

	public void setAnnouncementIssuerid(String announcementIssuerid) {
		this.announcementIssuerid = announcementIssuerid;
	}

	public String getAnnouncementIssuername() {
		return announcementIssuername;
	}

	public void setAnnouncementIssuername(String announcementIssuername) {
		this.announcementIssuername = announcementIssuername;
	}

	public Integer getAnnouncementStatus() {
		return announcementStatus;
	}

	public void setAnnouncementStatus(Integer announcementStatus) {
		this.announcementStatus = announcementStatus;
	}

	public Date getAnnouncementCreatetime() {
		return announcementCreatetime;
	}

	public void setAnnouncementCreatetime(Date announcementCreatetime) {
		this.announcementCreatetime = announcementCreatetime;
	}

	public String getAnnouncementFilestate() {
		return announcementFilestate;
	}

	public void setAnnouncementFilestate(String announcementFilestate) {
		this.announcementFilestate = announcementFilestate;
	}

	public String getFiledId() {
		return filedId;
	}

	public void setFiledId(String filedId) {
		this.filedId = filedId;
	}

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
}