package com.chinatower.product.legalms.modules.dispute.vo.tasktransfer;

import org.apache.commons.lang.StringUtils;

/**
 * @author 刘晓亮
 * @create 2019-12-25
 */
public class TFlowTransferVO {
    private Integer id;//主键
    private String syscode;// 系统标识
    private Integer compensateFlag;// 接口功能标识（0为通知业务转移，1为通知业务回滚）
    private String fromUserCode;// 源用户账号
    private String toUserCode;// 目标用户账号
    private Integer deliverWay;// 转移模式（0为按人转移，taskId为空，1为按工作项逐条转移，传入taskId数组，数组里面字段为Long类型）
    private String taskId;// 待办id
    private String resultCode;// 结果标识代码(服务异常返回9999,转移成功为0000)
    private String resultMsg;// 返回处理结果信息
    private Integer dealResult;// 业务转移具体结果标识码(按人转移或者逐条转移全部成功返回0,其余返回1)
    private String successTaskId;// 业务转移成功taskId数组(Long类型)
    private String failTaskId;// 业务转移失败taskId数组(Long类型)

    public void setSuccessTaskId(Long[] successTaskId) {
        this.successTaskId = StringUtils.join(successTaskId, ',');
    }

    public void setFailTaskId(Long[] failTaskId) {
        this.failTaskId = StringUtils.join(failTaskId, ',');
    }

    public String getSuccessTaskId() {
        return successTaskId;
    }

    public String getFailTaskId() {
        return failTaskId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(Long[] taskId) {
        this.taskId = StringUtils.join(taskId, ",");
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public Integer getDealResult() {
        return dealResult;
    }

    public void setDealResult(Integer dealResult) {
        this.dealResult = dealResult;
    }
}
