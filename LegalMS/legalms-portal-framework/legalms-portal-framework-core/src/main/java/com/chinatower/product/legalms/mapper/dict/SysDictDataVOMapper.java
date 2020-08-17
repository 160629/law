package com.chinatower.product.legalms.mapper.dict;

import com.chinatower.product.legalms.vo.dict.SysDictDataVO;
import feign.Param;

import java.util.List;

public interface SysDictDataVOMapper {

    List<SysDictDataVO> selectdictById(@Param("dictId") Integer dictId);

    List<SysDictDataVO> selectdictByType(@Param("dictType")String dictType);

    List<String> selectdictByTypeNotSort(@Param("dictType")String dictType);

    List<SysDictDataVO> selectSysDictData();

    int deleteSysDictData(SysDictDataVO sysDictDataVO);

    int updateSysDictData(SysDictDataVO sysDictDataVO);

    int addSysDictData(SysDictDataVO sysDictDataVO);

}
