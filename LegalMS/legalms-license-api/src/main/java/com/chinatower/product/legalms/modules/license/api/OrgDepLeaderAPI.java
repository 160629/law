package com.chinatower.product.legalms.modules.license.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.license.entity.depleader.OrgDepLeader;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @Date: 2019/11/18 11:41
 * @Description:
 */
public interface OrgDepLeaderAPI {

    /**
     * 功能描述:查询分管领导
     *
     * @param orgDepLeader
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @auther: guodong
     * @date: 2020/1/3 14:36
     */
    @PostMapping("/v1/orgdepleader/selectOrgDepLeader")
    public ProcessResult selectOrgDepLeader(OrgDepLeader orgDepLeader);

    /**
     * 功能描述: 清空分管领导信息
     *
     * @param orgDepLeader
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @auther: guodong
     * @date: 2020/1/3 14:36
     */
    @DeleteMapping("/v1/orgdepleader/deleteOrgDepLeader")
    public ProcessResult deleteOrgDepLeader(OrgDepLeader orgDepLeader);

    /**
     * 功能描述: 设置分管领导信息
     *
     * @param orgDepLeader
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @auther: guodong
     * @date: 2020/1/3 14:36
     */
    @PutMapping("/v1/orgdepleader/updateOrgDepLeader")
    public ProcessResult updateOrgDepLeader(OrgDepLeader orgDepLeader);

    /**
     * 功能描述: 查询公司组织树
     *
     * @param
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @auther: guodong
     * @date: 2020/1/3 14:36
     */
    @PostMapping("/v1/orgdepleader/selectOrgTree")
    public ProcessResult selectOrgTree();

    /**
     * 功能描述: 查询公司领导
     *
     * @param orgDepLeader
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @auther: guodong
     * @date: 2020/1/3 14:36
     */
    @PostMapping("/v1/orgdepleader/selectLeaderTree")
    public ProcessResult selectLeaderTree(OrgDepLeader orgDepLeader);

    @PostMapping("/initLeaderData")
    public ProcessResult initLeaderData();
}
