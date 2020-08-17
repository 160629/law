package com.chinatower.product.legalms;

		import com.chinatower.framework.cloud.ApplicationBootstrap;
		import org.springframework.boot.SpringApplication;


public class SystemApplication {

	private SystemApplication() {
		throw new IllegalAccessError("Utility class");
	}

	public static void main(String[] args) {
		SpringApplication.run(ApplicationBootstrap.class, args);
	}

}
