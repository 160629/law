package com.chinatower.product.legalms.modules.flow.service.common.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.flow.entity.common.AccountLogic;
import com.chinatower.product.legalms.modules.flow.mapper.common.UserInfoMapper;
import com.chinatower.product.legalms.modules.flow.service.common.UserInfoService;

/**
 * @Date: 2019/11/6 17:48
 * @Description:
 */
@SuppressWarnings("ALL")
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;


    /**
     * 功能描述:发送待阅人员查询
     *
     * @param roleList
     * @param userInfo
     * @param orgLevel
     * @return java.util.List<com.chinatower.product.legalms.modules.flowpath.entity.common.AccountLogic>
     * @auther: guodong
     * @date: 2020/3/17 9:42
     */
    @Override
    public List<AccountLogic> selectUserByRoleCode(List<String> roleList, UserInfo userInfo, String orgLevel) {
        /*01 总部查人 02 省份查人  */
        String unitCode = userInfo.getUnitCode();
        String enterpType = RequestHolder.getenterpType(unitCode);
        List<AccountLogic> list = null;
        if (orgLevel.equals("01")) {

            list = userInfoMapper.selectUserByRoleCode(roleList, null, enterpType, orgLevel);
        }
        if (orgLevel.equals("02")) {
            String provinceCode = RequestHolder.getProvinceCode(userInfo.getDeptId());
            list = userInfoMapper.selectUserByRoleCode(roleList, provinceCode, enterpType, orgLevel);
        }
        return list;
    }
    /**
     * 功能描述:案件交办抄送部门待阅人查询
     * @auther: guodong
     * @param roleList
     * @param orgList
     * @return java.util.List<com.chinatower.product.legalms.modules.flowpath.entity.common.AccountLogic>
     * @date: 2020/3/18 17:55
     */
    @Override
    public List<AccountLogic> selectCopyUserByRoleAndOrgCode(List<String> roleList, List<String> orgList) {
        return userInfoMapper.selectCopyUserByRoleAndOrgCode(roleList,orgList);
    }
    /**
     * 功能描述:查询指定账号的用户信息
     * @auther: guodong
     * @param accountId
     * @return com.chinatower.product.legalms.modules.flow.entity.common.AccountLogic
     * @date: 2020/6/30 14:45
     */
    @Override
    public List<AccountLogic> selectUserInfo(List<String> accountIdList){
        if(accountIdList.isEmpty()){
            return new ArrayList<>();
        }
        return userInfoMapper.selectUserInfo(accountIdList);

    }

    /**
     * 功能描述:根据起草人查部门负责人
     * @auther: guodong
     * @param accountId
     * @return java.util.List<com.chinatower.product.legalms.modules.flow.entity.common.AccountLogic>
     * @date: 2020/7/15 10:07
     */
    @Override
    public List<AccountLogic> selectUserInfoByOrgAndUserCode(String accountId,String role) {

        return userInfoMapper.selectUserInfoByOrgAndUserCode(accountId,role);
    }

    @Override
	public AccountLogic selectUserInfo(String accountId) {
		List<String>  list = new ArrayList<>();
		list.add(accountId);
		List<AccountLogic> selectUserInfo = userInfoMapper.selectUserInfo(list);
		if(null==selectUserInfo || selectUserInfo.isEmpty()) {
			return null;
		}
		return selectUserInfo.get(0);
	}
}
