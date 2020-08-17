package com.chinatower.product.legalms.common;

public class ConstClass {

    private ConstClass() {
        //
    }

    public static class SUCCESS {
        private SUCCESS() {
            //
        }

        public static final String SUCCESS_MESS = "处理成功";

        public static final String FILE_RESULT = "业务处理结果为:";
    }

    public static class FAILURE {

        private FAILURE() {
            //
        }

        public static final String FAILURE_MESS = "处理失败";

        public static final String DELETE_NODONE = "未删除到数据";

        public static final String OPERATION_EXCEPTION = "业务处理异常:";

        public static final String FILE_GET_ERROR = "获取签名失败";

        public static final String FILE_NOEXIST = "上传文件不能为空";

        public static final String PRIMARY_NOEXIST = "主键不能为空";

        public static final String BUSINESSID_NOEXIST = "业务主键不能为空";

        public static final String FILESHAREID_NOEXIST = "share表主键不能为空";

        public static final String FILE_ASTRICT = "文件上传不得超过50M";

        public static final String FILEID_NOEXIST = "FiledId不能为空";

        public static final String INSERT_ERROR = "上传文件失败";

        public static final String UPDATE_ERROR = "修改文件失败";

        public static final String UPDATE_ERROR_NORESULT = "修改文件失败,没有返回结果";
    }

}
