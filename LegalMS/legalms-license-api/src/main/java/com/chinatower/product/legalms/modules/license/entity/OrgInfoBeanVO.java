package com.chinatower.product.legalms.modules.license.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name ="t_pub_org_between")
@ToString
public class OrgInfoBeanVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "org_name")
    private String orgName;		//组织机构名称
	@Id
	@Column(name = "org_code")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String orgCode;			//组织机构编码

	@Column(name = "org_status")
    private String orgStatus;		//组织状态

	@Column(name = "org_remarks")
    private String orgRemarks;		//备注

	@Column(name = "enterp_type")
    private String enterpType;		//企业类型

	@Column(name = "org_level")
    private String orgLevel;		//组织机构等级

	@Column(name = "org_parent_code")
    private String orgParentCode;		//上级组织机构

	@Column(name = "org_change")
    private String orgChange;		//组织机构变更事项

	@Column(name = "org_path")
    private String orgPath;		//组织机构路径

	@Column(name = "created_by")
    private String createdBy;		//创建人员

	@Column(name = "creation_date")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date creationDate;		//创建时间

	@Column(name = "last_updated_by")
    private String lastUpdatedBy;		//最后修改人

	@Column(name = "last_update_date")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date lastUpdateDate;		//最后修改时间

	@Column(name = "org_reserved_text1")
    private String orgReservedText1;		//排序字段

	@Column(name = "org_relation")
    private String orgRelation;		//关联组织机构

	@Column(name = "flag")
    private String flag;		//新增/修改标记

	@Column(name = "url")
    private String url;		//此路径为ESB转换服务调用主数据的路径

	@Column(name = "mdm_org_code")
	private String mdmOrgCode;

	@Column(name = "org_base_id")
	private Integer orgBaseId;

}