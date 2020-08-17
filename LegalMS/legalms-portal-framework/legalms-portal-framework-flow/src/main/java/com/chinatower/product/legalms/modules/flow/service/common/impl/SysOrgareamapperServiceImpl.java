package com.chinatower.product.legalms.modules.flow.service.common.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.chinatower.product.legalms.modules.flow.entity.common.SysOrgareamapperVO;
import com.chinatower.product.legalms.modules.flow.mapper.common.SysOrgareamapperMapper;
import com.chinatower.product.legalms.modules.flow.service.common.SysOrgareamapperService;

@RestController
public class SysOrgareamapperServiceImpl implements SysOrgareamapperService {
    @Autowired
    private SysOrgareamapperMapper sysOrgareamapperMapper;
    @Override
    public SysOrgareamapperVO selectSysOrgareamapperByorgCode(String orgCode,String areaCode,String areaName) {
        return sysOrgareamapperMapper.selectSysOrgareamapperByorgCode(orgCode,areaCode,areaName);
    }
}
