package com.chinatower.product.legalms.modules.license.mapper;

import com.chinatower.product.legalms.modules.license.entity.AccountVO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AccountVOMapper extends Mapper<AccountVO> {


    AccountVO selectUserInfoByAccountId(String accountId);

    public void addAccount(AccountVO accountVO);

    public void updateAccount(AccountVO accountVO);

    public void deleteAccount(AccountVO accountVO);

    public AccountVO selectAccountByCode(AccountVO accountVO);

    public List<AccountVO> selectByStatus(String str);

    public void updateAccountBySyncStatus(@Param("status")String status, @Param("userCodeList") List<String> userCodeList);

    List<AccountVO> selectByUnStatus(String dealEd);

    void updateOrgCodeByUserCode(String userCode);

    void updateServiceCodeByAccoungId(@Param("userCode")String userCode,
                                      @Param("providerArea")String providerArea);
}
