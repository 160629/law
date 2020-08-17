package com.chinatower.product.legalms.utils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.exception.MyOwnRuntimeException;


public class HttpUtils {
	
	private HttpUtils() {
		super();
	}

	public static final String DEFAULT_ENCODING = "utf-8";
	public static final String DEFAULT_CONTENT_TYPE = "application/json;";
	public static final Integer DEFAULT_TIMEOUT = 10000 * 60 * 3;
	private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

	public static String post(String url, String params,Integer timeout) {
		return post(url, params, timeout, DEFAULT_CONTENT_TYPE, DEFAULT_ENCODING, DEFAULT_ENCODING, null);
	}

	public static String post(String url, String params) {
		return post(url, params, DEFAULT_TIMEOUT, DEFAULT_CONTENT_TYPE, DEFAULT_ENCODING, DEFAULT_ENCODING, null);
	}
	public static String post(String url, String params, Map<String, Object> headers) {
		return post(url, params, DEFAULT_TIMEOUT, DEFAULT_CONTENT_TYPE, DEFAULT_ENCODING, DEFAULT_ENCODING, headers);
	}

	public static String post(String url, String params, Integer timeout, String contentType, String sendEncoding,
			String receiveEncoding, Map<String, Object> headers) {

		HttpURLConnection con = null;
		BufferedReader in = null;
		try{
			URL obj = new URL(url);
			con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", contentType);
			con.setRequestProperty("connection", "Keep-Alive");
			con.setRequestProperty("Content-Length", String.valueOf(params.length()));
			if (null != headers) {
				for (Map.Entry<String, Object> head : headers.entrySet()) {
					con.setRequestProperty(head.getKey(), head.getValue() + "");
				}
			}
			con.setConnectTimeout(timeout);
			con.setReadTimeout(timeout);
			con.setDoOutput(true);
			con.setDoInput(true);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(), sendEncoding));
			bw.write(params);
			bw.flush();
			bw.close();
			// 根据ResponseCode判断连接是否成功
			int responseCode = con.getResponseCode();
			in = new BufferedReader(new InputStreamReader(
					responseCode == 200 ? con.getInputStream() : con.getErrorStream(), receiveEncoding));
			String inputLine;
			StringBuilder response = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
				response.append("\r\n");
			}
			return response.toString();
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			if (con != null) {
				con.disconnect();
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					log.error(ProcessResult.ERROR, e);
				}
			}
		}
	}
	
	public static Object map2JavaBean(Class<?> clazz, Map<String, Object> map) {
		try {
	        Object javabean = clazz.newInstance(); 
	        Method[] methods = clazz.getMethods(); 
	        for (Method method : methods) {
	            if (method.getName().startsWith("set")) {
	                String field = method.getName(); 
	                field = field.substring(field.indexOf("set") + 3);
	                field = field.toLowerCase().charAt(0) + field.substring(1);
	                if (map.containsKey(field)) {
	                    method.invoke(javabean, map.get(field));
	                }
	            }
	        }
	        return javabean;
		} catch (Exception e) {
			throw new MyOwnRuntimeException(e);
		}
    }
}
