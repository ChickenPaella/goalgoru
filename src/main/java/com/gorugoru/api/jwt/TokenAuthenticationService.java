package com.gorugoru.api.jwt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import com.gorugoru.api.service.UserService;

public class TokenAuthenticationService {
	
	private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationService.class);

	public static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";

	private final TokenHandler tokenHandler;

	public TokenAuthenticationService(String secret, UserService userService) {
		tokenHandler = new TokenHandler(secret, userService);
	}

	public void addAuthentication(HttpServletResponse response, UserAuthentication authentication) {
		final UserDetails user = authentication.getDetails();
		response.addHeader(AUTH_HEADER_NAME, tokenHandler.createTokenForUser(user));
	}

	public Authentication getAuthentication(HttpServletRequest request) {
		final String token = request.getHeader(AUTH_HEADER_NAME);
		if (token != null) {
			logger.info("getAuthentication() has token");
			final UserDetails user = tokenHandler.parseUserFromToken(token);
			logger.info("getAuthentication() username: "+user.getUsername());
			if (user != null) {
				return new UserAuthentication(user);
			}
		}
		logger.info("getAuthentication() has no token");
		return null;
	}
}
