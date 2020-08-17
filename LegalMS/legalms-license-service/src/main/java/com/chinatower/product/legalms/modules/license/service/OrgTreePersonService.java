package com.chinatower.product.legalms.modules.license.service;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.license.entity.floworgtree.CommenPermission;

/**
 * @Date: 2020/8/4 14:34
 * @Description:
 */
public interface OrgTreePersonService {

    ProcessResult selectOrgTreeAndPerson(CommenPermission commenPermission);


}
