package com.chinatower.product.legalms.modules.license;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.common.InterfaceLogUtil;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.modules.license.api.MenuAPI;
import com.chinatower.product.legalms.modules.license.commen.ResponseEnum;
import com.chinatower.product.legalms.modules.license.commen.ServerResponse;
import com.chinatower.product.legalms.modules.license.entity.MenuVO;
import com.chinatower.product.legalms.modules.license.mapper.MenuVOMapper;
import com.chinatower.product.legalms.modules.license.mapper.RoleMenuVOMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class MenuService implements MenuAPI {
    private static final Logger logger = LoggerFactory.getLogger("TransLog");
    @Autowired
    private MenuVOMapper menuVOMapper;

    @Autowired
    private RoleMenuVOMapper roleMenuVOMapper;

    /**
     * 4A_菜单变更接口
     *
     * @param menuVO
     * @return
     */
    public ServerResponse recieveMenu(@RequestBody MenuVO menuVO) {
        logger.info(InterfaceLogUtil.reqTransLog("请求参数为:" + menuVO));
//        if (isEmpty(menuVO)) return ServerResponse.error(ResponseEnum.ERROR);
//        if (isSpecialSymbols(menuVO)) return ServerResponse.error(ResponseEnum.ERROR);
//        if (isOut(menuVO)) return ServerResponse.error(ResponseEnum.ERROR);
        try {
            int count = menuVOMapper.selectMenuCountByMenuCode(menuVO.getMenuCode());
            if (count == 0 && !SystemInfo.DELETE_ID.equals(menuVO.getFlag())) {
                menuVOMapper.addMenu(menuVO);
                logger.info(InterfaceLogUtil.rspTransLog(SystemInfo.SYSTEM_RESULT));
            } else {
                if (SystemInfo.DELETE_ID.equals(menuVO.getFlag())) {
                    menuVOMapper.delectMenuByMenuCode(menuVO.getMenuCode());
                    roleMenuVOMapper.delectRolMenueByMenuCode(menuVO.getMenuCode());
                    logger.info(InterfaceLogUtil.reqTransLog(SystemInfo.SYSTEM_RESULT));
                } else {
                    menuVOMapper.updateMenu(menuVO);
                    logger.info(InterfaceLogUtil.rspTransLog(SystemInfo.SYSTEM_RESULT));
                }
            }
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR, e);
            return ServerResponse.error(ResponseEnum.ERROR);
        }
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }

//    private boolean isOut(@RequestBody MenuVO menuVO) {
//        return RequestHolder.getLength(menuVO.getMenuId()) > 10 || RequestHolder.getLength(menuVO.getMenuLevel()) > 10 ||
//                RequestHolder.getLength(menuVO.getParentMenuId()) > 10 || RequestHolder.getLength(menuVO.getMenuType()) > 10||menuVO.getMenuType()>2;
//    }
//
//    private boolean isSpecialSymbols(@RequestBody MenuVO menuVO) {
//        return RequestHolder.specialSymbols(menuVO.getFlag()) || RequestHolder.specialSymbols(menuVO.getServiceId()) ||
//                RequestHolder.specialSymbols(menuVO.getMenuName()) ||
//                RequestHolder.specialSymbols(menuVO.getMenuCode()) ||
//                RequestHolder.specialSymbols(menuVO.getParentMenuCode()) ||
//                RequestHolder.specialSymbols(menuVO.getMenuDesc());
//
//    }
//
//    private boolean isEmpty(@RequestBody MenuVO menuVO) {
//        return StringUtils.isBlank(menuVO.getFlag()) || StringUtils.isBlank(menuVO.getServiceId()) || StringUtils.isBlank(menuVO.getMenuName()) ||
//                StringUtils.isBlank(menuVO.getMenuCode()) || StringUtils.isBlank(menuVO.getParentMenuCode()) || StringUtils.isBlank(menuVO.getMenuDesc()) ||
//                menuVO.getMenuId() == null || menuVO.getMenuLevel() == null || menuVO.getParentMenuId() == null || menuVO.getMenuType() == null;
//
//    }

    @Override
    public ProcessResult selectMenuByMenuCode(@RequestParam("menuCode") String menuCode) {
        MenuVO menuVO = menuVOMapper.selectMenuByMenuCode(menuCode);
        return new ProcessResult(ProcessResult.SUCCESS, "", menuVO);
    }


}
