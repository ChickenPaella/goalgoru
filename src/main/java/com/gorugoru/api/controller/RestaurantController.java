package com.gorugoru.api.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gorugoru.api.domain.model.Restaurant;
import com.gorugoru.api.domain.model.RestaurantCategory;
import com.gorugoru.api.jackson.Views;
import com.gorugoru.api.service.RestaurantService;
import com.gorugoru.util.FileUtil;

/**
 * 식당 컨트롤러
 * @author Administrator
 *
 */
@RestController
@RequestMapping(path = "/rsnt",  produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RestaurantController {
	
	private static final Logger logger = LoggerFactory.getLogger(RestaurantController.class);
	
	@Autowired
	RestaurantService rsntService;
	
	@Autowired
	ObjectMapper mapper;
	
	@Value(value = "classpath:rsnt_db.txt")
	private Resource rsntDB;
	
	/**
	 * 식당 카테고리 리스트
	 * @param request
	 * @param model
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(path = "/cate/list", method = RequestMethod.GET)
	public ResponseEntity<?> cateList(HttpServletRequest request, ModelMap model) throws JsonProcessingException {
		logger.info("cateList()");
		
		List<RestaurantCategory> rsntCateList = rsntService.getCategoryList();
		
		if(rsntCateList.isEmpty()){
			//dummy
			rsntService.insertCategory("한식");
			rsntService.insertCategory("중식");
			rsntService.insertCategory("양식");
			rsntService.insertCategory("기타");
			
			rsntCateList = rsntService.getCategoryList();
		}
		
        String json = mapper.writerWithView(Views.DEF.class).writeValueAsString(rsntCateList);
		
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}
	
	/**
	 * 식당 리스트
	 * @param request
	 * @param model
	 * @param cate
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(path = "/{base_address}/{cate}/list", method = RequestMethod.GET)
	public ResponseEntity<?> cateList(HttpServletRequest request, ModelMap model,
			@PathVariable("base_address") String base_address, @PathVariable("cate") String cate) throws IOException {
		
		//TODO 받을 값이 주소 단위 동까지, 음식 카테고리, 음식메뉴 - 해당리스트
		logger.info("cateList() base_address: "+base_address+" cate: "+cate);
		
		List<Restaurant> rsntList = rsntService.getRestaurantListByDongAndCate("갈현1동", cate);
		
		if(rsntList.isEmpty()){
			//dummy
			
			List<Restaurant> list = FileUtil.loadRsntDBCSV(rsntDB.getFile());
			
			for(int i=0;i<list.size();i++){
				rsntService.insertRestaurant(list.get(i));
			}
			
			//rsntService.insertRestaurant("갈현1동", "사대명가", "한식", "블라블라", "02-123-1234", "시도", "구군", "동읍", "도로명", "기타");
			
			rsntList = rsntService.getRestaurantListByDongAndCate("갈현1동", cate);
		}
		
        String json = mapper.writerWithView(Views.DEF.class).writeValueAsString(rsntList);
		
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}
}
