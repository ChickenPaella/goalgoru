package com.gorugoru.api.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gorugoru.api.component.geo.DaumLocalComponent;
import com.gorugoru.api.domain.model.Restaurant;
import com.gorugoru.api.domain.model.RestaurantCategory;
import com.gorugoru.api.domain.model.RestaurantFood;
import com.gorugoru.api.domain.model.RestaurantLocation;
import com.gorugoru.api.domain.repository.RestaurantCategoryRepository;
import com.gorugoru.api.domain.repository.RestaurantLocationRepository;
import com.gorugoru.api.domain.repository.RestaurantRepository;
import com.gorugoru.api.dto.Location;

@Service
public class RestaurantService {
	
	@Autowired
	RestaurantCategoryRepository restaurantCategoryRepository;
	
	@Autowired
	RestaurantLocationRepository restaurantLocationRepository;
	
	@Autowired
	RestaurantRepository restaurantRepository;
	
	@Autowired
	DaumLocalComponent daumlocal;
	
	public void insertCategory(String name) {
		RestaurantCategory category = new RestaurantCategory(name);
		category.setCreated(new Date());
		restaurantCategoryRepository.save(category);
	}
	
	public List<RestaurantCategory> getCategoryList() {
		Sort sort = new Sort(Sort.Direction.ASC, "created");
		List<RestaurantCategory> rsntCateList = (List<RestaurantCategory>) restaurantCategoryRepository.findAll(sort);
		return rsntCateList;
	}
	
	public Restaurant insertRestaurant(Restaurant restaurant) {
		if(restaurant.getLocation() == null) return null;
		restaurant.setCreated(new Date());
		restaurant = restaurantRepository.save(restaurant);
		return restaurant;
	}
	
	public Restaurant insertRestaurant(String dong, String name, String category, String address, String phone,
			String sido, String gugun, String dongeup, String street, String bunji, String etc) {
		Restaurant restaurant = new Restaurant(dong, name, category, address, phone);
		restaurant.setCreated(new Date());
		RestaurantLocation loc = new RestaurantLocation(sido, gugun, dongeup, street, bunji, etc);
		loc.setRestaurant(restaurant);
		restaurant.setLocation(loc);
		restaurant = restaurantRepository.save(restaurant);
		return restaurant;
	}
	
	public List<Restaurant> getRestaurantListByCoord(double latitude, double longitude) {
		
		List<Restaurant> restaurantList = restaurantRepository.findByCoord(latitude, longitude);

		return restaurantList;
	}
	
	public List<Restaurant> getRestaurantList() {
		Sort sort = new Sort(Sort.Direction.ASC, "name");
		List<Restaurant> restaurantList = (List<Restaurant>) restaurantRepository.findAll(sort);
		return restaurantList;
	}
	
	public List<Restaurant> getRestaurantListByLocationAndCate(String sido, String sigugun, String dong, String cate) {
		List<Restaurant> restaurantList = (List<Restaurant>) restaurantRepository.findByLocationSidoAndLocationSigugunAndLocationDongAndCategoryOrderByNameAsc(sido, sigugun, dong, cate);
		return restaurantList;
	}
	
	public List<Restaurant> getRestaurantListByFoodsName(String name) {
		List<Restaurant> restaurantList = (List<Restaurant>) restaurantRepository.findByFoodsName(name);
		return restaurantList;
	}
	
	public Restaurant getRestaurant(long seq) {
		return restaurantRepository.getOne(seq);
	}
	
	public boolean normalizeLocation(Restaurant rsnt) {
		if(rsnt.getLocation().getNormalize() == 0){
			if(rsnt.getLocation().getLatitude() == 0d && rsnt.getLocation().getLongitude() == 0d){
				rsnt.getLocation().setDong("");//번지에 동이 들어있으므로, 제거
				String addr = rsnt.getLocation().toAddressString(false);
				Location loc = daumlocal.addr2coord(addr);
				if(loc != null){
					rsnt.getLocation().setDong(loc.getDong());
					rsnt.getLocation().setBunji(loc.getBunji());
					rsnt.getLocation().setLatitude(Double.parseDouble(loc.getLatitude()));
					rsnt.getLocation().setLongitude(Double.parseDouble(loc.getLongitude()));
					rsnt.getLocation().setNormalize(1);
				}
				insertRestaurant(rsnt);
				return true;
			}
		}
		
		return false;
	}
	
	@Transactional
	public List<RestaurantFood> getRestaurantFoodList(long seq) {
		Restaurant restaurant = restaurantRepository.getOne(seq);
		if(restaurant != null){
			return restaurant.getFoods();
		}
		return null;
	}
	
}
