package com.gorugoru.api.dto;

import java.util.HashMap;
import java.util.Map;

public class FoodConstants {
	
	public static final String[] FOOD_CATEGORY = {
			"떡집",
			"제과점",
			"한식",
			"중식",
			"분식"
	};
	
	public static final String[] MENU_LIST_DDUK = {
			"꿀떡","경단","인절미","시루떡","찰떡","절편","술떡","가래떡","영양떡","백설기"
	};
	
	public static final String[] MENU_LIST_BBANG = {
			"모닝빵","단팥빵","슈크림빵","소보루빵","피자빵","카스테라","바게트","소세지빵","햄에그 샌드위치","로스트 치킨 샐러드"
	};
	
	public static final String[] MENU_LIST_KR_BOB = {
			"비빔밥","고기만두","순두부찌개","된장찌개","국밥","김치찌개","도토리묵"
	};
	
	public static final String[] MENU_LIST_CH_BOB = {
			"짜장면","짬뽕","울면","우동"
	};
	
	public static final String[] MENU_LIST_BUN_BOB = {
			"떡볶이","김밥","라면","튀김","순대","오뎅","떡꼬치"
	};
	
	public static String[] getMenuList(String category){
		if("떡집".equals(category)){
			return MENU_LIST_DDUK;
		}else if("제과점".equals(category)){
			return MENU_LIST_BBANG;
		}else if("한식".equals(category)){
			return MENU_LIST_KR_BOB;
		}else if("중식".equals(category)){
			return MENU_LIST_CH_BOB;
		}else if("분식".equals(category)){
			return MENU_LIST_BUN_BOB;
		}
		
		return null;
	}
}
