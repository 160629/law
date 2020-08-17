package com.chinatower.product.legalms.modules.license.mapper;

import com.chinatower.product.legalms.modules.license.entity.AdmVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdmMapper {


    int selectAllAdmVO(@Param("admCode") String admCode);

    void addAdmVO(AdmVO admVO);

    void updateAdmVO(AdmVO admVO);

    List<AdmVO> selectAdmByAdmLevelAndParentAdmCode(AdmVO admVO);
//    List<AdmVO> selectAdmByAdmLevelAndParentAdmCode(Integer admLevel);
}
