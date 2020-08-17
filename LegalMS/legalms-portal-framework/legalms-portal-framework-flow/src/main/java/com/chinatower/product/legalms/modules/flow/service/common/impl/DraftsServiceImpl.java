package com.chinatower.product.legalms.modules.flow.service.common.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.modules.flow.entity.common.DraftsVO;
import com.chinatower.product.legalms.modules.flow.mapper.common.DraftsVOMapper;
import com.chinatower.product.legalms.modules.flow.service.common.DraftsService;
import com.chinatower.product.legalms.modules.flow.service.unview.TFlowCommonService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @Auther: G D
 * @Date: 2019/10/21 14:58
 * @Description:
 */
@SuppressWarnings("ALL")
@Service
@Transactional(rollbackFor = Exception.class)
public class DraftsServiceImpl implements DraftsService {

    @Autowired
    private DraftsVOMapper draftsVOMapper;

    @Autowired
	TFlowCommonService commonService;

    @Override

    public ProcessResult selectDrafts(DraftsVO draftsVO) {
        if(draftsVO.getPageNum()!=null&& draftsVO.getPageSize()!=null){
            PageHelper.startPage(draftsVO.getPageNum(), draftsVO.getPageSize());
        }
        draftsVO.setUserCode(RequestHolder.getLoginAcct());
        List<DraftsVO> draftsList = draftsVOMapper.selectDrafts(draftsVO);
        PageInfo<DraftsVO> pageInfo = new PageInfo<>(draftsList);
        return new ProcessResult(ProcessResult.SUCCESS, "查询成功", pageInfo);
    }

    @Override
    public ProcessResult updateDrafts(DraftsVO draftsVO) {
        draftsVO.setUpdateTime(new Date());
        int result = draftsVOMapper.updateDrafts(draftsVO);
        return new ProcessResult(ProcessResult.SUCCESS, "更新成功", result);
    }

    @Override
    public ProcessResult addDrafts(DraftsVO draftsVO) {
        int count = draftsVOMapper.selectDraftsCount(draftsVO);
        int result = 0;
        if (count > 0) {
            draftsVO.setUpdateTime(new Date());
            result = draftsVOMapper.updateDrafts(draftsVO);
        } else {
            draftsVO.setCreateTime(new Date());
            result = draftsVOMapper.insert(draftsVO);

        }
        return new ProcessResult(ProcessResult.SUCCESS, "增加成功", result);
    }

    /*从业务表批量删除草稿箱数据*/
    @Override
    public ProcessResult deleteBatchDrafts(DraftsVO draftsVO) {
        int result = draftsVOMapper.deleteBatchDrafts(draftsVO);
        return new ProcessResult(ProcessResult.SUCCESS, "删除成功", result);
    }


    @Override
    public ProcessResult delDraftsByItem(DraftsVO draftsVO){
        draftsVO.setUpdateTime(new Date());
        int result = draftsVOMapper.delDraftsByItem(draftsVO);
        /*逻辑删除业务表数据*/
        int i = commonService.updateByPrimaryKeyStatus(draftsVO.getApproveItemType(), draftsVO.getApproveItemId(), draftsVO.getApproveItemName(), "0");
        return new ProcessResult(ProcessResult.SUCCESS, "删除成功", result+"+"+i);
    }




    /*批量删除草稿箱数据*/
    @Override

    public ProcessResult delBatchDraftsByItem(DraftsVO draftsVO){
        List<String> approveItemIds = draftsVO.getApproveItemIdList();
        List<String> approveItemTypes = draftsVO.getApproveItemTypesList();
        List<String> itemNameList = draftsVO.getApproveItemNameList();
        if(approveItemIds.isEmpty()||approveItemTypes.isEmpty()){
            return new ProcessResult(ProcessResult.ERROR,"approveItemIds或approveItemTypes不能为空");
        }
        if(approveItemIds.size()!=approveItemTypes.size()){
            return new ProcessResult(ProcessResult.ERROR,"参数approveItemIds和approveItemTypes个数不匹配");
        }
        IntStream.range(0,approveItemIds.size()).forEach(i->{
            DraftsVO result = new DraftsVO();
            result.setApproveItemId(approveItemIds.get(i));
            result.setApproveItemType(approveItemTypes.get(i));
            result.setApproveItemName(itemNameList.get(i));
            result.setUpdateTime(new Date());
            draftsVOMapper.delDraftsByItem(result);
            /*逻辑删除业务表数据*/
            commonService.updateByPrimaryKeyStatus(result.getApproveItemType(), result.getApproveItemId(), result.getApproveItemName(), "0");
        });
        return new ProcessResult(ProcessResult.SUCCESS, "删除成功");
    }
}
