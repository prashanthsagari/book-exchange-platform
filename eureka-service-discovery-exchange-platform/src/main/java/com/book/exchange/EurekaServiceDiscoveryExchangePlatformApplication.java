package com.book.exchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServiceDiscoveryExchangePlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServiceDiscoveryExchangePlatformApplication.class, args);
	}

}
