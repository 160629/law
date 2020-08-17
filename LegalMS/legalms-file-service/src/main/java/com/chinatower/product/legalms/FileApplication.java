package com.chinatower.product.legalms;

import com.chinatower.framework.cloud.ApplicationBootstrap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

import javax.servlet.MultipartConfigElement;


public class FileApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationBootstrap.class, args);
	}

	@Bean
	public MultipartConfigElement multipartConfigElement(@Value("${multipart.maxFileSize}")String maxFileSize, @Value("${multipart.maxRequestSize}") String maxRequestSize) {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize(maxFileSize);
		factory.setMaxRequestSize(maxRequestSize);
		return factory.createMultipartConfig();
	}
}
