package com.gorugoru.api.component.geo;

import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class DaumClient {
	
	protected final String apikey;
	
	protected final RestTemplate restTemplate;

	public DaumClient(String apikey) {
		super();
		this.apikey = apikey;
		
		OkHttp3ClientHttpRequestFactory factory = new OkHttp3ClientHttpRequestFactory();
        factory.setReadTimeout(10 * 1000);
        factory.setConnectTimeout(10 * 1000);
        restTemplate = new RestTemplate(factory);
	}
	

}
