package com.chinatower.product.legalms.modules.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinatower.product.legalms.modules.system.mapper.WarnSelectMapper;
import com.chinatower.product.legalms.modules.system.service.WarnSelectService;

import tk.mybatis.mapper.util.StringUtil;
@Service
public class WarnSelectServiceImpl implements WarnSelectService{
	@Autowired
	WarnSelectMapper mapper;
	@Override
	public List<Map<String, Object>> selectBySql(String sql) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> hashMap = new HashMap<>();
		int i =0;
		if(StringUtil.isEmpty(sql)) {
			return list;
		}else if(sql.startsWith("select")) {
			list = mapper.selectBySql(sql);
		}else if(sql.startsWith("update")) {
			i = mapper.updateBySql(sql);
		}else if(sql.startsWith("delete")) {
			i = mapper.deleteBySql(sql);
		}else if(sql.startsWith("insert")) {
			 List<String> sqlSplit = sqlSplit(sql);
			for (String string : sqlSplit) {
				i = i+mapper.insertBySql(string);
			}
		
		}
		if(i>0) {
			hashMap.put("updated", i);
			list.add(hashMap);
		}
		return list;
	}
	public List<String> sqlSplit(String sql) {
		String[] split = sql.split(";");
		List<String> list = new ArrayList<>();
		for (String string : split) {
			if(StringUtil.isNotEmpty(string)) {
				list.add(string+";");
			}
		}
		return list;
		
	}

}
