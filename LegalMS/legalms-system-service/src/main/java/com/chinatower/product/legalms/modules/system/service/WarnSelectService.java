package com.chinatower.product.legalms.modules.system.service;

import java.util.List;
import java.util.Map;

public interface WarnSelectService {
	List<Map<String, Object>> selectBySql(String sql);
}
