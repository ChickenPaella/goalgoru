package com.gorugoru.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * MVC설정
 * WebMvcConfigurerAdapter 대신 WebMvcConfigurationSupport 사용
 * http://www.thymeleaf.org 사용
 * springboot 자동설정 참고
 * https://github.com/spring-projects/spring-boot/blob/master/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/web/WebMvcAutoConfiguration.java
 * @author Administrator
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport{
	
	@Bean
	public RequestMappingHandlerMapping requestMappingHandlerMapping() {
		RequestMappingHandlerMapping handlerMapping = super.requestMappingHandlerMapping();
		handlerMapping.setUseSuffixPatternMatch(false);//ignore url file extension(comma .)
		return handlerMapping;
	}
	
	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
    	//assign templates non-controller used. 단순 뷰페이지 호출
    	registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/auth/login").setViewName("login");
    }
	
}