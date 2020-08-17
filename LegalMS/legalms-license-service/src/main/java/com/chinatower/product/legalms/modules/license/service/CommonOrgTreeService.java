package com.chinatower.product.legalms.modules.license.service;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.license.entity.floworgtree.CommenPermission;

/**
 * @Date: 2020/7/21 15:00
 * @Description:
 */
public interface CommonOrgTreeService {


    ProcessResult selectUserOrgTreeInfo(CommenPermission commenPermission);

    ProcessResult selectUserOrgTreeInfoByCase(CommenPermission commenPermission);

}
