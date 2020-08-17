package com.chinatower.product.legalms.modules.flow.service.common.impl;

import java.text.SimpleDateFormat;
import java.util.*;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.CoreConstClass;
import com.chinatower.product.legalms.modules.flow.entity.common.PbusinessLogVO;
import com.chinatower.product.legalms.modules.flow.mapper.common.BusinessLogConfigMapper;
import com.chinatower.product.legalms.modules.flow.vo.flow.BusinessLogConfigVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinatower.product.legalms.common.StringUtil;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.flow.entity.common.BusinessLogVO;
import com.chinatower.product.legalms.modules.flow.mapper.common.BusinessLogMapper;
import com.chinatower.product.legalms.modules.flow.service.common.BusinessLogService;
import com.google.common.collect.Iterators;

@Service
public class BusinessLogServiceImpl implements BusinessLogService {
    @Autowired
    BusinessLogMapper businessLogMapper;
    @Autowired
    BusinessLogConfigMapper businessLogConfigMapper;

    protected static Logger logger = LoggerFactory.getLogger(BusinessLogServiceImpl.class);

    public int insertBusinessLog(UserInfo info, Map<String, Object> map) {
        BusinessLogVO businessLogVO = new BusinessLogVO();
        businessLogVO.setLoginAcct(info.getLoginAcct());
        businessLogVO.setLoginName(info.getLoginName());
        businessLogVO.setDeptId(info.getDeptId());
        businessLogVO.setDeptName(info.getDeptName());
        businessLogVO.setOrgId(info.getOrgId());
        businessLogVO.setOrgName(info.getOrgName());
        businessLogVO.setId(StringUtil.getId());
        businessLogVO.setApproveItemId((String) map.get("approveItemId"));
        businessLogVO.setFileId((String) map.get("fileId"));
        businessLogVO.setBusinessField1((String) map.get("businessAdvice"));
        businessLogVO.setApproveItemType((String) map.get("approveItemType"));
        businessLogVO.setActId((String) map.get("actId"));
        businessLogVO.setActName((String) map.get("actName"));
        businessLogVO.setActInstId(map.get("actInstId") + "");
        businessLogVO.setSort((Integer) map.get("sort"));
        businessLogVO.setActParentInstId((String) map.get("actParentInstId"));
        businessLogVO.setFlowId((String) map.get("flowId"));
        businessLogVO.setFlowPid((String) map.get("flowPid"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date completedTime = null;
        if (StringUtils.isNotBlank((String) map.get("completedTime"))) {
            try {
                completedTime = simpleDateFormat.parse((String) map.get("completedTime"));
            } catch (Exception e) {
                logger.error("日期转换错误", e);
                completedTime = null;
            }
        }
        businessLogVO.setCompletedTime(completedTime);
        List<BusinessLogVO> allData = businessLogMapper.selectBusinessLog(businessLogVO);
        if (!CoreConstClass.T_ISSUE_JOINTLY.equals(businessLogVO.getApproveItemType()) && !CoreConstClass.T_CASE_LEGISLATION.equals(businessLogVO.getApproveItemType()) && allData != null && !allData.isEmpty() && compareTo(businessLogVO, allData.get(0)) == 0) {
            return 0;
        }
        return businessLogMapper.insertBusinessLog(businessLogVO);
    }


    public int compareTo(BusinessLogVO businessLogVO1, BusinessLogVO businessLogVO2) {
        boolean isEqual;
        if (businessLogVO2 == null) {
            return -1;
        } else if (businessLogVO1 == businessLogVO2) {
            return 0;
        } else {
            isEqual = (isSameByBaseFields(businessLogVO1.getBusinessField1(), businessLogVO2.getBusinessField1())
                    && isSameByBaseFields(businessLogVO1.getBusinessField2(), businessLogVO2.getBusinessField2())
                    && isSameByBaseFields(businessLogVO1.getApproveItemId(), businessLogVO2.getApproveItemId()))
                    &&(isSameByFilesId(businessLogVO1.getFileId(), businessLogVO2.getFileId()));
        }
        return isEqual ? 0 : -1;
    }

    private boolean isSameByFilesId(String fileId1, String fileId2) {
        if (fileId1 != null && fileId2 != null){
            List<String> list1 = Arrays.asList(fileId1.split(","));
            List<String> list2 = Arrays.asList(fileId2.split(","));
            list1.sort(String::compareTo);
            list2.sort(String::compareTo);
            return Iterators.elementsEqual(list1.iterator(), list2.iterator());
        } else if (fileId1 == null && fileId2 == null){
            return true;
        }
        return false;
    }

    private boolean isSameByBaseFields(String field1, String field2) {
        if (field1 != null) {
            return field1.equals(field2);
        } else {
            return field2 == null;
        }
    }

    /**
     * 法律支撑，纠纷处理  查询纠纷处理意见
     * @param businessLogVO
     * @return
     */
    @Override
    public List<BusinessLogVO> selectBusinessLog(BusinessLogVO businessLogVO) {
        List<BusinessLogVO> allData = businessLogMapper.selectBusinessLog(businessLogVO);
        //查询历史纠纷日志表所有的数据
        StringBuilder bld = new StringBuilder();
        //拼接所有的列表中文件ID
        for (int i = 0; i < allData.size(); i++) {
            if (StringUtils.isNotBlank(allData.get(i).getFileId())) {
                bld.append(","+allData.get(i).getFileId());
            }
        }
        String str = bld.toString();
        //分割列表中的文件id
        String[] fiIds = str.split(",");
        //获取列表数据所有相关的文件信息
        List<Map<String, Object>> maps = businessLogMapper.selectFiles(fiIds);
        //循环所有的文件列表数据 因为这样才能按文件的时间倒序排序 在历史记录中也保持倒序排序
        for(int i= 0;i<maps.size();i++){
            Map<String, Object> data = maps.get(i);
            String dataFileId = (String) data.get("file_id");
            if(StringUtils.isNotBlank(dataFileId)){
                getNewMap(allData,dataFileId,data);
            }
        }
        return allData;
    }

    @Override
    public List<BusinessLogVO> selectBusinessLog2(BusinessLogVO businessLogVO) {
        List<PbusinessLogVO> pbusinessLogVOS = businessLogMapper.selectBusinessLog2(businessLogVO);
        List<BusinessLogVO> allData = new ArrayList<>();
        pbusinessLogVOS.forEach(x -> {
            if (x.getBusinessLogVOS()  != null) {
                allData.addAll(x.getBusinessLogVOS());
            }
        });
        for (int i = 0; i < allData.size(); i++) {
            setBusinessFiles(allData.get(i));
        }
        return allData;
    }

    private void setBusinessFiles(BusinessLogVO data) {
        if (data == null) {
            return;
        }
        if (StringUtils.isNotBlank(data.getFileId())) {
            List<Map<String, Object>> maps = businessLogMapper.selectFiles(data.getFileId().split(","));
            data.setFiles(maps);
        }
        if (data.getSubBusinessLog() != null && data.getSubBusinessLog().size() > 0) {
            for (int i = 0; i < data.getSubBusinessLog().size(); i++) {
                setBusinessFiles(data.getSubBusinessLog().get(i));
            }
        }
    }

    @Override
    public List<String> selectBusinessLogConfig(String approveItemId, String defName) {
        return businessLogConfigMapper.selectBusinessLogConfig(approveItemId, defName);
    }

    @Override
    public ProcessResult selectAll() {
        List<BusinessLogConfigVO> businessLogConfigVOS = null;
        try {
            businessLogConfigVOS = businessLogConfigMapper.selectAll();
        } catch (Exception e) {
            logger.error("查询纠纷处理意见配置失败：", e);
            return new ProcessResult(ProcessResult.SUCCESS, CoreConstClass.FAILURE_MESS);
        }
        return new ProcessResult(ProcessResult.SUCCESS, CoreConstClass.SUCCESS, businessLogConfigVOS);
    }

    @Override
    public ProcessResult addConfig(BusinessLogConfigVO businessLogConfigVO) {
        int i = 0;
        try {
            i = businessLogConfigMapper.addConfig(businessLogConfigVO);
        } catch (Exception e) {
            logger.error("新增纠纷处理意见配置失败：", e);
            return new ProcessResult(ProcessResult.SUCCESS, CoreConstClass.FAILURE_MESS);
        }
        return new ProcessResult(ProcessResult.SUCCESS, CoreConstClass.SUCCESS, i);
    }

    @Override
    public ProcessResult delConfig(List<BusinessLogConfigVO> businessLogConfigVOS) {
        int i = 0;
        try {
            i = businessLogConfigMapper.delConfig(businessLogConfigVOS);
        } catch (Exception e) {
            logger.error("删除纠纷处理意见配置失败：", e);
            return new ProcessResult(ProcessResult.SUCCESS, CoreConstClass.FAILURE_MESS);
        }
        return new ProcessResult(ProcessResult.SUCCESS, CoreConstClass.SUCCESS, i);
    }

    @Override
    public ProcessResult updateConfig(List<BusinessLogConfigVO> businessLogConfigVOS) {
        int i = 0;
        try {
            i = businessLogConfigMapper.updateConfig(businessLogConfigVOS);
        } catch (Exception e) {
            logger.error("修改纠纷处理意见配置失败：", e);
            return new ProcessResult(ProcessResult.SUCCESS, CoreConstClass.FAILURE_MESS);
        }
        return new ProcessResult(ProcessResult.SUCCESS, CoreConstClass.SUCCESS, i);
    }

    @Override
    public List<BusinessLogConfigVO> selectConfigActs(String processDefName, Integer versionId) {
        return businessLogConfigMapper.selectConfigActs(processDefName, versionId);
    }

    @Override
    public int selectBusinessConfigByIds(String activityDefId, String approveItemId, String deptId) {
        return businessLogConfigMapper.selectBusinessConfigByIds(activityDefId, approveItemId, deptId);
    }


    /**
     *
     * @param allData 历史纠纷意见的列表
     * @param dataFileId 文件id
     * @param data  文件对象
     * @return
     */
    private List<BusinessLogVO> getNewMap(List<BusinessLogVO> allData, String dataFileId,Map<String, Object> data) {
        for(int i=0;i<allData.size();i++){
            String fileId = allData.get(i).getFileId();
            boolean flag = false;
            //如果包含了这个文件就要放到 纠纷记录里面去
            if(StringUtils.isNotBlank(fileId) && fileId.indexOf(dataFileId)>-1){
                flag = true;
            }
            if(flag){
                List<Map<String, Object>> files = allData.get(i).getFiles();
                if(files!=null && !files.isEmpty()){
                    files.add(data);
                }else{
                    files = new ArrayList<>();
                    files.add(data);
                }
                allData.get(i).setFiles(files);
            }
        }
        return allData;
    }

    /*private List<Map<String, Object>> getMap(String[] arr, List<Map<String, Object>> maps) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            String id = arr[i];
            for (Map<String, Object> data:maps) {
                if (id.equals(data.get("file_id"))) {
                    list.add(data);
                }
            }
        }
        return list;
    }*/

}
