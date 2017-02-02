package com.gorugoru.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		//System.setProperty("debug", "true");//AUTO-CONFIGURATION REPORT
		System.setProperty("spring.profiles.active", "local");//use command line -Dspring.profiles.active=production
		SpringApplication.run(Application.class, args);
	}
}