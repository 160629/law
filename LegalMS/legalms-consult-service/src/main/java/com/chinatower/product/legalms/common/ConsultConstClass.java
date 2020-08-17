package com.chinatower.product.legalms.common;

public class ConsultConstClass {

    public static final String DRAWBACK_SUCCESS = "撤回成功";
    public static final String UNVIEW = "unView"; // 是否已读标识
    public static final String APPROVEITEMID = "approveItemId";// 业务id，key

    private ConsultConstClass() {
        //
    }

    public static class SUCCESS {
        private SUCCESS() {
            //
        }

        public static final String SUCCESS_MESS = "处理成功";

        public static final String DEAL_RESULT = "业务处理结果为:";
    }

    public static class FAILURE {
        public static final String DRAWBACK_FLOW_FAIL = "流程撤回失败，请检查流程下一步是否已审批";
        public static final String DRAWBACK_MEET_FAIL = "流程已进入会签，不允许撤回";
        public static final String DRAWBACK_FLOW_NULL_FAIL = "流程信息异常，请检查流程";
        public static final String DRAWBACK_FLOW_FINISH_FAIL = "下一步环节已审批，不允许撤回";

        private FAILURE() {
            //
        }
        public static final String USERINFO_FIND_ERROR = "用户信息查询失败";

        public static final String FAILURE_MESS = "处理失败";

        public static final String DRAFTS_FIND_ERROR = "查询草稿箱信息失败";

        public static final String DRAFTS_INSERT_ERROR = "插入草稿箱信息失败";

        public static final String DRAFTS_UPDATE_ERROR = "更新草稿箱信息失败";

        public static final String DRAFTS_DELETE_ERROR = "删除草稿箱信息失败";

        public static final String DRAFTS_ALLDELETE_ERROR = "批量删除草稿箱信息失败";

        public static final String ORGLEADER_FIND_ERROR = "查询分管领导信息失败";

        public static final String ORGLEVEL_FIND_ERROR = "组织等级查询信息失败";
    }

    public static final String NEXT_FLOW_PARAM_ERROR = "审批流程参数错误";
    public static final String MAINSEEDORGID = "mainSeedOrgId";
    public static final String JOINLYCODE = "joinlyCode";
    public static final String IDEXECUTIVEARMID = "idexecutiveArmId";


    public static final String SUCCESS_MESS = "处理成功";

    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    
    
    public static final String ACTIVITY_PROVINCETOHEAD_LIMIT = "2001";
    public static final String ACTIVITY_CITYTOPROVINCE_LIMIT = "3002";
    public static final String ACTIVITY_HEADTORPOVINCE_LIMIT = "1002";
    public static final String ACTIVITY_PROVINCETOCITY_LIMIT = "2003";
    //省份送地市限制取拟稿人公司code()
    public static final String ACTIVITY_PROVINCETOCITYCODE_LIMIT = "2013";
    public static final String TYPE_CODE_PROVINCETOHEAD = "2001";
    public static final String TYPE_CODE_CITYTOPROVINCE = "3002";
    public static final String TYPE_CODE_HEADTORPOVINCE = "1002";
    public static final String TYPE_CODE_PROVINCETOCITY = "2003";
    /*送回申报人确认人节点限制*/
    public static final String ACTIVITY_FLOWTOSBR_LIMIT = "13";
    /*送回申报人确认人节点限制*/
    public static final String ACTIVITY_FLOWTOBEFORE_LIMIT = "14";
    /*所有流程涉及回退节点限制*/
    public static final String ACTIVITY_FLOWBACK_LIMIT = "12";
    public static final String ACTIVITY_FLOWBACK_USERCODE = "FLOWBACKUSERCODE";
    /*案件协办部门限制*/
    public static final String ACTIVITY_JOINLY_LIMIT = "11";
    public static final String ACTIVITY_LEGISLATION_LIMIT = "15";
    public static final String ACTIVITY_UNIT_LIMIT = "1";
    public static final String ACTIVITY_DEPT_LIMIT = "3";


    public static final String ACTIVITY_NOT_LIMIT = "0";
    public static final String ACTIVITY_GROUPDEPT_LIMIT = "8";
    public static final String GROUPDEPT = "groupdept";
    public static final String ACTIVITY_LIMIT = "1";
    public static final String USER_FRONT_02 = "02";
    public static final String USER_FRONT_03 = "03";
    public static final String TYPE_CODE = "typecode";
    public static final String TYPE_CODE_ORG = "org";
    public static final String TYPE_CODE_UNIT = "unit";
    public static final String TYPE_CODE_ROLE = "role";
    public static final String TYPE_CODE_ORGROLE = "orgrole";
    public static final String TYPE_CODE_UNITROLE = "unitrole";
    public static final String FLOW_IS_NULL = "流程为空";
    public static final String FLOW_DEFINE_IS_NULL = "流程定义为空";
    public static final String WORK_ITEM_IS_NULL = "工作项为空";
    public static final String ACTIVITY_DEFINE_ITEM_IS_NULL = "活动定义为空";
    public static final String ISSUE_GUIDE_CITY_FLOW = "com.tower.issue.guide.city";
    public static final String ISSUE_GUIDE_HEAD_FLOW = "com.tower.issue.guide.head";
    public static final String ISSUE_GUIDE_STATE_FLOW = "com.tower.issue.guide.state";
    public static final String ISSUE_LAWSUIT_CITY_FLOW = "com.tower.issue.lawsuit.city";
    public static final String ISSUE_LAWSUIT_HEAD_FLOW = "com.tower.issue.lawsuit.head";
    public static final String ISSUE_LAWSUIT_STATE_FLOW = "com.tower.issue.lawsuit.state";
    public static final String ISSUE_LAWCASE_HEAD_FLOW = "com.tower.issue.lawcase.head";
    public static final String ISSUE_LAWCASE_STATE_FLOW = "com.tower.issue.lawcase.state";
    public static final String INSERT_FAIL = "保存失败";
    public static final String LIST_EMPTY = "集合为空";
    public static final String PARAMETER_LIST = "入参列表";
    public static final String FILE_SAVE_FAIL = "附件保存失败";
    public static final String TASK1 = "获取待办列表失败";
    public static final String TASK2 = "获取已办列表失败";
    public static final String TASK3 = "获取办结列表失败";
    public static final String DATE_FORMAT_FAIL = "日期格式转换错误";
    public static final String LAWLETTER_PROVINCE_FLOW = "";
    public static final String LAWLETTER_CITY_FLOW = "";
    public static final String PROCESS_CREATE_FAIL = "流程创建异常";
    public static final String LOGGER_NAME = "TransLog";
    public static final String OPERATION_EXCEPTION = "业务处理异常:";
    public static final String NEXT_FLOW_SUCCESS = "流程驱动成功";
    public static final String NEXT_FLOW_ERROR = "流程驱动失败";
    public static final String NEXT_FLOW_SAVE_ERROR = "保存失败";
    public static final String NEXT_FLOW_SAVE_SUCCESS = "保存成功";
    public static final String TENANLID = "lms";
    public static final String FLOW_BUSINESS_TABLENAME = "LMSBIZINFO";
    public static final String USERID = "1001";
    public static final String USERNAME = "tiger";
    public static final String SUCCESS = "000";
    public static final String BUSINESS_LAWLETTER ="文件的类型";



    public static final String UPDATE_SUCCESS = "更新成功";

    public static final String INSERT_ID = "1";

    public static final String UPDATE_ID = "2";

    public static final String DELETE_ID = "3";

    public static final String INSERT_SUCCESS = "增加成功";

    public static final String DELETE_SUCCESS = "删除成功";

    public static final String SYSTEM_RESULT = "业务处理结果为:";

    public static final String REQUEST_PARAM = "请求参数为:";

    public static final String ID_HEAD = "Announcement";

    public static final String FILE_Y = "1";

    public static final String FILE_N = "0";

    public static final String USERFINO_REDIS_KEY = "CT00026_system_saveLoginUserInfo";

    public static final String REDIS_SIGN = "CT00026_flowpath_";
    
    public static final String REDIS_SIGN_UNDONE = "CT00026_flowpath_undone";
    
    public static final String FIRST = "first";
    
    public static final String SECOND = "second";

    public static final String IS_COUNTERSIGN_N = "0";

    public static final String IS_COUNTERSIGN_Y =  "1";

    public static final String  COUNTERSIGN_N = "0";

    public static final String  COUNTERSIGN_Y = "1";

    public static final String IS_COUNTERSIGN =  "isCountersign";

    public static final String BUSINESS_FILED_SEPARATOR =  "#";

    public static final String BUSINESS_TABLE_TITLE =  "TITLE";
    public static final String BUSINESS_TABLE_CODE =  "CODE";
    public static final String BUSINESS_TABLE_TYPE =  "TYPE";
    public static final String BUSINESS_TABLE_PERSON =  "PERSON";
    public static final String BUSINESS_TABLE_CURWORK =  "CURWORK";
    public static final String BUSINESS_TABLE_ACCOUNT =  "ACCOUNT";
    public static final String TASK_COUNT =  "count";

    public static final String REQUEST_ERR_PARAM = "未传入参数:";

    public static final String LEVEL_WEIIRHT = "weighty";

    public static final String FLOW_ID = "flowId";

    public static final String FLOW_START_ACT = "flowStartAct";

    public static final String  ENFORCEMENT_N = "0";

    public static final String  ENFORCEMENT_Y = "1";

    public static final String IS_ENFORCEMENT =  "isEnforcement";

    public static final String MESSAGE_UNRIGHT =  "当前登录人没有该流程起草权限,请配置对应菜单";

    public static final String MESSAGE_UNACTRIGHT =  "当前环节未配置权限,请配置对应权限";

    public static final String MESSAGE_UNNEXTACT =  "当前环节未配置下一步活动,请配置对应活动";

    public static final String  BODY_RIGHT_LEVEL1 = "1";

    public static final String  BODY_RIGHT_LEVEL2 = "2";

    public static final String  BODY_RIGHT_LEVEL3 = "3";
    //案件交办查环节的标志
    public static final String HANDOVER_FLAG = "1";

    // 待办列表查询条件
    public static final String TASK_CONDITION = "condition";
    public static final String TASK_PAGECOND = "pageCond";
    public static final String TASK_PROCESSINSTID = "processInstId";
    public static final String TASK_ACTIVITYINSTID = "activityInstId";
    public static final String TASK_USERID = "userId";
    public static final String TASK_USERNAME = "username";
    public static final String TASK_TITLE = "title";
    public static final String TASK_CODE = "code";
    public static final String TASK_TYPE = "type";
    public static final String TASK_STARTTIME = "startTime";
    public static final String TASK_ENDTIME = "endTime";
    public static final String TASK_CHOOSETYPE = "chooseType";
    public static final String TASK_PAGENUM = "pageNum";
    public static final String TASK_PAGESIZE = "pageSize";
    public static final String T_ISSUE_GUIDE = "t_issue_guide";
    public static final String GUIDE_ID = "guide_id";
    public static final String JOINTLY_ID = "jointly_id";
    public static final String ID = "id";
    public static final String T_ISSUE_LAWSUIT = "t_issue_lawsuit";
    public static final String LAWSUIT_ID = "lawsuit_id";
    public static final String T_CASE_ASSIGN = "t_case_assign";
    public static final String ASSIGN_ID = "assign_id";
    public static final String YS = "YS";
    public static final String SS = "SS";
    public static final String JB = "JB";
    public static final String OPERATION_FAILED = "操作失败";

    public static final String T_ISSUE_JOINTLY = "t_issue_jointly";
    public static final String T_CASE_LEGISLATION = "t_case_legislation";

    

}
