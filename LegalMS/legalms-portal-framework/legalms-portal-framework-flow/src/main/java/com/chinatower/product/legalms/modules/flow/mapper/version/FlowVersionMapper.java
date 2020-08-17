package com.chinatower.product.legalms.modules.flow.mapper.version;

import com.chinatower.product.legalms.modules.flow.entity.common.FlowActivityConfigVO;
import com.chinatower.product.legalms.modules.flow.entity.common.FlowActivityPowerVO;
import com.chinatower.product.legalms.modules.flow.entity.version.FlowVersion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FlowVersionMapper {
    int insertRecord(@Param("flowVersion") FlowVersion flowVersion);

    int updateRecord(@Param("flowVersion") FlowVersion flowVersion);

    List<FlowVersion> selectAllRecords();

    FlowVersion selectRecordById(@Param("flowVersion") FlowVersion flowVersion);

    List<FlowActivityPowerVO> selectFromPowerByFlowId(String flowId);

    List<FlowActivityConfigVO> selectFromConfigByFlowId(String flowId);

    int insertPowers(@Param("flowActivityPowerVOS") List<FlowActivityPowerVO> flowActivityPowerVOS);

    int insertConfigs(@Param("flowActivityConfigVOS") List<FlowActivityConfigVO> flowActivityConfigVOS);

    FlowVersion selectLatestVersion(String flowId);
}
