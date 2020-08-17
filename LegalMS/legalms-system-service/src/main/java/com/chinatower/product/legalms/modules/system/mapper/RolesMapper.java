package com.chinatower.product.legalms.modules.system.mapper;

import com.chinatower.product.legalms.modules.system.entity.RoleVo;
import org.apache.ibatis.annotations.Param;

public interface RolesMapper {
    int selectAllRole(@Param("roleCode") String roleCode);

    void insertroles(RoleVo roleVo);
}
