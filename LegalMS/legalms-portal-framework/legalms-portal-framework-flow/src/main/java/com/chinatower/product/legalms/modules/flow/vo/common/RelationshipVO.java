package com.chinatower.product.legalms.modules.flow.vo.common;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Table(name = "t_case_relationship")
@ApiModel(value="关联Table")
public class RelationshipVO {
    /**
     *  关联表
     */

    @Column(name = "business_type")
    @ApiModelProperty(name="businessType",value = "业务表类型",required = true)
    private String businessType;   //业务表类型

    @Id
    @Column(name = "case_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(name="caseId",value = "案件id",required = true)
    private String caseId;  //案件id

    @Id
    @Column(name = "business_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(name="businessId",value = "业务表id",required = true)
    private String businessId;  //业务表id

    @Column(name = "tittle")
    @ApiModelProperty(name="tittle",value = "标题",required = true)
    private String tittle;   //标题

    @Column(name = "code")
    @ApiModelProperty(name="code",value = "编号",required = true)
    private String code;   //编号

    @Column(name = "module_name")
    @ApiModelProperty(name="moduleName",value = "跳转页面标识",required = true)
    private String moduleName;   //跳转页面标识
    //  1：更换关联案件，2：新增关联案件，3：删除关联案件
    private String optionType;   

    private String oldCaseId;   

    @Column(name = "is_delete")
    @ApiModelProperty(name="isDelete",value = "关联关系是否可以删除",required = true)
    private String isDelete;// 关联关系是否可以删除
    
    
    public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public String getOptionType() {
		return optionType;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}

	public String getOldCaseId() {
		return oldCaseId;
	}

	public void setOldCaseId(String oldCaseId) {
		this.oldCaseId = oldCaseId;
	}

	public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

	public RelationshipVO(String businessType, String caseId, String businessId, String tittle, String code,
			String moduleName) {
		super();
		this.businessType = businessType;
		this.caseId = caseId;
		this.businessId = businessId;
		this.tittle = tittle;
		this.code = code;
		this.moduleName = moduleName;
	}

	public RelationshipVO() {
		super();
	}

	@Override
	public String toString() {
		return "RelationshipVO [businessType=" + businessType + ", caseId=" + caseId + ", businessId=" + businessId
				+ ", tittle=" + tittle + ", code=" + code + ", moduleName=" + moduleName + ", optionType=" + optionType
				+ ", oldCaseId=" + oldCaseId + ", isDelete=" + isDelete + "]";
	}
    
    
}
