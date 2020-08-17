package com.chinatower.product.legalms.security;

import com.alibaba.fastjson.JSON;
import com.chinatower.framework.common_service.response.ProcessResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 功能描述: 获取用户指定信息
 * @auther: G D
 * @return
 * @date: 2019/10/24 15:51
 */
public final class SecurityClient {

    private SecurityClient() {
        //
    }

    protected static Logger logger = LoggerFactory.getLogger(SecurityClient.class);

    public static String getDeptId(String pid) {
        return getHeadValue(pid, SecConst.HEADER.USER_DEPTCODE);
    }
    public static String getAREAS(String pid) {
        return getHeadValue(pid, SecConst.HEADER.USER_AREAS);
    }
    public static String getDeptName(String pid) {
        return getHeadValue(pid, SecConst.HEADER.USER_DEPTNAME);
    }
    public static String getRoles(String pid) {
        return getHeadValue(pid, SecConst.HEADER.USER_ROLES);
    }
    public static String getIsMain(String pid) {
        return getHeadValue(pid, SecConst.HEADER.USER_ISMAIN);
    }
    /**
     * {"deptId":"00371608443","email":"mali3@chinaunicom.cn","globalId":"0000","globalSign":false,"isParent":0,
     * "mobile":"18653590789","orgName":"烟台市分公司社会渠道营销中心","path":"00,0000,0037,003716,00371608443",
     * "provinceId":"0037","provinceName":"山东省分公司","sid":"564361","siteName":"渠道支撑",
     * "uid":"mali3","uname":"mali3","userTruename":"马莉"}"
     *
     * @param pid
     * @param fieldName
     * @return
     */
    public static String getHeadValue(String pid, String fieldName) {
        if (StringUtils.isBlank(pid)) {
            return null;
        }
        try {
            //根据fielName对用户信息进行解密
            String decrypt = AES.decrypt(pid);
            Map map = JSON.parseObject(decrypt, Map.class);
            return map.get(fieldName).toString();
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR, e);
        }
        return null;
    }
}
