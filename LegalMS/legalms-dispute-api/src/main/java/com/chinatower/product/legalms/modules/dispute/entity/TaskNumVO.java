package com.chinatower.product.legalms.modules.dispute.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 刘晓亮
 * @create 2019-10-30 15:49
 */
@ApiModel("待办已办办结数量")
public class TaskNumVO {
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("用户id")
    private String userId;
    @ApiModelProperty("待办数量")
    private Integer toDoTask;
    @ApiModelProperty("已办数量")
    private Integer finishTask;
    @ApiModelProperty("办结数量")
    private Integer endTask;
    @ApiModelProperty("最后更新日期")
    private String lastUpdateDate;
    @ApiModelProperty("开始时间")
    private String startTime;
    @ApiModelProperty("结束时间")
    private String endTime;

    @Override
    public String toString() {
        return "TaskNumVO{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", toDoTask=" + toDoTask +
                ", finishTask=" + finishTask +
                ", endTask=" + endTask +
                ", lastUpdateDate='" + lastUpdateDate + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }

    public String getStartTime() {
        return startTime;
    }

    public TaskNumVO setStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public String getEndTime() {
        return endTime;
    }

    public TaskNumVO setEndTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    public String getId() {
        return id;
    }

    public TaskNumVO setId(String id) {
        this.id = id;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public TaskNumVO setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public Integer getToDoTask() {
        return toDoTask;
    }

    public TaskNumVO setToDoTask(Integer toDoTask) {
        this.toDoTask = toDoTask;
        return this;
    }

    public Integer getFinishTask() {
        return finishTask;
    }

    public TaskNumVO setFinishTask(Integer finishTask) {
        this.finishTask = finishTask;
        return this;
    }

    public Integer getEndTask() {
        return endTask;
    }

    public TaskNumVO setEndTask(Integer endTask) {
        this.endTask = endTask;
        return this;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public TaskNumVO setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
        return this;
    }
}
