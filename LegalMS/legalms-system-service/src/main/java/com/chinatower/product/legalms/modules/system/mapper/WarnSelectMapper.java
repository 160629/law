package com.chinatower.product.legalms.modules.system.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface WarnSelectMapper {
	List<Map<String,Object>> selectBySql(@Param("sql") String sql);

	int updateBySql(@Param("sql") String sql);

	int deleteBySql(@Param("sql") String sql);

	int insertBySql(@Param("sql") String sql);
}
