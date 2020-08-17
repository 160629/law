package com.chinatower.product.legalms.modules.system.mapper;


import com.chinatower.product.legalms.modules.system.entity.TSysCourtPage;
import com.chinatower.product.legalms.modules.system.entity.TSysCourtVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author shiyuqi
 * @date
 */
public interface TSysCourtMapper {

    List<TSysCourtVO> selectTSysCourt(@Param("tSysCourtPage")TSysCourtPage tSysCourtPage);

    int deleteTSysCourt(@Param("tSysCourtVO")TSysCourtVO tSysCourtVO);

    int addTSysCourt(TSysCourtVO tSysCourtVO);

    int updateTSysCourt(@Param("tSysCourtVO")TSysCourtVO tSysCourtVO);

    List<TSysCourtVO> selectTSysCourtName(@Param("tSysCourtPage")TSysCourtPage tSysCourtPage);

    TSysCourtVO selectTSysCourtOne(@Param("id")String id);

    List<TSysCourtVO> selectTSysCourtTree();

    List<TSysCourtVO> selectTSysCourtTree1(String pid);

    List<TSysCourtVO> selectAll(@Param("tSysCourtVO")TSysCourtVO tSysCourtVO);

    TSysCourtVO selectAll1(@Param("tSysCourtVO")TSysCourtVO tSysCourtVO);

    List<TSysCourtVO> selectTSysCourtList(@Param("id")String id);
}
