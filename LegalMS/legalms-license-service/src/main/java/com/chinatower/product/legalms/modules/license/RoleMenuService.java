package com.chinatower.product.legalms.modules.license;

import com.alibaba.fastjson.JSON;
import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.base.BaseController;
import com.chinatower.product.legalms.common.InterfaceLogUtil;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.license.api.RoleMenuAPI;
import com.chinatower.product.legalms.modules.license.commen.ResponseEnum;
import com.chinatower.product.legalms.modules.license.commen.ServerResponse;
import com.chinatower.product.legalms.modules.license.entity.*;
import com.chinatower.product.legalms.modules.license.mapper.OrgBeanVOMappper;
import com.chinatower.product.legalms.modules.license.mapper.RoleMenuVOMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@Api(tags = { "角色接口" })
public class RoleMenuService extends BaseController implements RoleMenuAPI {
    private static final Logger logger = LoggerFactory.getLogger("TransLog");
    /**
     * 4A_角色与菜单授权变更接口
     */
    @Autowired
    private RoleMenuVOMapper roleMenuVOMapper;
    @Autowired
    private OrgBeanVOMappper orgBeanVOMappper;


    public ServerResponse recieveRoleMenu(@RequestBody RecieveRoleMenuVo recieveRoleMenuVo) {
        logger.info(InterfaceLogUtil.reqTransLog("请求参数" + JSON.toJSONString(recieveRoleMenuVo)));
//        if(StringUtils.isBlank(recieveRoleMenuVo.getFlag())||StringUtils.isBlank(recieveRoleMenuVo.getServiceId())||
//                StringUtils.isBlank(recieveRoleMenuVo.getRoleCode())||recieveRoleMenuVo.getMenuCodes().isEmpty()){
//            return ServerResponse.error(ResponseEnum.ERROR);
//        }
//        if(RequestHolder.specialSymbols(recieveRoleMenuVo.getFlag())||
//                RequestHolder.specialSymbols(recieveRoleMenuVo.getServiceId())||
//                RequestHolder.specialSymbols(recieveRoleMenuVo.getRoleCode())
//                ){
//            return ServerResponse.error(ResponseEnum.ERROR);
//        }
        try {
            if(SystemInfo.INSERT_ID.equals(recieveRoleMenuVo.getFlag())){
                List<String> menuStrArr = recieveRoleMenuVo.getMenuCodes();//new ArrayList<>();
                if (menuStrArr != null && !menuStrArr.isEmpty()) {
                    /**
                     * 这种写法只适用于 下发这个角色的全量菜单关联数据
                     * 给menulist空值 删除rolecode全部数据
                     */
                    //roleMenuVOMapper.delectRolMenueByRoleCode(recieveRoleMenuVo.getRoleCode(),menuStrArr);
                    List<RoleMenuVO> result = roleMenuVOMapper.selectMenuCodeByrRole(recieveRoleMenuVo.getRoleCode(),menuStrArr);//查询是否有重复数据
                    if(!result.isEmpty()){
                        buildRepResult(recieveRoleMenuVo, menuStrArr, result);
                    }else {
                        buildCreResult(recieveRoleMenuVo, menuStrArr);
                    }
                }
            }else if(SystemInfo.DELETE_ID.equals(recieveRoleMenuVo.getFlag())){
                List<String> menuStrArr = recieveRoleMenuVo.getMenuCodes();
                /**
                 * 传入角色code和菜单集合删除对应的数据
                 */
                roleMenuVOMapper.delectRolMenueByRoleCode(recieveRoleMenuVo.getRoleCode(),menuStrArr);

            }
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR,e);
            return ServerResponse.error(ResponseEnum.ERROR);
        }
        return ServerResponse.success(ResponseEnum.SUCCESS);

    }

    private void buildCreResult(@RequestBody RecieveRoleMenuVo recieveRoleMenuVo, List<String> menuStrArr) {
        for (int k = 0; k < menuStrArr.size(); k++) {
            RoleMenuVO roleMenuVO = new RoleMenuVO();
            roleMenuVO.setServiceId(recieveRoleMenuVo.getServiceId());
            roleMenuVO.setFlag(recieveRoleMenuVo.getFlag());
            roleMenuVO.setMenuCode(menuStrArr.get(k));
            roleMenuVO.setRoleCode(recieveRoleMenuVo.getRoleCode());
            roleMenuVO.setCreateTime(new Date());
            roleMenuVOMapper.insertRoleMenu(roleMenuVO);
        }
    }

    private void buildRepResult(@RequestBody RecieveRoleMenuVo recieveRoleMenuVo, List<String> menuStrArr, List<RoleMenuVO> result) {
        List<String> collect = result.stream().map(RoleMenuVO::getMenuCode).collect(Collectors.toList());
        List<String> newList = new ArrayList<>();
        for (int i = 0; i < menuStrArr.size(); i++) {
            if(!collect.contains(menuStrArr.get(i))){
                newList.add(menuStrArr.get(i));
            }
        }
        if(!newList.isEmpty()){
            for (int k = 0; k < newList.size(); k++) {
                RoleMenuVO roleMenuVO = new RoleMenuVO();
                roleMenuVO.setServiceId(recieveRoleMenuVo.getServiceId());
                roleMenuVO.setFlag(recieveRoleMenuVo.getFlag());
                roleMenuVO.setMenuCode(newList.get(k));
                roleMenuVO.setRoleCode(recieveRoleMenuVo.getRoleCode());
                roleMenuVO.setCreateTime(new Date());
                roleMenuVOMapper.insertRoleMenu(roleMenuVO);
            }
        }
    }

    public static Date strToDateLong(String strDate) {
        String str = "yyyy-MM-dd HH:mm:ss";
        Date parse = null;
        try {
            if (strDate.length() < str.length()) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                parse = formatter.parse(strDate);
            } else {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                parse = formatter.parse(strDate);
            }
        } catch (ParseException e) {
            logger.error(ProcessResult.ERROR,e);
        }
        return parse;
    }
    @Override
    public ProcessResult selectRoleMenuByRoleCode(@RequestParam("roleCode")String roleCode) {
        RoleMenuVO roleMenuVO = roleMenuVOMapper.selectRoleMenuByRoleCode(roleCode);
        return new ProcessResult(ProcessResult.SUCCESS,"",roleMenuVO);
    }

    @Override
    public ProcessResult selectRole(@RequestBody ListParam listParam) {
        try {
            PageHelper.startPage(listParam.getPageNum(), listParam.getPageSize());
            List<RoleVo> list = roleMenuVOMapper.selectRole(listParam);
            PageInfo<RoleVo> pageInfo = new PageInfo<>(list);
            return new ProcessResult(ProcessResult.SUCCESS, "", pageInfo);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR,e);
            return new ProcessResult(ProcessResult.ERROR, e.getMessage());
        }
    }

    @Override
    public ProcessResult selectMenu(@RequestBody MenuVO menuVO) {
        try {

            List<RoleVo> list = roleMenuVOMapper.selectMenu(menuVO);
            return new ProcessResult(ProcessResult.SUCCESS, "", list);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR,e+"查询菜单失败");
            return new ProcessResult(ProcessResult.ERROR, e.getMessage());
        }
    }
    @Override
    public ProcessResult selectRoleMenu(@RequestBody RoleVo roleVo) {
        List<MenuVO> list =  roleMenuVOMapper.selectRoleMenu(roleVo);
        return new ProcessResult(ProcessResult.SUCCESS,"",list);
    }

    @Override
    public ProcessResult selectUser(@RequestBody ListParam listParam) {
        if(listParam.getPageNum()!=null&& listParam.getPageSize()!=null){
            PageHelper.startPage(listParam.getPageNum(), listParam.getPageSize());
        }
        if(StringUtils.isBlank(listParam.getRoleCode())){
            return new ProcessResult(ProcessResult.ERROR,"角色code为空");
        }
        List<OrgBeanVO> list = new ArrayList<>();

        List<String> roleList = Arrays.asList(listParam.getRoleCode().split(","));
        //获取用户信息
        UserInfo userInfo = RequestHolder.getUserInfo();
        String orgLevel = userInfo.getOrgLevel();
        String deptId = userInfo.getDeptId();
        String orgId = userInfo.getOrgId();
        String unitCode = userInfo.getUnitCode();
        String enterpType = RequestHolder.getenterpType(unitCode);
        //判断当前登录人是否为超级管理员
        List<Object> collect = userInfo.getRoleCodeList().stream().filter(x -> String.valueOf(x).equals("CHNTLEGALMS_0")).collect(Collectors.toList());
        String superRole = null;
      if(!collect.isEmpty()){
           superRole= collect.get(0).toString();//超级管理员角色
       }
        //根据用户信息判断当前登录人权限级别
        switch (orgLevel) {
            case "01":// 总部
                list = orgBeanVOMappper.selectCaseReceptInfoByLevel(roleList, enterpType, orgLevel,superRole);
                break;
            case "02":// 省份
                String provinceCode = RequestHolder.getProvinceCode(deptId);
                list = orgBeanVOMappper.selectCaseReceptInfoByLevel(roleList, provinceCode, orgLevel,superRole);
                break;
            case "03":// 地市
                list = orgBeanVOMappper.selectCaseReceptInfoByLevel(roleList, orgId, orgLevel,superRole);
                break;
            default:
                log.error("获取人员组织信息失败");
        }
        PageInfo<OrgBeanVO> pageInfo = new PageInfo<>(list);
        return new ProcessResult(ProcessResult.SUCCESS,"",pageInfo);
    }
}
