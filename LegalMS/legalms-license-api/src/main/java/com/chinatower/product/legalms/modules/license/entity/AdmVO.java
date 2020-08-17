package com.chinatower.product.legalms.modules.license.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "adm_base")
@ToString
public class AdmVO implements Serializable {
	private static final long serialVersionUID = 1L;

    @Column(name = "mdm_adm_Code")
    private String mdmAdmCode;		//

    @Id
    @Column(name = "adm_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String admCode;		//

    @Column(name = "adm_name")
    private String admName;		//

    @Column(name = "adm_alias")
    private String admAlias;		//

    @Column(name = "audit_status")
    private String admStatus;		//

    @Column(name = "relation_adm_code")
    private String relationAdmCode;		//

    @Column(name = "adm_remark")
    private String admRemark;		//


    @Column(name = "adm_base_id")
    private Integer admBaseId;		//

    @Column(name = "adm_level")
    private Integer admLevel;		//

    @Column(name = "parent_adm_code")
    private String parentAdmCode;		//

    @Column(name = "last_leve")
    private String lastLevel;		//

    @Column(name = "change_type")
    private String changeType;		//

    @Column(name = "last_update_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date lastUpdateDate;		//

    @Column(name = "last_updated_by")
    private String lastUpdatedBy;		//

    @Column(name = "creation_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date creationDate;		//

    @Column(name = "created_by")
    private String createdBy;		//

    @Column(name = "last_update_login")
    private Integer lastUpdateLogin;		//

    @Column(name = "org_id")
    private String orgId;		//

    @Column(name = "audit_status")
    private String auditStatus;		//

    @Column(name = "company_id")
    private Integer companyId;		//

    @Column(name = "reserve_one")
    private String reserveOne;		//

    @Column(name = "reserve_two")
    private String reserveTwo;		//

    @Column(name = "reserve_three")
    private String reserveThree;		//

    @Column(name = "reserve_four")
    private String reserveFour;		//

    @Column(name = "reserve_five")
    private String reserveFive;		//

    @Column(name = "adm_path")
    private String admPath;		//

}