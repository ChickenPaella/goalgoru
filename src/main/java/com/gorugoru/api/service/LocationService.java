package com.gorugoru.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gorugoru.api.domain.repository.RestaurantLocationRepository;

@Service
public class LocationService {
	
	@Autowired
	RestaurantLocationRepository restaurantLocationRepository;
	
	public List<String> getDongList(String sigugun) {
		List<String> dongList = restaurantLocationRepository.findBySigugun(sigugun);
		if(!dongList.isEmpty()) {
			return dongList;
		}
		return null;
	}
	
	public double getDistanceBetweenRestaurant(double latitude, double longitude) {
		return 0;
	}
}
