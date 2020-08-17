package com.chinatower.product.legalms.modules.flow.service.common.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.common.StringUtil;
import com.chinatower.product.legalms.modules.flow.entity.common.OrgBeanVO;
import com.chinatower.product.legalms.modules.flow.mapper.common.OrgBeanMapper;
import com.chinatower.product.legalms.modules.flow.service.common.OrgBeanService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Date: 2019/12/2 20:27
 * @Description:
 */
@SuppressWarnings("ALL")
@Service
@Transactional(rollbackFor = Exception.class)
public class OrgBeanServiceImpl implements OrgBeanService {

    @Autowired
    private OrgBeanMapper orgBeanMapper;

    public OrgBeanVO selectProOrgInfoByOrgCode(String deptId){
        OrgBeanVO orgBeanVO = orgBeanMapper.selectOrgInfoByOrgCode(deptId);
        String orgName = orgBeanVO.getOrgName();
        if(orgName.contains("分")){
            orgBeanVO.setOrgName(orgName.substring(0,orgName.indexOf('分')));
        }
        return orgBeanVO;
    }

    public OrgBeanVO selectOrgInfoByOrgCode(String deptId){
        return orgBeanMapper.selectOrgInfoByOrgCode(deptId);
    }

    @Override
    public ProcessResult selectOrgLevelByorgCode(OrgBeanVO orgBeanVO) {
        OrgBeanVO result = orgBeanMapper.selectOrgLevelByorgCode(orgBeanVO);
        return new ProcessResult(ProcessResult.SUCCESS, "", result);
    }

    @Override
    public List<String> selectOrgListByOrgCode(String orgCode) {
        List<OrgBeanVO> list =  orgBeanMapper.selectOrgListByOrgCode(orgCode);
        return list.stream().map(OrgBeanVO::getOrgCode).collect(Collectors.toList());
    }

    @Override
    public ProcessResult selectProvincePathByCode() {
        String deptId = RequestHolder.getDeptId();
        if (StringUtil.isEmpty(deptId)) {
            return new ProcessResult(ProcessResult.WARN, "当前人code为空");
        }
        OrgBeanVO result = orgBeanMapper.selectProvincePathByCode(getProvinceCode(deptId));
        return new ProcessResult(ProcessResult.SUCCESS, "", result);
    }
    
    private static String getProvinceCode(String orgCode) {
        String provinceCode = "";
        if (!StringUtil.isEmpty(orgCode)&&orgCode.length()>2) {
            if (("02").equals(orgCode.substring(0, 2)) || ("03").equals(orgCode.substring(0, 2))) {
                provinceCode = orgCode.substring(0, 4).concat("0001");
            } else {
                provinceCode = orgCode.substring(0, 2).concat("0001");
            }
            return provinceCode;
        }
        return provinceCode;
    }
}
