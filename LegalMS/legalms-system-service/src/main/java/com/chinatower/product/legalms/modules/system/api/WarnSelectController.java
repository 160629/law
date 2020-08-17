package com.chinatower.product.legalms.modules.system.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.system.service.WarnSelectService;

@RestController
public class WarnSelectController implements WarnSelectAPI {
	@Autowired
	WarnSelectService service;

	@Override
	public ProcessResult selectBySql(String sql) {

		try {
			List<Map<String, Object>> selectBySql = service.selectBySql(sql);
			return new ProcessResult(ProcessResult.SUCCESS, "SUCCESS", selectBySql);
		} catch (Exception e) {
			return new ProcessResult(ProcessResult.ERROR, e.toString());
		}
	}

}
