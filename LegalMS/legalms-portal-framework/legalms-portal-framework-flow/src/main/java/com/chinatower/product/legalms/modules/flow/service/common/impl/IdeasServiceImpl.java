package com.chinatower.product.legalms.modules.flow.service.common.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.StringUtil;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.flow.entity.common.IdeasVO;
import com.chinatower.product.legalms.modules.flow.mapper.common.IdeasMapper;
import com.chinatower.product.legalms.modules.flow.service.common.IdeasService;

@Service
public class IdeasServiceImpl implements IdeasService {
    @Autowired
    private IdeasMapper ideasMapper;
    @Override
    public ProcessResult addIdeas(IdeasVO ideasVO, UserInfo info) {
        ideasVO.setIdeaId(StringUtil.getId());
        ideasVO.setIdeaCreateDate(new Date());
       ideasVO.setIdeaUserId(info.getLoginAcct());
        ideasMapper.insert(ideasVO);
        return new ProcessResult(ProcessResult.SUCCESS, "增加成功");
    }

    @Override
    public ProcessResult deleteIdeas(IdeasVO ideasVO) {
        if ("2".equals(ideasVO.getIdeaType())) {
            ideasMapper.delete(ideasVO);
            return new ProcessResult(ProcessResult.SUCCESS, "删除成功");
        }else {
            return new ProcessResult(ProcessResult.ERROR, "删除失败");
        }
    }

    @Override
    public List<IdeasVO> selectIdeas(IdeasVO ideasVO,String loginAcct) {
                return ideasMapper.findPage(ideasVO,loginAcct);
    }

    @Override
    public ProcessResult updateIdeas(IdeasVO ideasVO) {
        int result = ideasMapper.update(ideasVO);
        return new ProcessResult(ProcessResult.SUCCESS, "更新成功", result);
    }
}
