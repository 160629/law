package com.chinatower.product.legalms.modules.cases.api;

import com.alibaba.fastjson.JSON;
import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.CaseInfo;
import com.chinatower.product.legalms.common.ConstClass;
import com.chinatower.product.legalms.common.InterfaceLogUtil;
import com.chinatower.product.legalms.modules.cases.entity.CaseMainVO;
import com.chinatower.product.legalms.modules.cases.entity.RelationshipVO;
import com.chinatower.product.legalms.modules.cases.issue.ListTissueLawsuitParam;
import com.chinatower.product.legalms.modules.cases.issue.TIssueLawsuit;
import com.chinatower.product.legalms.modules.cases.service.CaseMainService;
import com.chinatower.product.legalms.modules.cases.service.RelationshipService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@Api(tags = {"案件案宗关联接口"})
public class RelationshipApiImpl implements RelationshipApi{

    private static final Logger logger = LoggerFactory.getLogger(RelationshipApiImpl.class);

    @Autowired
    private RelationshipService relationshipService;

    @Autowired
    private CaseMainService caseMainService;

    @Override
    @ApiOperation("关联创建接口")
    public ProcessResult addRelationship(@RequestBody RelationshipVO relationshipVO) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(relationshipVO)));
        int a;
        try {
            a =relationshipService.addRelationship(relationshipVO);
            if (a == -1 || a == 0) {
                return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.ADD_RELATIONSHIP_ALREADY_FAILED);
            }
            log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.INSERT_SUCCESS+a));
            return new ProcessResult(ProcessResult.SUCCESS,ConstClass.SUCCESS.ADD_RELATIONSHIP_SUCCESS);
        }catch (Exception e){
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.ADD_RELATIONSHIP_FAILED);
        }
    }

    @Override
    @ApiOperation("关联删除接口")
    public ProcessResult deleteRelationship(@RequestBody RelationshipVO relationshipVO) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(relationshipVO)));
        int a;
        try {
            a =relationshipService.deleteRelationship(relationshipVO);
            log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.DELETE_SUCCESS+a));
            if (a>0){
                return new ProcessResult(ProcessResult.SUCCESS,ConstClass.SUCCESS.DELETE_RELATIONSHIP_SUCCESS);
            } else if (a == -1) {
                return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.RELATIONSHIP_LAWSUIT_DELETEFAILED);
            } else if (a == -2) {
                return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.RELATIONSHIP_CASE_ASSIGN_DELETEFAILED);
            } else {
                return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.RELATIONSHIP_DELETE_FAILED);
            }
        }catch (Exception e){
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.RELATIONSHIP_DELETE_FAILED);
        }
    }

    @Override
    public ProcessResult updateRelationshipByLawsuit(@RequestBody RelationshipVO relationshipVO) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ relationshipVO));
        try {
            int i = relationshipService.updateRelationshipByLawsuit(relationshipVO);
            log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.UPDATE_SUCCESS + i));
            return new ProcessResult(ProcessResult.SUCCESS, ConstClass.SUCCESS.SELECT_RELATIONSHIP_SUCCESS,i);
        }catch (Exception e){
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.ADD_OR_UPDATE_RELATIONSHIP_FAILED);
        }
    }

    @Override
    @ApiOperation("关联查询接口")
    public ProcessResult selectRelationship(@RequestBody RelationshipVO relationshipVO) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(relationshipVO)));
        try {
            List<RelationshipVO> relationshipVOS = relationshipService.selectRelationship(relationshipVO);
            log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.SELECT_SUCCESS+relationshipVOS));
            return new ProcessResult(ProcessResult.SUCCESS, ConstClass.SUCCESS.SELECT_RELATIONSHIP_SUCCESS,relationshipVOS);
        }catch (Exception e){
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.SELECT_RELATIONSHIP_FAILED);
        }
    }

    @Override
    public ProcessResult selectTIssueLawsuitList(@RequestBody ListTissueLawsuitParam param) {
        logger.info("卷宗-查询诉讼纠纷参数:param={}",param);
        try {
            CaseMainVO caseMainVO = caseMainService.selectCaseMain(param.getCaseId());
            param.setOrgId(caseMainVO.getInvolvedOrgId());
            PageHelper.startPage(param.getPageNum(), param.getPageSize());
            List<TIssueLawsuit> list = relationshipService.selectAll(param);
            PageInfo<TIssueLawsuit> pageInfo = new PageInfo<>(list);
            return new ProcessResult(ProcessResult.SUCCESS, "", pageInfo);
        } catch (Exception e) {
            logger.info(ConstClass.FAILURE.CLASS_CATCH,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.FAILURE_MESS);
        }
    }

    @Override
    public ProcessResult selectRelationshipByCaseId(@RequestParam String caseId) {
        logger.info("卷宗-根据卷宗id查询关联关系:param={}",caseId);
        try {
            List<RelationshipVO> relationshipVOS = relationshipService.selectRelationshipByCaseIds(caseId);
            return new ProcessResult(ProcessResult.SUCCESS, "", relationshipVOS);
        } catch (Exception e) {
            logger.info(ConstClass.FAILURE.SELECT_RELATIONSHIP_FAILED,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.FAILURE_MESS);
        }
    }
}
