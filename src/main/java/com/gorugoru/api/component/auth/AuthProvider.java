package com.gorugoru.api.component.auth;

public final class AuthProvider {
	private final String value;
	public static final String NONE_VALUE = "none";
	public static final AuthProvider NONE;
	public static final String KAKAO_VALUE = "kakao";
	public static final AuthProvider KAKAO;
	
	static {
		NONE = valueOf(NONE_VALUE);
		KAKAO = valueOf(KAKAO_VALUE);
	}
	
	public AuthProvider(String value) {
		this.value = value;
	}

	private static AuthProvider valueOf(String value){
		return new AuthProvider(value);
	}
	
	@Override
	public String toString(){
		return value;
	}
}
