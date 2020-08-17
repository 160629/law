package com.chinatower.product.legalms.modules.dispute.api;

import org.springframework.web.bind.annotation.PostMapping;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.flow.vo.common.TaskVO;

public interface TaskAPI {
    /**
     * 获取待办，已办，办结列表信息
     * @return
     * @throws Exception
     */
    @PostMapping("/taskList")
    ProcessResult queryPersonWorkItems(TaskVO taskVO);

//    @GetMapping("/taskCount")
//    ProcessResult queryPersonTaskCount(String userId);
}
