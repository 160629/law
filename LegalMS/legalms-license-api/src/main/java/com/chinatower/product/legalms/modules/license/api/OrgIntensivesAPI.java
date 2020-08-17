package com.chinatower.product.legalms.modules.license.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.license.entity.jiyuehua.OrgIntensives;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Date: 2020/4/9 14:56
 * @Description:
 */
public interface OrgIntensivesAPI {


    /**
     * 功能描述:集约化查询
     *
     * @param
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @auther: guodong
     * @date: 2020/4/9 14:58
     */
    @PostMapping("/v1/orgintensives/selectOrgIntensives")
    public ProcessResult selectOrgIntensives(OrgIntensives orgIntensives);

    @PostMapping("/v1/orgintensives/updateOrgIntensives")
    public ProcessResult updateOrgIntensives(OrgIntensives orgIntensives);


}
