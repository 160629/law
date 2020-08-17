package com.chinatower.product.legalms.common;

public class CoreConstClass {

    public static final String REQUEST_ERR_PARAM = "未传入参数:";
    public static final String XZ = "XZ";
    public static final String ZX = "ZX";
    public static final String T_ISSUE_CONSULT = "t_issue_consult";
    public static final String INSERT_FAIL = "保存失败";
    public static final String ID = "id";
    public static final String FAILURE_MESS = "处理失败";
    public static final String BUSINESS_FILED_SEPARATOR =  "#";
    public static final String BUSINESS_TABLE_TITLE =  "TITLE";
    public static final String BUSINESS_TABLE_CODE =  "CODE";
    public static final String BUSINESS_TABLE_TYPE =  "TYPE";
    public static final String BUSINESS_TABLE_PERSON =  "PERSON";
    public static final String BUSINESS_TABLE_CURWORK =  "CURWORK";
    public static final String BUSINESS_TABLE_ACCOUNT =  "ACCOUNT";
    public static final String APPROVEITEMID = "approveItemId";// 业务id，key
    public static final String NEXT_FLOW_ERROR = "流程驱动失败";
    public static final String NEXT_FLOW_SUCCESS = "流程驱动成功";
    public static final String NEXT_FLOW_SAVE_ERROR = "保存失败";
    public static final String NEXT_FLOW_SAVE_SUCCESS = "保存成功";
    public static final String DRAWBACK_FLOW_FAIL = "流程撤回失败，请检查流程下一步是否已审批";
    public static final String DRAWBACK_MEET_FAIL = "流程已进入会签，不允许撤回";
    public static final String DRAWBACK_FLOW_NULL_FAIL = "流程信息异常，请检查流程";
    public static final String DRAWBACK_FLOW_FINISH_FAIL = "下一步环节已审批，不允许撤回";
    public static final String DRAWBACK_SUCCESS = "撤回成功";
    public static final String NO_BUSINESS_STATE_FIELD = "没有此业务表状态字段";
    public static final String OPERATION_FAILED = "操作失败";
    public static final String TASK1 = "获取待办列表失败";
    public static final String TASK2 = "获取已办列表失败";
    public static final String TASK3 = "获取办结列表失败";
    public static final String FLOW_BUSINESS_TABLENAME = "LMSBIZINFO";
    public static final String DATE_FORMAT_FAIL = "日期格式转换错误";
    public static final String MESSAGE_UNACTRIGHT = "当前环节未配置权限,请配置对应权限";
    public static final String FILE_SAVE_FAIL = "附件保存失败";
    public static final String  BODY_RIGHT_LEVEL1 = "1";

    public static final String  BODY_RIGHT_LEVEL2 = "2";

    public static final String  BODY_RIGHT_LEVEL3 = "3";
    public static final String SUCCESS = "处理成功";
    public static final CharSequence FLOW_EXCEPTION_21050002 = "21050002";
    public static final CharSequence FLOW_EXCEPTION_WORK_NO_RUNNING = "workitem state should be running(10) or wait_receive(4)";
    public static final String T_ISSUE_JOINTLY = "t_issue_jointly";
    public static final String T_CASE_LEGISLATION = "t_case_legislation";
    public static final String IS_DICT_DATA_CACHE = "CT00026_IS_DICT_DATA_CACHE";
    public static final String MES = "mes";
    public static final String GETSUCCESS = "查询成功!";

    private CoreConstClass() {
        //
    }

    public static final String ERROE = "ERROR";

    public static final String FIRST = "first";
    
    public static final String SECOND = "second";
    
    public static final String THIRD = "third";
    
    public static final String T_ISSUE_LAWSUIT = "t_issue_lawsuit";
    public static final String LAWSUIT_ID = "lawsuit_id";
    public static final String T_CASE_ASSIGN = "t_case_assign";
    public static final String FLOW_IS_NULL = "流程为空";
    public static final String ACTIVITY_DEFINE_ITEM_IS_NULL = "活动定义为空";
    public static final String FLOW_DEFINE_IS_NULL = "流程定义为空";
    public static final String LIST_EMPTY = "集合为空";
    public static final String WORK_ITEM_IS_NULL = "工作项为空";
    public static final String TENANLID = "lms";
    public static final String LOGGER_NAME = "TransLog";
    public static final String REDIS_SIGN_UNDONE = "CT00026_flowpath_undone";
    public static final String REDIS_SIGN_KAFKA = "CT00026_flowpath_kafka";
    public static final String IS_AUTO_ONE = "1";
    public static final String FLOW_GUIDE_CITY = "com.tower.issue.guide.city";
    public static final String FLOW_GUIDE_STATE = "com.tower.issue.guide.state";
    public static final String FLOW_LAWSUIT_CITY = "com.tower.issue.lawsuit.city";
    public static final String FLOW_LAWSUIT_STATE = "com.tower.issue.lawsuit.state";
    public static final String FLOW_LEGISLATION_CITY = "com.tower.issue.legislation.cities";
    public static final String FLOW_JOINLY_CITY = "com.tower.issue.joinly.city";
    public static final String FLOW_GUIDE = "com.tower.issue.guide";
    public static final String FLOW_LAWSUIT = "com.tower.issue.lawsuit";
    public static final String FLOW_LEGISLATION = "com.tower.issue.legislation";
    public static final String FLOW_JOINLY = "com.tower.issue.joinly";
    public static final String SYS_FLOE_TYPE = "sys_flow_type";
    public static final String FLOW_LAWCASE = "com.tower.issue.lawcase";
    public static final String LAW_TOPIC = "lawTopic";
    
	public static final String CONTENT_TASKS = "您在法务管理平台收到一条{0}待办：\"{1}\"，请及时处理。";

	public static final String CONTENT_UNVIEW = "您在法务管理平台收到一条待阅：\"{0}\"";
    public static final Integer REDIS_TIMEOUT = 3000;
}
