package com.gorugoru.api.component;

public interface OAuth2Client {
	/**
	 * 인증
	 * @param code - 인증 코드
	 * @return response
	 */
	String authenticate(String code);
	
	/**
	 * 토큰 해제
	 * @param accessToken - 엑세스 토큰
	 * @return response
	 */
	String revokeToken(String accessToken);
	
}
