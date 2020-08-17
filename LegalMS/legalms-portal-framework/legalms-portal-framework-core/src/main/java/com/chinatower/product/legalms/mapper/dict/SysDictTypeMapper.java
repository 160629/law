package com.chinatower.product.legalms.mapper.dict;

import com.chinatower.product.legalms.vo.dict.SysDictTypePage;
import com.chinatower.product.legalms.vo.dict.SysDictTypeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDictTypeMapper {
    List<SysDictTypeVO> selectDictTypeAll(SysDictTypePage sysDictTypePage);

    int addDictType(SysDictTypeVO sysDictTypeVO);

    int updateDictType(SysDictTypeVO sysDictTypeVO);

    int selectDictTypeByName(@Param("dictName") String dictName);

    int deleteDictType(@Param("dictId")int dictId);

    int selectDictTypeByOne(@Param("dictType")String dictType, @Param("dictName")String dictName);

    SysDictTypeVO selectDictTypeById(@Param("dictId") int dictId);
}
