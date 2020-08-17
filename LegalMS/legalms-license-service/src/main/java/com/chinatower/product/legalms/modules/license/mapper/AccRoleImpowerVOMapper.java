package com.chinatower.product.legalms.modules.license.mapper;

import com.chinatower.product.legalms.modules.license.entity.AccRoleImpowerVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface AccRoleImpowerVOMapper {

    public List<AccRoleImpowerVO> selectBySingleCode(@Param("role") List<String> role,
                                                     @Param("org")List<String> org,
                                                     @Param("unit")List<String> unit);

    List<AccRoleImpowerVO>  selectAccRoleImpowerByCode(AccRoleImpowerVO accRoleImpowerVO);

    public void deleteAccRoleImpower(AccRoleImpowerVO accRoleImpowerVO);

    public void addAccRoleImpower(AccRoleImpowerVO accRoleImpowerVO);

    List<AccRoleImpowerVO> selectAccRoleImpowerByAccountId(String accountId);

    List<AccRoleImpowerVO> selectSepLawInfo(@Param("role") List<String> roleList,@Param("proCode") String proCode);
}
