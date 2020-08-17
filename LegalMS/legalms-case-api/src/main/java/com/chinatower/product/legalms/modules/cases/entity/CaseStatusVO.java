package com.chinatower.product.legalms.modules.cases.entity;

import java.util.List;

/**
 * @Date: 2019/12/26 09:57
 * @Description:
 */
public class CaseStatusVO {

    private String caseId;

    private List<String> caseIdList;

    public CaseStatusVO() {
        super();
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public List<String> getCaseIdList() {
        return caseIdList;
    }

    public void setCaseIdList(List<String> caseIdList) {
        this.caseIdList = caseIdList;
    }

    public CaseStatusVO(List<String> caseIdList) {
        this.caseIdList = caseIdList;
    }
}
