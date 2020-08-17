package com.chinatower.product.legalms.modules.license.service.impl;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.modules.license.entity.AccountLogicVO;
import com.chinatower.product.legalms.modules.license.entity.AccountVO;
import com.chinatower.product.legalms.modules.license.entity.OrgBeanVO;
import com.chinatower.product.legalms.modules.license.mapper.AccountLogicVOMapper;
import com.chinatower.product.legalms.modules.license.mapper.AccountVOMapper;
import com.chinatower.product.legalms.modules.license.mapper.OrgBeanVOMappper;
import com.chinatower.product.legalms.modules.license.service.AccountLogicService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: G D
 * @Date: 2019/10/12 10:52
 * @Description:
 */
@SuppressWarnings("ALL")
@Service
@Transactional(rollbackFor = Exception.class)
public class AccountLogicServiceImpl implements AccountLogicService {


    @Autowired
    private AccountLogicVOMapper accountLogicVOMapper;

    @Autowired
    private OrgBeanVOMappper orgBeanVOMappper;

    @Autowired
    private AccountVOMapper accountVOMapper;


    @Override
    public ProcessResult selectUserInfByAccountId(AccountLogicVO accountVO) {
        AccountVO result = accountVOMapper.selectUserInfoByAccountId(accountVO.getAccountId());
        result.setFlag(RequestHolder.getUserStatus(result.getFlag()));
        return new ProcessResult(ProcessResult.SUCCESS, "", result);
    }

    @Override
    public ProcessResult selectUserInfoByUserCode(AccountLogicVO accountVO) {
        List<AccountLogicVO> result = accountLogicVOMapper.selectUserInfoByUserCode(accountVO.getAccountCurUserId());
        return new ProcessResult(ProcessResult.SUCCESS, "", result);
    }

    @Override
    public ProcessResult selectUserInfoByOrgCode(AccountLogicVO accountVO) {
        PageHelper.startPage(accountVO.getPageNum(), accountVO.getPageSize());
        String roleCode = null;
        if (accountVO.getRoleCode() != null) {
            roleCode = accountVO.getRoleCode();
        }
        List<AccountLogicVO> result = accountLogicVOMapper.selectUserInfoByOrgCode(accountVO.getAccountOrgId(), accountVO.getTableType(), roleCode, accountVO.getAccountName());
        result.forEach(x -> x.setAccountStatus(RequestHolder.getUserStatus(x.getAccountStatus())));
        PageInfo<AccountLogicVO> pageInfo = new PageInfo<>(result);
        return new ProcessResult(ProcessResult.SUCCESS, "查询成功", pageInfo);
    }

    @Override
    public ProcessResult updateUserInfoByAccountId(AccountLogicVO accountVO) {
        if (StringUtils.isBlank(accountVO.getAccountOrgId()) || StringUtils.isBlank(accountVO.getAccountId())) {
            return new ProcessResult(ProcessResult.ERROR, "参数错误");
        }
        List<OrgBeanVO> orgBeanVOS = orgBeanVOMappper.selectOrgInfoByCode(accountVO.getAccountOrgId());
        if (orgBeanVOS.get(0).getOrgLevel().equals("04")) {
            String unitType = RequestHolder.getOrgType(orgBeanVOS.get(0).getEnterpType());
            accountVO.setAccountRemark(unitType + "-" + orgBeanVOS.get(0).getCompanyName() + "-" + orgBeanVOS.get(0).getOrgName());
            accountVO.setAccountUnitId(RequestHolder.getUnitCode(orgBeanVOS.get(0).getOrgCode()));
            accountLogicVOMapper.updateUserInfoByAccountId(accountVO);
        }
        return new ProcessResult(ProcessResult.SUCCESS, "更新成功");
    }

}
