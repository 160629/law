package com.chinatower.product.legalms.modules.license.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.license.entity.OrgBeanVO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

public interface OrgBeanAPI {

    @GetMapping("/v1/orgbean/selectOrgbean")
    public ProcessResult selectOrgbean(Integer pageNum, Integer pageSize);

    @PostMapping("/v1/orgbean/addOrgbean")
    public ProcessResult addOrgbean(OrgBeanVO orgBeanVO);

    @PutMapping("/v1/orgbean/updateOrgbean")
    public ProcessResult updateOrgbean(OrgBeanVO orgBeanVO);

    @DeleteMapping("/v1/orgbean/deleteOrgbean")
    public ProcessResult deleteOrgbean(OrgBeanVO orgBeanVO);

    @GetMapping("/v1/orgbean/selectOrgbeanByAreaName")
    public ProcessResult selectOrgbeanByAreaName();

    /**
     * 功能描述：查询指定组织信息
     * @auther: guodong
     * @param orgBeanVO
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @date: 2019/12/31 17:17
     */
    @PostMapping("/v1/orgbean/selectOrgInfoByorgCode")
    public ProcessResult selectOrgInfoByorgCode(OrgBeanVO orgBeanVO);

    /**
     * 功能描述:查询指定下级组织信息
     * @auther: guodong
     * @param orgBeanVO
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @date: 2019/12/31 17:17
     */
    @PostMapping("/v1/orgbean/selectSubordinateOrgInfoByorgCode")
    public ProcessResult selectSubordinateOrgInfoByorgCode(OrgBeanVO orgBeanVO);
}
