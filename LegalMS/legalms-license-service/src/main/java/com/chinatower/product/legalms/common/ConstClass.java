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
    }

    public static class FAILURE {
        private FAILURE() {
            //
        }

        public static final String FAILURE_MESS = "处理失败";

        public static final String FIND_DEPLEADER_ERROR = "分管领导处理失败";

        public static final String ORG_INTENSIVES_ERROR = "集约化处理失败";

        public static final String SMS_CONFIG_ERROR = "短信人员配置处理失败";

        public static final String FIND_SUPPLIER_ERROR = "供应商处理失败";

        public static final String FIND_ORGTREE_ERROR = "组织树信息查询失败";

        public static final String ACCOUNT_DEAL_ERROR = "账号操作失败";

        public static final String NEXT_USER_ERROR = "参与者处理失败";

        public static final String FIND_USER_ERROR = "查询用户信息失败";

        public static final String UPDATE_USER_ERROR = "更新用户信息失败";

        public static final String USERPOWER_DEAL_ERROR = "用户授权信息操作失败";

        public static final String USERAUXI_DEAL_ERROR = "用户兼职信息操作失败";
        //e.g.
        //public static final String UNFINISH_PROCESS_MESS = "此案件对应的诉讼流程未结束,不能结案";
    }
}