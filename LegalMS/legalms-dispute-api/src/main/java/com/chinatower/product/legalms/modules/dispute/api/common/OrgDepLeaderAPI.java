package com.chinatower.product.legalms.modules.dispute.api.common;

import com.chinatower.framework.common_service.response.ProcessResult;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Auther: G D
 * @Date: 2019/11/1 09:40
 * @Description:
 */
public interface OrgDepLeaderAPI {

    /**
     * 功能描述: 查询分管领导信息
     * @auther: guodong
     * @param creatorOrgCode
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @date: 2020/1/3 14:58
     */
    @GetMapping("v1/OrgDepLeader/selectOrgDepLeader")
    public ProcessResult selectOrgDepLeader(String creatorOrgCode);

}
