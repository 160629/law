package com.chinatower.product.legalms.modules.license.service;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.license.commen.ServerResponse;
import com.chinatower.product.legalms.modules.license.entity.AccRoleImpowerVO;

public interface AccRoleImpowerService {

    public ServerResponse operaAccRoleImpower(AccRoleImpowerVO accRoleImpowerVO);

    ProcessResult selectAccRoleImpowerByAccountId(AccRoleImpowerVO accRoleImpowerVO);

}
