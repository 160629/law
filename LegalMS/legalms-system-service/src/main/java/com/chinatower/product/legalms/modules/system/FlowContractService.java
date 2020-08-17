package com.chinatower.product.legalms.modules.system;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.base.BaseController;
import com.chinatower.product.legalms.common.ConstClass;
import com.chinatower.product.legalms.common.ScheduledProperties;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.system.api.FlowContractAPI;
import com.chinatower.product.legalms.modules.system.entity.SysQuickButtonVO;
import com.chinatower.product.legalms.modules.system.mapper.SysQuickButtonMapper;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

import java.util.List;
import java.util.Map;

@RestController
@Api(tags={"快捷键流程配置"})
public class FlowContractService extends BaseController implements FlowContractAPI {
    @Autowired
    private SysQuickButtonMapper sysQuickButtonMapper;

    @Autowired
    ScheduledProperties properties;

    private static final Logger logger = LoggerFactory.getLogger(FlowContractService.class);

    @Override
    public ProcessResult selectByRoleCode(@RequestBody SysQuickButtonVO sysQuickButtonVO) {
        String businessType = sysQuickButtonVO.getBusinessType();
            if (StringUtils.isNotBlank(businessType)){
            businessType = businessType + "%";
        }
        //获取用户信息
        UserInfo userInfo = RequestHolder.getUserInfo();
        String level = userInfo.getOrgLevel();
        if(StringUtils.isBlank(level)){
            return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.USERID_NOTEXIST_ERROR);
        }
        String isJiyuehua = userInfo.getIsJiYueHua();
        return getProcessResult(businessType,level,isJiyuehua,sysQuickButtonVO);
    }

    private ProcessResult getProcessResult( String businessType, String level, String isJiyuehua, SysQuickButtonVO sysQuickButtonVO) {
        if("1".equals(sysQuickButtonVO.getFlag()) && StringUtils.isNotBlank(businessType)){
            //如果总部 省分的表单不一致 会有问题
            List<SysQuickButtonVO> sysQuickButtonVOS = sysQuickButtonMapper.selectByLevel(businessType, level,isJiyuehua);
            return new ProcessResult(ProcessResult.SUCCESS,"",sysQuickButtonVOS);
        }else{
            String roleCode = sysQuickButtonVO.getRoleCode();
            if(StringUtils.isNotBlank(roleCode)){
                List<String> list = Arrays.asList(roleCode.split(","));
                List<SysQuickButtonVO> sysQuickButtonVOS=sysQuickButtonMapper.selectByRoleCode(list, businessType, level, isJiyuehua);
                return new ProcessResult(ProcessResult.SUCCESS,"",sysQuickButtonVOS);
            }else{
                return new ProcessResult(ProcessResult.ERROR,"传入的参数不能为空");
            }
        }
    }

    public ProcessResult selectQuickByUserId(String userId){
        try {
            logger.info("门户查询快捷入口参数：userId={}", userId);
            if(StringUtils.isBlank(userId)){
                return new ProcessResult(ProcessResult.ERROR,"传入的userId不能为空");
            }
            //获取用户信息
            List<Map> maps = sysQuickButtonMapper.selectorgLevelByAccountId(userId);
            return getProcessResultNew(maps,userId);
        }catch (Exception e){
            logger.error(ProcessResult.ERROR,e);
            return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.FAILURE_MESS);
        }
    }

    private ProcessResult getProcessResultNew(List<Map> maps,String userId){
        String level ="";
        String isJiyuehua = "";

        if(maps!=null && !maps.isEmpty() && maps.get(0)!=null){
            level = (String)maps.get(0).get("org_level");
            Object object = maps.get(0).get("is_jiyuehua");
            if(object==null ){
                isJiyuehua = "0";
            }else{
                String value = (String)object;
                if(StringUtils.isNotBlank(value)){
                    isJiyuehua = value;
                }else{
                    isJiyuehua = "0";
                }
            }
            if(StringUtils.isBlank(level)){
                return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.USERID_NOTEXIST_ERROR);
            }
        }else{
            return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.USERID_NOTEXIST_ERROR);
        }
        List<SysQuickButtonVO> sysQuickButtonVOS= getList( level,isJiyuehua,userId);
        return new ProcessResult(ProcessResult.SUCCESS,"",sysQuickButtonVOS);
    }

    /**
     * flag 1是 起草  2是 预览
     * @param level
     * @return
     */
    private List<SysQuickButtonVO> getList( String level,String isJiyuehua,String userId) {
        List<SysQuickButtonVO> sysQuickButtonVOS = sysQuickButtonMapper.selectByUserCode(null, level, isJiyuehua,userId);
        for (int i=0;i<sysQuickButtonVOS.size();i++){
            SysQuickButtonVO vo = sysQuickButtonVOS.get(i);
            vo.setPath(properties.getFormurl()+"/legalms/legalms-portal/form.html?flag=666&moduleName="+vo.getModuleName()+"&openType="+vo.getOpenType()+"&pageKey="+vo.getPageKey());
        }
        return sysQuickButtonVOS;
    }

    /*public static void main(String[] args){
        Object object = null;
        String string = (String)object;
        System.out.println(StringUtils.isBlank(string));
        System.out.println(StringUtils.isNotBlank(string));
    }*/

}
