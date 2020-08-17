package com.chinatower.product.legalms.modules.system;

import com.alibaba.fastjson.JSONObject;
import com.chinatower.product.legalms.common.ConstClass;
import com.chinatower.product.legalms.mapper.dict.SysDictDataVOMapper;
import com.chinatower.product.legalms.modules.system.entity.SysDictReturnDataVO;
import com.chinatower.product.legalms.vo.CacheModel;
import com.chinatower.product.legalms.vo.dict.SysDictDataVO;
import com.chinatower.provider.call.oauth.RedisServiceClient;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Aspect
@Component
public class SysDictDataAspect {

    protected static Logger logger = LoggerFactory.getLogger(SysDictDataAspect.class);

    @Autowired
    private RedisServiceClient redisServiceClient;

    @Autowired
    private SysDictDataService sysDictDataService;

    @Autowired
    private SysDictDataVOMapper sysDictDataVOMapper;

    @Pointcut("execution(* com.chinatower.product.legalms.modules.system.SysDictDataService.deleteSysDictData(..))")
    public void pointCutToDeleteSysDictData() {
        // 切入数据字典配置表，删除方法
    }

    @Pointcut("execution(* com.chinatower.product.legalms.modules.system.SysDictDataService.addSysDictData(..))")
    public void pointCutToAddSysDictData() {
        // 切入数据字典配置表，新增方法
    }

    @Pointcut("execution(* com.chinatower.product.legalms.modules.system.SysDictDataService.updateSysDictData(..))")
    public void pointCutToUpdateSysDictData() {
        // 切入数据字典配置表，更新方法
    }

//    @Pointcut("execution(* com.chinatower.product.legalms.modules.system.SysDictDataService.selectAllSysDictData(..))")
//    public void pointCutToSelectAllSysDictData() {
//        // 切入数据字典配置表，查询方法
//    }

    @After("pointCutToDeleteSysDictData() || pointCutToAddSysDictData() || pointCutToUpdateSysDictData()")
    public void afterChange() {
        // 从1 改成时间戳 解决 不同客户端之间不同步问题，一个客户端更改数据字典之后，另一个客户端取值还是1，表示未做更改，不会更新缓存
        // 现在改成时间戳后，判断时间戳是否相同（前台判断）
        String currentTime = String.valueOf(System.currentTimeMillis());
        CacheModel model = new CacheModel().setKey(ConstClass.SYS_DICT_DATA_CHANGE).setValue(currentTime);
        Map<String, Object> isChange = redisServiceClient.query(model);
        List<SysDictDataVO> sysDictDataVOS = sysDictDataVOMapper.selectSysDictData();
        Map<String, List<SysDictDataVO>> collect = sysDictDataVOS.stream().collect(Collectors.groupingBy(SysDictDataVO::getDictType));
        Map<String, List<SysDictReturnDataVO>> result = new HashMap<>();
        collect.forEach((key, value) -> {
            List<SysDictReturnDataVO> tempList = new ArrayList<>();
            value.forEach(x ->
                tempList.add(new SysDictReturnDataVO().setDictValue(x.getDictValue()).setDictCabel(x.getDictCabel()).setStatus(x.getStatus())));
            result.put(key, tempList);
        });
        String o = JSONObject.toJSON(result).toString();
        o = o.replaceAll("\"", "'");
        CacheModel data = new CacheModel().setKey(ConstClass.REDIS_DICT_DATA).setValue(o);
        Map<String, Object> isData = redisServiceClient.query(data);
        Map<String, Object> insert = null;
        logger.info("****afterChange***model.getExpireTime():"+model.getExpireTime());
        if (ConstClass.KEY_IS_NULL.equals(isChange.get(ConstClass.MES))) {
            insert = redisServiceClient.insert(model);
        } else {
            insert = redisServiceClient.update(model);
        }
        logger.info(insert.toString());
        if (ConstClass.KEY_IS_NULL.equals(isData.get(ConstClass.MES))) {
            insert = redisServiceClient.insert(data);
        } else {
            insert = redisServiceClient.update(data);
        }
        logger.info(insert.toString());
        //更新 数据字典更新时间
        SysDictDataVO sysDictDataVO = new SysDictDataVO();
        sysDictDataVO.setDictCode("5852601521001b4670a0"); //需要保持数据字典各环境的数据一致
        sysDictDataVO.setDictValue(currentTime);
        sysDictDataVOMapper.updateSysDictData(sysDictDataVO);
    }

//    @After("pointCutToSelectAllSysDictData()")
//    public void afterSelect() {
//        CacheModel model = new CacheModel().setKey(ConstClass.SYS_DICT_DATA_CHANGE).setValue("0");
//        Map<String, Object> isChange = redisServiceClient.query(model);
//        Map<String, Object> insert = null;
//        if (ConstClass.KEY_IS_NULL.equals(isChange.get(ConstClass.MES))) {
//            insert = redisServiceClient.insert(model);
//        } else {
//            insert = redisServiceClient.update(model);
//        }
//        logger.info(insert.toString());
//    }
}
