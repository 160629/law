package com.chinatower.product.legalms.modules.system;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.ConstClass;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.modules.system.api.MsgRedisAPI;
import com.chinatower.product.legalms.vo.CacheModel;
import com.chinatower.provider.call.oauth.RedisServiceClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Auther: G D
 * @Date: 2019/10/16 16:39
 * @Description:
 */
@RestController
@Api(tags={"Redis接口"})
@Slf4j
public class MsgRedisService implements MsgRedisAPI {

    private static final Logger logger = LoggerFactory.getLogger(SystemInfo.LOGGER_NAME);

    @Autowired
    private QueryService queryService;

    @Autowired
    private RedisServiceClient redisServiceClient;

    @Value("${spring.application.name}")
    private  String serverName;

    /**
     * 功能描述:根据key保存信息到redis
     * @auther: G D
     * @param expireTime
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @date: 2019/10/17 10:03
     */
    @Override
    @ApiOperation("根据key保存信息")
    public ProcessResult saveMsgByKey(@RequestParam("key")String key,
                                      @RequestParam("value")Object value,
                                      @RequestParam("expireTime")long expireTime) {
        try {
            CacheModel cacheModel = new CacheModel();
            cacheModel.setKey(SystemInfo.REDIS_SIGN+key);
            cacheModel.setValue(value);
            if(expireTime>0){
                cacheModel.setExpireTime(expireTime);
            }
            Map<String, Object> result = redisServiceClient.insert(cacheModel);
            return new ProcessResult(ProcessResult.SUCCESS, "", result);
        } catch (Exception e) {
            logger.error(SystemInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.REDIS_INSERT_ERROR);
        }


    }
    /**
     * 功能描述: 保存用户信息到redis
     * @auther: G D
     * @param request
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @date: 2019/10/17 9:51
     */

    @Override
    @ApiOperation("保存用户信息到redis")
    public ProcessResult saveUserInfo(HttpServletRequest request) {
        ProcessResult result = null;
        try {
            ProcessResult processResult = queryService.selectLoginUserInfo(request);
            if(processResult.getData()==null){
                return new ProcessResult(ProcessResult.ERROR);
            }
            String value = processResult.getData().toString();
            //用户信息key
            result = saveMsgByKey("loginUserInfo", value, 1800);
        } catch (Exception e) {
            logger.error(SystemInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.REDIS_INSERT_USER_ERROR);

        }
        return result;
    }

   /**
    * 功能描述:根据key从redis中查询信息
    * @auther: G D
    * @param key
    * @return com.chinatower.framework.common_service.response.ProcessResult
    * @date: 2019/10/17 10:03
    */
    @Override
    @ApiOperation("根据key查询信息")
    public ProcessResult selectMsgByKey(@RequestParam("key") String key) {

        try {
            CacheModel cacheModel = new CacheModel();
            cacheModel.setKey(SystemInfo.REDIS_SIGN+key);
            Map<String, Object> result = redisServiceClient.query(cacheModel);
            return new ProcessResult(ProcessResult.SUCCESS, "", result);
        } catch (Exception e) {
            logger.error(SystemInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.REDIS_FIND_ERROR);
        }
    }

    /**
     * 功能描述:根据key删除信息
     * @auther: G D
     * @param key
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @date: 2019/10/17 9:52
     */
    @Override
    @ApiOperation("根据key删除信息")
    public ProcessResult deleteMsgByKey(@RequestParam("key") String key) {

        try {
            CacheModel cacheModel = new CacheModel();
            cacheModel.setKey(SystemInfo.REDIS_SIGN+key);
            Map<String, Object> result = redisServiceClient.del(cacheModel);
            return new ProcessResult(ProcessResult.SUCCESS, "", result);
        } catch (Exception e) {
            logger.error(SystemInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.REDIS_DELETE_ERROR);
        }
    }

    /**
     * 功能描述:根据key更新信息（value和exprireTime选填）
     * @auther: G D
     * @param key
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @date: 2019/10/17 9:53
     */
    @Override
    @ApiOperation("根据key更新信息")
    public ProcessResult updateMsgByKey(@RequestParam("key") String key,
                                        @RequestParam("value")Object value,
                                        @RequestParam("expireTime") long expireTime) {

        try {
            CacheModel cacheModel = new CacheModel();
            cacheModel.setKey(SystemInfo.REDIS_SIGN+key);
            if(value!=null){
                cacheModel.setValue(value);
            }else if(expireTime>0){
                cacheModel.setExpireTime(expireTime);
            }
            Map<String, Object> result = redisServiceClient.update(cacheModel);
            return new ProcessResult(ProcessResult.SUCCESS, "", result);
        } catch (Exception e) {
            logger.error(SystemInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.REDIS_UPDATE_ERROR);
        }
    }

}
