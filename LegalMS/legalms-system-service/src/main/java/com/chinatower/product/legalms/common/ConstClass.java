package com.chinatower.product.legalms.common;

public class ConstClass {
    public static final String SYS_DICT_DATA_CHANGE = "CT00026_IS_DICT_DATA_CACHE";
    public static final String REDIS_DICT_DATA = "CT00026_REDIS_DICT_DATA";
    public static final String MES = "mes";

    private ConstClass() {
        //
    }

    public static final String SYSTEM_IS_ERROR = "标识错误";
    public static final String KEY_IS_ERROR = "key值格式错误!";
    public static final String KEY_IS_EXIST = "key值已存在!";
    public static final String TYPE_IS_ERROR = "已存储其他类型数据!";
    public static final String KEY_IS_NULL = "key值不存在!";
    public static final String VALUE_IS_NULL = "Value值为空!";
    public static final String ADD_SUCCESS = "新增成功!";
    public static final String DEL_SUCCESS = "删除成功!";
    public static final String GET_SUCCESS = "查询成功!";
    public static final String PUT_SUCCESS = "修改成功!";


    public static class SUCCESS {

        private SUCCESS() {
            //
        }
        public static final String SUCCESS_MESS = "处理成功";
    }

    public static class FAILURE {

        private FAILURE() {
            //
        }

        public static final String FAILURE_MESS = "处理失败";

        public static final String ENCRYPT_USER_ERROR = "用户信息加密失败";

        public static final String FIND_MENU_ERROR = "查询菜单失败";

        public static final String REDIS_INSERT_ERROR = "插入失败";

        public static final String REDIS_UPDATE_ERROR = "更新失败";

        public static final String REDIS_DELETE_ERROR = "删除失败";

        public static final String REDIS_FIND_ERROR = "查询失败";

        public static final String REDIS_INSERT_USER_ERROR = "插入用户失败";

        public static final String FIND_ROLEMENU_ERROR = "查询角色菜单失败";

        public static final String FIND_ROLE_ERROR = "查询角色失败";

        public static final String SYSDICT_DATA_INSERT_ERROR = "数据字典详情添加失败";

        public static final String SYSDICT_DATA_UPDATE_ERROR = "数据字典详情修改失败";

        public static final String SYSDICT_DATA_DELETE_ERROR = "数据字典详情删除失败";

        public static final String SYSDICT_TYPE_INSERT_ERROR = "数据字典添加失败";

        public static final String SYSDICT_TYPE_UPDATE_ERROR = "数据字典修改失败";

        public static final String SYSDICT_TYPE_DELETE_ERROR = "数据字典删除失败";

        public static final String USERID_NOTEXIST_ERROR = "当前账号id本系统不存在";

        public static final String DICTTYPE_BLANK_ERROR = "dictType不能为空";
    }
}
