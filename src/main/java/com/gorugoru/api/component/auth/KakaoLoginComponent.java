package com.gorugoru.api.component.auth;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class KakaoLoginComponent implements LoginComponent{
	
	private static final Logger logger = LoggerFactory.getLogger(KakaoLoginComponent.class);
	
	@Autowired
	ObjectMapper mapper;
	
	final KakaoClient kakaoClient;
	
	public KakaoLoginComponent(){
		kakaoClient = new KakaoClient("648dde0e73a6f287bebbb98ceb52aef1", "http://localhost:8080/api/auth/kakao_oauth");
	}
	
	/**
	 * 카카오 로그인
	 * token받아서 세션에 저장
	 */
	@Override
	public boolean auth(HttpSession session) {
		final String code = (String) session.getAttribute(AuthAttr.KAKAO_CODE);
		if(code == null || "".equals(code)){
			logger.error("auth() - session have no kakao_code");
			return false;
		}
		
		final String json = kakaoClient.authenticate(code);
		
		try {
			Token token = mapper.readValue(json, Token.class);
			if (token != null) {
				logger.info("login TOKEN: "+mapper.writeValueAsString(token));
				
				session.setAttribute(AuthAttr.KAKAO_TOKEN, token);
				session.removeAttribute(AuthAttr.KAKAO_CODE);
				
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * 사용자 토큰 갱신
	 */
	@Override
	public boolean refresh(HttpSession session) {
		Token kakaoToken = (Token) session.getAttribute(AuthAttr.KAKAO_TOKEN);
		if(kakaoToken == null){
			logger.error("refresh() - session has no token container");
			return false;
		}
		final String refreshToken = kakaoToken.getRefreshToken();
		if(refreshToken == null || "".equals(refreshToken)){
			logger.error("refresh() - session has no refresh_token");
			return false;
		}
		
		//TODO 토큰 만료시 처리 필요함
		final String json = kakaoClient.refresh(kakaoToken.getRefreshToken());
		
		try {
			Token token = mapper.readValue(json, Token.class);
			if (token != null) {
				logger.info("refreshToken TOKEN: "+mapper.writeValueAsString(token));
				
				//업데이트
				logger.info("prev access token: "+kakaoToken.getAccessToken());
				logger.info("new access token: "+token.getAccessToken());
				kakaoToken.setAccessToken(token.getAccessToken());
				kakaoToken.setExpiresIn(token.getExpiresIn());
				if(token.getRefreshToken() != null){
					/*
					 * 요청 결과로 갱신된 새로운 access_token과 해당 토큰의 만료 시간, 또한 Refresh Token
					 * 자체가 갱신되었으면 갱신된 refresh_token도 함께 받게 됩니다. refresh_token은 응답에
					 * 포함될 수도 있고 포함되지 않을 수도 있기 때문에 포함된 경우에 대해서만 갱신하여 저장하도록 합니다.
					 */
					kakaoToken.setRefreshToken(token.getRefreshToken());
				}
				session.setAttribute(AuthAttr.KAKAO_TOKEN, kakaoToken);
				
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * 로그인
	 * kakao 자동가입 check시 필요없음
	 */
	@Override
	public boolean login(HttpSession session) {
		Token kakaoToken = (Token)session.getAttribute(AuthAttr.KAKAO_TOKEN);
		if(kakaoToken == null){
			logger.error("login() - session has no token container");
			return false;
		}
		final String accessToken = kakaoToken.getAccessToken();
		if(accessToken == null || "".equals(accessToken)){
			logger.error("login() - session has no access_token");
			return false;
		}
		
		final String json = kakaoClient.signup(accessToken);
		//logger.info(json);
		
		try {
			ApiResponse res = mapper.readValue(json, ApiResponse.class);
			if (res != null) {
				logger.info("refreshToken res: "+mapper.writeValueAsString(res));
				logger.info("logout() id: "+res.getId());
				
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * 로그아웃 OAuth 세션해제
	 */
	@Override
	public boolean logout(HttpSession session) {
		Token kakaoToken = (Token) session.getAttribute(AuthAttr.KAKAO_TOKEN);
		if(kakaoToken == null){
			logger.error("logout() - session has no token container");
			return false;
		}
		final String accessToken = kakaoToken.getAccessToken();
		if(accessToken == null || "".equals(accessToken)){
			logger.error("logout() - session has no access_token");
			return false;
		}
		
		final String json = kakaoClient.revokeToken(accessToken);
		//logger.info(json);
		
		try {
			ApiResponse res = mapper.readValue(json, ApiResponse.class);
			if (res != null) {
				logger.info("refreshToken res: "+mapper.writeValueAsString(res));
				logger.info("logout() id: "+res.getId());
				
				session.removeAttribute(AuthAttr.KAKAO_TOKEN);
				
//				Enumeration<String> e = session.getAttributeNames();
//				while(e.hasMoreElements()){
//					String name = e.nextElement();
//					logger.info("remain session attr name: "+name);
//				}
				
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * 앱 연결해제
	 */
	@Override
	public boolean unlink(HttpSession session) {
		Token kakaoToken = (Token) session.getAttribute(AuthAttr.KAKAO_TOKEN);
		if(kakaoToken == null){
			logger.error("unlink() - session has no token container");
			return false;
		}
		final String accessToken = kakaoToken.getAccessToken();
		if(accessToken == null || "".equals(accessToken)){
			logger.error("unlink() - session has no access_token");
			return false;
		}
		
		final String json = kakaoClient.unlink(accessToken);
		
		try {
			ApiResponse res = mapper.readValue(json, ApiResponse.class);
			if (res != null) {
				logger.info("unlink res: "+mapper.writeValueAsString(res));
				logger.info("unlink() id: "+res.getId());
				
				session.removeAttribute(AuthAttr.KAKAO_TOKEN);
				
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * 사용자 정보
	 */
	@Override
	public UserProfile userInfo(HttpSession session) {
		Token kakaoToken = (Token) session.getAttribute(AuthAttr.KAKAO_TOKEN);
		if(kakaoToken == null){
			logger.error("userInfo() - session has no token container");
			return null;
		}
		final String accessToken = kakaoToken.getAccessToken();
		if(accessToken == null || "".equals(accessToken)){
			logger.error("userInfo() - session has no access_token");
			return null;
		}
		
		final String json = kakaoClient.userProfile(accessToken);
		logger.info(json);
		
		try {
			UserProfile res = mapper.readValue(json, UserProfile.class);
			if (res != null) {
				logger.info("userInfo res: "+mapper.writeValueAsString(res));
				logger.info("userInfo() id: "+res.getId());
				
				return res;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public boolean validate(String accessToken) {
		final String json = kakaoClient.validate(accessToken);
		logger.info(json);
		
		try {
			ApiResponse res = mapper.readValue(json, ApiResponse.class);
			if (res != null) {
				logger.info("validate res: "+mapper.writeValueAsString(res));
				logger.info("validate() id: "+res.getId());
				
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public String generateState() {
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}
	
	/**
	 * @param session - 검증용 state를 세션에 저장
	 * @return 로그인 url
	 */
	public String getLoginUrl(HttpSession session) {
		final String state = generateState();
		session.setAttribute("kakao_state", state);
		
		return kakaoClient.createAuthUrl(state);
	}
	
	public static final class Token implements Serializable{
		 
		private static final long serialVersionUID = 1L;
		
		@JsonProperty("access_token") private String accessToken;
		@JsonProperty("token_type") private String tokenType;
		@JsonProperty("refresh_token") private String refreshToken;
		@JsonProperty("expires_in") private int expiresIn;
		private Date expiresAt;
		@JsonProperty("scope") private String scope;
		
		public Token(){
		}
		 
		public Token(String access_token, String token_type, String refresh_token, int expires_in, String scope) {
			this.accessToken = access_token;
			this.tokenType = token_type;
			this.refreshToken = refresh_token;
			this.expiresIn = expires_in;
			this.expiresAt = new Date(System.currentTimeMillis() + this.expiresIn * 1000L);
			this.scope = scope;
		}

		public String getAccessToken() {
			return accessToken;
		}

		public void setAccessToken(String accessToken) {
			this.accessToken = accessToken;
		}

		public String getTokenType() {
			return tokenType;
		}

		public void setTokenType(String tokenType) {
			this.tokenType = tokenType;
		}

		public String getRefreshToken() {
			return refreshToken;
		}

		public void setRefreshToken(String refreshToken) {
			this.refreshToken = refreshToken;
		}

		public int getExpiresIn() {
			return expiresIn;
		}

		public void setExpiresIn(int expiresIn) {
			this.expiresIn = expiresIn;
			this.expiresAt = new Date(System.currentTimeMillis() + this.expiresIn * 1000L);
		}

		public Date getExpiresAt() {
			return expiresAt;
		}

		public void setExpiresAt(Date expiresAt) {
			this.expiresAt = expiresAt;
		}

		public String getScope() {
			return scope;
		}

		public void setScope(String scope) {
			this.scope = scope;
		}
		
	}
	
	public static class ApiResponse implements Serializable{
		 
		private static final long serialVersionUID = 1L;
		
		@JsonProperty("id") private long id;//앱연결된 사용자 ID
		
		public ApiResponse(){
		}
		
		public ApiResponse(long id){
			this.id = id;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}
		
	}
	
	public static final class UserProfile extends ApiResponse implements Serializable{
		 
		private static final long serialVersionUID = 1L;
		
		@JsonProperty("properties") private Properties properties;
		
		public UserProfile(){
			super();
		}
		
		public UserProfile(long id, Properties properties){
			super(id);
			this.properties = properties;
		}
		
		public Properties getProperties() {
			return properties;
		}

		public void setProperties(Properties properties) {
			this.properties = properties;
		}

		public static class Properties{
			@JsonProperty("nickname") private String nickname;
			@JsonProperty("profile_image") private String profileImage;
			@JsonProperty("thumbnail_image") private String thumbnailImage;
			
			public Properties(){
			}

			public Properties(String nickname, String profileImage, String thumbnailImage) {
				this.nickname = nickname;
				this.profileImage = profileImage;
				this.thumbnailImage = thumbnailImage;
			}

			public String getNickname() {
				return nickname;
			}

			public void setNickname(String nickname) {
				this.nickname = nickname;
			}

			public String getProfileImage() {
				return profileImage;
			}

			public void setProfileImage(String profileImage) {
				this.profileImage = profileImage;
			}

			public String getThumbnailImage() {
				return thumbnailImage;
			}

			public void setThumbnailImage(String thumbnailImage) {
				this.thumbnailImage = thumbnailImage;
			}
			
		}
		
	}

}
