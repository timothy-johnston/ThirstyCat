package com.tj.thirstycat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import com.tj.thirstycat.property.FileStorageProperties;


@ComponentScan("com.tj.thirstycat.repository")
@EnableConfigurationProperties({FileStorageProperties.class})
@SpringBootApplication
public class ThirstycatApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThirstycatApplication.class, args);
	}
	
	

}

