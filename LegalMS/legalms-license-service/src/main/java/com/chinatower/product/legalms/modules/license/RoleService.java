package com.chinatower.product.legalms.modules.license;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.common.InterfaceLogUtil;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.modules.license.api.RoleAPI;
import com.chinatower.product.legalms.modules.license.commen.ResponseEnum;
import com.chinatower.product.legalms.modules.license.commen.ServerResponse;
import com.chinatower.product.legalms.modules.license.entity.AccRoleImpowerVO;
import com.chinatower.product.legalms.modules.license.entity.RoleVo;
import com.chinatower.product.legalms.modules.license.mapper.AccRoleImpowerVOMapper;
import com.chinatower.product.legalms.modules.license.mapper.RoleMenuVOMapper;
import com.chinatower.product.legalms.modules.license.mapper.RoleVoMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class RoleService implements RoleAPI {
    private static final Logger logger = LoggerFactory.getLogger("TransLog");
    @Autowired
    private RoleVoMapper roleVoMapper;

    @Autowired
    private AccRoleImpowerVOMapper accRoleImpowerVOMapper;

    @Autowired
    private RoleMenuVOMapper roleMenuVOMapper;

    /**
     * 4A_角色变更接口
     *
     * @param roleVo
     * @return
     */
    @Override
    public ServerResponse recieveRole(@RequestBody RoleVo roleVo) {

        logger.info(InterfaceLogUtil.reqTransLog("请求参数为:" + roleVo));
//        if (isEmpty(roleVo)) return ServerResponse.error(ResponseEnum.ERROR);
//        if (isSepcialSymbols(roleVo)) return ServerResponse.error(ResponseEnum.ERROR);
//        if (isOut(roleVo)) return ServerResponse.error(ResponseEnum.ERROR);
        try {
            String createDateStr = roleVo.getCreateDate();
            roleVo.setCreateTime(strToDateLong(createDateStr));
            int count = roleVoMapper.selectRoleCountByRoleCode(roleVo.getRoleCode());
            if (count == 0 && !SystemInfo.DELETE_ID.equals(roleVo.getFlag())) {
                roleVoMapper.addRole(roleVo);
                logger.info(InterfaceLogUtil.rspTransLog(SystemInfo.SYSTEM_RESULT));
            } else if (SystemInfo.UPDATE_ID.equals(roleVo.getFlag()) || SystemInfo.INSERT_ID.equals(roleVo.getFlag())) {
                roleVoMapper.updateRole(roleVo);
                logger.info(InterfaceLogUtil.rspTransLog(SystemInfo.SYSTEM_RESULT));
            } else if (SystemInfo.DELETE_ID.equals(roleVo.getFlag())) {
                roleVoMapper.updateRole(roleVo);
                roleMenuVOMapper.delectRolMenueByRoleCode(roleVo.getRoleCode(), null);
                logger.info(InterfaceLogUtil.rspTransLog(SystemInfo.SYSTEM_RESULT));
            }
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR, e);
            return ServerResponse.error(ResponseEnum.ERROR);
        }
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }

//    private boolean isOut(@RequestBody RoleVo roleVo) {
//        return RequestHolder.getLength(roleVo.getRoleId()) > 10 || RequestHolder.getLength(roleVo.getRoleLevel()) > 1 ||
//                RequestHolder.getLength(roleVo.getRoleState()) > 1 ||
//                (RequestHolder.getLength(roleVo.getStatusCd()) > 1 && roleVo.getStatusCd() != null);
//
//    }
//
//    private boolean isSepcialSymbols(@RequestBody RoleVo roleVo) {
//        return RequestHolder.specialSymbols(roleVo.getFlag()) || RequestHolder.specialSymbols(roleVo.getServiceId()) ||
//                RequestHolder.specialSymbols(roleVo.getRoleName()) ||
//                RequestHolder.specialSymbols(roleVo.getRoleCode());
//
//    }
//
//    private boolean isEmpty(@RequestBody RoleVo roleVo) {
//        return StringUtils.isBlank(roleVo.getFlag()) || StringUtils.isBlank(roleVo.getServiceId()) || StringUtils.isBlank(roleVo.getRoleName()) ||
//                StringUtils.isBlank(roleVo.getRoleCode()) || roleVo.getRoleId() == null || roleVo.getRoleLevel() == null ||
//                roleVo.getRoleState() == null || (StringUtils.isNotEmpty(roleVo.getCreateDate()) && !isLegalDate(roleVo.getCreateDate()));
//    }


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
            logger.error(ProcessResult.ERROR, e);
        }
        return parse;
    }


    @Override
    public ProcessResult selectRoleByRoleCode(@RequestParam("roleCode") String roleCode) {
        RoleVo roleVo = roleVoMapper.selectRoleByRoleCode(roleCode);
        return new ProcessResult(ProcessResult.SUCCESS, "", roleVo);
    }

    @Override
    public ProcessResult selectAllRole() {
        try {
            List<RoleVo> roleList = roleVoMapper.selectAllRole();
            List<RoleVo> roleVoList = roleList.stream().filter(x -> x.getRoleCode().contains(SystemInfo.ROLE_CODE_MODEL)).collect(Collectors.toList());
            return new ProcessResult(ProcessResult.SUCCESS, "", roleVoList);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR, e);
            return new ProcessResult(ProcessResult.ERROR, e.toString());
        }
    }

    @Override
    public ProcessResult selectUnAccRoleImpowerByAccountId(@RequestBody AccRoleImpowerVO accRoleImpowerVO) {
        try {
            List<String> collect = accRoleImpowerVOMapper.selectAccRoleImpowerByAccountId(accRoleImpowerVO.getAccountId())
                    .stream().map(AccRoleImpowerVO::getRoleColeIds).collect(Collectors.toList());
            List<RoleVo> result = roleVoMapper.selectUnAllRole(collect).stream().filter(x -> x.getRoleCode().contains(SystemInfo.ROLE_CODE_MODEL)).collect(Collectors.toList());
            return new ProcessResult(ProcessResult.SUCCESS, "查询成功", result);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR, e);
            return new ProcessResult(ProcessResult.ERROR, e.toString());
        }
    }

//    private static boolean isLegalDate(String sDate) {
////        int legalLen = 10;
////        if ((sDate == null) || (sDate.length() != legalLen)) {
////            return false;
////        }
//
//        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            Date date = formatter.parse(sDate);
//            return sDate.equals(formatter.format(date));
//        } catch (Exception e) {
//            return false;
//        }
//    }
}