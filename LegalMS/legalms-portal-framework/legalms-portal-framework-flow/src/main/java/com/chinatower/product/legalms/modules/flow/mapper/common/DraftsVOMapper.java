package com.chinatower.product.legalms.modules.flow.mapper.common;

import java.util.List;

import com.chinatower.product.legalms.modules.flow.entity.common.DraftsVO;

public interface DraftsVOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DraftsVO record);

    int insertSelective(DraftsVO record);

    DraftsVO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DraftsVO record);

    int updateDrafts(DraftsVO record);

    List<DraftsVO> selectDrafts (DraftsVO draftsVO);

    int deleteBatchDrafts(DraftsVO draftsVO);

    int selectDraftsCount(DraftsVO draftsVO);

    int delDraftsByItem(DraftsVO draftsVO);

    int isExist(DraftsVO record);
}