package com.chinatower.product.legalms.modules.flow.service.common.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinatower.product.legalms.modules.flow.entity.common.SysBodyconfigVO;
import com.chinatower.product.legalms.modules.flow.mapper.common.SysBodyconfigMapper;
import com.chinatower.product.legalms.modules.flow.service.common.SysBodyconfigService;

@Service
public class SysBodyconfigServiceImpl implements SysBodyconfigService {

    @Autowired
    private SysBodyconfigMapper sysBodyconfigMapper;

    @Override
    public List<SysBodyconfigVO> selectAllSysBodyconfig(SysBodyconfigVO sysBodyconfigVO) {
        return sysBodyconfigMapper.selectAllSysBodyconfig(sysBodyconfigVO);
    }

    @Override
    public int addSysBodyconfig(List<SysBodyconfigVO> list) {

        sysBodyconfigMapper.deleteSysBodyconfig(list.get(0).getApproveItemId());
        return sysBodyconfigMapper.addSysBodyconfig(list);
    }

    @Override
    public int addSysBody(String ourLawsuitBody,String approveItemId,String approveItemType){
        sysBodyconfigMapper.deleteSysBodyconfigByIT(approveItemId,approveItemType);
//        UserInfo userInfo = RequestHolder.getUserInfo();
//        String unitCode = userInfo.getUnitCode();
//        String[] unitCodes = unitCode.split(",");
        String[] bodyCodes = ourLawsuitBody.split(",");
        List<SysBodyconfigVO> list=new ArrayList<>();
        for (int i=0;i<bodyCodes.length;i++){
            SysBodyconfigVO sysBodyconfigVO = new SysBodyconfigVO();
            sysBodyconfigVO.setBodyCode(bodyCodes[i]);
//            sysBodyconfigVO.setUnitCode(unitCode);
            sysBodyconfigVO.setApproveItemId(approveItemId);
            sysBodyconfigVO.setApproveItemType(approveItemType);
            list.add(sysBodyconfigVO);
        }

        return sysBodyconfigMapper.addSysBodyconfig(list);
    }

    @Override
    public int updateSysBodyconfig(SysBodyconfigVO sysBodyconfigVO) {
        return sysBodyconfigMapper.updateSysBodyconfig(sysBodyconfigVO);
    }

    @Override
    public int deleteSysBodyconfig(String approveItemId) {
        return sysBodyconfigMapper.deleteSysBodyconfig(approveItemId);
    }
}
