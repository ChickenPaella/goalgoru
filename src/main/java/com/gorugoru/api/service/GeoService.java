package com.gorugoru.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gorugoru.api.component.geo.DaumLocalComponent;
import com.gorugoru.api.domain.model.Restaurant;
import com.gorugoru.api.dto.Location;

@Service
public class GeoService {
	
	@Autowired
	RestaurantService rsntService;
	
	@Autowired
	DaumLocalComponent daumlocal;
	
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
				rsntService.insertRestaurant(rsnt);
				return true;
			}
		}
		
		return false;
	}
}
