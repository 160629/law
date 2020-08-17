package com.chinatower.product.legalms.common;

import com.chinatower.framework.common_service.response.ProcessResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil {

	public static final String LONG_STRICT = "yyyyMMddHHmmss";
	private static final Logger log = LoggerFactory.getLogger(CoreConstClass.LOGGER_NAME);
	public static String longStrict() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(LONG_STRICT);
		return simpleDateFormat.format(new Date());
	}

	/*public static  <T> List<T> copyPropertiesList(Class<T> cla,List objFrom) {
		register();
		List<T> list = new ArrayList<>(objFrom.size());
		try {
			for (int i = 0; i < objFrom.size(); i++) {
				Object instance = cla.newInstance();
				BeanUtils.copyProperties(instance, objFrom);
				list.add((T) instance);
			}
		} catch (Exception e) {
			throw new MyOwnRuntimeException(e);
		}
		return list;
	}
	public static void copyProperties(Object obj,Object objFrom) {
		register();
		try {
			BeanUtils.copyProperties(obj, objFrom);
		} catch (Exception e) {
			throw new MyOwnRuntimeException(e);
		}
	}*/
	/*public static void register() {
		ConvertUtils.register(new Converter() {
			public Object convert(Class arg0, Object arg) {
				if(!(arg instanceof String)){
					return null;
				}
				String pattern1="yyyy-MM-dd HH:mm:ss";
				String pattern2="yyyy-MM-dd";
				HashMap<Integer, String> map = new HashMap<>();
				map.put(pattern1.length(), pattern1);
				map.put(pattern2.length(), pattern2);
				String str=(String) arg;
				String pattern = map.get(str.length());
				if(str.trim().length()==0 || null==pattern)
					return null;
				SimpleDateFormat format = new SimpleDateFormat(pattern);
				try {
					return format.parse(str);
				} catch (Exception e) {
					log.error(ProcessResult.ERROR, e);
					throw new MyOwnRuntimeException(e);
				}
			}

		}, Date.class);
		ConvertUtils.register(new BigDecimalConverter(new BigDecimal("0")), BigDecimal.class);
	}*/
	public static String getId() {
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			log.error(ProcessResult.ERROR, e);
			Thread.currentThread().interrupt();
		}
		String string = new Date().getTime() + UUID.randomUUID().toString().substring(26);
		return string.substring(3);
	}

	public static boolean isEmpty(String str) {
		return null == str || str.isEmpty();
	}

	/** 下划线转驼峰 */
	public static String lineToHump(String str) {
		Pattern compile = Pattern.compile("_(\\w)");
		str = str.toLowerCase();
		Matcher matcher = compile.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/** 驼峰转下划线 */
	public static String humpToLine(String str) {
		Matcher matcher = Pattern.compile("[A-Z]").matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
	public static Map<String, Object> mapLineToHump(Map<String, Object> map) {
		Pattern pattern = Pattern.compile("_(\\w)");
		HashMap<String, Object> map1 = new HashMap<>();
		for (Entry<String, Object> en : map.entrySet()) {
			 boolean find = pattern.matcher(en.getKey()).find();
			 if(find) {
				 map1.put(lineToHump(en.getKey()), en.getValue());
			 }else {
				 map1.put(en.getKey(), en.getValue());
			 }
		}
		return map1;
	}

	public  static Map<String, Object> draftGetMap(String id,String status) {
		String[] ids= {"guideId","lawsuitId","assignId"};
		String[] statues= {"guideStatus","lawsuitStatus","assignStatus"};
		HashMap<String, Object> map = new HashMap<>();
		for (int i = 0; i < ids.length; i++) {
			map.put(ids[i], id);
			map.put(statues[i], status);
		}
		return map;
	}

	/*public  static <T> T getModel(Class<T> cla,Map<String, Object> map) {
		map =  mapLineToHump(map);
		try {
			T instance = cla.newInstance();
			register();
			BeanUtils.populate(instance, map);
			return instance;
		} catch (Exception e) {
			throw new MyOwnRuntimeException(e);
		}

	}*/
	private StringUtil() {
		super();
	}

	public static boolean mapEmpty(Map map) {
		return null == map ||  map.isEmpty();
	}
	public static boolean listEmpty(List list) {
		return null == list ||  list.isEmpty();
	}

	public static boolean listNotEmpty(List list) {
		return list != null && !list.isEmpty();
	}

	public static boolean strsEmpty(String... strs) {
		for (String string : strs) {
			if(isEmpty(string)) {
				return true;
			}
		}
		return false;
	}

	public static boolean stringEquals(String s1,String s2){
		if(null!=s1) {
			return s1.equals(s2);
		} else if(null!=s2) {
			return s2.equals(s1);
		} else {
			return true;
		}
	}
}
