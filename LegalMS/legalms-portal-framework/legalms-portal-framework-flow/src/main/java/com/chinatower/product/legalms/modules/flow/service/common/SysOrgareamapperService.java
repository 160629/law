package com.chinatower.product.legalms.modules.flow.service.common;

import com.chinatower.product.legalms.modules.flow.entity.common.SysOrgareamapperVO;

public interface SysOrgareamapperService  {

    SysOrgareamapperVO selectSysOrgareamapperByorgCode(String orgCode,String areaCode,String areaName) ;
}
