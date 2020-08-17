package com.chinatower.product.legalms.modules.cases.entity;

import com.chinatower.product.legalms.modules.cases.api.MoneyCheckUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.util.Date;

@Table(name = "t_case_main")
@Data
@ToString
@ApiModel(value="案宗Main")
public class CaseMainVO {
    /*
     * 案宗主表
     */
    @Id
    @Column(name = "case_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(name="caseId",value = "案件ID",required = true)
    private String caseId;  //案件ID

    @Column(name = "case_code")
    @ApiModelProperty(name="caseCode",value = "案件编号")
    private String caseCode;   //案件编号

    @Column(name = "case_title")
    @ApiModelProperty(name="caseTitle",value = "案件标题",required = true)
    private String caseTitle;  //案件标题

    @Column(name = "case_status")
    @ApiModelProperty(name="caseStatus",value = "案件状态")
    private String caseStatus;  //案件状态

    @Column(name = "drafter_unit")
    @ApiModelProperty(name="drafterUnit",value = "起草单位")
    private String drafterUnit; //起草单位

    @Column(name = "case_record_time")
    @ApiModelProperty(name="caseRecordTime",value = "立案时间",required = true)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date caseRecordTime;  //立案时间


    /**
     * 该字段已删除， 不展示再前端页面
     */
    @Column(name = "institution_case_code")
    @ApiModelProperty(name="institutionCaseCode",value = "审理机关案件编号",required = true)
    private String institutionCaseCode; //审理机关案件编号

    @Column(name = "lawsuit_type")
    @ApiModelProperty(name="lawsuitType",value = "诉讼类型",required = true)
    private String lawsuitType; //诉讼类型

    @Column(name = "our_lawsuit_body")
    @ApiModelProperty(name="ourLawsuitBody",value = "我方诉讼主体Id",required = true)
    private String ourLawsuitBody; //我方诉讼主体Id

    @Column(name = "our_lawsuit_body_name")
    @ApiModelProperty(name="ourLawsuitBodyName",value = "我方诉讼主体名称",required = true)
    private String ourLawsuitBodyName; //我方诉讼主体名称

    @Column(name = "our_lawsuit_identity")
    @ApiModelProperty(name="ourLawsuitIdentity",value = "我方诉讼地位",required = true)
    private String ourLawsuitIdentity; //我方诉讼地位

    @Column(name = "other_lawsuit_body")
    @ApiModelProperty(name="otherLawsuitBody",value = "诉讼相对方主体",required = true)
    private String otherLawsuitBody; //诉讼相对方主体

    @Column(name = "case_the_third")
    @ApiModelProperty(name="caseTheThird",value = "第三人")
    private String caseTheThird; //第三人

    @Column(name = "case_depute_money")
    @ApiModelProperty(name="caseDeputeMoney",value = "争议金额")
    private String caseDeputeMoney; //争议金额

    @Column(name = "case_special_line")
    @ApiModelProperty(name="caseSpecialLine",value = "案件所属专业线")
    private String caseSpecialLine; //案件所属专业线

    @Column(name = "related_body_attribution")
    @ApiModelProperty(name="relatedBodyAttribution",value = "我方涉诉公司主体归属")
    private String relatedBodyAttribution; //我方涉诉公司主体归属

    @Column(name = "case_review_grade")
    @ApiModelProperty(name="caseReviewGrade",value = "案件审级",required = true)
    private String caseReviewGrade; //案件审级

    @Column(name = "case_reason")
    @ApiModelProperty(name="caseReason",value = "案由")
    private String caseReason; //案由

    @Column(name = "depute_type")
    @ApiModelProperty(name="deputeType",value = "案件类型",required = true)
    private String deputeType; //案件类型

    @Column(name = "create_time")
    @ApiModelProperty(name="createTime",value = "创建时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime; //创建时间

    @Column(name = "last_update_time")
    @ApiModelProperty(name="lastUpdateTime",value = "最后更新时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date lastUpdateTime; //最后更新时间

    @Column(name = "case_is_finish")
    @ApiModelProperty(name="caseIsFinish",value = "案件是否结案",required = true)
    private Integer caseIsFinish   ; //案件是否结案

    @Column(name = "case_finish_time")
    @ApiModelProperty(name="caseFinishTime",value = "结案时间",required = true)
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date caseFinishTime; //结案时间

    @Column(name = "case_finish_way")
    @ApiModelProperty(name="caseFinishWay",value = "结案方式",required = true)
    private String caseFinishWay; //结案方式

    @Column(name = "large_lawsuit_mark")
    @ApiModelProperty(name="largeLawsuitMark",value = "重大诉讼标识")
    private String largeLawsuitMark; //重大诉讼标识

    @Column(name = "creator_account_id")
    @ApiModelProperty(name="creatorAccountId",value = "creator_account_id")
    private String creatorAccountId; //creator_account_id

    @Column(name = "creator_account_name")
    @ApiModelProperty(name="creatorAccountName",value = "起草人")
    private String creatorAccountName; //起草人

    @Column(name = "creator_cell")
    @ApiModelProperty(name="creatorCell",value = "联系电话")
    private String creatorCell;   //联系电话

    @Column(name = "case_truth")
    @ApiModelProperty(name="caseTruth",value = "案件事实",required = true)
    private String caseTruth;   //案件事实

    @Column(name = "plaintiff_lawsuit_request")
    @ApiModelProperty(name="plaintiffLawsuitRequest",value = "原告诉讼请求",required = true)
    private String plaintiffLawsuitRequest;   //原告诉讼请求

    @Column(name = "case_file")
    @ApiModelProperty(name="caseFile",value = "案件材料")
    private String caseFile;   //案件材料

    @Column(name = "case_keyword")
    @ApiModelProperty(name="caseKeyword",value = "案件关键字")
    private String caseKeyword;   //案件关键字

    @Column(name = "case_same_plaintiff")
    @ApiModelProperty(name="caseSamePlaintiff",value = "同案原告")
    private String caseSamePlaintiff;   //同案原告

    @Column(name = "case_same_defendant")
    @ApiModelProperty(name="caseSameDefendant",value = "同案被告")
    private String caseSameDefendant;   //同案被告

    @Column(name = "creator_dept_id")
    @ApiModelProperty(name="creatorDeptId",value = "creator_dept_id")
    private String creatorDeptId;   //creator_dept_id

    @Column(name = "creator_dept_name")
    @ApiModelProperty(name="creatorDeptName",value = "用户部门名称")
    private String creatorDeptName;     //用户部门名称

    @Column(name = "creator_unit_id")
    @ApiModelProperty(name="creatorUnitId",value = "creator_unit_id")
    private String creatorUnitId;   //creator_unit_id

    @Column(name = "creator_unit_name")
    @ApiModelProperty(name="creatorUnitName",value = "creator_unit_name")
    private String creatorUnitName;   //creator_unit_id

    @Column(name="case_explain")
    @ApiModelProperty(name="caseExplain",value = "结案说明")
    private String caseExplain;   //结案说明

    @Column(name = "ruling_classes")
    @ApiModelProperty(name="rulingClasses",value = "裁决类别",required = true)
    private String rulingClasses;

    @Column(name = "delete_status")
    @ApiModelProperty(name="deleteStatus",value = "删除状态",required = true)
    private Integer deleteStatus;

    @Column(name = "is_auto")
    @ApiModelProperty(name="isAuto",value = "是否为自动生成",required = true)
    private String isAuto;

    @ApiModelProperty(name = "shareType")
    private String shareType;

    @ApiModelProperty(name = "guideId", value = "关联引诉纠纷申报单id")
    private String guideId;

    @ApiModelProperty(name = "guideTitle", value = "关联引诉纠纷申报单标题")
    private String guideTitle;

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

    @ApiModelProperty(name = "rulingExecutiveVO", value = "裁决执行信息")
    private RulingExecutiveVO rulingExecutiveVO;

    public String getCaseDeputeMoney() {
        return MoneyCheckUtil.checkMoneyIsEmpty(caseDeputeMoney);
    }

    public void setCaseDeputeMoney(String caseDeputeMoney) {
        this.caseDeputeMoney = MoneyCheckUtil.checkMoneyIsEmpty(caseDeputeMoney);
    }
}
