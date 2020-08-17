package com.chinatower.product.legalms.modules.license.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.license.entity.floworgtree.CommenPermission;
import org.springframework.web.bind.annotation.PostMapping;

public interface OrgTreeCommonAPI {

    /**
     * 功能描述: 依据各流程查询组织或者公司Tree展示
     *
     * @param commenPermission
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @auther: guodong
     * @date: 2020/1/3 14:35
     */
    @PostMapping("/v1/floworg/selectOrgTree")
    public ProcessResult selectOrgTree(CommenPermission commenPermission);

}
