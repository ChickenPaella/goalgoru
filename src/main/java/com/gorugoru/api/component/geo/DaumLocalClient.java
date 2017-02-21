package com.gorugoru.api.component.geo;

import java.net.URI;
import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

public class DaumLocalClient extends DaumClient {
	
	private static final String OUTPUT = "json";//xml jsonp

	public DaumLocalClient(String apikey) {
		super(apikey);
	}
	
	/**
	 * GET	주소→좌표 변환	/local/geo/addr2coord
	 */
	public String addr2coord(String address){
		final URI uri = UriComponentsBuilder.newInstance().scheme("https").host("apis.daum.net")
				.path("/local/geo/addr2coord")
				.query("apikey={apikey}&q={query}&output={output}")
				.buildAndExpand(apikey, address, OUTPUT)
				.encode()
				.toUri();
		
		HttpHeaders headers = new HttpHeaders();
		if(OUTPUT.equals("json")){
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
		}
		
		HttpEntity<String> entity = new HttpEntity<String>("", headers);
		
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
	    
	    return response.getBody();
	}
	
	/**
	 * GET	좌표→주소 변환	/local/geo/coord2addr
	 */
	public String coord2addr(String latitude, String longitude){
		final URI uri = UriComponentsBuilder.newInstance().scheme("https").host("apis.daum.net")
				.path("/local/geo/coord2addr")
				.query("apikey={apikey}&latitude={latitude}&longitude={longitude}&inputCoordSystem=WGS84&output={output}")
				.buildAndExpand(apikey, latitude, longitude, OUTPUT)
				.encode()
				.toUri();
		
		HttpHeaders headers = new HttpHeaders();
		if(OUTPUT.equals("json")){
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
		}
		
		HttpEntity<String> entity = new HttpEntity<String>("", headers);
		
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
	    
	    return response.getBody();
	}
	
	/**
	 * GET	좌표계 변환	/local/geo/transcoord
	 */
	public String transcoord(String address){
		return null;
	}
	
	/**
	 * GET	좌표→상세주소 변환	/local/geo/coord2detailaddr
	 */
	public String coord2detailaddr(String address){
		return null;
	}
	
	/**
	 * GET	키워드로 장소검색	/local/v1/search/keyword.{format}
	 */
	public String searchKeyword(String address){
		return null;
	}
	
	/**
	 * GET	카테고리로 장소 검색	/local/v1/search/category.{format}
	 */
	public String searchCategory(String address){
		return null;
	}
	

}
