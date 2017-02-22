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
import com.gorugoru.api.dto.Address;
import com.gorugoru.api.jackson.Views;
import com.gorugoru.api.service.LocationService;

@RestController
@RequestMapping(value = "/location", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LocationController {
	
	private static final Logger logger = LoggerFactory.getLogger(LocationController.class);
	
	@Autowired
	DaumLocalComponent daumLocalComponent;
	
	@Autowired
	LocationService locationService;
	
	@Autowired
	ObjectMapper mapper;
	
	@RequestMapping(value = "/dong/{latitude},{longitude}", method = RequestMethod.GET)
	public ResponseEntity<?> dongList(HttpServletRequest req, ModelMap model,
			@PathVariable("latitude") String latitude, @PathVariable("longitude") String longitude) throws JsonProcessingException{
		
		logger.info("dongList latitude: " + latitude + "longitude: " + longitude);
		
		Address addr = daumLocalComponent.coord2addr(latitude, longitude);
		
		logger.info(addr.toString());
		
		List<String> dongList = locationService.getDongList(addr.getSigugun());
		
		if(dongList == null){
			return new ResponseEntity<String>("{result:\"NOT EXIST\"}", HttpStatus.BAD_REQUEST);
		}
		
		String json = mapper.writerWithView(Views.DEF.class).writeValueAsString(dongList);
		
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}
}
