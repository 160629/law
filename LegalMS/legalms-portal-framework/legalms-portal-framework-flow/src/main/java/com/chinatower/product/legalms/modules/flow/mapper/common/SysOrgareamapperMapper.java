package com.chinatower.product.legalms.modules.flow.mapper.common;

import com.chinatower.product.legalms.modules.flow.entity.common.SysOrgareamapperVO;

public interface SysOrgareamapperMapper {
    SysOrgareamapperVO selectSysOrgareamapperByorgCode(String orgCode,String areaCode,String areaName);
}
