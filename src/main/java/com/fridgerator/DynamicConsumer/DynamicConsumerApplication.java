package com.fridgerator.DynamicConsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DynamicConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DynamicConsumerApplication.class, args);
	}

}
