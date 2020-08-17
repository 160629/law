package com.chinatower.product.legalms.modules.dispute.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eos.workflow.data.WFProcessInst;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.common.DisputeConstClass;
import com.chinatower.product.legalms.common.FlowUtil;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.dispute.mapper.TaskMapper;
import com.chinatower.product.legalms.modules.dispute.service.TaskService;
import com.chinatower.product.legalms.modules.flow.vo.common.MyOwnRuntimeException;
import com.chinatower.product.legalms.modules.flow.vo.common.TaskVO;
import com.eos.workflow.api.BPSLoginManager;
import com.eos.workflow.api.BPSServiceClientFactory;
import com.eos.workflow.api.IBPSServiceClient;
import com.eos.workflow.api.IWFWorklistQueryManager;
import com.eos.workflow.data.WFWorkItem;
import com.eos.workflow.helper.ResultList;
import com.primeton.workflow.api.PageCond;
import com.primeton.workflow.api.WFServiceException;

/**
 * @author 刘晓亮
 * @create 2019-11-05 14:23
 */
@Service
@EnableScheduling
@PropertySource("classpath:/application-local.yml")
public class TaskServiceImpl implements TaskService {

    private static final Logger log = LoggerFactory.getLogger(DisputeConstClass.LOGGER_NAME);

    IBPSServiceClient client = null;
    BPSLoginManager loginManager = null;
    WFWorkItem w = null;
    Map bizObject = null;
    TaskVO taskVOResult = null;

    private final String[] flowType = {"", "法律支撑", "", "纠纷处理", "案件交办", "案件协办", "法律文书办理", "证照申请", "授权申请", "协助执行"};

    @Autowired
    TaskMapper taskMapper;
	@Autowired
	FlowUtil flowUtil;
	private List<Integer> setPageCont(TaskVO taskVO){
        int begin = taskVO.getPageNum() == null ? 0 : taskVO.getPageNum();
        int length = taskVO.getPageSize() == null ? 10 : taskVO.getPageSize();
        begin = (begin != 0) ? ((begin - 1) * length) : begin;
        List<Integer> pageCont = new ArrayList<>(2);
        pageCont.add(begin);
        pageCont.add(length);
        return pageCont;
    }
    @Override
    public ProcessResult queryPersonWorkItems(TaskVO taskVO) {
        log.info("查询参数：" + taskVO);
        UserInfo userInfo = StringUtils.isNotBlank(taskVO.getPid()) ? RequestHolder.getUserInfo(taskVO.getPid()): RequestHolder.getUserInfo();// 获取当前登录人信息
        String processInstId = taskVO.getProcessInstId();
        String activityInstId = taskVO.getActivityInstId();
        String userId = userInfo.getLoginAcct();
        String username = userInfo.getLoginName();
        String title = taskVO.getTitle();
        String code = taskVO.getCode();
        String type = flowType[Integer.parseInt(taskVO.getType() != null && !taskVO.getType().equals("") ? taskVO.getType() : "0")];
        String startTime = taskVO.getStartTime();
        String endTime = taskVO.getEndTime();
        String chooseType = taskVO.getChooseType();
        List<Integer> list = setPageCont(taskVO);
        int begin = list.get(0);
        int length = list.get(1);
        String time = getTime(chooseType);

        loginManager = BPSServiceClientFactory.getLoginManager();
        try {
            client = BPSServiceClientFactory.getClient(flowUtil.getFormName());
        } catch (WFServiceException e) {
            log.error(ProcessResult.ERROR,e);
            return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.OPERATION_FAILED);
        }

        IWFWorklistQueryManager queryManager = client.getWorklistQueryManager();
        loginManager.setCurrentUser(userId, username, DisputeConstClass.TENANLID, null);
        // 设置查询相关条件
        // 工作项条件
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        StringBuilder wiSql = new StringBuilder(" 1 = 1 ");
        // 工作项条件 值
        List windList = new ArrayList<>();
        try {
            setWindList(processInstId, activityInstId, wiSql, windList);
            setWindList(startTime, endTime, time, simpleDateFormat, wiSql, windList);
        } catch (Exception e) {
            log.error(ProcessResult.ERROR,e);
            return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.OPERATION_FAILED);
        }

        // 冗余字段条件
        StringBuilder bizSql = new StringBuilder();
        // 冗余字段条件 值
        List bizBindList = new ArrayList<>();
        setBizBindListByLike(title, code, type, bizSql, bizBindList);
        // 分页条件
        PageCond pageCond = new PageCond(begin, length, true);
        // 查询待办列表
        List<WFWorkItem> wfWorkItems = null;
        switch (chooseType) {
            case "1":
                try {
                    wfWorkItems = queryManager.queryPersonWorkItemsWithBizInfo(userId, "ALL", "ALL", DisputeConstClass.FLOW_BUSINESS_TABLENAME, wiSql.toString(), bizSql.toString(), windList, bizBindList, pageCond);
                    break;
                } catch (WFServiceException e) {
                    log.error(DisputeConstClass.TASK1, e);
                    log.error(ProcessResult.ERROR,e);
                    return new ProcessResult(ProcessResult.ERROR, "查询代办列表失败");
                }
            case "2":
            case "3":
                try {
                    setBizBindListOfCurwork(bizSql, bizBindList, "审批中,已退回,已作废,已办结");
                    wfWorkItems = queryManager.queryPersonFinishedWorkItemsWithBizInfo(userId, "ALL", false, DisputeConstClass.FLOW_BUSINESS_TABLENAME, wiSql.toString(), bizSql.toString(), windList, bizBindList, pageCond);
                    break;
                } catch (WFServiceException e) {
                    log.error(DisputeConstClass.TASK2, e);
                    log.error(ProcessResult.ERROR,e);
                    return new ProcessResult(ProcessResult.ERROR, "查询已办列表失败");
                }
//            case "3":
//                try {
//                    setBizBindListOfCurwork(bizSql, bizBindList, "已作废", "已办结");
//                    wfWorkItems = queryManager.queryPersonFinishedWorkItemsWithBizInfo(userId, "ALL", false, ConstClass.FLOW_BUSINESS_TABLENAME, wiSql.toString(), bizSql.toString(), windList, bizBindList, pageCond);
//                    break;
//                } catch (WFServiceException e) {
//                    log.error(ConstClass.TASK3, e);
//                    log.error(ProcessResult.ERROR,e);
//                    return new ProcessResult(ProcessResult.ERROR, "查询办结列表失败");
//                }
            default:
                return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.OPERATION_FAILED);
        }
        // 获取业务值
        ResultList<WFWorkItem> resultItems = (ResultList<WFWorkItem>) wfWorkItems;
        PageCond pageCond1 = resultItems.getPageCond();
        List<TaskVO> taskVOList = new ArrayList<>();
        for (int i = 0; i < wfWorkItems.size(); i++) {
            taskVOResult = new TaskVO();
            w = wfWorkItems.get(i);
            bizObject = w.getBizObject();
            setTaskVOResult(chooseType);
            taskVOList.add(taskVOResult);
        }


        Map<String, Object> result = new HashMap<>();
        result.put("taskList", taskVOList);
        result.put("count", pageCond1.getCount());
        result.put("totalPage", pageCond1.getTotalPage());
        result.put("currentPage", pageCond1.getCurrentPage());
        log.info("查询结果: " + result);
        return new ProcessResult(ProcessResult.SUCCESS, "查询成功", result);
    }
    private void setTaskVOResult(String chooseType) {
        taskVOResult.setTitle((String) bizObject.get("TITLE"))
                .setCode((String) bizObject.get("CODE"))
                .setType((String) bizObject.get("TYPE"))
                .setPerson((String) bizObject.get("PERSON"))
                .setWork((String) bizObject.get("CURWORK"))
                .setStartTime(w.getStartTime())
                .setEndTime(w.getEndTime())
                .setAccount(w.getParticipant())
                .setProcessInstId(String.valueOf(w.getProcessInstID()))
                .setCurrActivityDefId(w.getActivityDefID())
                .setCurrActivityDefName(w.getActivityInstName())
                .setActivityInstId(String.valueOf(w.getActivityInstID()));
        if ("1".equals(chooseType)) {
            taskVOResult.setChooseType("1").setTime(w.getStartTime());
        } else if ("2".equals(chooseType)){
            taskVOResult.setChooseType("2").setTime(w.getEndTime());
        } else {
            taskVOResult.setChooseType("3").setTime(w.getEndTime());
        }
    }
    private void setBizBindListOfCurwork(StringBuilder bizSql, List bizBindList, String fields) {
	    String[] fieldArray = fields.split(",");
	    StringBuilder sb = new StringBuilder();
	    sb.append("CURWORK in (");
        for (String field : fieldArray) {
            sb.append("?,");
            bizBindList.add(field);
        }
        String substring = sb.substring(0, sb.lastIndexOf(","));
        if (!StringUtils.isNotBlank(bizSql.toString())) {
            bizSql.append(substring);
        } else {
            bizSql.append(" and ").append(substring);
        }
        bizSql.append(")");
//        if (bizBindList.isEmpty()) {
//            bizSql.append("CURWORK in ( ?, ? ) ");
//            bizBindList.add(field1);
//            bizBindList.add(field2);
//        } else {
//            bizSql.append(" and CURWORK in ( ?, ? ) ");
//            bizBindList.add(field1);
//            bizBindList.add(field2);
//        }
    }
    private String getTime(String chooseType) {
        String time = null;
        if ("1".equals(chooseType)) {
            time = "STARTTIME";
        } else {
            time = "ENDTIME";
        }
        return time;
    }
    private void setBizBindListByLike(String title, String code, String type, StringBuilder bizSql, List bizBindList) {
        setBizBindListByLike(title, bizSql, bizBindList, "TITLE like ?", "and TITLE like ?");
        setBizBindListByLike(code, bizSql, bizBindList, "CODE like ?", " and CODE like ?");
        setBizBindListByLike(type, bizSql, bizBindList, "TYPE like ? ", " and TYPE like ? ");
    }
    private void setBizBindListByLike(String field, StringBuilder bizSql, List bizBindList, String s, String s2) {
        if (StringUtils.isNotBlank(field)) {
            if (bizBindList.isEmpty()) {
                bizSql.append(s);
                bizBindList.add("%" + field + "%");
            } else {
                bizSql.append(s2);
                bizBindList.add("%" + field + "%");
            }
        }
    }
//    private void setBizBindListByEquals(String field, StringBuilder bizSql, List bizBindList, String s, String s2) {
//        if (StringUtils.isNotBlank(field)) {
//            if (bizBindList.size() == 0) {
//                bizSql.append(s);
//                bizBindList.add(field);
//            } else {
//                bizSql.append(s2);
//                bizBindList.add(field);
//            }
//        }
//    }
    private void setWindList(String processInstId, String workItemInstId, StringBuilder wiSql, List windList) throws WFServiceException {
        if (StringUtils.isNotBlank(processInstId)) {
            processInstId = getMainFlowInstId(processInstId);
            wiSql.append(" and  rootProcInstID = ?");
//            wiSql.append(" and  bizInfo.PROCESSINSTID = ?");
            windList.add(Long.parseLong(processInstId));
        }
        if (StringUtils.isNotBlank(workItemInstId)) {
            wiSql.append(" and ACTIVITYINSTID = ?");
            windList.add(Long.parseLong(workItemInstId));
        }
    }

    private String getMainFlowInstId(String processInstId) throws WFServiceException {
        WFProcessInst wfProcessInst = client.getProcessInstManager().queryProcessInstDetail(Long.parseLong(processInstId));
        while (wfProcessInst != null) {
            if (wfProcessInst.getParentProcID() != 0L) {
                wfProcessInst = client.getProcessInstManager().queryProcessInstDetail(wfProcessInst.getParentProcID());
            } else {
                return wfProcessInst.getProcessInstID() + "";
            }
        }
        return processInstId;
    }

    private void setWindList(String startTime, String endTime, String time, SimpleDateFormat simpleDateFormat, StringBuilder wiSql, List windList) {
        if (StringUtils.isNotBlank(startTime)) {
            wiSql.append(" and  " + time + "  >= ? ");
            try {
                windList.add(new Timestamp(simpleDateFormat.parse(startTime).getTime()));
            } catch (ParseException e) {
                log.error(DisputeConstClass.DATE_FORMAT_FAIL, e);
                throw new MyOwnRuntimeException(DisputeConstClass.DATE_FORMAT_FAIL);
            }
        }
        if (StringUtils.isNotBlank(endTime)) {
            wiSql.append(" and  " + time + "  < ?");
            try {
                windList.add(new Timestamp(simpleDateFormat.parse(endTime).getTime()));
            } catch (ParseException e) {
                log.error(DisputeConstClass.TASK1, e);
                throw new MyOwnRuntimeException(DisputeConstClass.DATE_FORMAT_FAIL);
            }
        }
    }

    /**
     Calendar now = Calendar.getInstance();
     StringBuilder sb = null;
     @Autowired
     private RedisServiceClient redisServiceClient;

    @Override
    public ProcessResult queryPersonTaskCount(String userId) {
        if (userId.indexOf('_') != -1) {
            userId = userId.substring(0, userId.indexOf('_'));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        now.setTime(new Date());
        now.add(Calendar.DAY_OF_MONTH, -1);
        String lastUpdateDate = simpleDateFormat.format(now.getTime());
        List<TaskNumVO> taskNumVOS = taskMapper.queryPersonTaskCount(userId, lastUpdateDate);
        return new ProcessResult(ProcessResult.SUCCESS, "", taskNumVOS);
    }

    //    @Scheduled(cron = "${task}")
    public void countTask() {
        CacheModel model = new CacheModel().setKey(ConstClass.REDIS_SIGN_UNDONE);
        Map<String, Object> query = redisServiceClient.query(model);
        Object data = query.get("data");
        ArrayList<String> list = null == data ? new ArrayList<>() : (ArrayList) data;
        String json = null;
        String startTime = "";
        String endTime = "";
        ProcessResult task1 = null;// 待办
        ProcessResult task2 = null;// 已办
        ProcessResult task3 = null;// 办结
        List<TaskNumVO> taskNumVOS = new ArrayList<>();
        TaskNumVO taskNumVO = new TaskNumVO();
        List<String> yearMonth = getYearMonth();
        if (null != list && list.size() != 0) {
            for (String tempUserId : list) {
                if (tempUserId.indexOf('_') != -1) {
                    tempUserId = tempUserId.substring(0, tempUserId.indexOf('_'));
                }
                List<String> startTimes = getStartTimes(yearMonth);
                List<String> endTimes = getEndTimes(yearMonth);
                for (int i = 0; i < 12; i++) {
                    json = getJson(tempUserId, startTimes.get(i), endTimes.get(i), 1);
//                    task1 = queryPersonWorkItems(json);
                    json = getJson(tempUserId, startTimes.get(i), endTimes.get(i), 2);
//                    task2 = queryPersonWorkItems(json);
                    json = getJson(tempUserId, startTimes.get(i), endTimes.get(i), 3);
//                    task3 = queryPersonWorkItems(json);
                    taskNumVO = new TaskNumVO();
                    taskNumVO.setId(StringUtil.getId())
                            .setUserId(tempUserId)
                            .setStartTime(startTimes.get(i))
                            .setEndTime(endTimes.get(i))
                            .setToDoTask((int) ((Map<String, Object>) task1.getData()).get(ConstClass.TASK_COUNT) + (int) ((Map<String, Object>) task2.getData()).get("count"))
                            .setFinishTask((int) ((Map<String, Object>) task2.getData()).get(ConstClass.TASK_COUNT))
                            .setEndTask((int) ((Map<String, Object>) task3.getData()).get(ConstClass.TASK_COUNT));
                    taskNumVOS.add(taskNumVO);
                }
            }
            // 插入数据
            taskMapper.insertTask(taskNumVOS);
        }
    }
    private List<String> getEndTimes(List<String> yearMonth) {
        List<String> list = new ArrayList<>(12);
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMM");
        int year = 0;
        int month = 0;
        for (String temp : yearMonth) {
            sb = new StringBuilder();
            year = Integer.parseInt(temp.substring(0, 4));
            month = Integer.parseInt(temp.substring(4));
            //设置年份
            now.set(Calendar.YEAR, year);
            //设置月份
            now.set(Calendar.MONTH, month - 1);
            now.add(Calendar.MONTH, 1);
            list.add(sb.append(sf.format(now.getTime())).append("01000000").toString());
        }
        return list;
    }

    private List<String> getStartTimes(List<String> yearMonth) {
        List<String> list = new ArrayList<>(12);
        for (String temp : yearMonth) {
            sb = new StringBuilder();
            list.add(sb.append(temp).append("01000000").toString());
        }
        return list;
    }


    private List<String> getYearMonth() {
        int curYear = now.get(Calendar.YEAR);
        int preYear = now.get(Calendar.YEAR) - 1;
        int curMonth = now.get(Calendar.MONTH) + 1;
        List<String> list = new ArrayList<>(12);

        int i = curMonth;
        for (; i > 0; i--) {
            sb = new StringBuilder();
            sb.append(curYear);
            if (i >= 10) {
                sb.append(i);
            } else {
                sb.append(0).append(i);
            }
            list.add(sb.toString());
        }
        for (int j = 12; j - curMonth > 0; i--, j--) {
            sb = new StringBuilder();
            sb.append(preYear);
            if (j >= 10) {
                sb.append(j);
            } else {
                sb.append(0).append(j);
            }
            list.add(sb.toString());
        }
        return list;
    }

    private String getJson(String userId, String startTime, String endTime, int chooseType) {
        JSONObject jsonObject = new JSONObject();
        JSONObject condition = new JSONObject();
        JSONObject pageCond = new JSONObject();
        jsonObject.put("condition", condition);
        jsonObject.put("pageCond", pageCond);
        condition.put("startTime", startTime);
        condition.put("endTime", endTime);
        condition.put("chooseType", chooseType);
        pageCond.put("begin", 0);
        pageCond.put("length", 10);

        condition.put("userId", userId);
        return jsonObject.toJSONString();
    }
    */
}
