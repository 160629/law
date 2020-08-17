package com.chinatower.product.legalms.modules.flow.mapper.common;

import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.flow.vo.flow.BusinessLogConfigVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BusinessLogConfigMapper {
    List<String> selectBusinessLogConfig(@Param("approveItemId") String approveItemId, @Param("defName") String defName);

    List<BusinessLogConfigVO> selectAll();

    int addConfig(@Param("businessLogConfigVO") BusinessLogConfigVO businessLogConfigVO);

    int delConfig(@Param("businessLogConfigVOS") List<BusinessLogConfigVO> businessLogConfigVOS);

    int updateConfig(@Param("businessLogConfigVOS") List<BusinessLogConfigVO> businessLogConfigVOS);

    List<BusinessLogConfigVO> selectConfigActs(@Param("processDefName") String processDefName, @Param("versionId") Integer versionId);

    int selectBusinessConfigByIds(@Param("activityDefId") String activityDefId, @Param("approveItemId") String approveItemId, @Param("deptId") String deptId);

    void updateBusinessLog(UserInfo info, Map<String, Object> businessMap);
}
