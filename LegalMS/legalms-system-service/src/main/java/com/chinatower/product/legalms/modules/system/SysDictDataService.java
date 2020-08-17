package com.chinatower.product.legalms.modules.system;

import com.alibaba.fastjson.JSONObject;
import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.base.BaseController;
import com.chinatower.product.legalms.common.ConstClass;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.mapper.dict.SysDictDataVOMapper;
import com.chinatower.product.legalms.modules.system.api.SysDictDataApi;
import com.chinatower.product.legalms.modules.system.entity.SysDictReturnDataVO;
import com.chinatower.product.legalms.vo.CacheModel;
import com.chinatower.product.legalms.vo.dict.SysDictDataVO;
import com.chinatower.provider.call.oauth.RedisServiceClient;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class SysDictDataService extends BaseController implements SysDictDataApi {

    private static final Logger logger = LoggerFactory.getLogger("TransLog");

    @Autowired
    private RedisServiceClient redisServiceClient;

    @Autowired
    private SysDictDataVOMapper sysDictDataVOMapper;

    @Override
    public ProcessResult selectDictById(@PathParam("dictId")Integer dictId) {
        List<SysDictDataVO> sysDictDataVOS = sysDictDataVOMapper.selectdictById(dictId);
        return new ProcessResult(ProcessResult.SUCCESS,"",sysDictDataVOS);
    }

    @Override
    public ProcessResult selectDictByType(@PathParam("dictType")String dictType) {
        List<SysDictDataVO> sysDictDataVOS = sysDictDataVOMapper.selectdictByType(dictType);
        return new ProcessResult(ProcessResult.SUCCESS,"",sysDictDataVOS);
    }

    @Override
    public ProcessResult selectSysDictData(@RequestBody SysDictDataVO sysDictDataVO) {
        PageHelper.startPage(sysDictDataVO.getPageNum(),sysDictDataVO.getPageSize());
        List<SysDictDataVO> sysDictDatas= sysDictDataVOMapper.selectdictByType(sysDictDataVO.getDictType());
        PageInfo<SysDictDataVO> pageInfo = new PageInfo<>(sysDictDatas);
        return new ProcessResult(ProcessResult.SUCCESS,"",pageInfo);
    }

    @Override
    public ProcessResult addSysDictData(@RequestBody SysDictDataVO sysDictDataVO) {
        int a;
        try {
            UserInfo userInfo = RequestHolder.getUserInfo();
            sysDictDataVO.setCreateBy(userInfo.getLoginName());
            String dictcode = getId();
            sysDictDataVO.setDictCode(dictcode);
            a = sysDictDataVOMapper.addSysDictData(sysDictDataVO);
            return new ProcessResult(ProcessResult.SUCCESS,"",a);
        }catch (Exception e){
            logger.error(SystemInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.SYSDICT_DATA_INSERT_ERROR);
        }
    }

    @Override
    public ProcessResult updateSysDictData(@RequestBody SysDictDataVO sysDictDataVO) {
        int a;
        try {
            UserInfo userInfo = RequestHolder.getUserInfo();
            sysDictDataVO.setUpdateBy(userInfo.getLoginName());
            a = sysDictDataVOMapper.updateSysDictData(sysDictDataVO);
            return new ProcessResult(ProcessResult.SUCCESS,"",a);
        }catch (Exception e){
            logger.error(SystemInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.SYSDICT_DATA_UPDATE_ERROR);
        }
    }

    @Override
    public ProcessResult deleteSysDictData(@RequestBody SysDictDataVO sysDictDataVO) {
        if(sysDictDataVO.getDictType()==null && sysDictDataVO.getDictCode()==null){
            return new ProcessResult(ProcessResult.ERROR,"dictcode和dicttype都未传入");
        }
        try {
            int a = sysDictDataVOMapper.deleteSysDictData(sysDictDataVO);
            if(a!=0){
                return new ProcessResult(ProcessResult.SUCCESS,"",a);
            }else{
                return new ProcessResult(ProcessResult.ERROR,"刪除失败");
            }
        }catch (Exception e){
            logger.error(SystemInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.SYSDICT_DATA_DELETE_ERROR);
        }
    }

    @Override
    public ProcessResult selectAllSysDictData() {
        try {
            CacheModel cacheModel = new CacheModel().setKey(ConstClass.REDIS_DICT_DATA);
            Map<String, Object> query = redisServiceClient.query(cacheModel);
            if (ConstClass.GET_SUCCESS.equals(query.get(ConstClass.MES))) {
                return new ProcessResult(ProcessResult.SUCCESS, ConstClass.SUCCESS.SUCCESS_MESS, JSONObject.parseObject((String) query.get("data"), Map.class));
            } else {
                List<SysDictDataVO> sysDictDataVOS = sysDictDataVOMapper.selectSysDictData();
                Map<String, List<SysDictDataVO>> collect = sysDictDataVOS.stream().collect(Collectors.groupingBy(SysDictDataVO::getDictType));
                Map<String, List<SysDictReturnDataVO>> result = new HashMap<>();
                collect.forEach((key, value) -> {
                    List<SysDictReturnDataVO> tempList = new ArrayList<>();
                    value.forEach(x ->
                        tempList.add(new SysDictReturnDataVO().setDictValue(x.getDictValue()).setDictCabel(x.getDictCabel()).setStatus(x.getStatus())));
                    result.put(key, tempList);
                });
                return new ProcessResult(ProcessResult.SUCCESS, ConstClass.SUCCESS.SUCCESS_MESS, result);
            }
        } catch (Exception e) {
            logger.error(ConstClass.FAILURE.FAILURE_MESS, e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.FAILURE_MESS);
        }
    }
    public static String getId() {
        String string = System.currentTimeMillis() + UUID.randomUUID().toString().substring(26);
        return string.substring(3);
    }

    @Override
    public ProcessResult selectDictDataByType(@RequestBody SysDictDataVO sysDictDataVO) {
        String dictType = sysDictDataVO.getDictType();
        if(StringUtils.isNotBlank(dictType)){
            try {
                List<SysDictDataVO> sysDictDataVOS = sysDictDataVOMapper.selectdictByType(dictType);
                List<SysDictDataVO> list = getTreeList(sysDictDataVOS,dictType);
                return new ProcessResult(ProcessResult.SUCCESS, ConstClass.SUCCESS.SUCCESS_MESS, list);
            } catch (Exception e) {
                logger.error(ConstClass.FAILURE.FAILURE_MESS, e);
                return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.FAILURE_MESS);
            }
        }else{
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.DICTTYPE_BLANK_ERROR);
        }
    }

    private List<SysDictDataVO> getTreeList(List<SysDictDataVO> sysDictDataVOS,String dictType) {
        //循环给type赋值 叶子节点才可选 找到所有的叶子节点
        List<SysDictDataVO> lastChildNode = new ArrayList<>();
        StringBuilder lastChildNodeStr = new StringBuilder();
        //递归找到 所有的叶子节点
        treeMenuList(sysDictDataVOS , dictType, lastChildNode);
        if(!lastChildNode.isEmpty()){
            for(int i=0;i<lastChildNode.size();i++){
                 lastChildNodeStr.append(",").append(lastChildNode.get(i).getDictCode()).append(",");
            }

        }
        // 根据叶子节点 给type赋值
        if(StringUtils.isNotBlank(lastChildNodeStr.toString())){
            dealWithType(sysDictDataVOS,lastChildNodeStr.toString());
        }
        return sysDictDataVOS;
    }

    private List<SysDictDataVO> dealWithType(List<SysDictDataVO> list, String lastChildNodeStr) {
        for(int i=0;i<list.size();i++){
            String id = ","+list.get(i).getDictCode()+",";
            if(lastChildNodeStr.indexOf(id)>-1){
                list.get(i).setIsParent(false);
                list.get(i).setType("1");
            }else{
                list.get(i).setIsParent(true);
                list.get(i).setType("2");
            }
        }
        return list;
    }

    /**
     * 找到所用终节点
     * @param sysDictDataVOs
     * @param pid
     * @param lastChildNode
     * @return
     */
    public static List<SysDictDataVO> treeMenuList(List<SysDictDataVO>  sysDictDataVOs ,String pid,List<SysDictDataVO> lastChildNode) {
        List<SysDictDataVO> tempSysDictDataVO =  new  ArrayList<>();
        List<SysDictDataVO> tempSysDictDataVO1 ;
        for(SysDictDataVO node : sysDictDataVOs) {
            if(pid.equals(node.getDictParentCode())) {
                //说明存在子节点
                tempSysDictDataVO1 = treeMenuList(sysDictDataVOs,node.getDictCode(),lastChildNode);
                if(tempSysDictDataVO1.isEmpty()) {
                    //不存在子节点
                    lastChildNode.add(node);

                }
                //用于让上一级判断是否存在子节点
                //因为存在子节点则tempSysDictDataVO不为空
                //函数结束后返回tempSysDictDataVO给上一级以供判断
                tempSysDictDataVO.add(node);
                //System.out.println("当前节点存在子节点");
            }
        }
        return tempSysDictDataVO;
    }
}
