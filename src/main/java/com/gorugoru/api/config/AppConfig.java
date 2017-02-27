package com.gorugoru.api.config;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import okhttp3.OkHttpClient;

@Configuration
public class AppConfig {
	
	//OkHttpClients should be shared
	@Bean(name = {"httpClient", "okHttpClient"})
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
        		.connectTimeout(10, TimeUnit.SECONDS)
        		.readTimeout(10, TimeUnit.SECONDS).build();
    }
	
}
