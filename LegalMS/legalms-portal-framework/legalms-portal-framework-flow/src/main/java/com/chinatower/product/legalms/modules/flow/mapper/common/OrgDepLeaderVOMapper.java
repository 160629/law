package com.chinatower.product.legalms.modules.flow.mapper.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinatower.product.legalms.modules.flow.entity.common.AccountLogic;
import com.chinatower.product.legalms.modules.flow.entity.common.OrgDepLeader;

/**
 * @Auther: G D
 * @Date: 2019/10/31 18:28
 * @Description:
 */
public interface OrgDepLeaderVOMapper {


    List<AccountLogic> selectOrgDepLeader(String creatorOrgCode);

    List<AccountLogic> selectDepLeader(@Param("orgLevel") String orgLevel,
                                       @Param("enterpTye")String enterpTye,
                                       @Param("orgCode")String deptId);

    List<AccountLogic> selectOrgCodeByAccountId(String loginAcct);

    List<OrgDepLeader> selectDeptCodeByAccountId(String accountId);
}
