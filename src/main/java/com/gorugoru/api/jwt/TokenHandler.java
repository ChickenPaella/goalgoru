package com.gorugoru.api.jwt;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;

import com.gorugoru.api.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public final class TokenHandler {

    private final String secret;
    private final UserService userService;

    public TokenHandler(String secret, UserService userService) {
        this.secret = secret;
        this.userService = userService;
    }

    public UserDetails parseUserFromToken(String token) {
        String username = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return userService.loadUserByUsername(username);
    }

    public String createTokenForUser(UserDetails user) {
        return Jwts.builder()
        		.setHeaderParam("typ", "JWT")
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 4 * 60 * 60 * 1000)) // 4 hours
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
}