package com.chinatower.product.legalms.modules.system.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "t_file_main")
@ToString
public class FileMainVO implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "file_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String fileId;

    @Column(name = "file_name")
    private String fileName;           //文件名称

    @Column(name = "file_extension")
    private String fileExtension;      //文件后缀

    @Column(name = "file_http_url")
    private String fileHttpUrl;        //访问地址

    @Column(name = "file_upload_app_id")
    private String fileUploadAppId;   //文件模块类型

    @Column(name = "file_upload_User_id")
    private String fileUploadUserId;  //文件上传者ID

    @Column(name = "file_upload_time")
    private Date fileUploadTime;     //文件上传时间

    @Column(name = "file_status_update_time")
    private Date fileStatusUpdateTime;  //文件更新时间

    @Column(name = "file_status")
    private String fileStatus;     //文件状态

    @Column(name = "file_remark")
    private String fileRemark;       //备注

    @Column(name = "visibility_level")
    private String visibilityLevel;  //文件可见性级别

    @Column(name = "file_lengh")
    private String fileLengh;       //文件长度
}
