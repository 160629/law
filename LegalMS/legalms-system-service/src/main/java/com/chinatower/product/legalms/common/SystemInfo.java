package com.chinatower.product.legalms.common;

public class SystemInfo {

    private SystemInfo() {
        //
    }

    public static final String USER_PASSWORD = "1q2w3e4r";

    public static final String CURRUSER_ORGLEVEL = "CURRUSERORGLEVEL";//用户组织等级

    public static final String CURRUSER_ORGNAME = "CURRUSERORGNAME";//用户组织等级部门（省地市）

    public static final String CURRUSER_MOBILE = "CURRUSERMOBILE";//用户手机号

    public static final String CURRUSER_AREAEN = "CURRUSERAREAEN";//用户公司简称

    public static final String CURRUSER_UNITID = "CURRUSERUNITID";//公司编码

    public static final String CURRUSER_ENTERPTYPE = "CURRUSERENTERPTYPE";//企业类型

    public static final String  USER_ROLE_CODE_LIST = "ROLECODELIST"; //用户角色code集合

    public static final String PID = "pid";//获取详细信息（Headers获取用户加密信息）

    public static final String USER_CURRCODE = "currOrgCode";//header中获取

    public static final String USER_IDENTITYS = "IDENTITYS";//用户身份

    public static final String USER_DEPTCODE = "LOGINDEPTCODE";//获取部门编码

    public static final String USER_ISJIYUEHUA = "ISJIYUEHUA";//是否集约化

    public static final String  USER_LOGINACCT = "LOGINACCT"; //用户账号

    public static final String  USER_LOGINNAME = "LOGINNAME"; //用户名称

    public static final String  USER_ROLECODES = "ROLECODE"; //用户角色集合

    public static final String  USER_ROLENAME = "ROLENAME"; //用户角色名称

    public static final String  USER_ROLESIGN = "CHNTLEGALMS"; //用户角色标识

    public static final String USER_AREAS = "AREAS";//获取用户地区

    public static final String USER_DEPTNAME = "LOGINDEPTNAME";//获取用户部门名称

    public static final String USER_ROLES = "ROLES";//获取当前账号下所有角色

    public static final String USER_ISMAIN = "ISMAIN";//判断当前用户主从账号 1主 0 从

    public static final String SUCCESS = "000";

    public static final String FAILURE = "999";

    public static final String UPDATE_SUCCESS = "更新成功";

    public static final String INSERT_ID = "1";

    public static final String UPDATE_ID = "2";

    public static final String DELETE_ID = "3";

    public static final String INSERT_SUCCESS = "增加成功";

    public static final String LOGGER_NAME = "TransLog";

    public static final String DELETE_SUCCESS = "删除成功";

    public static final String OPERATION_EXCEPTION = "业务处理异常:";

    public static final String SYSTEM_RESULT = "业务处理结果为:";

    public static final String REQUEST_PARAM = "请求参数为:";

    public static final String ID_HEAD = "Announcement";

    public static final String FILE_Y = "1";

    public static final String FILE_N = "0";

    public static final String REDIS_SIGN = "CT00026_";

    public static final String QUERY_PERSONWORK = "请求异常，queryPersonWorkItems进入fallback,异常信息{}";

    public static final String CHECK_TKOEN_ERROR = "请求异常，校验凭证进入fallback,异常信息{}";

    public static final String CREATE_TKOEN_ERROR = "请求异常，申请凭证进入fallback,异常信息{}";

    public static final String QUERY_USERINFO_ERROR = "请求异常，查询用户进入fallback,异常信息{}";

    public static final String QUERY_BUTTON_ERROR = "请求异常，查询菜单按钮进入fallback,异常信息{}";

    public static final String REDIS_QUERY_ERROR = "请求异常，redis的query方法进入fallback,异常信息{}";

    public static final String REDIS_INSERT_ERROR = "请求异常，redis的insert方法进入fallback,异常信息{}";

    public static final String REDIS_UPDATE_ERROR = "请求异常，redis的update方法进入fallback,异常信息{}";

    public static final String REDIS_DEL_ERROR = "请求异常，redis的del方法进入fallback,异常信息{}";

    public static final String SERVICE_ROLES_ERROR = "请求异常，serviceRoles进入fallback,异常信息{}";

    public static final String SERVICE_MENUS_ERROR = "请求异常，serviceMenus进入fallback,异常信息{}";

    public static final String ROLE_MENUS_ERROR = "请求异常，roleMenus进入fallback,异常信息{}";

    public static final String USER_ROLE_ERROR = "请求异常，userRoles进入fallback,异常信息{}";

    //公告状态 0未发布 1发布中 2发布完毕
    public static final String ANNOUNCE_NO_STATE = "0";

    public static final String ANNOUNCE_ING_STATE = "1";

    public static final String ANNOUNCE_DEAD_STATE = "2";
}
