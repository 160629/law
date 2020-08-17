package com.chinatower.product.legalms.modules.cases.service.impl;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.CaseInfo;
import com.chinatower.product.legalms.modules.cases.entity.CaseMainVO;
import com.chinatower.product.legalms.modules.cases.entity.RelationshipVO;
import com.chinatower.product.legalms.modules.cases.issue.ListTissueLawsuitParam;
import com.chinatower.product.legalms.modules.cases.issue.TIssueLawsuit;
import com.chinatower.product.legalms.modules.cases.mapper.CaseMainMappper;
import com.chinatower.product.legalms.modules.cases.mapper.RelationshipMapper;
import com.chinatower.product.legalms.modules.cases.service.RelationshipService;
import com.chinatower.provider.call.flowpath.TFlowServiceClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class RelationshipServiceImpl implements RelationshipService {

    @Autowired
    private RelationshipMapper relationshipMapper;

    @Autowired
    TFlowServiceClient tFlowServiceClient;

    @Autowired
    CaseMainMappper caseMainMappper;


    @Override
    @Transactional
    public int addRelationship(RelationshipVO relationshipVO) {
        int count = relationshipMapper.selectRelationshipById(relationshipVO);
        deleteRelationshipByCount(count,relationshipVO);
        String title = relationshipVO.getTittle();
        if (relationshipVO.getIsAuto() != null && "1".equals(relationshipVO.getIsAuto())) {
            relationshipVO.setTittle(title.substring(0, title.lastIndexOf("卷宗")));
            relationshipVO.setIsDelete("0");
            count = relationshipMapper.addRelationship(relationshipVO);
        } else if (CaseInfo.I_ISSUE_LAWSUIT.equals(relationshipVO.getBusinessType())) {
            // 纠纷处理流程
            relationshipVO.setIsDelete("1");
            relationshipVO.setIsAuto("0");
            // 同步修改纠纷处理单信息，会在修改纠纷处理流程业务信息时，回调卷宗修改关联关系接口，新增关联关系，所以此处不用新增关联关系
            CaseMainVO caseMainVO = caseMainMappper.selectCaseMainById(relationshipVO.getCaseId());
            ProcessResult processResult = tFlowServiceClient.updateCaseId(relationshipVO.getCaseId(), relationshipVO.getBusinessId(), caseMainVO.getCaseTitle(), 1);
            if (processResult != null && "SUCCESS".equals(processResult.getResultStat())) {
                count = relationshipMapper.addRelationship(relationshipVO);
            } else {
                return 0;
            }
        } else {
            // 其他流程新增关联关系
            relationshipVO.setIsDelete("0");
            relationshipVO.setIsAuto("0");
            count = relationshipMapper.addRelationship(relationshipVO);
        }
        // 添加关联关系
        return count;
    }

    @Override
    @Transactional
    public int deleteRelationship(RelationshipVO relationshipVO) {
        if (relationshipVO.getBusinessType().equals(CaseInfo.I_ISSUE_LAWSUIT)) {
            // 删除纠纷处理流程
            int a = relationshipMapper.isLawsuitAutoLawsuit(relationshipVO);
            if (a == 1) {
                return -1;
            }
            // 修改纠纷处理单信息
            tFlowServiceClient.updateCaseId("", relationshipVO.getBusinessId(), "", -1);
            // 删除关联关系
            return relationshipMapper.deleteRelationship(relationshipVO);
        } else {
            return -2;
        }
    }
    @Override
    public List<RelationshipVO> selectRelationship(RelationshipVO relationshipVO) {
        return relationshipMapper.selectRelationship(relationshipVO);
    }

    @Override
    public int updateRelationship(RelationshipVO relationshipVO) {
        return relationshipMapper.updateRelationship(relationshipVO);
    }

    @Override
    public List<TIssueLawsuit> selectAll(ListTissueLawsuitParam param) {
        return relationshipMapper.selectAll(param);
    }

    @Override
    public int updateRelationshipByLawsuit(RelationshipVO relationshipVO) {
        switch (relationshipVO.getOptionType()) {
            case "1":
                // 更换关联案件
                int i = relationshipMapper.addRelationship(relationshipVO);
                relationshipVO.setCaseId(relationshipVO.getOldCaseId());
                i += relationshipMapper.deleteRelationship(relationshipVO);
                return i;
            case "2":
                // 新增关联案件
                return relationshipMapper.addRelationship(relationshipVO);
            case "3":
                // 删除关联案件
                relationshipVO.setCaseId(relationshipVO.getOldCaseId());
                return relationshipMapper.deleteRelationship(relationshipVO);
            default:
                return 0;
        }
    }

    @Override
    public List<RelationshipVO> selectRelationshipByCaseIds(String oldCaseId) {
        if (!StringUtils.isNotBlank(oldCaseId)) {
            return Collections.emptyList();
        }
        return relationshipMapper.selectRelationshipByCaseIds(oldCaseId);
    }

    @Override
    public int updateRelationshipsCaseId(List<RelationshipVO> relationshipVOS, String newCaseId) {
        relationshipMapper.deleteRelationships(relationshipVOS);
        relationshipVOS.forEach(x -> x.setCaseId(newCaseId));
        return relationshipMapper.addRelationships(relationshipVOS);
    }

    public int deleteRelationshipByCount(int count,RelationshipVO relationshipVO){
        if(count>0 && StringUtils.isNotBlank(relationshipVO.getBusinessId())&&StringUtils.isNotBlank(relationshipVO.getBusinessType())&&StringUtils.isNotBlank(relationshipVO.getCaseId())) {
           return relationshipMapper.deleteRelationship(relationshipVO);
        }
        return count;
    }
}
