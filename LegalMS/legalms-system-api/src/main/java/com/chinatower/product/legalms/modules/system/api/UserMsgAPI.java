package com.chinatower.product.legalms.modules.system.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: G D
 * @Date: 2019/10/18 17:10
 * @Description:
 */
public interface UserMsgAPI {

    /**
     * 功能描述: 加密用户信息
     * @auther: guodong
     * @param request
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @date: 2020/1/3 14:46
     */
    @GetMapping("v1/user/reqEncryptUserMsg")
    public ProcessResult reqEncryptUserMsg(HttpServletRequest request);

    /**
     * 功能描述:登录接口
     * @auther: guodong
     * @param request
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @date: 2020/6/9 15:24
     */
    @GetMapping("v1/user/reqEncryptUserMsgInfo")
    public ProcessResult reqEncryptUserMsgInfo(HttpServletRequest request);

    /**
     * 功能描述: 根据用户账号查询用户详细信息
     * @auther: guodong
     * @param loginAcct
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @date: 2020/1/3 14:46
     */
    @PostMapping("v1/user/selectLoginUserMsg")
    public ProcessResult selectLoginUserMsg(String loginAcct);
}
