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

@Table(name = "t_case_arbitrate")
@Data
@ToString
@ApiModel(value="仲裁Table")
public class ArbittateVO {
    /*
     * 仲裁/和解
     */

    @Id
    @Column(name = "arbitrate_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(name="arbitrateId",value = "仲裁ID",required = true)
    private String arbitrateId;  //仲裁ID

    @Column(name = "case_id")
    @ApiModelProperty(name="caseId",value = "案件ID",required = true)
    private String caseId;   //案件ID

    @Column(name = "claimant")
    @ApiModelProperty(name="claimant",value = "申请人")
    private String claimant;  //申请人

    @Column(name = "claimant_arbitrate_request")
    @ApiModelProperty(name="claimantArbitrateRequest",value = "仲裁请求")
    private String claimantArbitrateRequest; //仲裁请求

    @Column(name = "claimant_key_evidence")
    @ApiModelProperty(name="claimantKeyEvidence",value = "申请人关键证据")
    private String claimantKeyEvidence;  //申请人关键证据

    @Column(name = "respondent")
    @ApiModelProperty(name="respondent",value = "被申请人")
    private String respondent; //被申请人

    @Column(name = "respondent_plea_reply")
    @ApiModelProperty(name="respondentPleaReply",value = "被申请人答辩意见")
    private String respondentPleaReply; //被申请人答辩意见

    @Column(name = "respondent_key_evidence")
    @ApiModelProperty(name="respondentKeyEvidence",value = "被申请人关键证据")
    private String respondentKeyEvidence; //被申请人关键证据

    @Column(name = "review_applyfor_institution")
    @ApiModelProperty(name="reviewApplyforInstitution",value = "审理机构")
    private String reviewApplyforInstitution; //审理机构

    @Column(name = "depute_focus")
    @ApiModelProperty(name="deputeFocus",value = "争议焦点")
    private String deputeFocus; //争议焦点

    @Column(name = "reason_for_findings")
    @ApiModelProperty(name="reasonForFindings",value = "裁决理由")
    private String reasonForFindings; //裁决理由

    @Column(name = "of_arbitration")
    @ApiModelProperty(name="ofArbitration",value = "裁决结果")
    private String ofArbitration; //裁决结果

    @Column(name = "ruling_time")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @ApiModelProperty(name="ofArbitration",value = "裁决结果",required = true)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date rulingTime; //裁决时间

    @Column(name = "case_finish_way")
    @ApiModelProperty(name="caseFinishWay",value = "结案方式",required = true)
    private String caseFinishWay; //结案方式

    @Column(name = "ruling_money")
    @ApiModelProperty(name="rulingMoney",value = "裁决/和解金额",required = true)
    private String rulingMoney; //裁决/和解金额

    /**
     * 该字段已删除，不在前台展示
     */
    @Column(name = "executive_money")
    @ApiModelProperty(name="executiveMoney",value = "执行金额",required = true)
    private String executiveMoney; //执行金额

    /**
     * 该字段已删除，不在前台展示
     */
    @Column(name = "help_loss_money")
    @ApiModelProperty(name="helpLossMoney",value = "挽回损失金额",required = true)
    private String helpLossMoney; //挽回损失金额

    /**
     * 该字段已删除，不在前台展示
     */
    @Column(name = "executive_way")
    @ApiModelProperty(name="executiveWay",value = "执行方式",required = true)
    private String executiveWay; //执行方式

    /**
     * 该字段已删除，不在前台展示
     */
    @Column(name = "loss_money")
    @ApiModelProperty(name="lossMoney",value = "损失金额",required = true)
    private String lossMoney; //损失金额

    @Column(name = "arbitral_award")
    @ApiModelProperty(name="arbitralAward",value = "仲裁书",required = true)
    private String arbitralAward; //仲裁书

    @Column(name = "create_time")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(name="createTime",value = "create_time")
    private Date createTime; //create_time

    @Column(name = "creator_account_id")
    @ApiModelProperty(name="creatorAccountId",value = "creator_account_id")
    private String creatorAccountId; //creator_account_id

    @Column(name = "creator_account_name")
    @ApiModelProperty(name="creatorAccountName",value = "creator_account_name")
    private String creatorAccountName; //creator_account_name

    @Column(name = "last_update_time")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(name="lastUpdateTime",value = "last_update_time")
    private Date lastUpdateTime; //last_update_time

    @Column(name = "status")
    @ApiModelProperty(name="status",value = "状态")
    private String status; //状态

    @Column(name = "lawyer_money")
    @ApiModelProperty(name="lawyerMoney",value = "律师费",required = true)
    private String lawyerMoney; //律师费

    @Column(name = "lawsuit_money")
    @ApiModelProperty(name="lawsuitMoney",value = "诉讼费",required = true)
    private String lawsuitMoney; //诉讼费

    @Column(name = "institution_case_code")
    @ApiModelProperty(name = "institutionCaseCode", value = "审理机构案件编号")
    private String institutionCaseCode; // 审理机构案件编号

    @ApiModelProperty(name="shareType")
    private String shareType;

    public String getRulingMoney() {
        return MoneyCheckUtil.checkMoneyIsEmpty(rulingMoney);
    }

    public void setRulingMoney(String rulingMoney) {
        this.rulingMoney = MoneyCheckUtil.checkMoneyIsEmpty(rulingMoney);
    }

    public String getExecutiveMoney() {
        return MoneyCheckUtil.checkMoneyIsEmpty(executiveMoney);
    }

    public void setExecutiveMoney(String executiveMoney) {
        this.executiveMoney = MoneyCheckUtil.checkMoneyIsEmpty(executiveMoney);
    }

    public String getHelpLossMoney() {
        return MoneyCheckUtil.checkMoneyIsEmpty(helpLossMoney);
    }

    public void setHelpLossMoney(String helpLossMoney) {
        this.helpLossMoney = MoneyCheckUtil.checkMoneyIsEmpty(helpLossMoney);
    }

    public String getLossMoney() {
        return MoneyCheckUtil.checkMoneyIsEmpty(lossMoney);
    }

    public void setLossMoney(String lossMoney) {
        this.lossMoney = MoneyCheckUtil.checkMoneyIsEmpty(lossMoney);
    }

    public String getLawyerMoney() {
        return MoneyCheckUtil.checkMoneyIsEmpty(lawyerMoney);
    }

    public void setLawyerMoney(String lawyerMoney) {
        this.lawyerMoney = MoneyCheckUtil.checkMoneyIsEmpty(lawyerMoney);
    }

    public String getLawsuitMoney() {
        return MoneyCheckUtil.checkMoneyIsEmpty(lawsuitMoney);
    }

    public void setLawsuitMoney(String lawsuitMoney) {
        this.lawsuitMoney = MoneyCheckUtil.checkMoneyIsEmpty(lawsuitMoney);
    }
}

