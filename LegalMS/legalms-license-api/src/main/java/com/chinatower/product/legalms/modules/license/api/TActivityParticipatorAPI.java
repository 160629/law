package com.chinatower.product.legalms.modules.license.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @Date: 2020/8/4 15:45
 * @Description:
 */
public interface TActivityParticipatorAPI {

    /**
     * 功能描述:查询下一步参与者
     *
     * @param map
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @auther: guodong
     * @date: 2020/1/3 14:35
     */
    @PostMapping("/v1/accountlogic/selectOrgTree")
    public ProcessResult selectOrgTree(Map map);
}
