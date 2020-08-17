package com.chinatower.product.legalms.modules.flow.mapper.common;

import java.util.List;
import java.util.Map;

import com.chinatower.product.legalms.modules.flow.vo.common.FlowActivityConfigUpdateVO;
import org.apache.ibatis.annotations.Param;

import com.chinatower.product.legalms.modules.flow.entity.common.FlowActivityConfigVO;
import com.chinatower.product.legalms.modules.flow.entity.common.SysQuickButtonVO;
import com.chinatower.product.legalms.modules.flow.vo.common.PageList;

public interface FlowActivityConfigMapper {
    List<FlowActivityConfigVO> selParamActivityConfig(PageList param);

    List<FlowActivityConfigVO> selAllActivityConfig(FlowActivityConfigVO flowActivityConfigVO);

    FlowActivityConfigVO selActivityId(@Param("flowId") String flowId,@Param("beginId") String beginId,@Param("endId") String endId,@Param("versionId") Integer versionId);

    int selAllActivityCount(FlowActivityConfigVO flowActivityConfigVO);

    int updateActivityConfig(FlowActivityConfigVO flowActivityConfigVO);

    List<SysQuickButtonVO> selectByRoleCode(SysQuickButtonVO sysQuickButtonVO);

    int addActivityConfig(FlowActivityConfigVO flowActivityConfigVO);

    int delActivityConfig(FlowActivityConfigVO flowActivityConfigVO);

    List<Map> selectorgLevelByAccountId(String permissonKey);

    int updateConfig(@Param("configUpdateVOs") List<FlowActivityConfigUpdateVO> configUpdateVOs);
}
