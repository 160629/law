package com.chinatower.product.legalms.modules.license.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.license.entity.floworgtree.CommenPermission;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Date: 2020/8/4 14:21
 * @Description:
 */
public interface OrgTreePersonAPI {

    /**
     * 功能描述: 公共查人树 1委托代办查当前部门下所有人2案件移交查法务人3案件移交查归属人4组织机构查组织查人
     *
     * @param commenPermission
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @auther: guodong
     * @date: 2019/12/31 17:22
     */
    @PostMapping("/v1/orgleader/selectLeaderTree")
    public ProcessResult selectOrgTreeAndPerson(CommenPermission commenPermission);

}
