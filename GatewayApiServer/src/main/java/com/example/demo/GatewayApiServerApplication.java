package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApiServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApiServerApplication.class, args);
	}

}
