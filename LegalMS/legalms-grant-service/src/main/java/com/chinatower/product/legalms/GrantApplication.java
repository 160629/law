package com.chinatower.product.legalms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.chinatower.framework.cloud.ApplicationBootstrap;
import com.chinatower.product.legalms.common.SpringContextUtil;
import com.primeton.workflow.api.WFServiceException;

@SpringBootApplication
@EnableScheduling
@MapperScan(basePackages = {"com.chinatower.product.legalms.modules.flow.mapper"})
public class GrantApplication {

	public static void main(String[] args)throws WFServiceException {
		ConfigurableApplicationContext context = SpringApplication.run(ApplicationBootstrap.class, args);
		SpringContextUtil.setApplicationContext(context);
	}

}
