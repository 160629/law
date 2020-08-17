package com.chinatower.product.legalms.modules.system.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: G D
 * @Date: 2019/10/16 16:46
 * @Description:
 */
public interface MsgRedisAPI {

    @PostMapping("/v1/msg/saveMsgByKey")
    public ProcessResult saveMsgByKey(String key,Object value,long expireTime);

    @PostMapping("/v1/msg/saveUserInfo")
    public ProcessResult saveUserInfo(HttpServletRequest httpServletRequest);

    @GetMapping("/v1/msg/selectMsgByKey")
    public ProcessResult selectMsgByKey(String key);

    @DeleteMapping("/v1/msg/deleteMsgByKey")
    public ProcessResult deleteMsgByKey(String key);

    @PutMapping("/v1/msg/updateMsgByKey")
    public ProcessResult updateMsgByKey(String key,Object value,long expireTime);
}
