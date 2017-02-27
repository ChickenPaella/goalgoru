package com.gorugoru.api.component.geo;

import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import okhttp3.OkHttpClient;

public class DaumClient {
	
	protected final String apikey;
	
	protected final RestTemplate restTemplate;

	public DaumClient(OkHttpClient okHttpClient, String apikey) {
		super();
		this.apikey = apikey;
        this.restTemplate = new RestTemplate(new OkHttp3ClientHttpRequestFactory(okHttpClient));
	}
	

}
