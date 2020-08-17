package com.chinatower.product.legalms.modules.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chinatower.framework.cloud.feignlog.InfoFeginLog;
import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.ConstClass;
import com.chinatower.product.legalms.common.InterfaceLogUtil;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.common.ThreeDESUtil;
import com.chinatower.product.legalms.modules.system.api.RolesAPI;
import com.chinatower.product.legalms.modules.system.entity.RoleVo;
import com.chinatower.product.legalms.modules.system.mapper.RolesMapper;
import com.chinatower.provider.call.oauth.SelServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@Component
@Slf4j
public class RolesService implements RolesAPI {

    private static final Logger logger = LoggerFactory.getLogger("TransLog");

    @Autowired
    private SelServiceClient selServiceClient;

    @Autowired
    private RolesMapper rolesMapper;

    public ProcessResult selRoless(HttpServletRequest request) {
        String token = request.getHeader("token");
        JSONObject json = new JSONObject();
        json.put("SERVICEID", "CHNTLEGALMS");
        json.put("TOKEN", token);
        /** SENDCODE  SERVERCODE*/
        String encode = ThreeDESUtil.encode(json.toJSONString());
        /** mdc put的是esb给的码值,sendcode是发送方,servercode是服务提供方*/
        MDC.put(InfoFeginLog.LOG_SENDCODE,"124");
        MDC.put(InfoFeginLog.LOG_SERVERCODE,"124");
        return selServiceClient.serviceRoles(encode);
    }


    @Override
    public ProcessResult selRoles(HttpServletRequest request) {
        try {
            ProcessResult processResult = selRoless(request);
            String data = (String) processResult.getData();
            JSONObject jsonObject = JSON.parseObject(data);
            String listJson = jsonObject.get("ROLELIST").toString();
            JSONArray arr = JSON.parseArray(listJson);
            for (int i = 0; i < arr.size(); i++) {
                JSONObject orgbeanjson = JSON.parseObject(arr.get(i).toString());
                String createDate = (String) orgbeanjson.get("CREATE_DATE");
                String roleCode = (String) orgbeanjson.get("ROLE_CODE");
                String roleLevel = (String) orgbeanjson.get("ROLE_LEVEL");
                String roleName = (String) orgbeanjson.get("ROLE_NAME");
                String serbiceId = (String) orgbeanjson.get("SERVICEID");
                String remark = (String) orgbeanjson.get("REMARK");

                RoleVo roleVo = new RoleVo();
                roleVo.setCreateDate(strToDateLong(createDate));
                roleVo.setRoleCode(roleCode);
                roleVo.setRoleLevel(Integer.parseInt(roleLevel));
                roleVo.setRoleName(roleName);
                roleVo.setServiceId(serbiceId);
                roleVo.setRemark(remark);

                int count = rolesMapper.selectAllRole(roleCode);
                if (count == 0) {
                    rolesMapper.insertroles(roleVo);
                    logger.info(InterfaceLogUtil.rspTransLog(SystemInfo.SYSTEM_RESULT + SystemInfo.INSERT_SUCCESS));
                }
            }
            return new ProcessResult(ProcessResult.SUCCESS, "成功");
        } catch (Exception e) {
            logger.error(SystemInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.BUZ_EXCEPTION, ConstClass.FAILURE.FIND_ROLE_ERROR);
        }
    }


    //将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(strDate, pos);
    }
}
