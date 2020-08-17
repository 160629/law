package com.chinatower.product.legalms.modules.cases.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.base.BaseController;
import com.chinatower.product.legalms.common.CaseInfo;
import com.chinatower.product.legalms.common.ConstClass;
import com.chinatower.product.legalms.modules.cases.entity.CaseTransfer;
import com.chinatower.product.legalms.modules.cases.service.CaseTransferService;
import com.chinatower.product.legalms.utils.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Date: 2019/12/11 11:43
 * @Description:
 */
@Slf4j
@RestController
@Api(tags = "卷宗移交模块")
public class CaseTransferApiImpl extends BaseController implements CaseTransferApi {

    private static final Logger logger = LoggerFactory.getLogger(CaseTransferApiImpl.class);

    @Autowired
    private CaseTransferService caseTransferService;


    /**
     * 功能描述:卷宗移交记录查询
     * @auther: guodong
     * @param caseTransfer
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @date: 2019/12/11 11:50
     */
    @ApiOperation("卷宗移交查询")
    @Override
    public ProcessResult selectCaseTransferInfo(@RequestBody CaseTransfer caseTransfer) {
        try {
            return caseTransferService.selectCaseTransferInfo(caseTransfer);
        } catch (Exception e) {
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.FAILURE_MESS);
        }
    }


    /**
     * 功能描述:移交卷宗增加
     * @auther: guodong
     * @param caseTransfer
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @date: 2019/12/11 15:02
     */
    @Override
    public ProcessResult addCaseTransferInfo(@RequestBody CaseTransfer caseTransfer) {
        try {
            return caseTransferService.addCaseTransferInfo(caseTransfer);
        } catch (Exception e) {
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR,e.toString());
        }
    }

    @Override
    public void getExcelDate(HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = caseTransferService.getExcelDate();
        FileUtil.downloadExcel(list, response);
    }


}
