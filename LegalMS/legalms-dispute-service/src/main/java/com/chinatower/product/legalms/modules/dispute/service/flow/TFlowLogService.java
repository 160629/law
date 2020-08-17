package com.chinatower.product.legalms.modules.dispute.service.flow;

import java.util.List;
import java.util.Map;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowLog;
import com.chinatower.product.legalms.modules.flow.vo.common.TaskVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowLogVO;

/**
 * <p>
 * 审批人员表 服务类
 * </p>
 *
 * @author wangyong
 * @since 2019-09-30
 */
public interface TFlowLogService {

	List<TFlowLog> selectAll();

    List<TFlowLog> selectMainFlowLog(String businessId, String businessType);

	List<String> selectSubFlowIds(Long flowId);

	TFlowLog selectByPrimaryKey(String subProcessFlowLogId);

    ProcessResult nullifyFlow(Map<String, Object> businessMap);

	ProcessResult tempSelective(AddFlowLogVO addFlowLogVO);

    ProcessResult selectCompleteFlow(TaskVO taskVO);

	ProcessResult flowDrawBack(Long activityInstId);
}
