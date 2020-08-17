package com.chinatower.product.legalms.modules.cases.entity;

import com.chinatower.product.legalms.modules.cases.api.MoneyCheckUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@ToString
@ApiModel(value="案宗Main")
public class CaseMainAutoVO {

    @ApiModelProperty(name = "caseId", value = "案件ID")
    private String caseId;  //案件ID

    @ApiModelProperty(name = "caseCode", value = "案件编号")
    private String caseCode;  //案件编号

    @ApiModelProperty(name = "caseTitle", value = "案件标题")
    private String caseTitle;  //案件标题

    @ApiModelProperty(name = "caseSpecialLine", value = "案件所属专业线")
    private String caseSpecialLine; //案件所属专业线

    @ApiModelProperty(name = "createTime", value = "创建时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime; //创建时间

    @ApiModelProperty(name = "moduleName", value = "页面唯一标识")
    private String moduleName;

    @ApiModelProperty(name = "caseReason", value = "案由")
    private String caseReason; //案由

    @ApiModelProperty(name = "largeLawsuitMark", value = "重大诉讼标识")
    private String largeLawsuitMark; //重大诉讼标识

    @ApiModelProperty(name = "deputeType", value = "案件类型")
    private String deputeType; //案件类型

    @ApiModelProperty(name = "caseDeputeMoney", value = "争议金额")
    private String caseDeputeMoney; //争议金额

    @ApiModelProperty(name = "ourLawsuitBody", value = "我方诉讼主体Id")
    private String ourLawsuitBody; //我方诉讼主体Id

    @ApiModelProperty(name = "ourLawsuitBodyName", value = "我方诉讼主体名称")
    private String ourLawsuitBodyName; //我方诉讼主体名称

    @ApiModelProperty(name = "ourLawsuitIdentity", value = "我方诉讼地位")
    private String ourLawsuitIdentity; //我方诉讼地位

    @ApiModelProperty(name = "otherLawsuitBody", value = "诉讼相对方主体")
    private String otherLawsuitBody; //诉讼相对方主体

    @ApiModelProperty(name = "caseTheThird", value = "第三人")
    private String caseTheThird; //第三人

    @ApiModelProperty(name = "caseSamePlaintiff", value = "同案原告")
    private String caseSamePlaintiff;   //同案原告

    @ApiModelProperty(name = "caseSameDefendant", value = "同案被告")
    private String caseSameDefendant;   //同案被告

    @ApiModelProperty(name = "caseTruth", value = "案件事实")
    private String caseTruth;   //案件事实

    @ApiModelProperty(name = "caseFile", value = "案件材料")
    private String caseFile;   //案件材料

    @ApiModelProperty(name = "drafterUnit", value = "起草单位")
    private String drafterUnit; //起草单位

    @ApiModelProperty(name = "creatorDeptId", value = "creator_dept_id")
    private String creatorDeptId;   //creator_dept_id

    @ApiModelProperty(name = "creatorDeptName", value = "用户部门名称")
    private String creatorDeptName;     //用户部门名称

    @ApiModelProperty(name = "creatorUnitId", value = "creator_unit_id")
    private String creatorUnitId;   //creator_unit_id

    @ApiModelProperty(name = "creatorUnitName", value = "creator_unit_name")
    private String creatorUnitName;   //creator_unit_name

    @ApiModelProperty(name = "creatorAccountId", value = "creator_account_id")
    private String creatorAccountId; //creator_account_id

    @ApiModelProperty(name = "creatorAccountName", value = "起草人")
    private String creatorAccountName; //起草人

    @ApiModelProperty(name = "creatorCell", value = "联系电话")
    private String creatorCell;   //联系电话

    @ApiModelProperty(name = "isAuto", value = "是否为自动生成")
    private String isAuto;     //是否为自动生成

    @ApiModelProperty(name = "businessId", value = "业务表ID")
    private String businessId;

    @ApiModelProperty(name = "businessType", value = "业务类型")
    private String businessType;

    @ApiModelProperty(name = "caseStatus", value = "案件状态")
    private String caseStatus;

    @ApiModelProperty(name = "code", value = "业务表编号")
    private String code;

    @ApiModelProperty(name = "rulingClasses", value = "")
    private String rulingClasses;

    @ApiModelProperty(name = "caseRecordTime", value = "立案时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date caseRecordTime; // 立案时间

    @ApiModelProperty(name = "involvedAccountId", value = "涉案人ID")
    private String involvedAccountId;

    @ApiModelProperty(name = "involvedAccountName", value = "涉案人名称")
    private String involvedAccountName;

    @ApiModelProperty(name = "involvedDeptId", value = "涉案部门ID")
    private String involvedDeptId;

    @ApiModelProperty(name = "involvedDeptName", value = "涉案部门名称")
    private String involvedDeptName;

    @ApiModelProperty(name = "involvedOrgId", value = "涉案单位ID")
    private String involvedOrgId;

    @ApiModelProperty(name = "involvedOrgName", value = "涉案单位名称")
    private String involvedOrgName;

    @ApiModelProperty(name = "institutionCaseCode", value = "审理机构案件编号")
    private String institutionCaseCode; // 审理机构案件编号

    public String getCaseDeputeMoney() {
        return MoneyCheckUtil.checkMoneyIsEmpty(caseDeputeMoney);
    }

    public void setCaseDeputeMoney(String caseDeputeMoney) {
        this.caseDeputeMoney = MoneyCheckUtil.checkMoneyIsEmpty(caseDeputeMoney);
    }
}
