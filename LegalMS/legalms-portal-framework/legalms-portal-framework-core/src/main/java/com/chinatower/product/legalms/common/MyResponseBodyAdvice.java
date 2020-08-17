package com.chinatower.product.legalms.common;


import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.mapper.dict.SysDictDataVOMapper;
import com.chinatower.product.legalms.vo.CacheModel;
import com.chinatower.provider.call.oauth.RedisServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;
import java.util.Map;

/**
 * 拦截Controller方法默认返回参数，统一处理返回值/响应体
 *
 * @author yclimb
 * @date 2018/6/29
 */
@ControllerAdvice
public class MyResponseBodyAdvice implements ResponseBodyAdvice {

    private static final Logger logger = LoggerFactory.getLogger(MyResponseBodyAdvice.class);

    @Autowired
    RedisServiceClient redisServiceClient;

    @Autowired
    SysDictDataVOMapper sysDictDataVOMapper;

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        try {

            if(o instanceof ProcessResult){
                CacheModel model = new CacheModel().setKey(CoreConstClass.IS_DICT_DATA_CACHE);
                Map<String, Object> query = redisServiceClient.query(model);
                if(query!=null){
                    if (CoreConstClass.GETSUCCESS.equals(query.get(CoreConstClass.MES))) {
                        ((ProcessResult) o).setCallBack((String) query.get("data"));
                    } else {
                        logger.error(CoreConstClass.FAILURE_MESS,query);
                        setCallBack(o);
                    }
                }else{
                    logger.error(CoreConstClass.FAILURE_MESS,query);
                    setCallBack(o);
                }
            }
        }catch (Exception e){
            logger.error(CoreConstClass.FAILURE_MESS,e);
        }
        return o;
    }

    private void setCallBack(Object o) {
        List<String> list = sysDictDataVOMapper.selectdictByTypeNotSort("sys_dict_updatetime");
        if(!list.isEmpty()){
            ((ProcessResult) o).setCallBack(list.get(0));
        }
    }


    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }
}

