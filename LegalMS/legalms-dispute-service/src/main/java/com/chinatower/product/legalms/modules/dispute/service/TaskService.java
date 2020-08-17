package com.chinatower.product.legalms.modules.dispute.service;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.flow.vo.common.TaskVO;

/**
 * @author 刘晓亮
 * @create 2019-11-05 14:24
 */
public interface TaskService {

    ProcessResult queryPersonWorkItems(TaskVO taskVO);

//    ProcessResult queryPersonTaskCount(String userId);
}
