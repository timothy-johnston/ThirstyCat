package com.tj.thirstycat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.tj.thirstycat.property.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
	FileStorageProperties.class
})
public class ThirstycatApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThirstycatApplication.class, args);
	}

}

