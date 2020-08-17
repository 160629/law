package com.chinatower.product.legalms.modules.dispute.service.tasktransfer;

import com.chinatower.product.legalms.modules.dispute.vo.tasktransfer.TFlowTransferVO;
import com.chinatower.product.legalms.modules.dispute.vo.tasktransfer.TaskTransferParamVO;
import com.chinatower.product.legalms.modules.dispute.vo.tasktransfer.TaskTransferResultVO;

/**
 * @author 刘晓亮
 * @create 2019-12-25
 */
public interface TaskTransferService {
    String taskTransfer(TaskTransferParamVO taskTransferParamVO);
    String returnResult(TFlowTransferVO tFlowTransferVO, TaskTransferResultVO taskTransferResultVO, String fromUserId,
                        String toUserId, Long[] successTaskId, Long[] failTaskId);
}
