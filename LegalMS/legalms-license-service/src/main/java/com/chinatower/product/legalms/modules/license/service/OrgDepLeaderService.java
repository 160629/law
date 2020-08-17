package com.chinatower.product.legalms.modules.license.service;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.license.entity.depleader.OrgDepLeader;

import java.util.List;

/**
 * @Date: 2019/11/18 11:56
 * @Description:
 */
public interface OrgDepLeaderService {

    public ProcessResult selectOrgDepLeader(OrgDepLeader orgDepLeader);

    public ProcessResult updateLeaderInfo(OrgDepLeader orgDepLeader);

    public ProcessResult deleteOrgDepLeader(OrgDepLeader orgDepLeader);

    public ProcessResult selectOrgTree();

    public ProcessResult selectLeaderTree(OrgDepLeader orgDepLeader);

//    public ProcessResult selectLeaderTree(CommenPermission commenPermission);

    List<OrgDepLeader> selectDeptCodeByAccountId(String loginAcct);

    ProcessResult initLeaderData();
}
