package com.gorugoru.api.controller;

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

@RestController
@RequestMapping(path = "/food",  produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class FoodController {
	
	private static final Logger logger = LoggerFactory.getLogger(FoodController.class);
	
	@Autowired
	ObjectMapper mapper;
	
	@RequestMapping(path = "/v1/food/nutri/{name}", method = RequestMethod.GET)
	public ResponseEntity<?> nutri(HttpServletRequest request, ModelMap model,
			@PathVariable("name") String name) throws JsonProcessingException {
		
		logger.info("nutri()");
		
		String json = "";//mapper.writerWithView(Views.DEF.class).writeValueAsString(loc);
		
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}
}
