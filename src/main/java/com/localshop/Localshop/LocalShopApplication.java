package com.localshop.Localshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LocalShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocalShopApplication.class, args);
	}

}
