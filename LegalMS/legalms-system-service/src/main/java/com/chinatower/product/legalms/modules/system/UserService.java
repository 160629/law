package com.chinatower.product.legalms.modules.system;

import com.alibaba.fastjson.JSON;
import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.InterfaceLogUtil;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.modules.system.api.UserAPIYYY;
import com.chinatower.product.legalms.modules.system.entity.UserVO;
import com.chinatower.product.legalms.modules.system.mapper.UserVOMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Slf4j
public class UserService implements UserAPIYYY {
	
	 private static final Logger logger = LoggerFactory.getLogger("TransLog");
	
	@Autowired
	private UserVOMapper userVOMapper;
	@Override
	public ProcessResult findUser(@RequestParam("pageNum")Integer pageNum,@RequestParam("pageSize") Integer pageSize) {
		 PageHelper.startPage(pageNum, pageSize,"user_id desc");
	     List <UserVO> userList= userVOMapper.selectAllUser();
	     PageInfo<UserVO> pageInfo = new PageInfo<>(userList);
	     return new ProcessResult(ProcessResult.SUCCESS,"",pageInfo);
	}
	
	@Override
	public ProcessResult addUser(@RequestBody UserVO user) {
		logger.info(InterfaceLogUtil.reqTransLog("请求参数为:"+JSON.toJSONString(user)));
		int a;
		try {
			a = userVOMapper.insert(user);
			logger.info(InterfaceLogUtil.rspTransLog("业务处理结果为:"+a));
			return new ProcessResult(ProcessResult.SUCCESS,"",a);
		} catch (Exception e) {
			logger.error(SystemInfo.OPERATION_EXCEPTION,e);
			return new ProcessResult(ProcessResult.ERROR,e.getMessage());
		}
		
	}

	//更新公司名称简称
	@GetMapping("/updateAreaEN")
	public  ProcessResult updateAreaEN(){
		List<String> list = userVOMapper.selectOrgCode();
		list.forEach(x->userVOMapper.updateAreaEN(x));
		return new ProcessResult(ProcessResult.SUCCESS,"初始AreaEN成功");
	}
}
