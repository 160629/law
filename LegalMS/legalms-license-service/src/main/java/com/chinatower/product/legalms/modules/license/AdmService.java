package com.chinatower.product.legalms.modules.license;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.common.InterfaceLogUtil;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.modules.license.api.AdmAPI;
import com.chinatower.product.legalms.modules.license.commen.ResponseAdm;
import com.chinatower.product.legalms.modules.license.commen.ServerResponseAdm;
import com.chinatower.product.legalms.modules.license.entity.AdmVO;
import com.chinatower.product.legalms.modules.license.mapper.AdmMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class AdmService implements AdmAPI {

    private static final Logger logger = LoggerFactory.getLogger("TransLog");

    @Autowired
    private AdmMapper admMapper;

    /**
     * 新增或修改行政区
     */
    @Override
    @ResponseBody
    public ServerResponseAdm recieveAdm(@RequestBody String json) {
        try {

            logger.info(InterfaceLogUtil.reqTransLog("请求参数为:" + JSON.toJSONString(json)));

            JSONObject jsonObject = JSON.parseObject(json);//json字符串转化成json数据

            String listJson = jsonObject.get("listJson").toString();

            JSONArray arr = JSON.parseArray(listJson);

            return getServerResponseAdm(arr);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR, e);
            return ServerResponseAdm.error(ResponseAdm.ERROR);
        }
    }

    private ServerResponseAdm getServerResponseAdm(JSONArray arr) {
        for (int i = 0; i < arr.size(); i++) {
            JSONObject orgbeanjson = JSON.parseObject(arr.get(0).toString());
            String mdmAdmCode = (String) orgbeanjson.get("mdm_adm_code");
            String admCode = (String) orgbeanjson.get("adm_code");
            String admName = (String) orgbeanjson.get("adm_name");
            String admAlias = (String) orgbeanjson.get("adm_alias");
            Integer admLevel = (Integer) orgbeanjson.get("adm_level");
            String parentAdmCode = (String) orgbeanjson.get("parent_adm_code");
            String admStatus = (String) orgbeanjson.get("adm_status");
            String lastLevel = (String) orgbeanjson.get("last_level");
            String changeType = (String) orgbeanjson.get("change_type");
            String relationAdmCode = (String) orgbeanjson.get("relation_adm_code");
            String admRemark = (String) orgbeanjson.get("adm_remark");
            String createdBy = (String) orgbeanjson.get("created_by");
            String creationDate = (String) orgbeanjson.get("creation_date");
            String lastUpdatedBy = (String) orgbeanjson.get("last_updated_by");
            String lastUpdateDate = (String) orgbeanjson.get("last_update_date");
            Integer admBaseId = (Integer) orgbeanjson.get("adm_base_id");
            String admPath = (String) orgbeanjson.get("adm_path");
            Integer companyId = (Integer) orgbeanjson.get("company_id");
            Integer lastUpdateLogin = (Integer) orgbeanjson.get("last_update_login");
//            if (isEmpty(mdmAdmCode, admCode, admName, admLevel, admStatus, lastLevel))
//                return ServerResponseAdm.error(ResponseAdm.ERROR);
//            if (isEmptyl(createdBy, creationDate, lastUpdatedBy, lastUpdateDate))
//                return ServerResponseAdm.error(ResponseAdm.ERROR);
//            if (isSpecialSymbols(mdmAdmCode, admCode, admName, admStatus, lastLevel))
//                return ServerResponseAdm.error(ResponseAdm.ERROR);
//            if (isSpecialSymbolsl(createdBy, creationDate, lastUpdatedBy))
//                return ServerResponseAdm.error(ResponseAdm.ERROR);
//            if (RequestHolder.getLength(admLevel) > 1) {
//                return ServerResponseAdm.error(ResponseAdm.ERROR);
//            }
            AdmVO admVO = new AdmVO();
            admVO.setMdmAdmCode(mdmAdmCode);
            admVO.setAdmCode(admCode);
            admVO.setAdmName(admName);
            admVO.setAdmAlias(admAlias);
            admVO.setAdmLevel(admLevel);
            admVO.setParentAdmCode(parentAdmCode);
            admVO.setAdmStatus(admStatus);
            admVO.setLastLevel(lastLevel);
            admVO.setChangeType(changeType);
            admVO.setRelationAdmCode(relationAdmCode);
            admVO.setAdmRemark(admRemark);
            admVO.setCreatedBy(createdBy);
            admVO.setLastUpdatedBy(lastUpdatedBy);
            if (creationDate != null && !creationDate.equals("null")) {
                admVO.setCreationDate(strToDateLong(creationDate));
            }
            if (lastUpdateDate != null && !lastUpdateDate.equals("null")) {
                admVO.setLastUpdateDate(strToDateLong(lastUpdateDate));
            }
            admVO.setAdmBaseId(admBaseId);
            admVO.setAdmPath(admPath);
            admVO.setCompanyId(companyId);
            admVO.setLastUpdateLogin(lastUpdateLogin);
            int count = admMapper.selectAllAdmVO(admCode);
            if (count == 0) {
                admMapper.addAdmVO(admVO);
                logger.info(InterfaceLogUtil.rspTransLog(SystemInfo.SYSTEM_RESULT + SystemInfo.ADM_INSERT_SUCCESS));
            } else {
                admMapper.updateAdmVO(admVO);
                logger.info(InterfaceLogUtil.rspTransLog(SystemInfo.SYSTEM_RESULT + SystemInfo.ADM_UPDATE_SUCCESS));
            }
        }
        return ServerResponseAdm.success(ResponseAdm.SUCCESS);
    }
//
//    private boolean isSpecialSymbols(String mdmAdmCode, String admCode, String admName, String admStatus, String lastLevel) {
//        return RequestHolder.specialSymbols(mdmAdmCode) ||
//                RequestHolder.specialSymbols(admCode) ||
//                RequestHolder.specialSymbols(admName) ||
//                RequestHolder.specialSymbols(admStatus) ||
//                RequestHolder.specialSymbols(lastLevel);
//
//
//    }
//
//    private boolean isSpecialSymbolsl(String createdBy, String creationDate, String lastUpdatedBy) {
//        return RequestHolder.specialSymbols(createdBy) ||
//                RequestHolder.specialSymbols(creationDate) ||
//                RequestHolder.specialSymbols(lastUpdatedBy);
//
//    }
//
//    private boolean isEmpty(String mdmAdmCode, String admCode, String admName, Integer admLevel, String admStatus, String lastLevel) {
//        return StringUtils.isBlank(mdmAdmCode) || StringUtils.isBlank(admCode) ||
//                StringUtils.isBlank(admName) ||
//                admLevel == null ||
//                StringUtils.isBlank(admStatus) ||
//                StringUtils.isBlank(lastLevel);
//
//    }
//
//    private boolean isEmptyl(String createdBy, String creationDate, String lastUpdatedBy, String lastUpdateDate) {
//        return StringUtils.isBlank(createdBy) ||
//                StringUtils.isBlank(creationDate) ||
//                StringUtils.isBlank(lastUpdatedBy) || !isLegalDate(creationDate) ||
//                (!isLegalDate(lastUpdateDate) && StringUtils.isNotEmpty(lastUpdateDate));
//    }


    //将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(strDate, pos);
    }

    @Override
    public ProcessResult selectAdmByAdmLevelAndParentAdmCode(@RequestBody AdmVO admVO) {
        List<AdmVO> admVOS = null;
        if (StringUtils.isNotBlank(admVO.getParentAdmCode()) || admVO.getAdmLevel() != null) {
            admVOS = admMapper.selectAdmByAdmLevelAndParentAdmCode(admVO);
        }
        return new ProcessResult(ProcessResult.SUCCESS, "", admVOS);
    }

//    private static boolean isLegalDate(String sDate) {
////        int legalLen = 10;
////        if ((sDate == null) || (sDate.length() != legalLen)) {
////            return false;
////        }
//
//        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        try {
//            Date date = formatter.parse(sDate);
//            return sDate.equals(formatter.format(date));
//        } catch (Exception e) {
//            return false;
//        }
//    }

}
