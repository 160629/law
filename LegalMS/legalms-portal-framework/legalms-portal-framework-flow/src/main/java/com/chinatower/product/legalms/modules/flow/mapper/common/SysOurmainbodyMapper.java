package com.chinatower.product.legalms.modules.flow.mapper.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinatower.product.legalms.modules.flow.entity.common.SysOurmainbodyVO;
import com.chinatower.product.legalms.modules.flow.vo.common.SysOurmainbodyPage;

public interface SysOurmainbodyMapper {
    List<SysOurmainbodyVO> selectSysOurmainbodyAll(SysOurmainbodyPage sysOurmainbodyPage);

    List<SysOurmainbodyVO> selectSysOurmainbodyAndConfig(@Param("approveItemId") String approveItemId,@Param("approveItemType")  String approveItemType);
}
