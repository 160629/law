package com.chinatower.product.legalms.modules.system;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.base.BaseController;
import com.chinatower.product.legalms.common.ConstClass;
import com.chinatower.product.legalms.common.StringUtil;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.modules.system.api.TSysCourtApi;
import com.chinatower.product.legalms.modules.system.entity.TSysCourtPage;
import com.chinatower.product.legalms.modules.system.entity.TSysCourtVO;
import com.chinatower.product.legalms.modules.system.mapper.TSysCourtMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @author shiyuqi
 * @date
 */
@RestController
@Component
public class TSysCourtService extends BaseController implements TSysCourtApi {

    @Autowired
    private TSysCourtMapper tSysCourtMapper;
    private static final Logger logger = LoggerFactory.getLogger("TransLog");
    @Override
    public ProcessResult selectTSysCourt(@RequestBody TSysCourtPage tSysCourtPage) {
       /* List<String> list = new ArrayList<>();
            if (!StringUtils.isNotBlank(tSysCourtPage.getPid()) && !tSysCourtPage.getId().equals("0") ){
                List<TSysCourtVO> sysCourtVOLists = tSysCourtMapper.selectAlls(tSysCourtPage.getId());
                for (int i = 0; i < sysCourtVOLists.size(); i++) {
                    list.add(sysCourtVOLists.get(i).getId());
                }
                if (!sysCourtVOLists.isEmpty()){
                    tSysCourtPage.setIdList(list);
                }else{
                    tSysCourtPage.setIdList(Arrays.asList(tSysCourtPage.getId()));
                }
            }else{
                tSysCourtPage.setIdList(Arrays.asList(tSysCourtPage.getId()));
    }*/
        try {
            PageHelper.startPage(tSysCourtPage.getPageNum(), tSysCourtPage.getPageSize());// 分页对象  分页查询

            if ("1".equals(tSysCourtPage.getFlag())){
                List<TSysCourtVO> list= tSysCourtMapper.selectTSysCourt(tSysCourtPage);
                PageInfo<TSysCourtVO> pageInfo = new PageInfo<>(list);
                return new ProcessResult(ProcessResult.SUCCESS, "", pageInfo);
            }else{
                List<TSysCourtVO> list1= tSysCourtMapper.selectTSysCourtName(tSysCourtPage);
                PageInfo<TSysCourtVO> pageInfo = new PageInfo<>(list1);
                return new ProcessResult(ProcessResult.SUCCESS, "", pageInfo);
            }
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR,e);
            return new ProcessResult(ProcessResult.ERROR,e.getMessage());
        }
    }
    public ProcessResult selectTSysCourtName(@RequestBody TSysCourtPage tSysCourtPage) {
        try {
            PageHelper.startPage(tSysCourtPage.getPageNum(),tSysCourtPage.getPageSize());
            List<TSysCourtVO> list= tSysCourtMapper.selectTSysCourtName(tSysCourtPage);
            PageInfo<TSysCourtVO> pageInfo = new PageInfo<>(list);
            return new ProcessResult(ProcessResult.SUCCESS, "", pageInfo);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR,e);
            return new ProcessResult(ProcessResult.ERROR,e.getMessage());
        }
    }
    @Override
    public ProcessResult deleteTSysCourt(@RequestBody TSysCourtVO tSysCourtVO) {
        try {
             tSysCourtVO = tSysCourtMapper.selectAll1(tSysCourtVO);
             if (!tSysCourtVO.getSysCourtVOList().isEmpty() && tSysCourtVO.getSysCourtVOList() != null) {
                for (int i = 0; i < tSysCourtVO.getSysCourtVOList().size(); i++) {
                     if (tSysCourtVO.getSysCourtVOList().get(i).getState().equals("1")) {
                         break;
                     }else{
                         int a = tSysCourtMapper.deleteTSysCourt(tSysCourtVO);
                         if(a!=0){
                             return new ProcessResult(ProcessResult.SUCCESS,"",a);
                         }else{
                             return new ProcessResult(ProcessResult.ERROR,"刪除失败");
                         }
                     }
                 }
                 return new ProcessResult(ProcessResult.ERROR, "当前法院含有下级法院，不可删除!");
             }
            int a = tSysCourtMapper.deleteTSysCourt(tSysCourtVO);
            if(a!=0){
                return new ProcessResult(ProcessResult.SUCCESS,"",a);
            }else{
                return new ProcessResult(ProcessResult.ERROR,"刪除失败");
            }
        }catch (Exception e){
            logger.error(SystemInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.SYSDICT_DATA_DELETE_ERROR);
        }
    }

    @Override
    public ProcessResult addTSysCourt(@RequestBody TSysCourtVO tSysCourtVO) {
        int a;
        try {
            tSysCourtVO.setId(StringUtil.getId());
            tSysCourtVO.setState("1");
            a = tSysCourtMapper.addTSysCourt(tSysCourtVO);
            return new ProcessResult(ProcessResult.SUCCESS,"",a);
        }catch (Exception e){
            logger.error(SystemInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.SYSDICT_DATA_INSERT_ERROR);
        }
    }


    @Override
    public ProcessResult updateTSysCourt(@RequestBody TSysCourtVO tSysCourtVO) {
        int a;
        try {
           tSysCourtVO.setCourtOldName(tSysCourtVO.getCourtName());
            a = tSysCourtMapper.updateTSysCourt(tSysCourtVO);
            return new ProcessResult(ProcessResult.SUCCESS,"",a);
        }catch (Exception e){
            logger.error(SystemInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.SYSDICT_DATA_UPDATE_ERROR);
        }
    }

    @Override
    public ProcessResult selectTSysCourtOne(String id) {
        try {
            TSysCourtVO  tSysCourtVO = tSysCourtMapper.selectTSysCourtOne(id);
            List<TSysCourtVO> list = tSysCourtMapper.selectTSysCourtList(id);
            if (list != null && !list.isEmpty()){
                tSysCourtVO.setIsParent("true");
            }else{
                tSysCourtVO.setIsParent("false");
            }
            return new ProcessResult(ProcessResult.SUCCESS, "", tSysCourtVO);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR,e);
            return new ProcessResult(ProcessResult.ERROR,e.getMessage());
        }
    }

    @Override
    public ProcessResult selectTSysCourtTree(@RequestBody(required=false) TSysCourtVO tSysCourtVO) {
        try {
       List<TSysCourtVO> tSysCourtVOList = tSysCourtMapper.selectAll(tSysCourtVO);
            for (int i = 0; i < tSysCourtVOList.size(); i++) {
                if (!tSysCourtVOList.get(i).getSysCourtVOList().isEmpty() && tSysCourtVOList.get(i).getSysCourtVOList() != null){
                for (int j=0; j<tSysCourtVOList.get(i).getSysCourtVOList().size();j++) {
                    if(tSysCourtVOList.get(i).getSysCourtVOList().get(j).getState().equals("1")){
                        tSysCourtVOList.get(i).setIsParent("true");
                        break;
                    } else{
                        tSysCourtVOList.get(i).setIsParent("false");
                    }
                }
                }else{
                    tSysCourtVOList.get(i).setIsParent("false");
                }
            }
            return new ProcessResult(ProcessResult.SUCCESS, "", tSysCourtVOList);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR,e);
            return new ProcessResult(ProcessResult.ERROR,e.getMessage());
        }
        /*return treeList(null,tSysCourtVO);*/
    }
    /*//ztree递归
    public List<Map<String, Object>>  treeList(String pid,List<TSysCourtVO> tSysCourtVO){
        List<Map<String, Object>>  list=new ArrayList<Map<String, Object>>();
        for (TSysCourtVO court: tSysCourtVO) {
            Map<String,Object> map=null;
            if(pid == court.getPid()){
                map=new HashMap<String, Object>();
                map.put("id",court.getId());
                map.put("courtName",court.getCourtName());
                map.put("courtLevel",court.getCourtLevel());
                map.put("children",treeListes(court.getId()));
            }
            if(map!=null){
                list.add(map);
            }
        }
        return list;
    }

    private List<Map<String, Object>> treeListes(String pid) {
        List<TSysCourtVO>  TSysCourtVO1= tSysCourtMapper.selectTSysCourtTree1(pid);
       return treeLists(pid,TSysCourtVO1);
    }

    private List<Map<String, Object>> treeLists(String pid, List<TSysCourtVO> tSysCourtVO1) {
        List<Map<String, Object>>  list1=new ArrayList<Map<String, Object>>();
        for (TSysCourtVO court1: tSysCourtVO1) {
            Map<String,Object> map=null;
            if(pid.equals(court1.getPid())){
                map=new HashMap<String, Object>();
                map.put("id",court1.getId());
                map.put("courtName",court1.getCourtName());
                map.put("courtLevel",court1.getCourtLevel());
                map.put("children",treeListes(court1.getId()));
            }
            if(map!=null){
                list1.add(map);
            }
        }
        return list1;
    }*/

}
