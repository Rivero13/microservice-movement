package com.bootcamp.ms.movement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EntityScan({"com.bootcamp.ms.commons.entity"})
public class MicroserviceMovementApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceMovementApplication.class, args);
	}

}
