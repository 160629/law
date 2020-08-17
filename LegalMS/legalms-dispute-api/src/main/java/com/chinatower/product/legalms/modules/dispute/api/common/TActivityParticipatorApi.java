package com.chinatower.product.legalms.modules.dispute.api.common;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.dispute.entity.CommonParam;
import org.springframework.web.bind.annotation.PostMapping;


public interface TActivityParticipatorApi {



    /**
     * 功能描述:获取流程下一步参与者
     * @auther: guodong
     * @param commonParam
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @date: 2020/1/3 14:58
     */
    @PostMapping("/v1/activity/selectParticipatorByCode")
    public ProcessResult selectParticipatorByCode (CommonParam commonParam);


}

