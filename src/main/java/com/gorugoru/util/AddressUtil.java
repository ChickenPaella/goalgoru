package com.gorugoru.util;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 주소 유틸리티
 * 도로명 주소만 지원
 * @author Administrator
 *
 */
public class AddressUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(AddressUtil.class);
	
	private static final String SEPARATOR = " ";
	
	/**
	 * 머지
	 * @param sido - 시도 OO도, OO시
	 * @param sigugun - 시구군 OO시, OO구, OO시 OO구
	 * @param dongeupOrStreet - 동읍면 optional(도로명의 경우 없기도 함) OO길
	 * @param bunji - 번지 O동 O호 or OO-OO
	 * @param etc - 집 호수 아파트 호수 등등 OO호 optional
	 * @return merged address
	 */
	public static String merge(@NotNull String sido, @NotNull String gugun, @NotNull String dongeupOrStreet, @NotNull String bunji, String etc){
		StringBuilder sb = new StringBuilder();
		sb.append(sido);
		sb.append(SEPARATOR).append(gugun);
		sb.append(SEPARATOR).append(dongeupOrStreet);
		sb.append(SEPARATOR).append(bunji);
		if(etc != null) sb.append(SEPARATOR).append(etc);
		
		return sb.toString();
	}
	
}
