package com.gorugoru.api.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gorugoru.api.domain.model.AteHistory;
import com.gorugoru.api.domain.model.User;
import com.gorugoru.api.domain.repository.AteHistoryRepository;
import com.gorugoru.api.dto.Receipt;
import com.gorugoru.api.jackson.Views;
import com.gorugoru.api.service.AteHistoryService;
import com.gorugoru.api.service.UserService;

@Controller
@RequestMapping(path = "/atehistory", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AteHistoryController {
	
	private static final Logger logger = LoggerFactory.getLogger(AteHistoryController.class);
	
	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	AteHistoryService ateHistoryService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	AteHistoryRepository ateHistoryRepository;
	
	@ResponseBody
	@RequestMapping(path = "/save/eat", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<?> saveEat(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody AteHistory ateHistory) throws JsonProcessingException {
		
		logger.info("resgistAteHistory()");
		logger.info("User ID: " + ateHistory.getId());
		logger.info("Restaurant name: " + ateHistory.getRestaurantName());
		
		ateHistory = ateHistoryService.insertAteHistory(ateHistory);
		
		String json = mapper.writerWithView(Views.DEF.class).writeValueAsString(ateHistory);
		
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(path = "/save/ate/{id}", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<?> saveAte(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody Receipt receipt, @PathVariable("id") String id) throws JsonProcessingException {
		
		logger.info("resgistAteHistory()");
		logger.info("Food name: " + receipt.getFoodName());
		logger.info("Restaurant name: " + receipt.getRestaurantName());
		
		Date eatDate = new Date();
		
		AteHistory ateHistory = ateHistoryRepository.findOneByIdAndEatDate(id, eatDate);
		
		ateHistory = ateHistoryService.insertReceipt(ateHistory, receipt);
		
		userService.managePoint(id, ateHistory.getFoodName());
		
		String json = mapper.writerWithView(Views.DEF.class).writeValueAsString(ateHistory);
		
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}
}
