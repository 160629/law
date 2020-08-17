package com.chinatower.product.legalms.modules.flow.entity.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name="t_sys_idea_common")
@ApiModel("常见意见实体类")
public class IdeasVO {
    @Id
    @ApiModelProperty(value="ID（不传）",name="ideaId")
    private String ideaId;

    @ApiModelProperty(value="类型",name="ideaType")
    private String ideaType;

    @ApiModelProperty(value="用户ID",name="ideaUserId")
    private String ideaUserId;

    @ApiModelProperty(value = "意见的内容",name = "ideaContent")
    private String ideaContent;

    @ApiModelProperty(value = "序号",name = "ideaOrder")
    private Integer ideaOrder;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value="创建时间",name="ideaCreateDate")
    private Date ideaCreateDate;

    public String getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(String ideaId) {
        this.ideaId = ideaId;
    }

    public String getIdeaType() {
        return ideaType;
    }

    public void setIdeaType(String ideaType) {
        this.ideaType = ideaType;
    }

    public String getIdeaUserId() {
        return ideaUserId;
    }

    public void setIdeaUserId(String ideaUserId) {
        this.ideaUserId = ideaUserId;
    }

    public String getIdeaContent() {
        return ideaContent;
    }

    public void setIdeaContent(String ideaContent) {
        this.ideaContent = ideaContent;
    }

    public Date getIdeaCreateDate() {
        return ideaCreateDate;
    }

    public void setIdeaCreateDate(Date ideaCreateDate) {
        this.ideaCreateDate = ideaCreateDate;
    }

    public Integer getIdeaOrder() {
        return ideaOrder;
    }

    public void setIdeaOrder(Integer ideaOrder) {
        this.ideaOrder = ideaOrder;
    }
}
