package com.chinatower.product.legalms.modules.dispute.vo.tasktransfer;

import java.util.Arrays;

/**
 * @author 刘晓亮
 * @create 2019-12-20
 */
public class TaskTransferParamVO {
    /**
     * syscode              ------系统标识
     * compensateFlag(int)  ------接口功能标识（0为通知业务转移，1为通知业务回滚）
     * fromUserCode         ------源用户账号
     * toUserCode           ------目标用户账号
     * deliverWay(int)      ------转移模式（0为按人转移，taskId为空，1为按工作项逐条转移，传入taskId数组，数组里面字段为Long类型）
     * taskId               ------任务列表
     */
    private String syscode;
    private Integer compensateFlag;
    private String fromUserCode;
    private String toUserCode;
    private Integer deliverWay;
    private Long[] taskId;

    @Override
    public String toString() {
        return "TaskTransferParamVO{" +
                "syscode='" + syscode + '\'' +
                ", compensateFlag=" + compensateFlag +
                ", fromUserCode='" + fromUserCode + '\'' +
                ", toUserCode='" + toUserCode + '\'' +
                ", deliverWay=" + deliverWay +
                ", taskId=" + Arrays.toString(taskId) +
                '}';
    }

    public String getSyscode() {
        return syscode;
    }

    public void setSyscode(String syscode) {
        this.syscode = syscode;
    }

    public Integer getCompensateFlag() {
        return compensateFlag;
    }

    public void setCompensateFlag(Integer compensateFlag) {
        this.compensateFlag = compensateFlag;
    }

    public String getFromUserCode() {
        return fromUserCode;
    }

    public void setFromUserCode(String fromUserCode) {
        this.fromUserCode = fromUserCode;
    }

    public String getToUserCode() {
        return toUserCode;
    }

    public void setToUserCode(String toUserCode) {
        this.toUserCode = toUserCode;
    }

    public Integer getDeliverWay() {
        return deliverWay;
    }

    public void setDeliverWay(Integer deliverWay) {
        this.deliverWay = deliverWay;
    }

    public Long[] getTaskId() {
        return taskId;
    }

    public void setTaskId(Long[] taskId) {
        this.taskId = taskId;
    }
}
