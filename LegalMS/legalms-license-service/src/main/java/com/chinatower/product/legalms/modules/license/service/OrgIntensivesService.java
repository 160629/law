package com.chinatower.product.legalms.modules.license.service;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.license.entity.jiyuehua.OrgIntensives;


/**
 * @Date: 2020/4/9 15:04
 * @Description:
 */
public interface OrgIntensivesService {
    ProcessResult selectOrgIntensives(OrgIntensives orgIntensives);

    ProcessResult updateOrgIntensives(OrgIntensives orgIntensives);

}
