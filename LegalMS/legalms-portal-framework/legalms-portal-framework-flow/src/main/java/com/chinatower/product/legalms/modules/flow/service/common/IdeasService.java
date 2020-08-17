package com.chinatower.product.legalms.modules.flow.service.common;

import java.util.List;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.flow.entity.common.IdeasVO;

public interface IdeasService {

    ProcessResult updateIdeas(IdeasVO ideasVO);

    ProcessResult addIdeas(IdeasVO ideasVO, UserInfo userInfo);

    List<IdeasVO> selectIdeas(IdeasVO ideasVO,String loginAcct);

    ProcessResult deleteIdeas(IdeasVO ideasVO);
}
