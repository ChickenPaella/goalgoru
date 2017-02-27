package com.gorugoru.api.component.auth;

import java.net.URI;
import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.gorugoru.api.component.OAuth2Client;

import okhttp3.OkHttpClient;

/**
 * 카카오 REST API 클라이언트
 * TODO: 쓰레딩 고려해야함. RestTemplate thread-safe??
 * @author Administrator
 *
 */
public class KakaoClient implements OAuth2Client {
	
	//private static final Logger logger = LoggerFactory.getLogger(KakaoClient.class);
	
	private final String clientId;
	private final String redirectUri;
	private final RestTemplate restTemplate;
	
	public KakaoClient(OkHttpClient okHttpClient, String clientId, String redirectUri){
		this.clientId = clientId;
		this.redirectUri = redirectUri;
		this.restTemplate = new RestTemplate(new OkHttp3ClientHttpRequestFactory(okHttpClient));
	}
	
	/**
	 * 인증 사용자 인터랙션 url 생성
	 */
	public String createAuthUrl(final String state){		
		final URI uri = UriComponentsBuilder.newInstance().scheme("https").host("kauth.kakao.com")
					.path("/oauth/authorize")
					.query("client_id={app_key}&redirect_uri={redirect_uri}&response_type=code&state={state}")
					.buildAndExpand(clientId, redirectUri, state)
					.encode()
					.toUri();
		
		return uri.toString();
	}
	
	/**
	 * request request auth server
	 * @param isAuthOrRefresh - if true, 인증 or 갱신
	 * @param code - 인증시 필수
	 * @param refreshToken - 갱신시 필수
	 */
	private String requestToken(final boolean isAuthOrRefresh, final String code, final String refreshToken){
		final URI uri = UriComponentsBuilder.newInstance().scheme("https").host("kauth.kakao.com")
				.path("/oauth/token")
				.build()
				.encode()
				.toUri();
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	    
	    MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
	    body.add("grant_type", isAuthOrRefresh ? "authorization_code" : "refresh_token");
	    body.add("client_id", clientId);
	   
		if (isAuthOrRefresh) {
			body.add("redirect_uri", redirectUri);
			body.add("code", code);
		} else {
			body.add("refresh_token", refreshToken);
		}
	    
	    HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(body, headers);
	    
		try {
			ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
			if (response.getStatusCode() == HttpStatus.OK) {
				return response.getBody();
			}
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * 인증 사용자 토큰 초기 요청
	 * @param session
	 */
	public String authenticate(final String code){
		return requestToken(true, code, null);
	}
	
	/**
	 * 갱신 사용자 토큰 갱신
	 * @param refreshToken
	 * @return response body
	 */
	public String refresh(final String refreshToken){
		return requestToken(false, null, refreshToken);
	}
	
	/**
	 * request resource server
	 * @param path
	 * @param accessToken
	 * @param body
	 * @return
	 */
	private String requestAPI(final String path, final String accessToken, final MultiValueMap<String, String> body){
		return requestAPI(path, HttpMethod.POST, accessToken, body);
	}
	
	/**
	 * request resource server
	 * @param path
	 * @param method
	 * @param accessToken
	 * @param body
	 * @return
	 */
	private String requestAPI(final String path, final HttpMethod method, final String accessToken, final MultiValueMap<String, String> body){
		final URI uri = UriComponentsBuilder.newInstance().scheme("https").host("kapi.kakao.com")
				.path(path)
				.build()
				.encode()
				.toUri();
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer ".concat(accessToken));
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
	    
		HttpEntity<?> entity = null;
		if (body != null) {
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			entity = new HttpEntity<MultiValueMap<String, String>>(body, headers);
		} else {
			// empty body
			entity = new HttpEntity<String>("", headers);
		}

		try {
			ResponseEntity<String> response = restTemplate.exchange(uri, method, entity, String.class);
			if (response.getStatusCode() == HttpStatus.OK) {
				return response.getBody();
			}
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * 세션 해제 - 모든 토큰 사용불가
	 * @param accessToken
	 * @return response body
	 */
	public String revokeToken(final String accessToken){
		return requestAPI("/v1/user/logout", accessToken, null);
	}
	
	/**
	 * 앱 연결(가입)
	 * @param accessToken
	 * @return response body
	 */
	public String signup(final String accessToken){
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
	    body.add("properties", "{}");//가입하는 사용자의 정보. Json 형태의 key-value.
		return requestAPI("/v1/user/signup", accessToken, body);
	}
	
	/**
	 * 앱 연결해제(탈퇴)
	 */
	public String unlink(final String accessToken){
		return requestAPI("/v1/user/unlink", accessToken, null);
	}
	
	/**
	 * 사용자 정보요청
	 */
	public String userProfile(final String accessToken){
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
	    //body.add("propertyKeys", "[]");//optional 사용자 정보의 키 리스트. Json Array 형태.
	    body.add("secure_resource", "true");//이미지 url을 https로 반환할지 여부. true/false
		return requestAPI("/v1/user/me", accessToken, body);
	}
	
	/**
	 * 사용자 정보저장
	 */
	public String updateUserProfile(final String accessToken){
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
	    body.add("properties", "{}");//가입하는 사용자의 정보. Json 형태의 key:value.
		return requestAPI("/v1/user/update_profile", accessToken, body);
	}
	
	/**
	 * 사용자 토큰 유효성 검사 및 정보 얻기
	 */
	public String validate(final String accessToken) {
		return requestAPI("/v1/user/access_token_info", HttpMethod.GET, accessToken, null);
	}
}
