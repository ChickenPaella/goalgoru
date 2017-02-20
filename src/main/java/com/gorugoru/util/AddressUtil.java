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
	 * @param sido - OO도 OO시, OO도, OO시
	 * @param gugun - 구군 optional(대부분은 있으나 없기도 함)
	 * @param dongeup - 동읍면 optional(수도권은 거의 없음)
	 * @param street - OO길
	 * @param detail - OO호
	 * @return merged address
	 */
	public static String merge(@NotNull String sido, String gugun, String dongeup, @NotNull String street, @NotNull String etc){
		StringBuilder sb = new StringBuilder();
		sb.append(sido);
		if(gugun != null) sb.append(SEPARATOR).append(gugun);
		if(dongeup != null) sb.append(SEPARATOR).append(dongeup);
		sb.append(SEPARATOR).append(street);
		sb.append(SEPARATOR).append(etc);
		
		return sb.toString();
	}
	
}
