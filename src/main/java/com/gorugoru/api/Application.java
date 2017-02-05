package com.gorugoru.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gorugoru.util.NetUtil;

@SpringBootApplication
public class Application {
	
	private static final Logger logger = LoggerFactory.getLogger(Application.class);
			
	public static void main(String[] args) {
		//System.setProperty("debug", "true");//AUTO-CONFIGURATION REPORT
		if(!NetUtil.getHostAddr().equals("222.122.203.70") && System.getProperty("spring.profiles.active") == null){
			logger.info("use prop LOCAL");
			System.setProperty("spring.profiles.active", "local");//use command line -Dspring.profiles.active=production
		}
		
		SpringApplication.run(Application.class, args);
	}
}