package com.chinatower.product.legalms.modules.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.BuildEncodeMsg;
import com.chinatower.product.legalms.modules.system.entity.TaskVO;
import com.chinatower.product.legalms.modules.system.service.CheckUserTokenService;
import com.chinatower.product.legalms.modules.system.service.UserMsgService;
import com.chinatower.provider.call.dispute.DisputeServiceClient;
import com.chinatower.provider.call.oauth.OauthServiceClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Date: 2019/12/17 17:14
 * @Description:
 */
@SuppressWarnings("ALL")
@Service
public class CheckUserTokenServiceImpl implements CheckUserTokenService {

    @Autowired
    private OauthServiceClient serviceClient;

    @Autowired
    private UserMsgService userMsgService;

    @Autowired
    private DisputeServiceClient disputeServiceClient;

    @Override
    public ProcessResult checkToken(@RequestParam("token") String token,
                                    @RequestParam("userId") String userId,
                                    @RequestParam("appAcctId") String appAcctId,
                                    @RequestParam("activityInstId") String activityInstId,
                                    @RequestParam("processInstId") String processInstId,
                                    @RequestParam("moduleName") String moduleName,
                                    @RequestParam("viewId") String viewId,
                                    @RequestParam(value = "mainProcessInstID",required = false) String mainProcessInstID) {

        /*appAcctId主账号；userId主或从*/
        String encode = BuildEncodeMsg.getCheckEncode(token, userId);
        //检验token
        ProcessResult processResult = serviceClient.checkBwdaToken(encode);
        if(processResult.getData()==null){
            return new ProcessResult(ProcessResult.ERROR,processResult.getMess()+"||"+processResult.getResultStat());
        }
        String result = processResult.getData().toString();
        Map map = JSON.parseObject(result, Map.class);
        //校验失败，返回信息
        if (!"0".equals(map.get("RSP"))) {
            return new ProcessResult(ProcessResult.WARN, "检验失败", map.get("ERRDESC").toString());
        }
        //判断主从账号
        if (appAcctId.equals(userId)) {
            Map map1 = getMap(userId, activityInstId, processInstId, token, moduleName,viewId,mainProcessInstID);
            return new ProcessResult(ProcessResult.SUCCESS, "", map1);
        } else {
            String userIds  = null;
            //非主账号，申请token
            if(userId.contains("_")){
                userIds= userId.split("_")[0];
            }else {
                userIds = userId;
            }
            String createEncode = BuildEncodeMsg.getCreateEncode(userIds, "");
            ProcessResult bwdaToken = serviceClient.createBwdaToken(createEncode);
            if(bwdaToken.getData()==null){
                return new ProcessResult(ProcessResult.ERROR,bwdaToken.getMess()+"||"+bwdaToken.getResultStat());
            }
            String result1 = bwdaToken.getData().toString();
            Map map1 = JSON.parseObject(result1, Map.class);
            if (!"0".equals(map1.get("RSP"))) {
                return new ProcessResult(ProcessResult.WARN, "userId参数错误", map1.get("ERRDESC").toString());
            }
            String createtoken = String.valueOf(map1.get("TOKEN"));
            //组装返回信息
            Map map2 = getMap(userId, activityInstId, processInstId, createtoken, moduleName,viewId,mainProcessInstID);
            return new ProcessResult(ProcessResult.SUCCESS, "", map2);
        }
    }

    private Map getMap(String userId, String activityInstId, String processInstId, String createtoken, String moduleName,String viewId,String mainProcessInstID) {
        if(StringUtils.isNotBlank(mainProcessInstID)){
            processInstId = mainProcessInstID;
        }
        ProcessResult userMsg = userMsgService.reqEncryptUserMsg(createtoken, userId);
        String userReuslt = JSON.toJSONString(userMsg.getData());
        Map map = JSON.parseObject(userReuslt, Map.class);
        String pid = map.get("decryptUser").toString();
        /*是否有待阅*/
        if ("unview".equals(moduleName)) {
            map.put("moduleName", moduleName);
            map.put("viewId",viewId);
        } else {
            ProcessResult workItems = disputeServiceClient.queryPersonWorkItems(new TaskVO(processInstId, pid, activityInstId, "1"));
            String jsonString = JSON.toJSONString(workItems.getData());
            Map workItemsMap = JSON.parseObject(jsonString, Map.class);
            map.put("taskList", workItemsMap.get("taskList"));
        }
        return map;
    }
}
