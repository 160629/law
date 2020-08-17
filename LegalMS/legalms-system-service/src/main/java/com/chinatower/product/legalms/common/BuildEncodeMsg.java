package com.chinatower.product.legalms.common;

import com.alibaba.fastjson.JSONObject;
import com.chinatower.framework.cloud.feignlog.InfoFeginLog;
import org.slf4j.MDC;

/**
 * @Date: 2019/12/17 14:38
 * @Description:
 */
public class BuildEncodeMsg {

    private BuildEncodeMsg() {
        //feel
        super();
    }

    public static final String SERVICEID = "SERVICEID";

    public static final String CHNTLEGALMS = "CHNTLEGALMS";

    public static final String TOKEN = "TOKEN";

    public static final String SERVERCODE_VAL = "40.4007";

    public static final String SENDCODE_VAL = "60.6006";
    /**
     * 功能描述:查询用户码
     *
     * @param token
     * @param loginAccount
     * @return java.lang.String
     * @auther: guodong
     * @date: 2019/12/17 15:28
     */

    public static String getQueryEncode(String token, String loginAccount) {
        JSONObject json = new JSONObject();
        json.put("LOGINACCOUNT", loginAccount);
        json.put(SERVICEID, CHNTLEGALMS);
        json.put(TOKEN, token);
        /** SENDCODE  SERVERCODE*/
        String encode = ThreeDESUtil.encode(json.toJSONString());
        /** mdc put的是esb给的码值,sendcode是发送方,servercode是服务提供方*/
        MDC.put(InfoFeginLog.LOG_SENDCODE, SENDCODE_VAL);
        MDC.put(InfoFeginLog.LOG_SERVERCODE, SERVERCODE_VAL);
        return encode;
    }

    /**
     * 功能描述:查询菜单码
     *
     * @param loginAccount
     * @param token
     * @param menucode
     * @return java.lang.String
     * @auther: guodong
     * @date: 2019/12/17 15:41
     */
    public static String getButtonsEncode(String loginAccount, String token, String menucode) {
        JSONObject json = new JSONObject();
        json.put("LOGINACCOUNT", loginAccount);
        json.put(SERVICEID, CHNTLEGALMS);
        json.put(TOKEN, token);
        json.put("MENUCODE", menucode);
        /** SENDCODE  SERVERCODE*/
        String encode = ThreeDESUtil.encode(json.toJSONString());
        /** mdc put的是esb给的码值,sendcode是发送方,servercode是服务提供方*/
        MDC.put(InfoFeginLog.LOG_SENDCODE, SENDCODE_VAL);
        MDC.put(InfoFeginLog.LOG_SERVERCODE, SERVERCODE_VAL);
        return encode;
    }


    /**
     * 功能描述:凭证校验码
     *
     * @param token
     * @param loginAccount
     * @return java.lang.String
     * @auther: guodong
     * @date: 2019/12/17 15:28
     */
    public static String getCheckEncode(String token, String loginAccount) {
        JSONObject json = new JSONObject();
        json.put("LOGINNAME", loginAccount);
        json.put(SERVICEID, CHNTLEGALMS);
        json.put(TOKEN, token);
        /** SENDCODE  SERVERCODE*/
        String encode = ThreeDESUtil.encode(json.toJSONString());
        /** mdc put的是esb给的码值,sendcode是发送方,servercode是服务提供方*/
        MDC.put(InfoFeginLog.LOG_SENDCODE, SENDCODE_VAL);
        MDC.put(InfoFeginLog.LOG_SERVERCODE, SERVERCODE_VAL);
        return encode;
    }

    /**
     * 功能描述:凭证申请码
     *
     * @param loginAcct
     * @param accessIpAddr
     * @return java.lang.String
     * @auther: guodong
     * @date: 2019/12/17 15:34
     */
    public static String getCreateEncode(String loginAcct, String accessIpAddr) {
        JSONObject json = new JSONObject();
        json.put("LOGINACCT", loginAcct);
        json.put(SERVICEID, CHNTLEGALMS);
        json.put("ACCESSIPADDR", accessIpAddr);
        /** SENDCODE  SERVERCODE*/
        String encode = ThreeDESUtil.encode(json.toJSONString());
        /** mdc put的是esb给的码值,sendcode是发送方,servercode是服务提供方*/
        MDC.put(InfoFeginLog.LOG_SENDCODE, SENDCODE_VAL);
        MDC.put(InfoFeginLog.LOG_SERVERCODE, SERVERCODE_VAL);
        return encode;
    }


}
