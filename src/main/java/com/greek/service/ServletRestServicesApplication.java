package com.greek.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletRestServicesApplication extends SpringBootServletInitializer {

	private static Class<RestServicesApplication> applicationClass = RestServicesApplication.class;

	public static void main(String[] args) {
		SpringApplication.run(applicationClass, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.properties("spring.config.name:pegasus-rest-api").sources(applicationClass);
	}

}
