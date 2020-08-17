package com.chinatower.product.legalms.modules.dispute.service.common;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.dispute.entity.CommonParam;

public interface TActivityAarticipatorService {

    public ProcessResult selectParticipatorByCode(CommonParam commonParam);

}
