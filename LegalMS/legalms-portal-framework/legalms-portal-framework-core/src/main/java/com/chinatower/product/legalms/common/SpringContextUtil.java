package com.chinatower.product.legalms.common;

import org.springframework.context.ConfigurableApplicationContext;

public class SpringContextUtil {
	private static ConfigurableApplicationContext applicationContext;

	private static String localIpStr;
	
	public static String getLocalIpStr() {
		return localIpStr;
	}


	// 获取上下文
	public static ConfigurableApplicationContext getApplicationContext() {
		return applicationContext;
	}

	// 设置上下文
	public static void setApplicationContext(ConfigurableApplicationContext applicationContext) {
		SpringContextUtil.applicationContext = applicationContext;
		SpringContextUtil.localIpStr = applicationContext.getEnvironment().getPropertySources()
				.get("springCloudClientHostInfo").getProperty("spring.cloud.client.ip-address").toString();
	}

	// 通过名字获取上下文中的bean
	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	// 通过类型获取上下文中的bean
	public static Object getBean(Class<?> requiredType) {
		return applicationContext.getBean(requiredType);
	}

	private SpringContextUtil() {
	}
	
}
