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
import com.gorugoru.api.domain.model.User;
import com.gorugoru.api.domain.repository.UserRepository;
import com.gorugoru.api.jackson.Views;
import com.gorugoru.api.service.UserService;

/**
 * JSON REST API Test
 * @author Administrator
 *
 */
@RestController
@RequestMapping(path = "/user",  produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ObjectMapper mapper;
	
	@RequestMapping(path = "/save/{name}/{age}", method = RequestMethod.GET)
	public ResponseEntity<?> save(HttpServletRequest request, ModelMap model,
			@PathVariable("name") String name,
			@PathVariable("age") int age) {
		logger.info("save() {} {}", name, age);
		
		User user = userService.insertUser(name, age);
		
		return new ResponseEntity<String>("OK "+user.getId(), HttpStatus.OK);
	}
	
	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public ResponseEntity<?> list(HttpServletRequest request, ModelMap model) throws JsonProcessingException {
		logger.info("list()");
		
		List<User> userList = userService.getUserList();
        String json = mapper.writerWithView(Views.DEF.class).writeValueAsString(userList);
		
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/find/{name}/{age}", method = RequestMethod.GET)
	public ResponseEntity<?> list2(HttpServletRequest request, ModelMap model,
			@PathVariable("name") String name,
			@PathVariable("age") int age) {
		logger.info("find()");
		
		List<User> userList = (List<User>) userRepository.findByNameAndAgeLessThan(name, age);
		
		return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
	}
	
}
