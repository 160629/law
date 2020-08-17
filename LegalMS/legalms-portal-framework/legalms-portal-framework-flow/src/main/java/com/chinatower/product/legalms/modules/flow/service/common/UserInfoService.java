package com.chinatower.product.legalms.modules.flow.service.common;

import java.util.List;

import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.flow.entity.common.AccountLogic;

public interface UserInfoService {


	public List<AccountLogic> selectUserByRoleCode(List<String> roleList, UserInfo userInfo, String orgLevel);

	public List<AccountLogic> selectCopyUserByRoleAndOrgCode(List<String> roleList, List<String> orgList);

	public List<AccountLogic> selectUserInfo(List<String> accountIdList);

	public List<AccountLogic> selectUserInfoByOrgAndUserCode(String accountId ,String role);
	
	public AccountLogic selectUserInfo(String accountId);
}
