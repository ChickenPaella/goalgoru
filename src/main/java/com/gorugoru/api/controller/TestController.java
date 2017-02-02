package com.gorugoru.api.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * JSON REST API Test
 * @author Administrator
 *
 */
@RestController
@RequestMapping(path = "/test",  produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TestController {
	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@RequestMapping(path = {"/path", "/path/{value}"}, method = RequestMethod.GET)
	public ResponseEntity<?> path(HttpServletRequest request, ModelMap model, @PathVariable("value") Optional<String> value) {
		logger.info("path() {}", value);
		
		ReturnObject obj = new ReturnObject();
		obj.setKey("KEY");
		if(value.isPresent()) obj.setValue(value.get());
		else obj.setValue("VALUE");
		
		return new ResponseEntity<ReturnObject>(obj, HttpStatus.OK);
	}
	
	private class ReturnObject{
		private String key;
		private String value;
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}

}