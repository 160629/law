package com.chinatower.product.legalms.modules.flow.service.common.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.common.CoreConstClass;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.flow.entity.common.SysOurmainbodyVO;
import com.chinatower.product.legalms.modules.flow.mapper.common.SysOurmainbodyMapper;
import com.chinatower.product.legalms.modules.flow.service.common.SysOurmainbodyService;
import com.chinatower.product.legalms.modules.flow.vo.common.SysOurmainbodyPage;

@Service
public class SysOurmainbodyServiceImpl implements SysOurmainbodyService {

    @Autowired
    private SysOurmainbodyMapper sysOurmainbodyMapper;

    @Override
    public List<SysOurmainbodyVO> selectSysOurmainbodyAll(SysOurmainbodyPage sysOurmainbodyPage) {
        UserInfo userInfo = RequestHolder.getUserInfo();
        String fromType = sysOurmainbodyPage.getFromType();
        if(fromType.equals(CoreConstClass.BODY_RIGHT_LEVEL1)){
            return sysOurmainbodyMapper.selectSysOurmainbodyAll(sysOurmainbodyPage);
        }else if (fromType.equals(CoreConstClass.BODY_RIGHT_LEVEL2)) {
            sysOurmainbodyPage.setEnterpType(userInfo.getOrgLevel());
            return sysOurmainbodyMapper.selectSysOurmainbodyAll(sysOurmainbodyPage);
        }else if (fromType.equals(CoreConstClass.BODY_RIGHT_LEVEL3)){
            sysOurmainbodyPage.setUnitCode(userInfo.getUnitCode());
            return sysOurmainbodyMapper.selectSysOurmainbodyAll(sysOurmainbodyPage);
        }else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<SysOurmainbodyVO> selectSysOurmainbodyAndConfig(String approveItemId, String approveItemType) {
        return sysOurmainbodyMapper.selectSysOurmainbodyAndConfig(approveItemId,approveItemType);
    }

}
