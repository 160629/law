package com.chinatower.product.legalms.modules.flow.service.common;


import java.util.List;

import com.chinatower.product.legalms.modules.flow.entity.common.AccountLogic;
import com.chinatower.product.legalms.modules.flow.entity.common.OrgDepLeader;

/**
 * @Auther: G D
 * @Date: 2019/10/31 16:17
 * @Description:
 */
public interface OrgDepLeaderService {

    List<AccountLogic> selectOrgDepLeader(String creatorOrgCode);

    List<OrgDepLeader> selectDeptCodeByAccountId(String accountId);

}
