package com.chinatower.product.legalms;


import com.alibaba.fastjson.JSON;
import com.chinatower.product.legalms.common.SystemTag;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.exception.MyOwnRuntimeException;
import com.chinatower.product.legalms.security.AES;
import com.chinatower.product.legalms.security.SecConst;
import com.chinatower.product.legalms.security.SecurityClient;
import com.github.pagehelper.util.StringUtil;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by duanzj on 2018/9/12.
 */
public final class RequestHolder {
    /**
     * 获取当前线程的Request对象
     */
    public static final ThreadLocal<ServletRequest> requestLocal = new ThreadLocal<>();

    /**
     * 获取当前线程的Response对象
     */
    public static final ThreadLocal<ServletResponse> responseLocal = new ThreadLocal<>();

    public static HttpServletRequest getRequest() {

        return (HttpServletRequest) requestLocal.get();
    }

    public static HttpServletResponse getResponse() {
        return (HttpServletResponse) responseLocal.get();
    }


    /**
     * 功能描述:对用户信息进行解密
     *
     * @param
     * @return java.lang.String
     * @auther: G D
     * @date: 2019/10/28 12:33
     */
    public static Object getUserMsg() {
        String result = AES.decrypt(getPid());
        return JSON.parseObject(result);
    }

    public static Object getUserMsg(String pid) {
        String result = AES.decrypt(pid);
        return JSON.parseObject(result);
    }

    /**
     * 功能描述: 获取当前账号
     *
     * @param
     * @return java.lang.String
     * @auther: G D
     * @date: 2019/10/30 11:24
     */
    public static String getLoginAcct() {
        String userMsg = getUserMsg().toString();
        Map map = JSON.parseObject(userMsg, Map.class);
        return map.get(SecConst.HEADER.USER_LOGINACCT).toString();
    }

    /**
     * 功能描述: 获取账号名字
     *
     * @param
     * @return java.lang.String
     * @auther: G D
     * @date: 2019/10/30 11:25
     */
    public static String getLoginName() {
        String userMsg = getUserMsg().toString();
        Map map = JSON.parseObject(userMsg, Map.class);
        return map.get(SecConst.HEADER.USER_LOGINNAME).toString();
    }

    /**
     * 功能描述: 获取当前用户组织编码
     *
     * @param
     * @return java.lang.String
     * @auther: G D
     * @date: 2019/10/24 15:57
     */
    public static String getDeptId() {
        String deptId = SecurityClient.getDeptId(getPid());
        if (StringUtils.isNotEmpty(deptId)) {
            return deptId;
        }
        throw new MyOwnRuntimeException("部门ID不存在！");
    }

    /**
     * 功能描述:获取当前用户地区
     *
     * @param
     * @return java.lang.String
     * @auther: G D
     * @date: 2019/10/24 16:07
     */
    public static String getAREAS() {
        String areas = SecurityClient.getAREAS(getPid());
        if (StringUtils.isNotEmpty(areas)) {
            return areas;
        }
        throw new MyOwnRuntimeException("用户地区不存在！");
    }

    /**
     * 功能描述: 获取当前用户部门名称
     *
     * @param
     * @return java.lang.String
     * @auther: G D
     * @date: 2019/10/24 16:07
     */
    public static String getDeptName() {
        String deptName = SecurityClient.getDeptName(getPid());
        if (StringUtils.isNotEmpty(deptName)) {
            return deptName;
        }
        throw new MyOwnRuntimeException("部门名称不存在！");
    }

    /**
     * 功能描述:获取当前用户拥有角色
     *
     * @param
     * @return java.lang.String
     * @auther: G D
     * @date: 2019/10/24 16:08
     */
    public static String getRoles() {
        String roles = SecurityClient.getRoles(getPid());
        if (StringUtils.isNotEmpty(roles)) {
            return roles;
        }
        throw new MyOwnRuntimeException("用户角色不存在！");
    }

    /**
     * 功能描述: 1主 0 从
     *
     * @param
     * @return java.lang.String
     * @auther: G D
     * @date: 2019/10/24 16:20
     */
    public static String getIsMain() {
        String isMain = SecurityClient.getIsMain(getPid());
        if (StringUtils.isNotEmpty(isMain)) {
            return isMain;
        }
        throw new MyOwnRuntimeException("无法判断主从账号，标志缺失");
    }


    /**
     * 功能描述: 获取header中加密用户信息
     *
     * @param
     * @return java.lang.String
     * @auther: G D
     * @date: 2019/10/24 15:56
     */
    public static String getPid() {
        String header = getRequest().getHeader(SecConst.HEADER.PID);
        if (StringUtils.isNotBlank(header)) {
            return header;
        }
        throw new MyOwnRuntimeException("PID为空");
    }
    /**
     * 功能描述:获取当前用户组织编码
     *
     * @param
     * @return java.lang.String
     * @auther: G D
     * @date: 2019/10/24 15:49
     */
    public static String getCurrCode() {
        String pid = getPid();
        Map map = JSON.parseObject(AES.decrypt(pid), Map.class);
        String currCode = map.get(SecConst.HEADER.USER_DEPTCODE).toString();
        if (StringUtils.isNotEmpty(currCode)) {
            return currCode;
        }
        throw new MyOwnRuntimeException("当前currOrgCode不存在");
    }

    public static UserInfo getUserInfo() {
        HashMap<String, String> type = new HashMap<>();
        type.put("01", "CT");
        type.put("02", "TE");
        type.put("03", "TZ");
        String userMsg = getUserMsg().toString();
        Map map = JSON.parseObject(userMsg, Map.class);
        //String isMain = map.get(SecConst.HEADER.USER_ISMAIN) + "";
        String userLoginAcct = map.get(SecConst.HEADER.USER_LOGINACCT) + "";
        String isJiYueHua = map.get(SecConst.HEADER.USER_ISJIYUEHUA) + "";
        String userDeptCode = map.get(SecConst.HEADER.USER_DEPTCODE) + "";
        String userEnterpType = map.get(SecConst.HEADER.CURRUSER_ENTERPTYPE) + "";
        List<Object> list = (List<Object>) map.get(SecConst.HEADER.USER_ROLE_CODE_LIST);
        String areaEN = map.get(SecConst.HEADER.CURRUSER_AREAEN) + "";
        String orgLevel = map.get(SecConst.HEADER.CURRUSER_ORGLEVEL) + "";
        String orgParentName = map.get(SecConst.HEADER.CURRUSER_ORGNAME) + "";
        UserInfo info = new UserInfo();
        info.setLoginAcct(userLoginAcct)
                .setMobile(map.get(SecConst.HEADER.CURRUSER_MOBILE) + "")
                .setOrgId(map.get(SecConst.HEADER.CURRUSER_UNITID) + "")
                .setLoginName(map.get(SecConst.HEADER.USER_LOGINNAME) + "")
                //.setIsMain(isMain)
                .setDeptId(userDeptCode)
                .setUnitCode(type.get(userEnterpType))
                .setRoleCodeList(list)
                .setOrgCode(areaEN)
                .setDeptName(map.get(SecConst.HEADER.USER_DEPTNAME) + "")
                .setRoles(map.get(SecConst.HEADER.USER_ROLES) + "")
                .setOrgLevel(orgLevel)
                .setIsJiYueHua(isJiYueHua)
                .setOrgName(orgParentName)
                .setEnterType(userEnterpType);
        return info;
    }

    public static UserInfo getUserInfo(String pid) {
        HashMap<String, String> type = new HashMap<>();
        type.put("01", "CT");
        type.put("02", "TE");
        type.put("03", "TZ");
        String userMsg = getUserMsg(pid).toString();
        Map map = JSON.parseObject(userMsg, Map.class);
        //String isMain = map.get(SecConst.HEADER.USER_ISMAIN) + "";
        String userLoginAcct = map.get(SecConst.HEADER.USER_LOGINACCT) + "";
        String isJiYueHua = map.get(SecConst.HEADER.USER_ISJIYUEHUA) + "";
        String userDeptCode = map.get(SecConst.HEADER.USER_DEPTCODE) + "";
        String userEnterpType = map.get(SecConst.HEADER.CURRUSER_ENTERPTYPE) + "";
        List<Object> list = (List<Object>) map.get(SecConst.HEADER.USER_ROLE_CODE_LIST);
        String areaEN = map.get(SecConst.HEADER.CURRUSER_AREAEN) + "";
        String orgLevel = map.get(SecConst.HEADER.CURRUSER_ORGLEVEL) + "";
        String orgParentName = map.get(SecConst.HEADER.CURRUSER_ORGNAME) + "";
        UserInfo info = new UserInfo();
        info.setLoginAcct(userLoginAcct)
                .setMobile(map.get(SecConst.HEADER.CURRUSER_MOBILE) + "")
                .setOrgId(map.get(SecConst.HEADER.CURRUSER_UNITID) + "")
                .setLoginName(map.get(SecConst.HEADER.USER_LOGINNAME) + "")
                //.setIsMain(isMain)
                .setDeptId(userDeptCode)
                .setUnitCode(type.get(userEnterpType))
                .setRoleCodeList(list)
                .setOrgCode(areaEN)
                .setDeptName(map.get(SecConst.HEADER.USER_DEPTNAME) + "")
                .setRoles(map.get(SecConst.HEADER.USER_ROLES) + "")
                .setOrgLevel(orgLevel)
                .setIsJiYueHua(isJiYueHua)
                .setOrgName(orgParentName)
                .setEnterType(userEnterpType);
        return info;
    }

    public static String getOrgStatus(String str) {
        return StringUtils.isBlank(str)?null:SystemTag.OrgStatusEnum.flag(str).getDesc();
    }
    public static String getUserStatus(String str) {
        return StringUtils.isBlank(str)?null:SystemTag.UserStatusEnum.flag(str).getDesc();
    }
    public static String getOrgLevel(String str) {
        String orgLevel = "";
        if ("01".equals(str)) {
            orgLevel = "总部";
        } else if ("00".equals(str)) {
            orgLevel = "中国铁塔集团";
        } else if ("02".equals(str)) {
            orgLevel = "省分";
        } else if ("03".equals(str)) {
            orgLevel = "地市";
        } else if ("04".equals(str)) {
            orgLevel = "部门";
        }else {
            orgLevel = str;
        }
        return orgLevel;
    }

    /*转换公司标识*/
    public static String getenterpType(String str) {
        return StringUtils.isBlank(str)?null:SystemTag.OrgEnterTypeEnum.flag(str).getDesc();
    }

    public static String getOrgType(String str) {
        return StringUtils.isBlank(str)?null:SystemTag.OrgTypeEnum.flag(str).getDesc();
    }

    /*部门orgcode转换省份orgcode*/
    public static String getProvinceCode(String orgCode) {
        String provinceCode = "";
        if (!StringUtil.isEmpty(orgCode) && orgCode.length() > 2) {
            if (("02").equals(orgCode.substring(0, 2)) || ("03").equals(orgCode.substring(0, 2))) {
                provinceCode = orgCode.substring(0, 4).concat("0001");
            } else {
                provinceCode = orgCode.substring(0, 2).concat("0001");
            }
            return provinceCode;
        }
        return provinceCode;
    }
    public static boolean specialSymbols(String str) {
        String regEx = "[`~!@#$%^&*+=|{}';'\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }
    public static int getLength(int num){
        return String.valueOf(num).length();
    }
    /**
     * 功能描述: 获取指定orgcode所属公司code
     *
     * @param
     * @return java.lang.String
     * @auther: G D
     * @date: 2019/10/24 9:48
     */
    public static String getUnitCode(String orgCode) {
        String unitCode = "";
        if ("02".equals(orgCode.substring(0, 2))) {
            unitCode = orgCode.substring(0, 8);
        } else if ("03".equals(orgCode.substring(0, 2))) {
            unitCode = orgCode.substring(0, 8);
        } else {
            unitCode = orgCode.substring(0, 6);
        }
        return unitCode;
    }

    public static void main(String[] args) {
  //           System.out.println(getOrgStatus("01"));
//       System.out.println(RequestHolder.getDeptName());
//        System.out.println(RequestHolder.getDeptId());
//        System.out.println(RequestHolder.getRoles());
//        System.out.println(RequestHolder.getAREAS());
//        System.out.println(RequestHolder.getIsMain());
//        System.out.println(RequestHolder.getUserMsg());
        //System.out.println(AES.decrypt(getPid()));
        //System.out.println(getLoginAcct());
    }
}

