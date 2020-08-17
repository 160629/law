package com.chinatower.product.legalms.modules.system;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.base.BaseController;
import com.chinatower.product.legalms.common.ConstClass;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.mapper.dict.SysDictDataVOMapper;
import com.chinatower.product.legalms.mapper.dict.SysDictTypeMapper;
import com.chinatower.product.legalms.modules.system.api.SysDictTypeApi;
import com.chinatower.product.legalms.vo.dict.SysDictDataVO;
import com.chinatower.product.legalms.vo.dict.SysDictTypePage;
import com.chinatower.product.legalms.vo.dict.SysDictTypeVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class SysDictTypeService extends BaseController implements SysDictTypeApi {

    private static final Logger logger = LoggerFactory.getLogger("TransLog");

    @Autowired
    private SysDictTypeMapper sysDictTypeMapper;

    @Autowired
    private SysDictDataVOMapper sysDictDataVOMapper;

    @Override
    public ProcessResult selectDictType(@RequestBody SysDictTypePage sysDictTypePage) {
        PageHelper.startPage(sysDictTypePage.getPageNum(),sysDictTypePage.getPageSize());
        List<SysDictTypeVO> sysDictTypes= sysDictTypeMapper.selectDictTypeAll(sysDictTypePage);
        PageInfo<SysDictTypeVO> pageInfo = new PageInfo<>(sysDictTypes);
        return new ProcessResult(ProcessResult.SUCCESS,"",pageInfo);
    }

    @Override
    public ProcessResult addDictType(@RequestBody SysDictTypeVO sysDictTypeVO) {
        int a;
        try {
            UserInfo userInfo = RequestHolder.getUserInfo();
            sysDictTypeVO.setCreateBy(userInfo.getLoginName());
            int count = sysDictTypeMapper.selectDictTypeByOne(sysDictTypeVO.getDictType(),sysDictTypeVO.getDictName());
            if(count==0) {
                a = sysDictTypeMapper.addDictType(sysDictTypeVO);
                return new ProcessResult(ProcessResult.SUCCESS,"",a);
            }else {
                return new ProcessResult(ProcessResult.ERROR,"添加失败，字典类型或名称重复");
            }
        }catch (Exception e){
            logger.error(SystemInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.SYSDICT_TYPE_INSERT_ERROR);
        }
    }

    @Override
    public ProcessResult updateDictType(@RequestBody SysDictTypeVO sysDictTypeVO) {
        int a;
        try {
            UserInfo userInfo = RequestHolder.getUserInfo();
            sysDictTypeVO.setUpdateBy(userInfo.getLoginName());
//            count = sysDictTypeMapper.selectDictTypeByName(sysDictTypeVO.getDictName());
//            if(count==0) {
            a = sysDictTypeMapper.updateDictType(sysDictTypeVO);
            return new ProcessResult(ProcessResult.SUCCESS,"",a);
//            }else {
//                return new ProcessResult(ProcessResult.ERROR,"修改失败，字典名称重复");
//            }
        }catch (Exception e){
            logger.error(SystemInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.SYSDICT_TYPE_UPDATE_ERROR);
        }
    }

    @Override
    @Transactional
    public ProcessResult deleteDictType(@RequestParam("dictId") int dictId) {
        int a;
        try {
            SysDictTypeVO sysDictTypeVO = sysDictTypeMapper.selectDictTypeById(dictId);
            String dictType = sysDictTypeVO.getDictType();
            a = sysDictTypeMapper.deleteDictType(dictId);
            if(a!=0){
                SysDictDataVO vo = new SysDictDataVO();
                vo.setDictType(dictType);
                sysDictDataVOMapper.deleteSysDictData(vo);
            }
            return new ProcessResult(ProcessResult.SUCCESS,"",a);
        }catch (Exception e){
            logger.error(SystemInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.SYSDICT_TYPE_DELETE_ERROR);
        }
    }

   /* @Override
    public ProcessResult sysDictTypeExcelExpress(HttpServletResponse response) {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(".xlsx");
            //创建一个表格
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowLine = 1;
            //获取数据
            List<SysDictTypeVO> sysDictTypeVOS = sysDictTypeMapper.selectDictTypeAll();
            //遍历数据，获取其中元素
            for (SysDictTypeVO s:sysDictTypeVOS) {
                XSSFRow row = sheet.createRow(rowLine);

                if(StringUtils.isNotBlank(s.getDictId().toString())){
                    row.createCell(0).setCellValue(s.getDictId());
                }

                if(StringUtils.isNotBlank(s.getDictName())){
                    row.createCell(1).setCellValue(s.getDictName());
                }

                if(StringUtils.isNotBlank(s.getDictType())){
                    row.createCell(2).setCellValue(s.getDictType());
                }

                if(StringUtils.isNotBlank(s.getStatus())){
                    row.createCell(3).setCellValue(s.getStatus());
                }

                if(StringUtils.isNotBlank(s.getCreateBy())){
                    row.createCell(4).setCellValue(s.getCreateBy());
                }

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                if(StringUtils.isNotBlank(format.format(s.getCreateTime()))){
                    row.createCell(5).setCellValue(s.getCreateTime());
                }

                if(StringUtils.isNotBlank(s.getUpdateBy())){
                    row.createCell(6).setCellValue(s.getUpdateBy());
                }

                if(StringUtils.isNotBlank(format.format(s.getUpdateTime()))){
                    row.createCell(7).setCellValue(s.getUpdateTime());
                }

                if(StringUtils.isNotBlank(s.getRemark())){
                    row.createCell(8).setCellValue(s.getRemark());
                }
                rowLine++;
            }
            ServletOutputStream outputStream = response.getOutputStream();
            response.reset();   // 清空
            // 设置响应的文件的头文件格式
            response.setHeader("Content-disposition", "attachment;filename=" + new String("快递记录导出".getBytes("utf-8"), "ISO-8859-1") + ".xlsx");
            response.setContentType("application/msexcel");
            response.setCharacterEncoding("UTF-8");
            workbook.write(outputStream);
            outputStream.close();
            return null;
        } catch (IOException e) {
            return null;
        }
    }*/

}