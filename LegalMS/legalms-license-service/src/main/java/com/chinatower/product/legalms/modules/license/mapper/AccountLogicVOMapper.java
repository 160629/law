package com.chinatower.product.legalms.modules.license.mapper;

import com.chinatower.product.legalms.modules.license.entity.AccountLogicVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountLogicVOMapper {

    int insert(AccountLogicVO record);

    List<AccountLogicVO> selectBySingleCode(@Param("list") List<String> list);

    AccountLogicVO selectUserInfByAccountId(String userCode);

    List<AccountLogicVO> selectUserInfoByUserCode(String userCode);

    List<AccountLogicVO> selectUserInfoByOrgCode(@Param("orgCode")String orgCode,
                                                 @Param("param")String param,
                                                 @Param("roleCode")String roleCode,
                                                 @Param("accountName")String accountName);

    List<AccountLogicVO> selectCurrOrgTreeByOrgCode(String deptId);

    List<AccountLogicVO> selectUserInfo(AccountLogicVO accountLogicVO);

    void updateUserInfo(AccountLogicVO accountLogicVO);

    int updateUserInfoByAccountId(AccountLogicVO accountVO);
}