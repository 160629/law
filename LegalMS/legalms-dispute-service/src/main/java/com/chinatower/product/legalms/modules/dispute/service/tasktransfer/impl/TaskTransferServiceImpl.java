package com.chinatower.product.legalms.modules.dispute.service.tasktransfer.impl;

import com.alibaba.fastjson.JSONObject;
import com.chinatower.product.legalms.modules.dispute.service.tasktransfer.TaskTransferService;
import com.chinatower.product.legalms.modules.dispute.vo.tasktransfer.TFlowTransferVO;
import com.chinatower.product.legalms.modules.dispute.vo.tasktransfer.TaskTransferParamVO;
import com.chinatower.product.legalms.modules.dispute.vo.tasktransfer.TaskTransferResultVO;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 刘晓亮
 * @create 2019-12-25
 */
@Service
public class TaskTransferServiceImpl implements TaskTransferService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("TransLog");
    @Override
    @Transactional
    public String taskTransfer(TaskTransferParamVO taskTransferParamVO) {
        String errorResultCode = "9999";
        String successResultCode = "0000";
        TFlowTransferVO tFlowTransferVO = new TFlowTransferVO();
        if (!StringUtils.isNotBlank(taskTransferParamVO.getSyscode()) || !"CHNTLEGALMS".equals(taskTransferParamVO.getSyscode())) {
            return returnResult(null, getTaskTransferResultVO(errorResultCode, "系统标识不正确", 1),
                    null, null, null, null);
        }
        if (!StringUtils.isNotBlank(taskTransferParamVO.getFromUserCode()) || !StringUtils.isNotBlank(taskTransferParamVO.getToUserCode())) {
            return returnResult(null, getTaskTransferResultVO(errorResultCode, "源用户账号或目标用户账号为空", 1),
                    null, null, null, null);
        }
        tFlowTransferVO.setSyscode(taskTransferParamVO.getSyscode());
        tFlowTransferVO.setFromUserCode(taskTransferParamVO.getFromUserCode());
        tFlowTransferVO.setToUserCode(taskTransferParamVO.getToUserCode());
        tFlowTransferVO.setCompensateFlag(taskTransferParamVO.getCompensateFlag());
        tFlowTransferVO.setTaskId(taskTransferParamVO.getTaskId());
        if (0 == taskTransferParamVO.getCompensateFlag()) { // 业务处理
            if (0 == taskTransferParamVO.getDeliverWay() ) { // 按人转移
                return returnResult(tFlowTransferVO, getTaskTransferResultVO(successResultCode, "按人转移成功", 0),
                        null, null, new Long[]{}, new Long[]{});
            } else { // 按taskId转移
                return returnResult(tFlowTransferVO, getTaskTransferResultVO(successResultCode, "按工作项逐条转移成功", 0),
                        null, null, taskTransferParamVO.getTaskId(), new Long[]{});
            }
        } else if (0 == taskTransferParamVO.getDeliverWay() ) {  // 业务回滚 // 按人转移 回滚
            return returnResult(tFlowTransferVO, getTaskTransferResultVO(successResultCode, "业务数据回滚成功", 0),
                    null, null, taskTransferParamVO.getTaskId(), new Long[]{});
        } else { // 按taskId转移 回滚
            return returnResult(tFlowTransferVO, getTaskTransferResultVO(successResultCode, "业务数据回滚成功", 0),
                    null, null, taskTransferParamVO.getTaskId(), new Long[]{});
        }


    }

    private TaskTransferResultVO getTaskTransferResultVO(String errorResultCode, String resultMsg, int dealResult) {
        TaskTransferResultVO taskTransferResultVO = new TaskTransferResultVO();
        taskTransferResultVO.setResultCode(errorResultCode);
        taskTransferResultVO.setResultMsg(resultMsg);
        taskTransferResultVO.setDealResult(dealResult);
        return taskTransferResultVO;
    }

    /**
     * @Param taskTransferResultVO
     *  resultCode      -------结果标识代码(服务异常返回9999,转移成功为0000)
     *  resultMsg       -------返回处理结果信息
     *  dealResult(int) -------业务转移具体结果标识码(按人转移或者逐条转移全部成功返回0,其余返回1)
     *   data                 -------在按工作项逐条转移失败的时候，需要返回具体处理结果明细
     * @param successTaskId   -------业务转移成功taskId数组(Long类型)
     * @param failTaskId      -------业务转移失败taskId数组(Long类型)
     * @return
     */
    public String returnResult(TFlowTransferVO tFlowTransferVO, TaskTransferResultVO taskTransferResultVO,
                               String fromUserId, String toUserId, Long[] successTaskId, Long[] failTaskId) {
        TaskTransferResultVO.TaskResultData taskResultData = taskTransferResultVO.initTaskResultData();
        taskResultData.setFromUserId(fromUserId);
        taskResultData.setToUserId(toUserId);
        taskResultData.setSuccessTaskId(successTaskId);
        taskResultData.setFailTaskId(failTaskId);
        taskTransferResultVO.setData(taskResultData);
        if (tFlowTransferVO != null) {
            tFlowTransferVO.setResultMsg(taskTransferResultVO.getResultMsg());
            tFlowTransferVO.setResultCode(taskTransferResultVO.getResultCode());
            tFlowTransferVO.setDealResult(taskTransferResultVO.getDealResult());
            tFlowTransferVO.setSuccessTaskId(successTaskId);
            tFlowTransferVO.setFailTaskId(failTaskId);
        }
        logger.info("执行结果" + taskTransferResultVO.toString());
        return JSONObject.toJSONString(taskTransferResultVO);
    }
}
