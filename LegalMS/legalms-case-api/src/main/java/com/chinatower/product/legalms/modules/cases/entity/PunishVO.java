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

@Table(name = "t_case_punish")
@Data
@ToString
@ApiModel(value="行政复议Table")
public class PunishVO {

    /*
     * 行政复议
     */

    @Id
    @Column(name = "punish_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(name="punishId",value = "行政复议ID",required = true)
    private String punishId;  //行政复议ID

    @Column(name = "case_id")
    @ApiModelProperty(name="caseId",value = "案件ID",required = true)
    private String caseId;   //案件ID

    @Column(name = "our_body")
    @ApiModelProperty(name="ourBody",value = "我方主体名称")
    private String ourBody;  //我方主体名称

    @Column(name = "penalty_institution")
    @ApiModelProperty(name="penaltyInstitution",value = "执行行政处罚机关名称")
    private String penaltyInstitution; //执行行政处罚机关名称

    @Column(name = "decided_penalty_truth")
    @ApiModelProperty(name="decidedPenaltyTruth",value = "认定处罚事实")
    private String decidedPenaltyTruth;  //认定处罚事实

    @Column(name = "judicially_legal_regulations")
    @ApiModelProperty(name="judiciallyLegalRegulations",value = "法律依据")
    private String judiciallyLegalRegulations; //法律依据

    @Column(name = "penalty_content")
    @ApiModelProperty(name="penaltyContent",value = "处罚内容")
    private String penaltyContent; //处罚内容

    @Column(name = "administrative_penalty_award")
    @ApiModelProperty(name="administrativePenaltyAward",value = "行政处罚判决书",required = true)
    private String administrativePenaltyAward; //行政处罚判决书

    @Column(name = "is_hearing")
    @ApiModelProperty(name="isHearing",value = "是否听证")
    private String isHearing; //是否听证

    @Column(name = "hearing_opinion")
    @ApiModelProperty(name="hearingOpinion",value = "听证意见")
    private String hearingOpinion; //听证意见

    @Column(name = "is_reconsider")
    @ApiModelProperty(name="isReconsider",value = "是否复议")
    private String isReconsider; //是否复议

    @Column(name = "administrative_reconsider_award")
    @ApiModelProperty(name="administrativeReconsiderAward",value = "行政复议决定书")
    private String administrativeReconsiderAward; //行政复议决定书

    @Column(name = "bring_administrative_lawsuit")
    @ApiModelProperty(name="bringAdministrativeLawsuit",value = "是否提起行政诉讼")
    private String bringAdministrativeLawsuit; //是否提起行政诉讼

    @Column(name = "penalty_money")
    @ApiModelProperty(name="penaltyMoney",value = "行政处罚金额",required = true)
    private String penaltyMoney; //行政处罚金额

    @Column(name = "executive_way")
    @ApiModelProperty(name="executiveWay",value = "执行方式",required = true)
    private String executiveWay; //执行方式

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
    @ApiModelProperty(name="lastUpdateTime",value = "last_update_time")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date lastUpdateTime; //last_update_time

    @Column(name = "status")
    @ApiModelProperty(name="status",value = "状态")
    private String status; //状态

    @Column(name = "biz_id")
    @ApiModelProperty(name="bizId",value = "biz_id")
    private String bizId; //biz_id

    @Column(name = "lawyer_money")
    @ApiModelProperty(name="lawyerMoney",value = "律师费",required = true)
    private String lawyerMoney; //律师费

    @Column(name = "lawsuit_money")
    @ApiModelProperty(name="lawsuitMoney",value = "诉讼费",required = true)
    private String lawsuitMoney; //诉讼费

    @ApiModelProperty(name = "shareType")
    private String shareType;

    public String getPenaltyMoney() {
        return MoneyCheckUtil.checkMoneyIsEmpty(penaltyMoney);
    }

    public void setPenaltyMoney(String penaltyMoney) {
        this.penaltyMoney = MoneyCheckUtil.checkMoneyIsEmpty(penaltyMoney);
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
