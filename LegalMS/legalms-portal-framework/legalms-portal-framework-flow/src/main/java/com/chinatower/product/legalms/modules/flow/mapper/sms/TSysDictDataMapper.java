package com.chinatower.product.legalms.modules.flow.mapper.sms;

import java.util.List;

import com.chinatower.product.legalms.modules.flow.entity.sms.TSysDictData;

public interface TSysDictDataMapper {
    int deleteByPrimaryKey(String dictCode);

    int insert(TSysDictData record);

    int insertSelective(TSysDictData record);

    TSysDictData selectByPrimaryKey(String dictCode);
    
    List<TSysDictData> selectByType(String dictType,String status,String dictValue);
    
    int updateByPrimaryKeySelective(TSysDictData record);

    int updateByPrimaryKey(TSysDictData record);
}