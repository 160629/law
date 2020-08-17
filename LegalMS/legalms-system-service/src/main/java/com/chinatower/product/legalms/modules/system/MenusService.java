package com.chinatower.product.legalms.modules.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chinatower.framework.cloud.feignlog.InfoFeginLog;
import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.ConstClass;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.common.ThreeDESUtil;
import com.chinatower.product.legalms.modules.system.api.MenusAPI;
import com.chinatower.product.legalms.modules.system.entity.MenuVO;
import com.chinatower.product.legalms.modules.system.mapper.MenusMapper;
import com.chinatower.provider.call.oauth.SelServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Component
public class MenusService implements MenusAPI {

    private static final Logger logger = LoggerFactory.getLogger("TransLog");

    @Autowired
    private SelServiceClient selServiceClient;

    @Autowired
    private MenusMapper menusMapper;

    public ProcessResult selMenuss(HttpServletRequest request) {
        String token = request.getHeader("token");
        String menucode = request.getHeader("menucode");
        JSONObject json = new JSONObject();
        json.put("SERVICEID", "CHNTLEGALMS");
        json.put("TOKEN", token);
        json.put("MENUCODE",menucode);
        /** SENDCODE  SERVERCODE*/
        String encode = ThreeDESUtil.encode(json.toJSONString());
        /** mdc put的是esb给的码值,sendcode是发送方,servercode是服务提供方*/
        MDC.put(InfoFeginLog.LOG_SENDCODE,"124");
        MDC.put(InfoFeginLog.LOG_SERVERCODE,"124");
        return selServiceClient.serviceMenus(encode);
    }

    @Override
    public ProcessResult selMenus(HttpServletRequest request) {
        try {
            ProcessResult result = selMenuss(request);
            String data = (String) result.getData();
            JSONObject jsonObject = JSON.parseObject(data);
            String listJson = jsonObject.get("MENULIST").toString();
            JSONArray arr = JSON.parseArray(listJson);

            List<MenuVO> list = new ArrayList<>();
            for (int i = 0; i < arr.size(); i++) {
                JSONObject menujson = JSON.parseObject(arr.get(i).toString());
                String menuCode = (String) menujson.get("MENUCODE");
                String menuLevel = (String) menujson.get("MENULEVEL");
                String menuName = (String) menujson.get("MENUNAME");
                String parentMenuId = (String) menujson.get("P_MENUCODE");
                String serviceId = (String) menujson.get("SERVICEID");
                String menuUrl = (String) menujson.get("URL");

                MenuVO menuVO = new MenuVO();
                menuVO.setMenuCode(menuCode);
                menuVO.setMenuLevel(Integer.parseInt(menuLevel));
                menuVO.setMenuName(menuName);
                menuVO.setParentMenuId(Integer.parseInt(parentMenuId));
                menuVO.setServiceId(serviceId);
                menuVO.setMenuUrl(menuUrl);

                list.add(menuVO);
            }
            return new ProcessResult(ProcessResult.SUCCESS, "成功",list);
        }catch (Exception e){
            logger.error(SystemInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.BUZ_EXCEPTION, ConstClass.FAILURE.FIND_MENU_ERROR);
        }
    }

    //将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(strDate, pos);
    }
}
