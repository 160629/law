package com.chinatower.product.legalms.common;

public class ConstClass {

    private ConstClass() {
        //
    }

    public static class SUCCESS {
        public static final String NULLIFY_CASE_MAIN_SUCCESS = "卷宗作废成功";

        private SUCCESS() {
            //
        }

        public static final String SUCCESS_MESS = "处理成功";
        // 再审
        public static final String DELETE_AGAIN_SUCCESS = "删除再审信息成功";
        public static final String SELECT_AGAIN_SUCCESS = "查询再审信息成功";
        public static final String ADD_AGAIN_SUCCESS = "新增再审信息成功";
        public static final String UPDATE_AGAIN_SUCCESS = "修改再审信息成功";
        // 仲裁
        public static final String DELETE_ARBITTATE_SUCCESS = "删除仲裁信息成功";
        public static final String SELECT_ARBITTATE_SUCCESS = "查询仲裁信息成功";
        public static final String ADD_ARBITTATE_SUCCESS = "新增仲裁信息成功";
        public static final String UPDATE_ARBITTATE_SUCCESS = "修改仲裁信息成功";
        // 卷宗移交
        public static final String SELECT_CASE_TRANSFER_SUCCESS = "查询卷宗移交记录成功";
        // 一审
        public static final String SELECT_FIRST_INSTANCE_SUCCESS = "查询一审信息成功";
        public static final String ADD_FIRST_INSTANCE_SUCCESS = "新增一审信息成功";
        public static final String UPDATE_FIRST_INSTANCE_SUCCESS = "修改一审信息成功";
        public static final String DELETE_FIRST_INSTANCE_SUCCESS = "删除一审信息成功";
        // 行政复议
        public static final String SELECT_PUNISH_SUCCESS = "查询行政复议信息成功";
        public static final String ADD_PUNISH_SUCCESS = "新增行政复议信息成功";
        public static final String UPDATE_PUNISH_SUCCESS = "修改行政复议信息成功";
        public static final String DELETE_PUNISH_SUCCESS = "删除行政复议信息成功";
        // 关联信息
        public static final String ADD_RELATIONSHIP_SUCCESS = "新增关联信息成功";
        public static final String DELETE_RELATIONSHIP_SUCCESS = "关联信息删除成功";
        public static final String SELECT_RELATIONSHIP_SUCCESS = "查询关联关系成功";
        // 裁决
        public static final String SELECT_RULINGEXECUTIVE_SUCCESS = "查询裁决信息成功";
        public static final String ADD_RULINGEXECUTIVE_SUCCESS = "新增裁决信息成功";
        public static final String UPDATE_RULINGEXECUTIVE_SUCCESS = "修改裁决信息成功";
        // 二审
        public static final String SELECT_SECOND_INSTANCE_SUCCESS = "查询二审信息成功";
        public static final String ADD_SECOND_INSTANCE_SUCCESS = "新增二审信息成功";
        public static final String UPDATE_SECOND_INSTANCE_SUCCESS = "更新二审信息成功";
        public static final String DELETE_SECOND_INSTANCE_SUCCESS = "删除二审信息成功";
        // 卷宗主表
        public static final String SELECT_CASE_MAIN_LIST_SUCCESS = "查询卷宗列表成功";
        public static final String SELECT_CASE_MAINS_SUCCESS = "查询卷宗移交列表成功";
        public static final String SELECT_RELEVANCE_CASE_MAIN_SUCCESS = "卷宗关联信息查询成功";
        public static final String SELECT_ALL_CASE_MAIN_BY_DEPT_SUCCESS = "查询成功";
        public static final String CREATE_CASE_SUCCESS = "创建卷宗成功";
        public static final String UPDATE_RULING_CLASSES_SUCCESS = "修改裁决类别成功";
        public static final String UPDATE_CASE_MAIN_SUCCESS = "修改卷宗信息成功";
        public static final String ADD_CASE_MAIN_SUCCESS = "新增卷宗信息成功";
        public static final String DELETE_CASE_MAIN_SUCCESS = "删除卷宗成功";
        public static final String AUTO_ADD_CASE_MAIN_SUCCESS = "自动创建卷宗成功";
    }

    public static class FAILURE {
        public static final String CLASS_CATCH = "selectTIssueLawsuitList类捕捉异常";
        public static final String NULLIFY_CASE_MAIN_FAILED = "作废卷宗失败";

        private FAILURE() {
            //
        }

        public static final String FAILURE_MESS = "处理失败";
        // 再审
        public static final String DELETE_AGAIN_FAILED = "删除再审信息失败";
        public static final String SELECT_AGAIN_FAILED = "查询再审信息失败";
        public static final String ADD_OR_UPDATE_AGAIN_FAILED = "新增或修改再审失败";
        // 仲裁
        public static final String DELETE_ARBITTATE_FAILED = "删除仲裁信息失败";
        public static final String SELECT_ARBITTATE_FAILED = "查询仲裁信息失败";
        public static final String ADD_OR_UPDATE_ARBITTATE_FAILED = "新增或修改仲裁失败";
        // 卷宗移交
        public static final String NO_ADMIN_POWER = "无系统管理员权限";
        public static final String SELECT_POWER_FAILED = "获取权限范围失败";
        public static final String NO_USERINFO = "承接人用户信息不全（用户编码，用户组织编码，或用户名称）";
        public static final String SELECT_USERINFO_FAILED = "查询承接人信息失败,请联系管理员";
        public static final String TRANSFER_FAILED = "不可跨公司移交";
        // 关联信息
        public static final String RELATIONSHIP_DELETE_FAILED = "关联信息删除失败";
        public static final String RELATIONSHIP_LAWSUIT_DELETEFAILED = "该卷宗由此纠纷处理单自动生成的，不允许删除";
        public static final String RELATIONSHIP_CASE_ASSIGN_DELETEFAILED = "不允许删除";
        public static final String ADD_RELATIONSHIP_FAILED = "新增关联信息失败";
        public static final String ADD_RELATIONSHIP_ALREADY_FAILED = "该单据已经被关联，不允许再被关联";
        public static final String ADD_OR_UPDATE_RELATIONSHIP_FAILED = "新增或修改关联关系失败";
        // 一审
        public static final String SELECT_FIRST_INSTANCE_FAILED = "查询一审信息失败";
        public static final String ADD_OR_UPDATE_FIRST_INSTANCE_FAILED = "新增或修改一审信息失败";
        public static final String DELETE_FIRST_INSTANCE_FAILED = "删除一审信息成功";
        // 行政复议
        public static final String SELECT_PUNISH_FAILED = "查询行政复议信息失败";
        public static final String ADD_OR_UPDATE_PUNISH_FAILED = "新增或修改行政复议信息失败";
        public static final String DELETE_PUNISH_FAILED = "删除行政复议信息失败";
        public static final String SELECT_RELATIONSHIP_FAILED = "查询关联信息失败";
        // 裁决
        public static final String SELECT_RULINGEXECUTIVE_FAILED = "查询裁决信息失败";
        public static final String ADD_OR_UPDATE_RULINGEXECUTIVE_FAILED = "新增或修改裁决信息失败";
        // 二审
        public static final String SELECT_SECOND_INSTANCE_FAILED = "查询二审信息失败";
        public static final String ADD_OR_UPDATE_SECOND_INSTANCE_FAILED = "新增二审信息失败";
        public static final String DELETE_SECOND_INSTANCE_FAILED = "删除二审信息失败";
        // 卷宗主表
        public static final String SELECT_CASE_MAIN_LIST_FAILED = "查询卷宗列表失败";
        public static final String SELECT_CASE_MAINS_FAILED = "查询卷宗移交列表失败";
        public static final String SELECT_RELEVANCE_CASE_MAIN_FAILED = "卷宗关联信息查询失败";
        public static final String SELECT_ALL_CASE_MAIN_BY_DEPT_FAILED = "查询失败";
        public static final String CREATE_CASE_FAILED = "创建卷宗失败";
        public static final String UPDATE_RULING_CLASSES_FAILED = "修改裁决类别失败";
        public static final String UPDATE_CASE_MAIN_FAILED = "修改卷宗信息失败";
        public static final String DELETE_CASE_MAIN_FAILED = "删除卷宗失败";
        public static final String ADD_OR_UPDATE_CASE_MAIN_FAILED = "新增或修改卷宗信息失败";
        public static final String AUTO_ADD_CASE_MAIN_FAILED = "自动创建卷宗失败";


    }
}
