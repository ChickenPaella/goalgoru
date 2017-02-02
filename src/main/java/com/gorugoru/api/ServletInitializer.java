package com.gorugoru.api;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		//Map<String, Object> props = new HashMap<>();
        //props.put("spring.config.name", "api-app");
        //application.properties(props);
		return application.sources(Application.class);
	}

}
