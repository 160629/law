package com.chinatower.product.legalms.modules.license.mapper;

import com.chinatower.product.legalms.modules.license.entity.jiyuehua.OrgIntensives;
import com.chinatower.product.legalms.modules.license.entity.jiyuehua.OrgIntensivesVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Date: 2020/4/9 15:07
 * @Description:
 */
public interface OrgIntensivesMapper {

    List<OrgIntensivesVO> selectOrgIntensives(OrgIntensives orgIntensives);

    int updateOrgIntensives(OrgIntensives orgIntensives);

    List<OrgIntensivesVO> selectOrgIntensivesByCodeList(@Param("orgCodeList") List<String> orgCodeList);

    void addrgIntensives(OrgIntensives orgIntensives);
}
