package com.tj.thirstyCatWeb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

//SpringBootServletInitializer runs SpringApplication from a WAR deployment
@SpringBootApplication
public class ThirstyCatWebApplication  extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ThirstyCatWebApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ThirstyCatWebApplication.class, args);
		
		
	}

}

