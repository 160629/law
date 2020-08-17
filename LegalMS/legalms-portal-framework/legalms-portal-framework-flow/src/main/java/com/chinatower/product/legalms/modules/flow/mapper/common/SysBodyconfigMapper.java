package com.chinatower.product.legalms.modules.flow.mapper.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinatower.product.legalms.modules.flow.entity.common.SysBodyconfigVO;

public interface SysBodyconfigMapper {
    List<SysBodyconfigVO> selectAllSysBodyconfig(SysBodyconfigVO sysBodyconfigVO);

    int addSysBodyconfig(@Param("syslist") List<SysBodyconfigVO> list);

    int updateSysBodyconfig(SysBodyconfigVO sysBodyconfigVO);

    int deleteSysBodyconfig(@Param("approveItemId") String approveItemId);

    void deleteSysBodyconfigByIT(@Param("approveItemId")String approveItemId, @Param("approveItemType")String approveItemType);
}
