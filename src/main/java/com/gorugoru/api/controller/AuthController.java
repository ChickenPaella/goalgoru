package com.gorugoru.api.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.gorugoru.api.component.auth.AuthAttr;
import com.gorugoru.api.component.auth.AuthProvider;
import com.gorugoru.api.component.auth.KakaoLoginComponent;
import com.gorugoru.api.component.auth.KakaoLoginComponent.Token;
import com.gorugoru.api.component.auth.KakaoLoginComponent.UserProfile;
import com.gorugoru.api.domain.model.User;
import com.gorugoru.api.service.UserService;

/**
 * 로그인 컨트롤러
 * @author Administrator
 *
 */
@Controller
@RequestMapping(path = "/auth")
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	KakaoLoginComponent kakaoLogin;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	UserService userService;
	
	/**
	 * 카카오 로그인 요청
	 * 리다이렉트
	 * js로 구현시 필요없음
	 * @return
	 */
	@RequestMapping(value = "/kakao")
	public String kakao() {
		
		String referer = request.getHeader("referer");
		logger.info("kakao() - LOGIN referer: "+referer);
		HttpSession session = request.getSession();
		session.setAttribute(AuthAttr.KAKAO_REFERER, referer);
		
		String url = kakaoLogin.getLoginUrl(session);

		return "redirect:".concat(url);
	}
	
	/**
	 * 카카오 로그인 redirect uri
	 * @param state
	 * @param code
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(path = "/kakao_oauth", method = RequestMethod.GET)
	public String kakao_oauth(
			@RequestParam("code") String code,
			@RequestParam("state") String state,
			@CookieValue(name = "ltoat", required = false) String lastOAuthAccessToken,
			@CookieValue(name = "ltusq", required = false) String lastUserSeq,
			HttpServletResponse response) throws Exception{
		
		logger.info("kakao_oauth() - code: "+code);
		
		HttpSession session = request.getSession();
		String referer = (String) session.getAttribute(AuthAttr.KAKAO_REFERER);
		logger.info("kakao_oauth() - referer: "+referer);
		String state2 = (String) session.getAttribute(AuthAttr.KAKAO_STATE);
		
		if(state2 == null || !state2.equals(state)){
			throw new Exception("Invalid Request");
		}
		
		session.removeAttribute(AuthAttr.KAKAO_REFERER);
		session.removeAttribute(AuthAttr.KAKAO_STATE);
		
		//코드받기 성공
		session.setAttribute(AuthAttr.AUTH_PROVIDER, AuthAttr.KAKAO);
		session.setAttribute(AuthAttr.KAKAO_CODE, code);
		
		boolean needAuth = true;
		boolean authOK = false;
		
		//기존 발급된 토큰 사용
		if(lastOAuthAccessToken != null && lastUserSeq != null){
			logger.info(lastOAuthAccessToken);
			logger.info(lastUserSeq);
			
			if(userService.exists(Long.parseLong(lastUserSeq))){
				if(kakaoLogin.validate(lastOAuthAccessToken)){
					/*TODO DB에서 토큰정보를 가져와서, 재사용하면 된다.
					하지만 토큰 저장도 아직이다. Token객체 재사용할 수 있다. 의미는 없다.
					needAuth = false;
					authOK = true;
					*/
				}
			}
		}
		
		//토큰발급 필요
		if(needAuth){
			if(kakaoLogin.auth(session)){
				//oauth 인증 성공
				//앱 연결 자동연결 사용하므로, 재요청 400 Bad Request 발생
//				if(kakaoLogin.login(session)){
					authOK = true;
//				}
			}else{
				//oauth 인증 실패
				throw new Exception("Auth Fail");
			}
		}
		
		//사용자 프로필
		if(authOK){
			//프로필 가져오기
			UserProfile user = kakaoLogin.userInfo(session);
			if(user != null){
				logger.info(String.valueOf(user.getId()));
				logger.info(user.getProperties().getNickname());
				logger.info(user.getProperties().getProfileImage());
				logger.info(user.getProperties().getThumbnailImage());
				
				//DB저장 or 업데이트
				User newUser = userService.regsistUserForSNS(AuthProvider.KAKAO, String.valueOf(user.getId()),
						user.getProperties().getNickname(), null, null, null, user.getProperties().getThumbnailImage(), null);
				
				if(newUser != null && newUser.getSeq() > 0){
					//성공
					Token token = (Token) session.getAttribute(AuthAttr.KAKAO_TOKEN);
					response.addCookie(new Cookie("ltoat", token.getAccessToken()));
					response.addCookie(new Cookie("ltusq", String.valueOf(newUser.getSeq())));
				}else{
					throw new Exception("Database Error");
				}
			}else{
				//api 오류 or json parsing 오류
				throw new Exception("Request Error");
			}
		}
		
		if(referer == null){
			referer = "";
		}
		
		return "redirect:".concat(referer);
	}
	
	@RequestMapping(value = "/logout")
	public String logout() {
		HttpSession session = request.getSession();
		if(kakaoLogin.logout(session)){
			session.removeAttribute(AuthAttr.AUTH_PROVIDER);
		}

		return "redirect:/";
	}
	
	//=======================================TEST===============================================
	
	@RequestMapping(value = "/refresh")
	public String refresh() {
		HttpSession session = request.getSession();
		if(kakaoLogin.refresh(session)){
		}

		return "redirect:/";
	}
	
	@RequestMapping(value = "/unlink")
	public String unlink() {
		HttpSession session = request.getSession();
		if(kakaoLogin.unlink(session)){
			session.removeAttribute(AuthAttr.AUTH_PROVIDER);
			
			//TODO 회원 DB삭제
		}

		return "redirect:/";
	}
}
