package com.chinatower.product.legalms.modules.system.mapper;


import com.chinatower.product.legalms.modules.system.entity.UserVO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


public interface UserVOMapper extends Mapper<UserVO>{
	
	List<UserVO> selectAllUser();

	void updateAreaEN(String orgcode);

	List<String> selectOrgCode();

}