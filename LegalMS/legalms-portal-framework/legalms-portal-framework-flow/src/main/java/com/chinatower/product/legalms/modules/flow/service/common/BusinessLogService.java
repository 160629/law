package com.chinatower.product.legalms.modules.flow.service.common;

import java.util.List;
import java.util.Map;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.flow.entity.common.BusinessLogVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.BusinessLogConfigVO;

public interface BusinessLogService {

    int insertBusinessLog(UserInfo info, Map<String,Object> map);

    /**
     * 法律支撑，纠纷处理，法律文书转办，案件协办
     * 纠纷处理意见查询接口
     * 案件协办，法律文书转办
     * 执行意见
     * @param businessLogVO
     * @return
     */
    List<BusinessLogVO> selectBusinessLog(BusinessLogVO businessLogVO);

    List<BusinessLogVO> selectBusinessLog2(BusinessLogVO businessLogVO);

    List<String> selectBusinessLogConfig(String approveItemId, String defName);

    ProcessResult selectAll();

    ProcessResult addConfig(BusinessLogConfigVO businessLogConfigVO);

    ProcessResult delConfig(List<BusinessLogConfigVO> businessLogConfigVOS);

    ProcessResult updateConfig(List<BusinessLogConfigVO> businessLogConfigVOS);

    List<BusinessLogConfigVO> selectConfigActs(String processDefName, Integer versionId);

    int selectBusinessConfigByIds(String activityDefId, String approveItemId, String deptId);


}
