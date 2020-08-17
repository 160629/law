package com.chinatower.product.legalms.modules.dispute.api.common;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.flow.entity.common.IdeasVO;

public interface IdeaApi {

    @PostMapping("/v1/ideas/addIdeas")
    public ProcessResult addIdeas(IdeasVO ideasVO);

    @PostMapping("/v1/ideas/deleteIdeas")
    public ProcessResult deleteIdeas(IdeasVO ideasVO);

    @PostMapping("/v1/ideas/selectIdeas")
    public ProcessResult selectIdeas(IdeasVO ideasVO);

    @PostMapping("/v1/ideas/updateIdeas")
    public ProcessResult updateIdeas(@RequestBody IdeasVO ideasVO);
}



