package com.gorugoru.api.component.auth;

import javax.servlet.http.HttpSession;

/**
 * common social login interface
 * maybe OAuth2
 * @author Administrator
 *
 */
interface LoginComponent {
	/**
	 * 토큰 요청
	 * @param session
	 * @return
	 */
	boolean auth(HttpSession session);
	/**
	 * 토큰 갱신
	 * @param session
	 * @return
	 */
	boolean refresh(HttpSession session);
	/**
	 * 앱 연결
	 * @param session
	 * @return
	 */
	boolean login(HttpSession session);
	/**
	 * 사용자 정보
	 * @param session
	 * @return
	 */
	Object userInfo(HttpSession session);
	/**
	 * 로그아웃
	 * @param session
	 * @return
	 */
	boolean logout(HttpSession session);
	/**
	 * 앱 연결 해제
	 * @param session
	 * @return
	 */
	boolean unlink(HttpSession session);
	String generateState();
}
