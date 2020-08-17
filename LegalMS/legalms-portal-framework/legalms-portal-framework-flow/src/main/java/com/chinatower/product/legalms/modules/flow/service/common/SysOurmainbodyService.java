package com.chinatower.product.legalms.modules.flow.service.common;

import java.util.List;

import com.chinatower.product.legalms.modules.flow.entity.common.SysOurmainbodyVO;
import com.chinatower.product.legalms.modules.flow.vo.common.SysOurmainbodyPage;

public interface SysOurmainbodyService {
    List<SysOurmainbodyVO> selectSysOurmainbodyAll(SysOurmainbodyPage sysOurmainbodyPage);

    List<SysOurmainbodyVO> selectSysOurmainbodyAndConfig(String approveItemId, String approveItemType);
}
