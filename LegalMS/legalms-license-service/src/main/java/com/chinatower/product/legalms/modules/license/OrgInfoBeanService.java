package com.chinatower.product.legalms.modules.license;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.common.InterfaceLogUtil;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.modules.license.api.OrgInfoBeanAPI;
import com.chinatower.product.legalms.modules.license.commen.ResponseOrg;
import com.chinatower.product.legalms.modules.license.commen.ServerResponseOrg;
import com.chinatower.product.legalms.modules.license.entity.OrgBeanVO;
import com.chinatower.product.legalms.modules.license.entity.OrgInfoBeanVO;
import com.chinatower.product.legalms.modules.license.mapper.OrgBeanVOMappper;
import com.chinatower.product.legalms.modules.license.mapper.OrgInfoBeanVOMappper;
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

@RestController
public class OrgInfoBeanService implements OrgInfoBeanAPI {
    private static final Logger logger = LoggerFactory.getLogger("TransLog");

    @Autowired
    private OrgInfoBeanVOMappper orgInfoBeanVOMappper;

    @Autowired
    private OrgBeanVOMappper orgBeanVOMappper;
    @Override
    @ResponseBody
    public ServerResponseOrg recieveOrginfoBean(@RequestBody String json) {
        try {
            logger.info(InterfaceLogUtil.reqTransLog("请求参数为:" + JSON.toJSONString(json)));

            JSONObject jsonObject = JSON.parseObject(json);//json字符串转化成json数据

            String listJson = jsonObject.get("listJson").toString();

            JSONArray arr = JSON.parseArray(listJson);

            for (int i = 0; i < arr.size(); i++) {
                JSONObject orgbeanjson = JSON.parseObject(arr.get(0).toString());
                String orgCode = (String) orgbeanjson.get("org_code");
                String orgName = (String) orgbeanjson.get("org_name");
                String orgLevel = (String) orgbeanjson.get("org_level");
                String orgParentCode = (String) orgbeanjson.get("org_parent_code");
                String enterpType = (String) orgbeanjson.get("enterp_type");
                String orgStatus = (String) orgbeanjson.get("org_status");
                String orgChange = (String) orgbeanjson.get("org_change");
                String orgRelation = (String) orgbeanjson.get("org_relation");
                String orgRemarks = (String) orgbeanjson.get("org_remarks");
                String orgPath = (String) orgbeanjson.get("org_path");
                String createdBy = (String) orgbeanjson.get("created_by");
                String creationDate = (String) orgbeanjson.get("creation_date");
                String lastUpdatedBy = (String) orgbeanjson.get("last_updated_by");
                String lastUpdateDate = (String) orgbeanjson.get("last_update_date");
                String mdmOrgCode = (String) orgbeanjson.get("mdm_org_code");
                Integer orgBaseId = (Integer) orgbeanjson.get("org_base_id");
                String orgReservedText1 = (String) orgbeanjson.get("org_reserved_text_1");
                OrgInfoBeanVO orginfoBeanVO = new OrgInfoBeanVO();
                orginfoBeanVO.setOrgCode(orgCode);
                orginfoBeanVO.setOrgName(orgName);
                orginfoBeanVO.setOrgLevel(orgLevel);
                orginfoBeanVO.setOrgParentCode(orgParentCode);
                orginfoBeanVO.setEnterpType(enterpType);
                orginfoBeanVO.setOrgStatus(orgStatus);
                orginfoBeanVO.setOrgChange(orgChange);
                orginfoBeanVO.setOrgRelation(orgRelation);
                orginfoBeanVO.setOrgRemarks(orgRemarks);
                orginfoBeanVO.setOrgPath(orgPath);
                orginfoBeanVO.setCreatedBy(createdBy);
                orginfoBeanVO.setCreationDate(strToDateLong(creationDate));
                orginfoBeanVO.setLastUpdatedBy(lastUpdatedBy);
                orginfoBeanVO.setLastUpdateDate(strToDateLong(lastUpdateDate));
                orginfoBeanVO.setMdmOrgCode(mdmOrgCode);
                orginfoBeanVO.setOrgBaseId(orgBaseId);
                orginfoBeanVO.setOrgReservedText1(orgReservedText1);

                int count = orgInfoBeanVOMappper.selectAllOrgInfoBean(orgCode);

                if (count == 0) {
//                    if(StringUtils.isBlank(createdBy)||StringUtils.isBlank(creationDate)||
//                            StringUtils.isBlank(enterpType)||
//                            StringUtils.isBlank(orgCode)||
//                            StringUtils.isBlank(orgLevel)||
//                            StringUtils.isBlank(orgName)||
//                            StringUtils.isBlank(orgParentCode)||
//                            StringUtils.isBlank(orgStatus)||!isLegalDate(creationDate)
//                            ){
//                        return ServerResponseOrg.error(ResponseOrg.ERROR);
//                    }
//                    if(RequestHolder.specialSymbols(createdBy)||
//                            RequestHolder.specialSymbols(creationDate)||
//                            RequestHolder.specialSymbols(enterpType)||
//                            RequestHolder.specialSymbols(orgCode)||
//                            RequestHolder.specialSymbols(orgLevel)||
//                            RequestHolder.specialSymbols(orgName)||
//                            RequestHolder.specialSymbols(orgParentCode)||
//                            RequestHolder.specialSymbols(orgStatus)){
//                        return ServerResponseOrg.error(ResponseOrg.ERROR);
//                    }
                    orgInfoBeanVOMappper.addOrginfoBean(orginfoBeanVO);
                    logger.info(InterfaceLogUtil.rspTransLog(SystemInfo.SYSTEM_RESULT + SystemInfo.ORGBEANINFO_INSERT_SUCCESS));
                } else {
                    orgInfoBeanVOMappper.updateOrginfoBean(orginfoBeanVO);
                    logger.info(InterfaceLogUtil.rspTransLog(SystemInfo.SYSTEM_RESULT + SystemInfo.ORGBEANINFO_UPDATE_SUCCESS));
                }
                OrgBeanVO orgBeanVO = new OrgBeanVO();
                orgBeanVO.setOrgId(orgCode);
                orgBeanVO.setOrgCode(orgCode);
                orgBeanVO.setOrgName(orgName);
                orgBeanVO.setOrgLevel(orgLevel);
                orgBeanVO.setOrgParentCode(orgParentCode);
                orgBeanVO.setEnterpType(enterpType);
                orgBeanVO.setOrgStatus(orgStatus);
                orgBeanVO.setOrgChange(orgChange);
                orgBeanVO.setOrgRemarks(orgRemarks);
                orgBeanVO.setOrgPath(orgPath);
                orgBeanVO.setOrgCreateTime(strToDateLong(creationDate));
                orgBeanVO.setOrgStatusUpdateTime(strToDateLong(lastUpdateDate));
                orgBeanVO.setMdmOrgCode(mdmOrgCode);
                orgBeanVO.setOrgBaseId(orgBaseId);
                orgBeanVO.setOrgReservedText1(orgReservedText1);

                int count1 = orgBeanVOMappper.selectAllOrgBeanbyCode(orgCode);
                if (count1 == 0) {
                    orgBeanVOMappper.insertOrgBeanbyCode(orgBeanVO);
                    logger.info(InterfaceLogUtil.rspTransLog(SystemInfo.SYSTEM_RESULT + SystemInfo.ORGBEAN_INSERT_SUCCESS));
                } else {
                    orgBeanVOMappper.updateOrgBeanbyCode(orgBeanVO);
                    logger.info(InterfaceLogUtil.rspTransLog(SystemInfo.SYSTEM_RESULT + SystemInfo.ORGBEAN_UPDATE_SUCCESS));
                }
            }
            logger.info(JSONObject.toJSONString(ServerResponseOrg.success(ResponseOrg.SUCCESS)));
            return ServerResponseOrg.success(ResponseOrg.SUCCESS);
        }catch (Exception e){
            logger.error(ProcessResult.ERROR,e);
            return ServerResponseOrg.error(ResponseOrg.ERROR);
        }
    }

   /*@Override
    @ResponseBody
    public String test(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> stringHttpEntity = new HttpEntity<>(json, headers);
        String result = restTemplate.postForObject("http://123.126.34.157:1184/orginfoBean/recieveOrginfoBean", stringHttpEntity, String.class);
        return result;
    }*/

    //将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
    public static Date strToDateLong(String strDate) {
        if(StringUtils.isNotBlank(strDate)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ParsePosition pos = new ParsePosition(0);
            return formatter.parse(strDate, pos);
        }
        else {
            return null;
        }
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
