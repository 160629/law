package com.chinatower.product.legalms.security;

/**
 * Created by duanzj on 2018/12/18.
 */
public final class SecConst {
    private SecConst() {
        //
    }

    public static class HEADER{
        private HEADER() {
            //
        }

        public static final String CURRUSER_ORGLEVEL = "CURRUSERORGLEVEL";//用户组织等级

        public static final String CURRUSER_ORGNAME = "CURRUSERORGNAME";//用户组织等级部门（省地市）

        public static final String CURRUSER_AREAEN = "CURRUSERAREAEN";//用户公司简称

        public static final String CURRUSER_MOBILE = "CURRUSERMOBILE";//用户手机号

        public static final String CURRUSER_UNITID = "CURRUSERUNITID";//公司编码

        public static final String USER_ISJIYUEHUA = "ISJIYUEHUA";//是否集约化

        public static final String CURRUSER_ENTERPTYPE = "CURRUSERENTERPTYPE";//企业类型

        public static final String  USER_ROLE_CODE_LIST = "ROLECODELIST"; //用户角色code集合

        public static final String PID = "pid";//获取详细信息（Headers获取用户加密信息）

        public static final String USER_CURRCODE = "currOrgCode";//header中获取

        public static final String USER_IDENTITYS = "IDENTITYS";//用户身份

        public static final String USER_DEPTCODE = "LOGINDEPTCODE";//获取部门编码

        public static final String  USER_LOGINACCT = "LOGINACCT"; //用户账号

        public static final String  USER_LOGINNAME = "LOGINNAME"; //用户名称

        public static final String USER_AREAS = "AREAS";//获取用户地区

        public static final String USER_DEPTNAME = "LOGINDEPTNAME";//获取用户部门名称

        public static final String USER_ROLES = "ROLES";//获取当前账号下所有角色

        public static final String USER_ISMAIN = "ISMAIN";//判断当前用户主从账号 1主 0 从


    }
}
