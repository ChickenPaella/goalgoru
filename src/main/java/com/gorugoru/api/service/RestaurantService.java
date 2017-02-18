package com.gorugoru.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gorugoru.api.domain.model.Restaurant;
import com.gorugoru.api.domain.repository.RestaurantRepository;

@Service
public class RestaurantService {
	
	@Autowired
	RestaurantRepository restaurantRepository;
	
	public Restaurant insertRestaurant(String dong, String name, String category, String address, String phone) {
		Restaurant restaurant = new Restaurant(dong, name, category, address, phone);
		restaurant = restaurantRepository.save(restaurant);
		return restaurant;
	}
	
	public List<Restaurant> getRestaurantList() {
		List<Restaurant> restaurantList = (List<Restaurant>) restaurantRepository.findAll();
		return restaurantList;
	}
}
