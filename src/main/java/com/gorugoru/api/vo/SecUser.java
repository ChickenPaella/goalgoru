package com.gorugoru.api.vo;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class SecUser extends org.springframework.security.core.userdetails.User{

	private static final long serialVersionUID = 1L;
	
	public SecUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}
	
	public SecUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
}
