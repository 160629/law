package com.chinatower.product.legalms.modules.flow.service.common;

import java.util.List;
import java.util.Map;

import com.chinatower.product.legalms.modules.flow.entity.common.FlowActivityConfigVO;
import com.chinatower.product.legalms.modules.flow.entity.common.SysQuickButtonVO;
import com.chinatower.product.legalms.modules.flow.vo.common.FlowActivityConfigUpdateVO;
import com.chinatower.product.legalms.modules.flow.vo.common.PageList;

public interface FlowActivityConfigService {

    List<FlowActivityConfigVO> selParamActivityConfig(PageList param);

    List<FlowActivityConfigVO> selActivityConfig(FlowActivityConfigVO flowActivityConfigVO);

    FlowActivityConfigVO selActivityId(String flowId,String beginId,String endId,Integer versionId);

    int selActivityCount(FlowActivityConfigVO flowActivityConfigVO);

    int updateActivityConfig(FlowActivityConfigVO flowActivityConfigVO);

    List<SysQuickButtonVO> selectByRoleCode(SysQuickButtonVO sysQuickButtonVO);

    int addActivityConfig(FlowActivityConfigVO flowActivityConfigVO);

    int delActivityConfig(FlowActivityConfigVO flowActivityConfigVO);

    List<Map> selectorgLevelByAccountId(String permissonKey);

    String judgeFlow(String flowId, String actId,Integer versionId);

    int updateConfig(List<FlowActivityConfigUpdateVO> configUpdateVOs);
}
