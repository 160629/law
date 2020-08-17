package com.chinatower.product.legalms.modules.organization.service.impl;

import com.chinatower.product.legalms.common.OrgBeanVO;
import com.chinatower.product.legalms.modules.organization.mapper.OrgBeanCommonMappper;
import com.chinatower.product.legalms.modules.organization.service.OrgBeanCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Date: 2020/6/10 15:20
 * @Description:
 */
@SuppressWarnings("ALL")
@Service
@Transactional(rollbackFor = Exception.class)
public class OrgBeanCommonServiceImpl implements OrgBeanCommonService {


    @Autowired
    private OrgBeanCommonMappper orgBeanCommonMappper;

    @Override
    public List<String> selectOrgInfoByOrgCode(String orgCode) {
        return orgBeanCommonMappper.selectOrgInfoByOrgCode(orgCode).stream().map(OrgBeanVO::getOrgCode).collect(Collectors.toList());
    }

    @Override
    public List<String> selectOrgInfoLimitByOrgCode(String orgCode) {
        return orgBeanCommonMappper.selectOrgInfoLimitByOrgCode(orgCode).stream().map(OrgBeanVO::getOrgCode).collect(Collectors.toList());

    }

    @Override
    public List<String> selectOrgListByOrgCode(String orgCode) {
       List<OrgBeanVO> list =  orgBeanCommonMappper.selectOrgListByOrgCode(orgCode);
        return list.stream().map(OrgBeanVO::getOrgCode).collect(Collectors.toList());
    }

    @Override
    public List<String> selectCompanyCodeListBySpeCode(List<String> orgCodeList) {
        List<OrgBeanVO> list =  orgBeanCommonMappper.selectCompanyCodeListBySpeCode(orgCodeList);
        return list.stream().map(OrgBeanVO::getCompanyCode).collect(Collectors.toList());
    }
}
