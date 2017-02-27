package com.gorugoru.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.gorugoru.api.component.geo.DaumLocalComponent;
import com.gorugoru.api.constant.JsonResults;
import com.gorugoru.api.jackson.Views;
import com.gorugoru.api.service.GeoService;
import com.gorugoru.api.vo.Address;
import com.gorugoru.api.vo.Location;

@RestController
@RequestMapping(path = "/geo",  produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class GeoController {
	
	private static final Logger logger = LoggerFactory.getLogger(GeoController.class);
	
	@Autowired
	DaumLocalComponent daumLocalComponent;
	
	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	GeoService geoService;
	
	@RequestMapping(path = "/v1/addr2geo/{address}", method = RequestMethod.GET)
	public ResponseEntity<?> addr2geo(HttpServletRequest request, ModelMap model,
			@PathVariable("address") String address) throws JsonProcessingException {
		
		logger.info("addr2geo()");
		
		Location loc = daumLocalComponent.addr2coord(address);
		
		logger.info(loc.toString());
		
		String json = mapper.writerWithView(Views.DEF.class).writeValueAsString(loc);
		
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}
	
	/**
	 * 위도,경도 -> 주소
	 * @param request
	 * @param model
	 * @param latitude
	 * @param longitude
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(path = "/v1/geo2addr/{latitude},{longitude}", method = RequestMethod.GET)
	public ResponseEntity<?> geo2addr(HttpServletRequest request, ModelMap model,
			@PathVariable("latitude") String latitude, @PathVariable("longitude") String longitude) throws JsonProcessingException {
		
		logger.info("geo2addr() latitude: "+latitude+" longitude: "+longitude);
		
		Address addr = daumLocalComponent.coord2addr(latitude, longitude);
		
		logger.info(addr.toString());
		
		String json = mapper.writerWithView(Views.DEF.class).writeValueAsString(addr);
		
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/dong/{latitude},{longitude}", method = RequestMethod.GET)
	public ResponseEntity<?> dongList(HttpServletRequest req, ModelMap model,
			@PathVariable("latitude") String latitude, @PathVariable("longitude") String longitude) throws JsonProcessingException{
		
		logger.info("dongList latitude: " + latitude + "longitude: " + longitude);
		
		Address addr = daumLocalComponent.coord2addr(latitude, longitude);
		
		logger.info(addr.toString());
		
		List<String> dongList = geoService.getDongList(addr.getSigugun());
		
		if(dongList == null){
			return new ResponseEntity<String>(JsonResults.RESULT_FAIL_NOT_EXISTS, HttpStatus.BAD_REQUEST);
		}
		
		String json = mapper.writerWithView(Views.DEF.class).writeValueAsString(dongList);
		
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/distance/{nowLat},{nowLng},{setLat},{setLng}")
	public ResponseEntity<?> getDistance(HttpServletRequest req, ModelMap model,
			@PathVariable("nowLat") String nowLat, 
			@PathVariable("nowLng") String nowLng,
			@PathVariable("setLat") String setLat, 
			@PathVariable("setLng") String setLng
			) throws JsonProcessingException {
		
		logger.info("getDistance nowLat: " + nowLat + "nowLng: " + nowLng + "setLat: " + setLat + "setLng: " + setLng);

		String distance = geoService.getDistanceBetweenRestaurant(Double.valueOf(nowLat).doubleValue(),Double.valueOf(nowLng).doubleValue(),Double.valueOf(setLat).doubleValue(),Double.valueOf(setLng).doubleValue());
		
		String json = mapper.writerWithView(Views.DEF.class).writeValueAsString(distance);
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}
}
