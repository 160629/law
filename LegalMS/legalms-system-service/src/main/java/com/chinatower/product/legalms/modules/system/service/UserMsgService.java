package com.chinatower.product.legalms.modules.system.service;

import com.chinatower.framework.common_service.response.ProcessResult;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: G D
 * @Date: 2019/10/18 17:18
 * @Description:
 */
public interface UserMsgService {

    public ProcessResult reqEncryptUserMsg(HttpServletRequest request);

    public ProcessResult reqEncryptUserMsg(String token, String loginAccount);

    public ProcessResult selectLoginUserMsg(String loginAcct);

    public ProcessResult reqEncryptUserMsgInfo(HttpServletRequest request);
}
