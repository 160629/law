package com.chinatower.product.legalms.modules.license.mapper;

import com.chinatower.product.legalms.modules.license.entity.ListParam;
import com.chinatower.product.legalms.modules.license.entity.MenuVO;
import com.chinatower.product.legalms.modules.license.entity.RoleMenuVO;
import com.chinatower.product.legalms.modules.license.entity.RoleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMenuVOMapper {
    String selectRoleMenuCount(RoleMenuVO roleMenuVO);
    void insertRoleMenu(RoleMenuVO roleMenuVO);
    Integer updataRoleMenu(RoleMenuVO roleMenuVO);


    Integer delectRolMenueByRoleCode(@Param("roleCode") String roleCode, @Param("menulist")List<String> menulist);

    RoleMenuVO selectRoleMenuByRoleCode(@Param("roleCode")String roleCode);

    List<RoleVo> selectRole(@Param("listParam") ListParam listParam);

    List<RoleVo> selectMenu(@Param("menuVO") MenuVO menuVO);

    List<MenuVO> selectRoleMenu(@Param("roleVo") RoleVo roleVo);

    List<RoleMenuVO> selectMenuCodeByrRole(@Param("roleCode") String roleCode,@Param("menuStrArr") List<String> menuStrArr);

    Integer delectRolMenueByMenuCode(@Param("menuCode") String menuCode);
}
