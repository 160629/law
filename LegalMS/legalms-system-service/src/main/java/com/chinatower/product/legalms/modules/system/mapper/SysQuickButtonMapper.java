package com.chinatower.product.legalms.modules.system.mapper;

import com.chinatower.product.legalms.modules.system.entity.SysQuickButtonPage;
import com.chinatower.product.legalms.modules.system.entity.SysQuickButtonVO;
import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.Map;

public interface SysQuickButtonMapper {

    List<SysQuickButtonVO> selectByRoleCode(@Param("roleCodes") List<String>  roleCodes, @Param("businessType") String businessType, @Param("level") String level, @Param("isJiyuehua")String isJiyuehua);

    List<SysQuickButtonVO> selectByUserCode(@Param("businessType") String businessType, @Param("level") String level, @Param("isJiyuehua")String isJiyuehua, @Param("accountId")String accountId);

    List<SysQuickButtonVO> selectByLevel( @Param("businessType") String businessType, @Param("level") String level, @Param("isJiyuehua")String isJiyuehua);

    List<String> selectAccRoleImpowerByAccountId(String permissonKey);

    List<Map> selectorgLevelByAccountId(String permissonKey);

    List<SysQuickButtonVO> sellectQuickButton(@Param("sysQuickButtonPage") SysQuickButtonPage sysQuickButtonPage);

    int deleteQuickButton(SysQuickButtonVO sysQuickButtonVO);

    int addQuickButton(SysQuickButtonVO sysQuickButtonVO);

    int  updateQuickButton(SysQuickButtonVO sysQuickButtonVO);
}
