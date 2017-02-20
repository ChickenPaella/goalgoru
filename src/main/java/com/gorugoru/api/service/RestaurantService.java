package com.gorugoru.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gorugoru.api.domain.model.Restaurant;
import com.gorugoru.api.domain.model.RestaurantCategory;
import com.gorugoru.api.domain.model.RestaurantLocation;
import com.gorugoru.api.domain.repository.RestaurantCategoryRepository;
import com.gorugoru.api.domain.repository.RestaurantRepository;

@Service
public class RestaurantService {
	
	@Autowired
	RestaurantCategoryRepository restaurantCategoryRepository;
	
	@Autowired
	RestaurantRepository restaurantRepository;
	
	public void insertCategory(String name) {
		RestaurantCategory category = new RestaurantCategory(name);
		restaurantCategoryRepository.save(category);
	}
	
	public List<RestaurantCategory> getCategoryList() {
		Sort sort = new Sort(Sort.Direction.ASC, "created");
		List<RestaurantCategory> rsntCateList = (List<RestaurantCategory>) restaurantCategoryRepository.findAll(sort);
		return rsntCateList;
	}
	
	public Restaurant insertRestaurant(String dong, String name, String category, String address, String phone,
			String sido, String gugun, String dongeup, String street, String etc) {
		Restaurant restaurant = new Restaurant(dong, name, category, address, phone);
		RestaurantLocation loc = new RestaurantLocation(sido, gugun, dongeup, street, etc);
		loc.setRestaurant(restaurant);
		restaurant.setLocation(loc);
		restaurant = restaurantRepository.save(restaurant);
		return restaurant;
	}
	
	public List<Restaurant> getRestaurantList() {
		Sort sort = new Sort(Sort.Direction.ASC, "name");
		List<Restaurant> restaurantList = (List<Restaurant>) restaurantRepository.findAll(sort);
		return restaurantList;
	}
	
	public List<Restaurant> getRestaurantListByCate(String cate) {
		Sort sort = new Sort(Sort.Direction.ASC, "name");
		List<Restaurant> restaurantList = (List<Restaurant>) restaurantRepository.findAll(sort);
		return restaurantList;
	}

	

	
}
