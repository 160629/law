package com.chinatower.product.legalms;

import com.chinatower.framework.cloud.ApplicationBootstrap;
import com.chinatower.product.legalms.common.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LicenseApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ApplicationBootstrap.class, args);
		SpringContextUtil.setApplicationContext(context);
	}
}
