package com.chinatower.product.legalms.modules.system;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.system.api.OrgBetweenAPI;
import com.chinatower.product.legalms.modules.system.entity.OrgInfoBeanVO;
import com.chinatower.product.legalms.modules.system.mapper.OrgBetweenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrgBetweenService implements OrgBetweenAPI {

    @Autowired
    private OrgBetweenMapper orgBetweenMapper;

    @Override
    public ProcessResult selectOrgBean(String orgCode) {
        List<OrgInfoBeanVO> list = orgBetweenMapper.selectOrgBeanByCode(orgCode);
        return new ProcessResult(ProcessResult.SUCCESS,"",list);
    }
}
