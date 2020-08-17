package com.chinatower.product.legalms.modules.cases.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "t_file_share")
@ToString
public class FileShareVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "file_share_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String fileShareId;		//ID

    @Column(name = "file_type")
    private Integer fileType;         //文件业务类型

    @Column(name = "file_share_business_key")
    private String fileShareBusinessKey;    //文件业务主键ID

    @Column(name = "file_share_type")
    private String fileShareType;    //文件归属模块

    @Column(name = "file_share_status_upd_user_id")
    private String fileShareStatusUpdUserId;    //文件上传者ID

    @Column(name = "file_order")
    private Integer fileOrder;      //文件排序号

    @Column(name = "file_share_status")
    private String fileShareStatus;    //文件状态

    @Column(name = "file_share_status_upd_time")
    private Date fileShareStatusUpdTime;   //文件更新时间

    @Column(name = "file_remark")
    private String fileRemark;   //备注

    @Column(name = "file_id")
    private String fileId;    //文件ID

}
