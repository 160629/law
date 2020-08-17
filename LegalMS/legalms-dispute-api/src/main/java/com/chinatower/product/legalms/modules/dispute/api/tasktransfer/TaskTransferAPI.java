package com.chinatower.product.legalms.modules.dispute.api.tasktransfer;

import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author 刘晓亮
 * @create 2019-12-19
 * 4A移交待办接口
 */
public interface TaskTransferAPI {

    @PostMapping("/taskTransfer")
    String taskTransfer(String param);
}
