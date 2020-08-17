package com.chinatower.product.legalms.modules.flow.service.common.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinatower.product.legalms.modules.flow.entity.common.AccountLogic;
import com.chinatower.product.legalms.modules.flow.entity.common.OrgDepLeader;
import com.chinatower.product.legalms.modules.flow.mapper.common.OrgDepLeaderVOMapper;
import com.chinatower.product.legalms.modules.flow.service.common.OrgDepLeaderService;

/**
 * @Auther: G D
 * @Date: 2019/10/31 16:17
 * @Description:
 */
@SuppressWarnings("ALL")
@Service
public class OrgDepLeaderServiceImpl implements OrgDepLeaderService {
    @Autowired
    private OrgDepLeaderVOMapper orgDepLeaderVOMapper;

    @Override
    public List<AccountLogic> selectOrgDepLeader(String creatorOrgCode) {

        return orgDepLeaderVOMapper.selectOrgDepLeader(creatorOrgCode);
    }

    @Override
    public List<OrgDepLeader> selectDeptCodeByAccountId(String accountId) {
        return orgDepLeaderVOMapper.selectDeptCodeByAccountId(accountId);
    }
}
