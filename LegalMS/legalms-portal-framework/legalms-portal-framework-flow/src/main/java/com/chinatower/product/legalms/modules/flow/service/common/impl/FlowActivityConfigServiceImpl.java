package com.chinatower.product.legalms.modules.flow.service.common.impl;

import java.util.List;
import java.util.Map;

import com.chinatower.product.legalms.modules.flow.entity.common.FlowActivityPowerVO;
import com.chinatower.product.legalms.modules.flow.service.common.FlowActivityConfigService;
import com.chinatower.product.legalms.modules.flow.service.common.FlowActivityPowerService;
import com.chinatower.product.legalms.modules.flow.vo.common.FlowActivityConfigUpdateVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.chinatower.product.legalms.modules.flow.entity.common.FlowActivityConfigVO;
import com.chinatower.product.legalms.modules.flow.entity.common.SysQuickButtonVO;
import com.chinatower.product.legalms.modules.flow.mapper.common.FlowActivityConfigMapper;
import com.chinatower.product.legalms.modules.flow.vo.common.PageList;

@Service
@Transactional(rollbackFor = Exception.class)
public class FlowActivityConfigServiceImpl implements FlowActivityConfigService {

    @Autowired
    private FlowActivityConfigMapper flowActivityConfigMapper;

    @Autowired
    private FlowActivityPowerService flowActivityPowerService;

    @Override
    public List<FlowActivityConfigVO> selParamActivityConfig(PageList param) {
        return flowActivityConfigMapper.selParamActivityConfig(param);
    }

    @Override
    public List<FlowActivityConfigVO> selActivityConfig(FlowActivityConfigVO flowActivityConfigVO) {

        if (StringUtils.isNotBlank(flowActivityConfigVO.getImportantLevel())){
            flowActivityConfigVO.setImportantLevel("%" + flowActivityConfigVO.getImportantLevel() + "%");
        }

        if(StringUtils.isNotBlank(flowActivityConfigVO.getHandoverLevel())){
            flowActivityConfigVO.setHandoverLevel("%" + flowActivityConfigVO.getHandoverLevel() + "%");
        }

        return flowActivityConfigMapper.selAllActivityConfig(flowActivityConfigVO);
    }

    @Override
    public FlowActivityConfigVO selActivityId (String flowId,String beginId,String endId,Integer versionId) {
        return flowActivityConfigMapper.selActivityId(flowId, beginId, endId, versionId);
    }

    @Override
    public int selActivityCount(FlowActivityConfigVO flowActivityConfigVO) {
        return flowActivityConfigMapper.selAllActivityCount(flowActivityConfigVO);
    }

    @Override
    public int updateActivityConfig(@RequestBody FlowActivityConfigVO   flowActivityConfigVO) {
        return flowActivityConfigMapper.updateActivityConfig(flowActivityConfigVO);
    }

    @Override
    public List<SysQuickButtonVO> selectByRoleCode(SysQuickButtonVO sysQuickButtonVO) {
        return flowActivityConfigMapper.selectByRoleCode(sysQuickButtonVO);
    }

    @Override
    public int addActivityConfig(FlowActivityConfigVO flowActivityConfigVO) {
        return flowActivityConfigMapper.addActivityConfig(flowActivityConfigVO);
    }

    @Override
    public int delActivityConfig(FlowActivityConfigVO flowActivityConfigVO) {
        return flowActivityConfigMapper.delActivityConfig(flowActivityConfigVO);
    }

    @Override
    public List<Map> selectorgLevelByAccountId(String permissonKey){
     return flowActivityConfigMapper.selectorgLevelByAccountId(permissonKey);
    }

    /**
     * 流程相关权限
     * @return
     */
    public String judgeFlow(String flowId, String actId,Integer versionId) {
        FlowActivityPowerVO flowActivityPowerVO = new FlowActivityPowerVO(flowId,actId,versionId);
        List<FlowActivityPowerVO> flowActivityPowerVOs = flowActivityPowerService.selActivityPower(flowActivityPowerVO);
        if(!flowActivityPowerVOs.isEmpty()){
            return flowActivityPowerVOs.get(0).getPermissionJson();
        }
        return null;
    }

    public int updateConfig(List<FlowActivityConfigUpdateVO> configUpdateVOs){
        return flowActivityConfigMapper.updateConfig(configUpdateVOs);
    }
}
