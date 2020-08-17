package com.chinatower.product.legalms.modules.flow.mapper.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinatower.product.legalms.modules.flow.entity.common.IdeasVO;

public interface IdeasMapper {

    void insert(IdeasVO ideasVO);

    int update(IdeasVO ideasVO);

    List<IdeasVO> findPage(@Param("ideasVO") IdeasVO ideasVO,@Param("loginAcct")String  loginAcct);

    void delete(IdeasVO ideasVO);
}
