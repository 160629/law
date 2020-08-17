package com.chinatower.product.legalms.modules.license.mapper;

import com.chinatower.product.legalms.modules.license.entity.MenuVO;
import feign.Param;


public interface MenuVOMapper {
    public Integer selectMenuCountByMenuCode(String menuCode);
    public void addMenu(MenuVO menuVO);
    public void updateMenu(MenuVO menuVO);
    public Integer delectMenuByMenuCode(String menuCode);
    public MenuVO selectMenuByMenuCode(@Param("menuCode") String menuCode);
}
