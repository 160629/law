package com.chinatower.product.legalms.modules.license.service.impl;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.license.entity.OrgBeanVO;
import com.chinatower.product.legalms.modules.license.entity.jiyuehua.OrgIntensives;
import com.chinatower.product.legalms.modules.license.entity.jiyuehua.OrgIntensivesVO;
import com.chinatower.product.legalms.modules.license.mapper.OrgBeanVOMappper;
import com.chinatower.product.legalms.modules.license.mapper.OrgIntensivesMapper;
import com.chinatower.product.legalms.modules.license.service.OrgIntensivesService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Date: 2020/4/9 15:04
 * @Description:
 */
@SuppressWarnings("ALL")
@Service
@Transactional(rollbackFor = Exception.class)
public class OrgIntensivesServiceImpl implements OrgIntensivesService {



    @Autowired
    private OrgIntensivesMapper orgIntensivesMapper;

    @Autowired
    private OrgBeanVOMappper orgBeanVOMappper;


    @Override
    public ProcessResult selectOrgIntensives(OrgIntensives orgIntensives) {
        UserInfo userInfo = RequestHolder.getUserInfo();
        String orgCode = orgIntensives.getOrgCode();
        //递归查询该节点下所有orgCode
        if (StringUtils.isNotBlank(orgCode)) {
            List<OrgBeanVO> orgCodeList = orgBeanVOMappper.selectOrgCodeListByOrgCode(orgCode);
            List<OrgBeanVO> collect = orgCodeList.stream().filter(x -> !x.getOrgCode().endsWith("0001")).filter(x -> !x.getOrgCode().endsWith("100001")).collect(Collectors.toList());
            List<OrgBeanVO> result = orgBeanVOMappper.selectOrgInfoLists(collect.stream().map(OrgBeanVO::getOrgCode).collect(Collectors.toList()));
            List<String> list = result.stream().map(OrgBeanVO::getOrgCode).collect(Collectors.toList());
            orgIntensives.setOrgCodeList(list.isEmpty()?Arrays.asList(orgCode):list);
        }
        PageHelper.startPage(orgIntensives.getPageNum(), orgIntensives.getPageSize());
        orgIntensives.setCurrOrgCode(userInfo.getDeptId());
        orgIntensives.setOrgLevel(userInfo.getOrgLevel());
        //判断是否为超级管理员
        List<Object> superAdmin = userInfo.getRoleCodeList().stream().filter(x -> String.valueOf(x).equals("CHNTLEGALMS_0")).collect(Collectors.toList());
        if (!superAdmin.isEmpty()) {
            String superRole = superAdmin.get(0).toString();//超级管理员角色
            orgIntensives.setSuperRole(superRole);
        }
        //判断是否为系统管理员
        List<Object> admin = userInfo.getRoleCodeList().stream().filter(x -> String.valueOf(x).equals("CHNTLEGALMS_7")).collect(Collectors.toList());
        if (admin.isEmpty() && superAdmin.isEmpty()) {
            return new ProcessResult(ProcessResult.ERROR, "无系统管理员权限");
        }
        orgIntensives.setEnterpType(RequestHolder.getenterpType(userInfo.getUnitCode()));
        List<OrgIntensivesVO> list = orgIntensivesMapper.selectOrgIntensives(orgIntensives);
        //List<OrgIntensivesVO> collect = list.stream().filter(x -> !x.getOrgCode().endsWith("0001")).filter(x -> !x.getOrgCode().endsWith("100001")).collect(Collectors.toList());
        PageInfo<OrgIntensivesVO> pageInfo = new PageInfo<>(list);
        return new ProcessResult(ProcessResult.SUCCESS, "", pageInfo);
    }

    @Override
    public ProcessResult updateOrgIntensives(OrgIntensives orgIntensives) {
        List<String> orgCodeList = orgIntensives.getOrgCodeList();
        if(!orgCodeList.isEmpty()){
            //判断orgCode是否存在
            List<OrgIntensivesVO> list = orgIntensivesMapper.selectOrgIntensivesByCodeList(orgIntensives.getOrgCodeList());
            //筛选不存在的orgCode
            List<String> collect = list.stream().map(OrgIntensivesVO::getOrgCode).collect(Collectors.toList());
            if(!list.isEmpty()&&list.size()<orgCodeList.size()){
                List<String> newList = new ArrayList<>();
                for (int i = 0; i < orgCodeList.size(); i++) {
                    buildList(orgCodeList, collect, newList, i);
                }
                if(!newList.isEmpty()){
                    orgIntensives.setOrgCodeList(newList);
                    orgIntensivesMapper.addrgIntensives(orgIntensives);
                    orgIntensives.setOrgCodeList(collect);
                    orgIntensivesMapper.updateOrgIntensives(orgIntensives);
                }
            }else if(list.isEmpty()){
                orgIntensivesMapper.addrgIntensives(orgIntensives);
            }else{
                orgIntensivesMapper.updateOrgIntensives(orgIntensives);
            }
        }
        return new ProcessResult(ProcessResult.SUCCESS,"设置成功");
    }

    private void buildList(List<String> list, List<String> collect, List<String> newList, int i) {
        if(!collect.contains(list.get(i))){
            newList.add(list.get(i));
        }
    }
}
