package com.chinatower.product.legalms.modules.system.mapper;

import com.chinatower.product.legalms.modules.system.entity.MenuVO;

public interface MenusMapper {
    int selectAllMenus(String menuCode);

    void insertMenus(MenuVO menuVO);
}
