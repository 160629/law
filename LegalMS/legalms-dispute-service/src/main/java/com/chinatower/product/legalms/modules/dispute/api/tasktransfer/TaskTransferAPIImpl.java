package com.chinatower.product.legalms.modules.dispute.api.tasktransfer;

import com.alibaba.fastjson.JSONObject;
import com.chinatower.product.legalms.modules.dispute.service.tasktransfer.TaskTransferService;
import com.chinatower.product.legalms.modules.dispute.vo.tasktransfer.TaskTransferParamVO;

import com.chinatower.product.legalms.modules.dispute.vo.tasktransfer.TaskTransferResultVO;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author 刘晓亮
 * @create 2019-12-19
 */
@RestController
public class TaskTransferAPIImpl implements com.chinatower.product.legalms.modules.dispute.api.tasktransfer.TaskTransferAPI {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("TransLog");
    @Autowired
    TaskTransferService taskTransferService;
    @Override
    /**
     * 4A待办转移接口
     */
    public String taskTransfer(String param) {
        TaskTransferParamVO taskTransferParamVO = null;
        try {
            logger.info("4A待办移交，jsonc参数：" + param);
            String requestInfo = JSONObject.parseObject(param).toJSONString();
            taskTransferParamVO = JSONObject.parseObject(requestInfo, TaskTransferParamVO.class);
        } catch (Exception e) {
            TaskTransferResultVO taskTransferResultVO = new TaskTransferResultVO();
            taskTransferResultVO.setResultCode("9999");
            taskTransferResultVO.setResultMsg("参数解析错误");
            taskTransferResultVO.setDealResult(1);
            return taskTransferService.returnResult(null, taskTransferResultVO,
                    null, null, null, null);
        }
        return taskTransferService.taskTransfer(taskTransferParamVO);
    }


}

