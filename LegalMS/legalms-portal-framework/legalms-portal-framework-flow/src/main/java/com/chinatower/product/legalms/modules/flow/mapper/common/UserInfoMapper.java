package com.chinatower.product.legalms.modules.flow.mapper.common;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinatower.product.legalms.modules.flow.entity.common.AccountLogic;

/**
 * @Date: 2019/11/6 17:51
 * @Description:
 */
public interface UserInfoMapper {

    List<AccountLogic> selectUserByRoleCode(@Param("roleList") List<String> roleList,
                                            @Param("orgCode") String orgCode,
                                            @Param("enterpType") String enterpType,
                                            @Param("orgLevel") String orgLevel);

    List<AccountLogic> selectCopyUserByRoleAndOrgCode(@Param("roleList") List<String> roleList,
                                                      @Param("orgList") List<String> orgList);

    List<AccountLogic> selectUserInfo(@Param("accountIdList") List<String> accountIdList);

    List<AccountLogic> selectUserInfoByOrgAndUserCode(@Param("accountId")String accountId,
                                                      @Param("role")String role);
}
