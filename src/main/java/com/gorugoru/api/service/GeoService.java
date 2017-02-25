package com.gorugoru.api.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gorugoru.api.component.geo.DaumLocalComponent;
import com.gorugoru.api.domain.model.Restaurant;
import com.gorugoru.api.dto.Location;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.gorugoru.api.domain.repository.RestaurantLocationRepository;


@Service
public class GeoService {
	
	@Autowired
	RestaurantLocationRepository restaurantLocationRepository;

	public List<String> getDongList(String sigugun) {
		List<String> dongList = new ArrayList<String>(); 
		
		for(int i = 0;i<restaurantLocationRepository.findBySigugun(sigugun).size();i++) {
			dongList.add(restaurantLocationRepository.findBySigugun(sigugun).get(i).getDong());
		}
		
		if(!dongList.isEmpty()) {
			return dongList;
		}
		return null;
	}
	
	public String getDistanceBetweenRestaurant(double nowLat, double nowLng, double setLat, double setLng) {
		double earthRadius = 6371000; //meters
	    double dLat = Math.toRadians(setLat - nowLat);
	    double dLng = Math.toRadians(setLng - nowLng);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	               Math.cos(Math.toRadians(nowLat)) * Math.cos(Math.toRadians(setLat)) *
	               Math.sin(dLng/2) * Math.sin(dLng/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    float dist = (float) (earthRadius * c);

	    return Double.toString(Math.round(dist));
	}

}
