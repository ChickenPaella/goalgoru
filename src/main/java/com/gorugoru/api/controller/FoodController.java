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
import com.gorugoru.api.component.NutriMiner;
import com.gorugoru.api.constant.JsonResults;
import com.gorugoru.api.domain.model.FoodNutri;
import com.gorugoru.api.jackson.Views;
import com.gorugoru.api.service.FoodNutriService;

@RestController
@RequestMapping(path = "/food",  produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class FoodController {
	
	private static final Logger logger = LoggerFactory.getLogger(FoodController.class);
	
	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	FoodNutriService foodNutriService;
	
	/**
	 * 음식 영양소 정보(음식 상세보기) db조회후 없으면 fatsecret사이트 파싱
	 * @param request
	 * @param model
	 * @param name
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(path = "/nutri/{name}", method = RequestMethod.GET)
	public ResponseEntity<?> nutri(HttpServletRequest request, ModelMap model,
			@PathVariable("name") String name) throws JsonProcessingException {
		
		logger.info("nutri() name: "+name);
		
		FoodNutri foodNutri = foodNutriService.getFoodNutriByName(name);
		
		if(foodNutri == null) return new ResponseEntity<String>(JsonResults.RESULT_FAIL_NOT_FOUND, HttpStatus.BAD_REQUEST);
		
		String json = mapper.writerWithView(Views.DEF.class).writeValueAsString(foodNutri);
		
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}
}
