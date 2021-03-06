package com.gorugoru.api.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

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
import com.gorugoru.api.constant.FoodConstants;
import com.gorugoru.api.constant.JsonResults;
import com.gorugoru.api.domain.model.FoodNutri;
import com.gorugoru.api.domain.model.Restaurant;
import com.gorugoru.api.domain.model.RestaurantCategory;
import com.gorugoru.api.domain.model.RestaurantFood;
import com.gorugoru.api.jackson.Views;
import com.gorugoru.api.service.FoodNutriService;
import com.gorugoru.api.service.GeoService;
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
	
	@Autowired
	GeoService geoService;
	
	@Autowired
	FoodNutriService foodNutriService;
	
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
			for(int i=0;i<FoodConstants.FOOD_CATEGORY.length;i++){
				rsntService.insertCategory(FoodConstants.FOOD_CATEGORY[0]);
			}
			
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
			return new ResponseEntity<String>(JsonResults.RESULT_FAIL_INVALID_REQUEST_PARAM, HttpStatus.BAD_REQUEST);
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
	
	/**
	 * 식당 상세보기
	 * @param request
	 * @param model
	 * @param rsnt_seq
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(path = "/view/{rsnt_seq}", method = RequestMethod.GET)
	public ResponseEntity<?> view(HttpServletRequest request, ModelMap model,
			@PathVariable("rsnt_seq") String rsnt_seq) throws IOException, InterruptedException {
		
		logger.info("view() rsnt_seq: "+rsnt_seq);
		
		long seq = Long.parseLong(rsnt_seq);
		if(seq <= 0){
			return new ResponseEntity<String>(JsonResults.RESULT_FAIL_INVALID_REQUEST_PARAM, HttpStatus.BAD_REQUEST);
		}
		
		Restaurant restaurant = rsntService.getRestaurant(seq);
		
		if(restaurant == null){
			return new ResponseEntity<String>(JsonResults.RESULT_FAIL_NOT_EXISTS, HttpStatus.BAD_REQUEST);
		}
		
		List<RestaurantFood> foodList = restaurant.getFoods();//rsntService.getRestaurantFoodList(seq);
		
		//더미
		if(foodList.isEmpty()){
			String menuList[] = FoodConstants.getMenuList(restaurant.getCategory());
			//없으면 전부 분식처리
			if(menuList == null) menuList = FoodConstants.MENU_LIST_BUN_BOB;
			if(menuList != null){
				Random rand = new Random(System.currentTimeMillis());
				
				for(int i=0;i<menuList.length;i++){
					FoodNutri foodNutri = foodNutriService.getFoodNutriByName(menuList[i]);
					if(foodNutri == null){
						logger.info("pre defined food parsing nutri FAIL: "+menuList[i]);
						continue;
					}
					int price = rand.nextInt(3) * 500 + 3000;
					RestaurantFood food = new RestaurantFood(menuList[i], "고루고루 맛있게 먹어요", price, foodNutri.getCalorie(), foodNutri.getMainNutri());
					food.setRestaurant(restaurant);
					foodList.add(food);
				}
				restaurant.setFoods(foodList);
				rsntService.insertRestaurant(restaurant);
			}
		}
		
        String json = mapper.writerWithView(Views.MORE.class).writeValueAsString(restaurant);
        
        return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(path="/list/geo/{latitude},{longitude}", method = RequestMethod.GET)
	public ResponseEntity<?> distanceList(HttpServletRequest request, ModelMap model,
			@PathVariable("latitude") String latitude, @PathVariable("longitude") String longitude) throws IOException {
		
		List<Restaurant> rsntList = rsntService.getRestaurantListByCoord(Double.valueOf(latitude).doubleValue(), Double.valueOf(longitude).doubleValue());
		
		rsntList.forEach((input) -> {
			input.getLocation().getLatitude();
			input.getLocation().getLongitude();
			input.setDistance(geoService.getDistanceBetweenRestaurant(Double.valueOf(latitude).doubleValue(), 
					Double.valueOf(longitude).doubleValue(),
					input.getLocation().getLatitude(), 
					input.getLocation().getLongitude()));
		});
		
		String json = mapper.writerWithView(Views.DEF.class).writeValueAsString(rsntList);
		
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}
	
	/**
	 * 음식으로 식당 검색
	 * @param request
	 * @param model
	 * @param name
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(path="/list/food/{name}", method = RequestMethod.GET)
	public ResponseEntity<?> distanceList(HttpServletRequest request, ModelMap model,
			@PathVariable("name") String name) throws IOException {
		
		List<Restaurant> rsntList = rsntService.getRestaurantListByFoodsName(name);
		
		String json = mapper.writerWithView(Views.DEF.class).writeValueAsString(rsntList);
		
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}
}
