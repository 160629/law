package com.chinatower.product.legalms.modules.system;

import com.alibaba.fastjson.JSONArray;
import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.ConstClass;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.modules.system.api.TSysCourtNewApi;
import com.chinatower.product.legalms.modules.system.entity.TSysCourtNew;
import com.chinatower.product.legalms.modules.system.mapper.TSysCourtNewMapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.Map;

@RestController
@Slf4j
@Api(tags={"tSysCourtNew"})
public class TSysCourtNewService implements TSysCourtNewApi {
    @Autowired
    TSysCourtNewMapper tSysCourtNewMapper;
    private static final Logger logger = LoggerFactory.getLogger("TransLog");
    public ProcessResult readTxtFile() {
        TSysCourtNew tSysCourtNew=new TSysCourtNew();
        InputStreamReader read =null;
        BufferedReader bufferedReader=null;
        try {
            String encoding = "GBK";
//            String listingFolder = config.getProperty("myApplication.listingFolder").toString();
            File file = new File("TSysCourt.txt");
//            File file = new File("G:\\A_文档资料\\Java项目资料\\2019铁塔法务项目组\\法院数据\\法院数据.txt");
            if (file.isFile() && file.exists()){ //判断文件是否存在
                read= new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                 bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                int i=0;

                while ((lineTxt = bufferedReader.readLine()) != null) {
                    i++;
                    //如果行数是二的倍数
                    if(i%2==0){
                        String[] arr=lineTxt.split(";");
                        for (int j = 0; j < arr.length; j++) {
                            String pid =arr[j].substring(arr[j].indexOf('_')+1,arr[j].indexOf('='));
                            tSysCourtNew.setPid(Integer.valueOf(pid));
                           String json=arr[j].substring(arr[j].indexOf('=') + 1);

                            JSONArray array = JSONArray.parseArray(json);
                            for (int k = 0; k < array.size(); k++){
                                Map<String, Object> jsonMap = (Map<String, Object>)array.get(k);
                                tSysCourtNew.setHasNext(String.valueOf(jsonMap.get("hasNext")));
                                tSysCourtNew.setId(Integer.valueOf(jsonMap.get("id").toString()));
                                tSysCourtNew.setSname(jsonMap.get("sname").toString());
                                tSysCourtNewMapper.addTest(tSysCourtNew);
                            }

                        }
                    }
                }
            }
            return new ProcessResult(ProcessResult.SUCCESS, "");
        } catch (Exception e) {
            logger.error(SystemInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.SYSDICT_DATA_INSERT_ERROR);
        } finally {
          if (bufferedReader!=null){
                try {
                    bufferedReader.close();
                    if (read !=null){
                        read.close();
                    }
                } catch (IOException e) {
                    logger.error(SystemInfo.OPERATION_EXCEPTION,e);
                }
            }
        }
    }



}
