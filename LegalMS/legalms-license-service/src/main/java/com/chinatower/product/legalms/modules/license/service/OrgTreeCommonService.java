package com.chinatower.product.legalms.modules.license.service;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.license.entity.floworgtree.CommenPermission;

/**
 * @Date: 2019/11/27 14:38
 * @Description:
 */
public interface OrgTreeCommonService {
    ProcessResult orgTreeCommonService(CommenPermission commenPermission);
}
