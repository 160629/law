package com.chinatower.product.legalms.modules.dispute.service.assist.impl;

import com.chinatower.product.legalms.modules.dispute.entity.assist.TIssueAssistRelation;
import com.chinatower.product.legalms.modules.dispute.mapper.assist.TIssueAssistRelationMapper;
import com.chinatower.product.legalms.modules.dispute.service.assist.TIssueAssistRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("tIssueAssistRelation")
public class TIssueAssistRelationServiceImpl implements TIssueAssistRelationService {

    @Autowired
    TIssueAssistRelationMapper tIssueAssistRelationMapper;

    @Override
    public int insertRelation(TIssueAssistRelation tIssueAssistRelation) {
        return tIssueAssistRelationMapper.insertRelation(tIssueAssistRelation);
    }

    @Override
    public int deleteRelation(TIssueAssistRelation tIssueAssistRelation) {
        return tIssueAssistRelationMapper.deleteRelation(tIssueAssistRelation);
    }

    @Override
    public List<TIssueAssistRelation> selectRelationByAssistPid(String assistId) {
        return tIssueAssistRelationMapper.selectRelationByAssistPid(assistId);
    }
}
