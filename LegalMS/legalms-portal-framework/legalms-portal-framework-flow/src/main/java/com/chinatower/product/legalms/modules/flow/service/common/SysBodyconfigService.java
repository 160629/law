package com.chinatower.product.legalms.modules.flow.service.common;

import java.util.List;

import com.chinatower.product.legalms.modules.flow.entity.common.SysBodyconfigVO;


public interface SysBodyconfigService {
    List<SysBodyconfigVO> selectAllSysBodyconfig(SysBodyconfigVO sysBodyconfigVO);

    int addSysBodyconfig(List<SysBodyconfigVO> list);

    int updateSysBodyconfig(SysBodyconfigVO sysBodyconfigVO);

    int deleteSysBodyconfig(String approveItemId);

    int addSysBody(String ourLawsuitBody,String approveItemId,String approveItemType);

}
