package com.gorugoru.api.config.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.gorugoru.api.component.auth.AuthAttr;
import com.gorugoru.api.component.auth.KakaoLoginComponent;

/**
 * 커스텀 로그아웃 핸들러
 * 스프링 시큐리티와 외부 로그아웃 연동을 위함.
 * @author Administrator
 *
 */
public class CustomLogoutHandler implements LogoutHandler {
	
	@Autowired
	KakaoLoginComponent kakaoLogin;
	
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		final HttpSession session = request.getSession(false);
		
		if(session != null){
			String provider = (String) session.getAttribute(AuthAttr.AUTH_PROVIDER);
			
			if (AuthAttr.KAKAO.equals(provider)) {
				if(kakaoLogin.logout(session)){
					session.removeAttribute(AuthAttr.AUTH_PROVIDER);
				}
			}
			
		}
		
	}

}
