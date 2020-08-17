package com.chinatower.product.legalms.modules.cases.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.base.BaseController;
import com.chinatower.product.legalms.common.CaseInfo;
import com.chinatower.product.legalms.common.ConstClass;
import com.chinatower.product.legalms.common.InterfaceLogUtil;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.cases.entity.*;
import com.chinatower.product.legalms.modules.cases.issue.ListParam;
import com.chinatower.product.legalms.modules.cases.service.*;
import com.chinatower.product.legalms.utils.FileUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Slf4j
@RestController
@Api(tags = {"案件案宗接口"})
public class CaseMainApiImpl extends BaseController implements CaseMainApi{

    private static final Logger logger = LoggerFactory.getLogger(CaseMainApiImpl.class);
    @Autowired
    private CaseMainService caseMainService;

    @Autowired
    private FileShareVOService fileShareVOService;

    @Autowired
    RelationshipService relationshipService;

    @Autowired
    TFlowMainService tFlowMainService;

    @Autowired
    private RulingExecutiveService rulingExecutiveService;

    @Override
    @ApiOperation("卷宗列表接口")
    public ProcessResult selCaseMain(@RequestBody ListParam param) {
        try {
//            PageHelper.startPage(param.getPageNum(), param.getPageSize());
//            List<CaseMainVO> caseMainList= caseMainService.selectAllCaseMain(param);
//            PageInfo<CaseMainVO> pageInfo = new PageInfo<>(caseMainList);
            PageInfo<CaseMainVO> pageInfo = caseMainService.selectAllCaseMain(param);
            log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.SELECT_SUCCESS));
            return new ProcessResult(ProcessResult.SUCCESS, ConstClass.SUCCESS.SELECT_CASE_MAIN_LIST_SUCCESS,pageInfo);
        }catch (Exception e){
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.SELECT_CASE_MAIN_LIST_FAILED);
        }
    }

    @Override
    @ApiIgnore
    public ProcessResult selCaseMains(@RequestBody ListParam param) {
        try {
           return caseMainService.selectAllCaseMains(param);
        }catch (Exception e){
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.SELECT_CASE_MAINS_FAILED);
        }
    }

    @Override
    @ApiOperation("卷宗关联查询接口")
    public ProcessResult selRelevanceCaseMain(@RequestBody ListParam param) {
        try {
            PageHelper.startPage(param.getPageNum(), param.getPageSize());
            List<CaseMainVO> caseMainList= caseMainService.selectAllRelevanceCaseMain(param);
            PageInfo<CaseMainVO> pageInfo = new PageInfo<>(caseMainList);
            log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.SELECT_SUCCESS));
            return new ProcessResult(ProcessResult.SUCCESS, ConstClass.SUCCESS.SELECT_RELEVANCE_CASE_MAIN_SUCCESS,pageInfo);
        }catch (Exception e){
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.SELECT_RELEVANCE_CASE_MAIN_FAILED);
        }
    }

/*    @Override
    public ProcessResult selRelevanceCaseMains(@RequestBody ListParam param) {
        try {
            PageHelper.startPage(param.getPageNum(), param.getPageSize(),"last_update_time desc");
            List<CaseMainVO> caseMainList= caseMainService.selectAllRelevanceCaseMains(param);
            PageInfo<CaseMainVO> pageInfo = new PageInfo<>(caseMainList);
            log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.SELECT_SUCCESS));
            return new ProcessResult(ProcessResult.SUCCESS,"",pageInfo);
        }catch (Exception e){
            log.info(CaseInfo.OPERATION_EXCEPTION+e);
            return new ProcessResult(ProcessResult.ERROR,e.getMessage());
        }
    }*/

    @Override
    @ApiOperation("卷宗权限查询")
    public ProcessResult selectAllCaseMainByDept(@RequestBody ListParam param) {
        try {
            PageInfo<CaseMainVO> pageInfo = caseMainService.selectAllCaseMainByDept(param);
            log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.SELECT_SUCCESS));
            return new ProcessResult(ProcessResult.SUCCESS,ConstClass.SUCCESS.SELECT_ALL_CASE_MAIN_BY_DEPT_SUCCESS,pageInfo);
        }catch (Exception e){
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.SELECT_ALL_CASE_MAIN_BY_DEPT_FAILED);
        }
    }

    @Override
    @ApiOperation("卷宗创建接口")
    public ProcessResult addCaseMain(@RequestBody CaseMainVO caseMainVO) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(caseMainVO)));
        int a;
        try {
         /*   long timestamp = System.currentTimeMillis();
            String nonce = String.valueOf(new Random().nextInt(10));
            String id = CaseInfo.ID_HEAD_MAIN+timestamp+nonce;
            caseMainVO.setCaseId(id);*/
            //获取用户信息
            UserInfo userInfo = RequestHolder.getUserInfo();
            caseMainVO.setDrafterUnit(userInfo.getOrgId());
//            String unitName = userInfo.getUnitCode();
//            String orgCode = userInfo.getOrgCode();
            caseMainVO.setCreatorAccountName(userInfo.getLoginName());
            caseMainVO.setCreatorAccountId(userInfo.getLoginAcct());
            caseMainVO.setCreatorCell(userInfo.getMobile());
            caseMainVO.setCreatorDeptId(userInfo.getDeptId());
            caseMainVO.setCreatorDeptName(userInfo.getDeptName());
            caseMainVO.setCreatorUnitId(userInfo.getOrgId());
            a = caseMainService.addCaseMain(caseMainVO);
            String caseId=caseMainVO.getCaseId();
            log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.INSERT_SUCCESS+a));
            /**
             * 2019年12月7日16:29:13
             */
            if(StringUtils.isNotBlank(caseMainVO.getCaseFile())) {
                String[] arr = caseMainVO.getCaseFile().split(",");
                for(int i=0;i<arr.length;i++){
                    if(StringUtils.isNotBlank(arr[i])){
                        if(i==0){
                            fileShareVOService.deleteByCase(caseMainVO.getCaseId(),"casemain");
                        }
                        String shareid = UUID.randomUUID().toString().replaceAll("-", "") + new Random().nextLong();
                        FileShareVO fileShareVO = new FileShareVO();
                        fileShareVO.setFileShareBusinessKey(caseMainVO.getCaseId());
                        fileShareVO.setFileId(arr[i]);
                        fileShareVO.setFileShareType("casemain");
                        fileShareVO.setFileShareId(shareid);
                        fileShareVOService.insertFileShare(fileShareVO);
                    }
                }
            }
            return new ProcessResult(ProcessResult.SUCCESS,ConstClass.SUCCESS.CREATE_CASE_SUCCESS,caseId);
        }catch (Exception e){
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.CREATE_CASE_FAILED);
        }
    }

//    @Override
//    @ApiOperation("卷宗详情接口")
//    public ProcessResult selectCaseMain(@RequestBody CaseMainVO caseMain) {
//        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(caseMain)));
//        try {
//            CaseMainVO caseMainVO = caseMainService.selectCaseMain(caseMain.getCaseId());
//            setImportance(caseMainVO);
//            List<FileMainVO> fileMainVOS = fileShareVOService.selectFileById(caseMain.getCaseId(),caseMain.getShareType());
//            RelationshipVO relationshipVO = new RelationshipVO();
//            relationshipVO.setCaseId(caseMain.getCaseId());
//            List<RelationshipVO> relationshipVOS = relationshipService.selectRelationship(relationshipVO);
//            if (relationshipVOS != null && !relationshipVOS.isEmpty()) {
//                caseMainVO.setGuideId(relationshipVOS.get(0).getBusinessId());
//                caseMainVO.setGuideTitle(relationshipVOS.get(0).getTittle());
//            }
//            Map<String,Object> map = new HashMap<>();
//            map.put("caseMainVO",caseMainVO);
//            map.put("fileMainVOS",fileMainVOS);
//            log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.SELECT_SUCCESS+map));
//            return new ProcessResult(ProcessResult.SUCCESS,ConstClass.SUCCESS.CREATE_CASE_SUCCESS,map);
//        }catch (Exception e){
//            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
//            return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.SELECT_ALL_CASE_MAIN_BY_DEPT_FAILED);
//        }
//    }

    /**
     * 查询案件主表信息，及裁决执行细信息
     * @param caseMain
     * @return
     */
    @Override
    @ApiOperation("卷宗详情接口")
    public ProcessResult selectCaseMain(@RequestBody CaseMainVO caseMain) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(caseMain)));
        try {
            // 查询卷宗主表信息
            CaseMainVO caseMainVO = caseMainService.selectCaseMain(caseMain.getCaseId());
            // 设置重要程度
            setImportance(caseMainVO);
            // 查询卷宗关联的所有文件
            List<FileMainVO> fileMainVOS = fileShareVOService.selectFileById(caseMain.getCaseId(),caseMain.getShareType());
            // 查询与该卷宗关联的流程信息
            RelationshipVO relationshipVO = new RelationshipVO();
            relationshipVO.setCaseId(caseMain.getCaseId());
            List<RelationshipVO> relationshipVOS = relationshipService.selectRelationship(relationshipVO);
            // 现在好像没用了？
            if (relationshipVOS != null && !relationshipVOS.isEmpty()) {
                caseMainVO.setGuideId(relationshipVOS.get(0).getBusinessId());
                caseMainVO.setGuideTitle(relationshipVOS.get(0).getTittle());
            }
            // 查询裁决执行信息
            RulingExecutiveVO rulingExecutiveVO = rulingExecutiveService.selectRulingExecutive(caseMain.getCaseId());
            // 组装返回数据
            Map<String,Object> map = new HashMap<>();
            map.put("caseMainVO",caseMainVO);
            map.put("fileMainVOS",fileMainVOS);
            map.put("rulingExecutiveVO",rulingExecutiveVO);
            log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.SELECT_SUCCESS+map));
            return new ProcessResult(ProcessResult.SUCCESS,ConstClass.SUCCESS.CREATE_CASE_SUCCESS,map);
        }catch (Exception e){
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.SELECT_ALL_CASE_MAIN_BY_DEPT_FAILED);
        }
    }

    private void setImportance(CaseMainVO caseMainVO) {
        if (!StringUtils.isNotBlank(caseMainVO.getLargeLawsuitMark()) && caseMainVO.getCaseDeputeMoney() != null && !caseMainVO.getCaseDeputeMoney().equals(BigDecimal.valueOf(0))) {
            if (new BigDecimal(caseMainVO.getCaseDeputeMoney()).compareTo(BigDecimal.valueOf(10000000)) <= 0) {
                caseMainVO.setLargeLawsuitMark("commonly");
            } else {
                caseMainVO.setLargeLawsuitMark("weighty");
            }
        }
    }

    @Override
    @ApiOperation("卷宗结案创建/修改接口")
//    @ApiImplicitParams({@ApiImplicitParam(name = "caseId", value = "卷宗ID", required = true),
//            @ApiImplicitParam(name = "caseExplain", value = "结案说明", required = true)
//    })
    public ProcessResult updateExplain(@RequestBody CaseMainVO caseMainVO) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(caseMainVO)));
        String a;
        try {
            // 更新裁决执行信息
            int count = rulingExecutiveService.selectAllRulingExecutiveByCount(caseMainVO.getCaseId());
            if (count == 0) {
                rulingExecutiveService.addRulingExecutive(caseMainVO.getRulingExecutiveVO());
                log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.INSERT_SUCCESS));
            } else {
                rulingExecutiveService.updateRulingExecutive(caseMainVO.getRulingExecutiveVO());
                log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.UPDATE_SUCCESS));
            }
            // 更新结案信息
            a = caseMainService.updateExplain(caseMainVO);
            if(a.equals(CaseInfo.UPDATE_S)){
                log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.UPDATE_SUCCESS+a));
                return new ProcessResult(ProcessResult.SUCCESS,ConstClass.SUCCESS.CREATE_CASE_SUCCESS,a);
            }else{
                return new ProcessResult(ProcessResult.ERROR, a);
            }
        }catch (Exception e){
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.CREATE_CASE_FAILED);
        }
    }

    @Override
    @ApiOperation("卷宗修改案件状态接口")
    public ProcessResult updateCaseStatus(@RequestBody CaseMainVO caseMainVO) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(caseMainVO)));
        String a;
        try {
//            a = caseMainService.updateCaseStatusById(caseMainVO);
//            log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.UPDATE_SUCCESS+a));
//            return new ProcessResult(ProcessResult.SUCCESS,ConstClass.SUCCESS.SUCCESS_MESS,a);
            a = caseMainService.updateCaseStatusById(caseMainVO);
            if(!a.endsWith("审批流程未结束")){
                log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.UPDATE_SUCCESS+a));
                return new ProcessResult(ProcessResult.SUCCESS,ConstClass.SUCCESS.CREATE_CASE_SUCCESS,a);
            }else{
                return new ProcessResult(ProcessResult.ERROR, a);
            }
        }catch (Exception e){
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.FAILURE_MESS);
        }
    }

    @Override
    @ApiOperation("卷宗裁决类别修改接口")
//    @ApiImplicitParams({@ApiImplicitParam(name = "caseId", value = "卷宗ID", required = true),
//            @ApiImplicitParam(name = "rulingClasses", value = "裁决类别", required = true)
//    })
    public ProcessResult updaterulingClassesById(@RequestBody CaseMainVO caseMainVO) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(caseMainVO)));
        int a;
        try {
            String caseId = caseMainVO.getCaseId();
            String rulingClasses = caseMainVO.getRulingClasses();
            String caseReviewGrade = caseMainVO.getCaseReviewGrade();
            a = caseMainService.updaterulingClassesById(caseId,rulingClasses,caseReviewGrade);
            log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.UPDATE_SUCCESS+a));
            return new ProcessResult(ProcessResult.SUCCESS, ConstClass.SUCCESS.UPDATE_RULING_CLASSES_SUCCESS,a);
        }catch (Exception e){
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.UPDATE_RULING_CLASSES_FAILED);
        }
    }

    @Override
    @ApiOperation("卷宗修改接口")
    public ProcessResult updateCaseMain(@RequestBody CaseMainVO caseMainVO) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(caseMainVO)));
        int a;
        try {
            a = caseMainService.updateCaseMain(caseMainVO);
            log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.UPDATE_SUCCESS+a));
            return new ProcessResult(ProcessResult.SUCCESS,ConstClass.SUCCESS.UPDATE_CASE_MAIN_SUCCESS,a);
        }catch (Exception e){
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.UPDATE_CASE_MAIN_FAILED);
        }
    }

    @Override
    @ApiOperation("卷宗删除接口")
    public ProcessResult deleteCaseMain(@RequestBody CaseMainVO caseMainVO) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(caseMainVO)));
        int a;
        try {
            a = caseMainService.deleteCaseMainByIds(caseMainVO.getCaseId());
            log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.UPDATE_SUCCESS+a));
            return new ProcessResult(ProcessResult.SUCCESS,ConstClass.SUCCESS.DELETE_CASE_MAIN_SUCCESS,a);
        }catch (Exception e){
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.DELETE_CASE_MAIN_FAILED);
        }
    }

    @Override
    public ProcessResult deleteCaseMains(@RequestBody List<CaseMainVO> caseMainVOS) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(caseMainVOS)));
        int a;
        try {
            if (caseMainVOS != null && !caseMainVOS.isEmpty()) {
                a = caseMainService.deleteCaseMainByIdsArray(caseMainVOS);
                log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.UPDATE_SUCCESS+a));
                return new ProcessResult(ProcessResult.SUCCESS,ConstClass.SUCCESS.DELETE_CASE_MAIN_SUCCESS,a);
            } else {
                logger.error(CaseInfo.OPERATION_EXCEPTION,"批量删除卷宗失败" + caseMainVOS);
                return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.DELETE_CASE_MAIN_FAILED);
            }
        }catch (Exception e){
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.DELETE_CASE_MAIN_FAILED);
        }
    }


    @Override
    @ApiOperation("卷宗修改或创建接口")
    public ProcessResult addOrUpdateCaseMain(@RequestBody CaseMainVO caseMainVO) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(caseMainVO)));
        try {
            RelationshipVO relationshipVO = new RelationshipVO();
            int count = caseMainService.selectAllCaseMainByCount(caseMainVO.getCaseId());
            if (StringUtils.isNotBlank(caseMainVO.getGuideId())) {
                relationshipVO.setCaseId(caseMainVO.getCaseId());
                relationshipVO.setBusinessId(caseMainVO.getGuideId());
                relationshipVO.setTittle(caseMainVO.getGuideTitle());
                TFlowMain tFlowMain = tFlowMainService.selectFlowMainByBusinessId(caseMainVO.getGuideId());
                relationshipVO.setBusinessType(tFlowMain.getApproveItemType());
                relationshipVO.setModuleName(tFlowMain.getModuleName());
                relationshipVO.setIsDelete("1");
                relationshipVO.setIsAuto("0");
            }
            if (count == 0) {
                //获取用户信息
                UserInfo userInfo = RequestHolder.getUserInfo();
                caseMainVO.setDrafterUnit(userInfo.getOrgId());
                caseMainVO.setCreatorAccountName(userInfo.getLoginName());
                caseMainVO.setCreatorAccountId(userInfo.getLoginAcct());
                caseMainVO.setCreatorCell(userInfo.getMobile());
                caseMainVO.setCreatorDeptId(userInfo.getDeptId());
                caseMainVO.setCreatorDeptName(userInfo.getDeptName());
                caseMainVO.setCreatorUnitId(userInfo.getOrgId());
                caseMainVO.setCreatorUnitName(userInfo.getOrgName());
                if (StringUtils.isBlank(caseMainVO.getCaseCode())) {
                    String caseCode = caseMainService.selectCode(userInfo.getUnitCode(),CaseInfo.CODE_NULL, userInfo.getOrgCode()).get(0);
                    caseMainVO.setCaseCode(caseCode);
                }
                caseMainService.addCaseMain(caseMainVO);
                String caseId=caseMainVO.getCaseId();
                if (StringUtils.isNotBlank(caseMainVO.getGuideId())) {
                    relationshipService.addRelationship(relationshipVO);
                }
                log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.INSERT_SUCCESS));
                return new ProcessResult(ProcessResult.SUCCESS,ConstClass.SUCCESS.UPDATE_CASE_MAIN_SUCCESS,caseId);
            } else {
                caseMainService.updateCaseMain(caseMainVO);
                String caseId=caseMainVO.getCaseId();
                if (StringUtils.isNotBlank(caseMainVO.getGuideId())) {
                    relationshipService.deleteRelationship(relationshipVO);
                    relationshipService.addRelationship(relationshipVO);
                }
                log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.UPDATE_SUCCESS));
                return new ProcessResult(ProcessResult.SUCCESS,ConstClass.SUCCESS.ADD_CASE_MAIN_SUCCESS,caseId);
            }
        }catch (Exception e){
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.ADD_OR_UPDATE_CASE_MAIN_FAILED);
        }
    }

    @Override
    @ApiOperation("卷宗自动创建创建接口")
    public ProcessResult addAutoCaseMain(@RequestBody String json) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(json)));
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CaseMainAutoVO caseMainAutoVO = objectMapper.readValue(json, CaseMainAutoVO.class);
            log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.INSERT_SUCCESS));
            UserInfo userInfo = RequestHolder.getUserInfo();
            if (StringUtils.isBlank(caseMainAutoVO.getCaseCode())) {
                String caseCode = caseMainService.selectCode(userInfo.getUnitCode(),CaseInfo.CODE_NULL, userInfo.getOrgCode()).get(0);
                caseMainAutoVO.setCaseCode(caseCode);
            }
            int count = caseMainService.addAutoCaseMain(caseMainAutoVO);
            return new ProcessResult(ProcessResult.SUCCESS,ConstClass.SUCCESS.AUTO_ADD_CASE_MAIN_SUCCESS,count);
        }catch (Exception e){
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.AUTO_ADD_CASE_MAIN_FAILED);
        }
    }

    @Override
    public void test(HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = caseMainService.getExcelDate();
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public ProcessResult nullifyCase(@RequestBody String params) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ params));
        Map<String, String> map = JSONObject.parseObject(params, Map.class);
        int a;
        try {
            a = caseMainService.nullifyCaseByIdsArray(map.get("oldCaseId"), map.get("newCaseId"));
            log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.UPDATE_SUCCESS+a));
            return new ProcessResult(ProcessResult.SUCCESS,ConstClass.SUCCESS.NULLIFY_CASE_MAIN_SUCCESS,a);
        }catch (Exception e){
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.NULLIFY_CASE_MAIN_FAILED);
        }
    }

    @Override
    public ProcessResult selectNewCaseMainList(@RequestBody NewCaseListParam param) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(param)));
        try {
            CaseMainVO caseMainVO = caseMainService.selectCaseMain(param.getNullifyCaseId());
            param.setCreatorUnitId(caseMainVO.getCreatorUnitId());
            PageHelper.startPage(param.getPageNum(), param.getPageSize());
            List<CaseMainVO> caseMainVOS = caseMainService.selectNewCaseMainList(param);
            PageInfo<CaseMainVO> caseMainVOPageInfo = new PageInfo<>(caseMainVOS);
            log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.SELECT_SUCCESS+caseMainVOPageInfo));
            return new ProcessResult(ProcessResult.SUCCESS,ConstClass.SUCCESS.SELECT_CASE_MAIN_LIST_SUCCESS,caseMainVOPageInfo);
        } catch (Exception e) {
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.SELECT_CASE_MAIN_LIST_FAILED);
        }
    }

}