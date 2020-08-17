package com.chinatower.product.legalms.modules.system.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.system.entity.UserVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

public interface UserAPIYYY {

	
	@GetMapping("/user/findUser")
    public ProcessResult findUser(Integer pageNum, Integer pageSize);
	
	@PostMapping("/user/save")
    public ProcessResult addUser(UserVO user);

}
