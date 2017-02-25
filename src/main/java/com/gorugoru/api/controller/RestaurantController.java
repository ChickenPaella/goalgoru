package com.gorugoru.api.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gorugoru.api.component.geo.DaumLocalComponent;
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
	
	@Autowired
	DaumLocalComponent daumlocal;
	
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
	 * @throws InterruptedException 
	 */
	@RequestMapping(path = "/list/{search_address}/{search_cate}", method = RequestMethod.GET)
	public ResponseEntity<?> cateList(HttpServletRequest request, ModelMap model,
			@PathVariable("search_address") String search_address, @PathVariable("search_cate") String search_cate) throws IOException, InterruptedException {
		
		//TODO 받을 값이 주소 단위 동까지, 음식 카테고리, 음식메뉴 - 해당리스트
		logger.info("cateList() search_address: "+search_address+" search_cate: "+search_cate);
		
		String[] addresses = search_address.split(" ");
		if(addresses.length != 3){
			return new ResponseEntity<String>("{msg:\"address invalid\"}", HttpStatus.BAD_REQUEST);
		}
		
		//더미 데이터!!
		List<Restaurant> list = rsntService.getRestaurantList();
		if(list.isEmpty()){
			//dummy
			
			//jar일경우 getFile이 안됨
			InputStream inputStream = rsntDB.getInputStream();
			File tsv = File.createTempFile("temp_db", ".txt");
			try {
				FileCopyUtils.copy(inputStream, new FileOutputStream(tsv));
			} finally {
			    IOUtils.closeQuietly(inputStream);
			}
			
			list = FileUtil.loadRsntDBCSV(tsv);
			for(int i=0;i<list.size();i++){
				Restaurant rsnt = list.get(i);
				rsnt = rsntService.insertRestaurant(rsnt);
				if(rsntService.normalizeLocation(rsnt)){
					Thread.sleep(10000);
				}
			}
		}

		List<Restaurant> rsntList = rsntService.getRestaurantListByLocationAndCate(addresses[0], addresses[1], addresses[2], search_cate);
		
        String json = mapper.writerWithView(Views.DEF.class).writeValueAsString(rsntList);
		
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}
}
