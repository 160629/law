package com.chinatower.product.legalms.modules.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.BuildEncodeMsg;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.modules.system.entity.AccRoleImpowerVO;
import com.chinatower.product.legalms.modules.system.entity.UserMsg;
import com.chinatower.product.legalms.modules.system.mapper.UserMsgMapper;
import com.chinatower.product.legalms.modules.system.service.UserMsgService;
import com.chinatower.product.legalms.security.AES;
import com.chinatower.provider.call.oauth.OauthServiceClient;
import com.chinatower.provider.call.oauth.QueryServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Auther: G D
 * @Date: 2019/10/18 17:21
 * @Description:
 */
@SuppressWarnings("ALL")
@Service
@Slf4j
public class UserMsgServiceImpl implements UserMsgService {

    private static final Logger logger = LoggerFactory.getLogger("TransLog");


    @Autowired
    private OauthServiceClient oauthServiceClient;

    @Autowired
    private QueryServiceClient queryServiceClient;

    @Autowired
    private UserMsgMapper userMsgMapper;


    /**
     * 功能描述:用户加密信息
     *
     * @param request
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @auther: guodong
     * @date: 2019/12/17 20:30
     */
    @Override
    public ProcessResult reqEncryptUserMsg(HttpServletRequest request) {
        try {
            String loginAccount = request.getHeader("loginAccount");
            return buildUserResult(loginAccount);
        } catch (Exception e) {
            logger.error(SystemInfo.OPERATION_EXCEPTION, e);
            return new ProcessResult(ProcessResult.ERROR, e.toString());
        }
    }

    @Override
    public ProcessResult reqEncryptUserMsgInfo(HttpServletRequest request) {
        try {
            String loginAccount = request.getHeader("loginAccount");
            String passWord = request.getHeader("passWord");
            if(SystemInfo.USER_PASSWORD.equals(passWord)){
                return buildUserResult(loginAccount);
            }
            return new ProcessResult(ProcessResult.ERROR, "验证失败");
        } catch (Exception e) {
            logger.error(SystemInfo.OPERATION_EXCEPTION, e);
            return new ProcessResult(ProcessResult.ERROR, e.toString());
        }
    }

    /**
     * 功能描述:获取加密用户信息（CheckUserTokenAPI）
     *
     * @param token
     * @param loginAccount
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @auther: guo
     * @date: 2019/12/17 20:31
     */
    @Override
    public ProcessResult reqEncryptUserMsg(String token, String loginAccount) {
        try {
            return buildUserResult(loginAccount);
        } catch (Exception e) {
            logger.error(SystemInfo.OPERATION_EXCEPTION, e);
            return new ProcessResult(ProcessResult.ERROR, e.toString());
        }
    }

//    private ProcessResult buildUserResults(String loginAccount, String token) {
//        if (StringUtils.isBlank(token)) {
//            return new ProcessResult(ProcessResult.ERROR, "token为空");
//        }
//        if (StringUtils.isBlank(loginAccount)) {
//            return new ProcessResult(ProcessResult.ERROR, "loginAccount为空");
//        }
//        String encode = BuildEncodeMsg.getQueryEncode(token, loginAccount);
//        ProcessResult result = queryServiceClient.queryLoginUserInfo(encode);
//        if (result.getData() == null) {
//            return new ProcessResult(ProcessResult.ERROR, result.getMess() + "||" + result.getResultStat());
//        }
//        if (StringUtils.isBlank(result.getData().toString())) {
//            return new ProcessResult(ProcessResult.ERROR, "4A接口异常");
//        }
//        String value = result.getData().toString();
//        Map map = JSON.parseObject(value, Map.class);
//        if (map.get("RSP").equals("1")) {
//            return new ProcessResult(ProcessResult.ERROR, map.get("ERRDESC").toString() + "(4A)");
//        }
//        List<Map> userInfo = new ArrayList<>();
//        List<Map> maps = JSON.parseArray(map.get(SystemInfo.USER_IDENTITYS).toString(), Map.class);
//        //根据当前currOrgCode获取指定用户信息
//        /*根据当前登录人loginAccount去查t_pub_account_tab中的accountId*/
//        UserMsg userMsg = userMsgMapper.selectUserInfo(null, null, loginAccount);
//        if (userMsg == null) {
//            return new ProcessResult(ProcessResult.SUCCESS, "无此用户信息");
//        }
//        if (StringUtils.isBlank(userMsg.getOrgCode())) {
//            return new ProcessResult(ProcessResult.SUCCESS, "获取用户信息orgCode失败");
//        }
//        IntStream.range(0, maps.size()).forEach(i -> {
//            if (userMsg.getOrgCode().equals(maps.get(i).get(SystemInfo.USER_DEPTCODE))) {
//                userInfo.add(maps.get(i));
//            }
//        });
//        if (userInfo.isEmpty()) {
//            return new ProcessResult(ProcessResult.ERROR, "数据匹配错误");//从account_tab 表中查出来的orgCode与4a查出来的的deptCode不一致，数据问题
//        }
//        Map mapInfo = userInfo.get(0);
//        /*构建用户信息并返回去重角色集合*/
//        List<Object> roleList = buildUserInfos(userMsg.getOrgCode(), map, mapInfo);
//        //用户信息进行加密并返回
//        String decrypt = AES.encrypt(JSON.toJSONString(mapInfo));
//        //返回组装用户信息
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("decryptUser", decrypt);
//        hashMap.put("roleList", roleList);
//        return new ProcessResult(ProcessResult.SUCCESS, "", hashMap);
//    }

//    private List<Object> buildUserInfos(String currOrgCode, Map map, Map mapInfo) {
//        //组装用户详细信息
//        UserMsg userMsg = userMsgMapper.selectUserInfo(currOrgCode, null, map.get(SystemInfo.USER_LOGINACCT).toString());
//        UserMsg userResult = userMsgMapper.selectUserLevel(userMsg.getUserUnitId());
//        mapInfo.put(SystemInfo.CURRUSER_MOBILE, userMsg.getMobile() == null ? null : userMsg.getMobile());
//        mapInfo.put(SystemInfo.CURRUSER_UNITID, userMsg.getUserUnitId() == null ? null : userMsg.getUserUnitId());
//        mapInfo.put(SystemInfo.CURRUSER_ENTERPTYPE, userMsg.getUserEnterpType() == null ? null : userMsg.getUserEnterpType());
//        mapInfo.put(SystemInfo.CURRUSER_AREAEN, userMsg.getAreaEn() == null ? null : userMsg.getAreaEn());
//        mapInfo.put(SystemInfo.CURRUSER_ORGNAME, userResult.getOrgName() == null ? null : userResult.getOrgName());
//        mapInfo.put(SystemInfo.CURRUSER_ORGLEVEL, userResult.getUserLevel() == null ? null : userResult.getUserLevel());
//        //组装当前用户名称和账号
//        mapInfo.put(SystemInfo.USER_LOGINACCT, map.get(SystemInfo.USER_LOGINACCT));
//        mapInfo.put(SystemInfo.USER_LOGINNAME, map.get(SystemInfo.USER_LOGINNAME));
//        //mapInfo.put(SystemInfo.USER_ISMAIN,map.get(SystemInfo.USER_ISMAIN));
//        //组装当前用户组织信息角色ROLECODE集合
//        List<Object> roleList = new ArrayList<>();
//        //重新组装ROLES ，过滤成法务系统角色
//        List<Map> rolesList = new ArrayList<>();
//        List<Map> list = JSON.parseArray(mapInfo.get(SystemInfo.USER_ROLES).toString(), Map.class);
//        list.forEach(x -> {
//            if (x.size() > 0 && x.get(SystemInfo.USER_ROLECODES).toString().contains(SystemInfo.USER_ROLESIGN)) {
//                Map<String, Object> hashMap = new HashMap<>();
//                roleList.add(x.get(SystemInfo.USER_ROLECODES));
//                hashMap.put(SystemInfo.USER_ROLECODES, x.get(SystemInfo.USER_ROLECODES));
//                hashMap.put(SystemInfo.USER_ROLENAME, x.get(SystemInfo.USER_ROLENAME));
//                if (!rolesList.isEmpty() && roleList.contains(hashMap.get(SystemInfo.USER_ROLECODES))) {
//                    rolesList.remove(hashMap);
//                }
//                rolesList.add(hashMap);
//            }
//        });
//        //角色信息进行去重
//        List<Object> collect = roleList.stream().distinct().collect(Collectors.toList());
//
//        mapInfo.put(SystemInfo.USER_ROLES, rolesList);
//        mapInfo.put(SystemInfo.USER_ROLE_CODE_LIST, collect);
//        return collect;
//    }

    /**
     * 功能描述:组装用户信息
     *
     * @param loginAccount
     * @param token
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @auther: guodong
     * @date: 2019/12/17 20:30
     */
    private ProcessResult buildUserResult(String loginAccount) {
        if (StringUtils.isBlank(loginAccount)) {
            return new ProcessResult(ProcessResult.ERROR, "loginAccount为空");
        }
        //根据当前currOrgCode获取指定用户信息
        /*根据当前登录人loginAccount去查t_pub_account_tab中的accountId*/
        UserMsg userMsg = userMsgMapper.selectUserInfo(null, null, loginAccount);
        if(userMsg==null){
            return new ProcessResult(ProcessResult.ERROR, "无此用户信息");
        }
        userMsg.setUserCode(loginAccount);
        Map mapInfo = new HashMap();
        /*构建用户信息并返回去重角色集合*/
        List<Object> roleList = buildUserInfo(userMsg, mapInfo);
        //用户信息进行加密并返回
        String decrypt = AES.encrypt(JSON.toJSONString(mapInfo));
        //返回组装用户信息
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("decryptUser", decrypt);
        hashMap.put("roleList", roleList);
        return new ProcessResult(ProcessResult.SUCCESS, "", hashMap);
    }


    private List<Object> buildUserInfo(UserMsg userMsg, Map mapInfo) {
        //组装用户详细信息
        //UserMsg userResult = userMsgMapper.selectUserLevel(userMsg.getUserUnitId());
        mapInfo.put(SystemInfo.CURRUSER_MOBILE, userMsg.getMobile() == null ? null : userMsg.getMobile());
        mapInfo.put(SystemInfo.CURRUSER_UNITID, userMsg.getUserUnitId() == null ? null : userMsg.getUserUnitId());
        mapInfo.put(SystemInfo.CURRUSER_ENTERPTYPE, userMsg.getUserEnterpType() == null ? null : userMsg.getUserEnterpType());
        mapInfo.put(SystemInfo.CURRUSER_AREAEN, userMsg.getAreaEn() == null ? null : userMsg.getAreaEn());
        mapInfo.put(SystemInfo.CURRUSER_ORGNAME, userMsg.getParentOrgName() == null ? null : userMsg.getParentOrgName());
        mapInfo.put(SystemInfo.CURRUSER_ORGLEVEL, userMsg.getOrgLevel() == null ? null : userMsg.getOrgLevel());
        mapInfo.put(SystemInfo.USER_DEPTNAME, userMsg.getOrgName() == null ? null : userMsg.getOrgName());
        mapInfo.put(SystemInfo.USER_ROLES, null);
        mapInfo.put(SystemInfo.USER_DEPTCODE, userMsg.getOrgCode());
        if ("03".equals(userMsg.getOrgLevel())) {
        mapInfo.put(SystemInfo.USER_ISJIYUEHUA, userMsg.getIsJiYueHua() == null ? "0" : userMsg.getIsJiYueHua());
        }else {
        mapInfo.put(SystemInfo.USER_ISJIYUEHUA, "share");
        }
        //组装当前用户名称和账号
        mapInfo.put(SystemInfo.USER_LOGINACCT, userMsg.getUserCode());
        mapInfo.put(SystemInfo.USER_LOGINNAME, userMsg.getUserName());
        //重新组装ROLES ，过滤成法务系统角色
        List<AccRoleImpowerVO> result = userMsgMapper.selectAccRoleImpowerByAccountId(userMsg.getUserCode());
        List<Object> collect = result.stream().map(AccRoleImpowerVO::getRoleColeIds).filter(x -> x.contains("CHNTLEGALMS_")).distinct().collect(Collectors.toList());
        mapInfo.put(SystemInfo.USER_ROLE_CODE_LIST, collect);
        return collect;
    }


    @Override
    public ProcessResult selectLoginUserMsg(String loginAcct) {
        if (StringUtils.isBlank(loginAcct)) {
            new ProcessResult(ProcessResult.ERROR, "请设置账号");
        }
        String encode = BuildEncodeMsg.getCreateEncode(loginAcct, "");
        ProcessResult result = oauthServiceClient.createBwdaToken(encode);
        if (result.getData() == null) {
            return new ProcessResult(ProcessResult.ERROR, result.getMess() + "||" + result.getResultStat());
        }
        Map map = JSON.parseObject(result.getData().toString(), Map.class);
        if (map.get("RSP").equals("1")) {
            return new ProcessResult(ProcessResult.ERROR, map.get("ERRDESC").toString() + "(4A)");
        }
        String token = map.get("TOKEN").toString();
        String encodes = BuildEncodeMsg.getQueryEncode(token, loginAcct);
        return queryServiceClient.queryLoginUserInfo(encodes);
    }


}
