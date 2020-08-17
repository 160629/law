package com.chinatower.product.legalms.modules.license.service.impl;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.license.entity.AccountLogicVO;
import com.chinatower.product.legalms.modules.license.entity.OrgBeanVO;
import com.chinatower.product.legalms.modules.license.entity.SmsConfigurationVO;
import com.chinatower.product.legalms.modules.license.mapper.AccountLogicVOMapper;
import com.chinatower.product.legalms.modules.license.mapper.OrgBeanVOMappper;
import com.chinatower.product.legalms.modules.license.service.SmsConfigurationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Date: 2020/7/12 13:23
 * @Description:
 */
@Service
public class SmsConfigurationServiceImpl implements SmsConfigurationService {


    @Autowired
    private AccountLogicVOMapper accountLogicVOMapper;

    @Autowired
    private OrgBeanVOMappper orgBeanVOMappper;




    @Override
    public ProcessResult selectUserInfo(SmsConfigurationVO smsConfigurationVO) {
        UserInfo userInfo = RequestHolder.getUserInfo();
        String orgCode = smsConfigurationVO.getOrgCode();
        //递归查询该节点下所有orgCode
        if (StringUtils.isNotBlank(orgCode)) {
            List<String> collect = new ArrayList<>();
            List<OrgBeanVO> orgCodeList = orgBeanVOMappper.selectOrgCodeListByOrgCode(orgCode);
            if(!orgCodeList.isEmpty()){
                List<String> orgCodeResult = orgCodeList.stream().map(OrgBeanVO::getOrgCode).collect(Collectors.toList());
                orgCodeResult.add(orgCode);//包含本节点
                List<OrgBeanVO>   result = orgBeanVOMappper.selectOrgInfoList(orgCodeResult);
                collect = result.stream().map(OrgBeanVO::getOrgCode).collect(Collectors.toList());
            }else {
                collect.addAll(Arrays.asList(orgCode.split(",")));
            }
            smsConfigurationVO.setOrgCodeList(collect);
        }
        PageHelper.startPage(smsConfigurationVO.getPageNum(), smsConfigurationVO.getPageSize());
        //判断是否为超级管理员
        List<Object> superAdmin = userInfo.getRoleCodeList().stream().filter(x -> String.valueOf(x).equals("CHNTLEGALMS_0")).collect(Collectors.toList());
        if (!superAdmin.isEmpty()) {
            String superRole = superAdmin.get(0).toString();//超级管理员角色
            smsConfigurationVO.setSuperRole(superRole);
        }
        //判断是否为系统管理员
        List<Object> admin = userInfo.getRoleCodeList().stream().filter(x -> String.valueOf(x).equals("CHNTLEGALMS_7")).collect(Collectors.toList());
        if (admin.isEmpty() && superAdmin.isEmpty()) {
            return new ProcessResult(ProcessResult.ERROR, "无系统管理员权限");
        }
        smsConfigurationVO.setOrgCode(userInfo.getDeptId());
        smsConfigurationVO.setOrgLevel(userInfo.getOrgLevel());
        smsConfigurationVO.setEnterpType(RequestHolder.getenterpType(userInfo.getUnitCode()));
        List<AccountLogicVO> list =  accountLogicVOMapper.selectUserInfo(smsConfigurationVO);
        PageInfo<AccountLogicVO> pageInfo = new PageInfo<>(list);
        return new ProcessResult(ProcessResult.SUCCESS, "", pageInfo);
    }

    @Override
    public ProcessResult updateUserInfo(SmsConfigurationVO smsConfigurationVO) {
        List<String> accountIdList = smsConfigurationVO.getAccountIdList();
        if(accountIdList.isEmpty()){
            return new ProcessResult(ProcessResult.ERROR,"设置人员不能为空");
        }
        if(StringUtils.isBlank(smsConfigurationVO.getAccountPhoneStatus())){
            return new ProcessResult(ProcessResult.ERROR,"设置手机开关不能为空");
        }
        accountLogicVOMapper.updateUserInfo(smsConfigurationVO);
        return new ProcessResult(ProcessResult.SUCCESS, "设置成功");
    }
}
