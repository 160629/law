package com.chinatower.product.legalms.modules.flow.mapper.common;

import java.util.List;
import java.util.Map;

import com.chinatower.product.legalms.modules.flow.entity.common.PbusinessLogVO;
import org.apache.ibatis.annotations.Param;

import com.chinatower.product.legalms.modules.flow.entity.common.BusinessLogVO;

public interface BusinessLogMapper {
    int insertBusinessLog(@Param("businessLogVO")BusinessLogVO businessLogVO);


    List<BusinessLogVO> selectBusinessLog(@Param("businessLogVO") BusinessLogVO businessLogVO);

    List<PbusinessLogVO> selectBusinessLog2(@Param("businessLogVO") BusinessLogVO businessLogVO);

    List<Map<String, Object>> selectFiles(@Param("fiIds") String[] fiIds);

    int updateBusinessLog(@Param("businessLogVO") BusinessLogVO businessLogVO);


}
