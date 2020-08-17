package com.chinatower.product.legalms.modules.license.mapper;

import com.chinatower.product.legalms.modules.license.entity.RoleVo;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


public interface RoleVoMapper extends Mapper<RoleVo> {

    public void addRole(RoleVo roleVo);
    public void updateRole(RoleVo roleVo);
    public Integer delectRoleByRoleCode(String roleCode);
    public Integer selectRoleCountByRoleCode(String roleCode);
    public RoleVo selectRoleByRoleCode(@Param("roleCode") String roleCode);

    List<RoleVo> selectAllRole();

    List<RoleVo> selectUnAllRole(@Param("roleColes") List<String> roleColes);

    List<RoleVo> selectYetAllRole(@Param("roleColes") List<String> roleColes);
}
