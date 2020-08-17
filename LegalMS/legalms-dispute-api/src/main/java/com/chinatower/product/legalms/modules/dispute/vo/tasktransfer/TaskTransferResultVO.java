package com.chinatower.product.legalms.modules.dispute.vo.tasktransfer;

import java.util.Arrays;

/**
 * @author 刘晓亮
 * @create 2019-12-20
 */
public class TaskTransferResultVO {
    /**
     * resultCode           -------结果标识代码(服务异常返回9999,转移成功为0000)
     * resultMsg            -------返回处理结果信息
     * dealResult(int)      -------业务转移具体结果标识码(按人转移或者逐条转移全部成功返回0,其余返回1)
     * data                 -------在按工作项逐条转移失败的时候，需要返回具体处理结果明细
     * fromUserId;
     * toUserId;
     * successTaskId        -------业务转移成功taskId数组(Long类型)
     * failTaskId           -------业务转移失败taskId数组(Long类型)
     */

    String resultCode;
    String resultMsg;
    Integer dealResult;
    TaskResultData data;

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

    public TaskResultData getData() {
        return data;
    }

    public void setData(TaskResultData data) {
        this.data = data;
    }

    public TaskResultData initTaskResultData(){
        return new TaskResultData();
    }
    public class TaskResultData{
        Long[] successTaskId;
        Long[] failTaskId;
        String fromUserId;
        String toUserId;

        public Long[] getSuccessTaskId() {
            return successTaskId;
        }

        public void setSuccessTaskId(Long[] successTaskId) {
            this.successTaskId = successTaskId;
        }

        public Long[] getFailTaskId() {
            return failTaskId;
        }

        public void setFailTaskId(Long[] failTaskId) {
            this.failTaskId = failTaskId;
        }

        public String getFromUserId() {
            return fromUserId;
        }

        public void setFromUserId(String fromUserId) {
            this.fromUserId = fromUserId;
        }

        public String getToUserId() {
            return toUserId;
        }

        public void setToUserId(String toUserId) {
            this.toUserId = toUserId;
        }

        @Override
        public String toString() {
            return "TaskResultData{" +
                    "successTaskId=" + Arrays.toString(successTaskId) +
                    ", failTaskId=" + Arrays.toString(failTaskId) +
                    ", fromUserId='" + fromUserId + '\'' +
                    ", toUserId='" + toUserId + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TaskTransferResultVO{" +
                "resultCode='" + resultCode + '\'' +
                ", resultMsg='" + resultMsg + '\'' +
                ", dealResult=" + dealResult +
                ", data=" + data +
                '}';
    }
}
