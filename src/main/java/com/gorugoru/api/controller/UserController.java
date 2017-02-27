package com.gorugoru.api.controller;

import java.security.Principal;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gorugoru.api.constant.JsonResults;
import com.gorugoru.api.domain.model.AteHistory;
import com.gorugoru.api.domain.model.Nutri;
import com.gorugoru.api.domain.model.User;
import com.gorugoru.api.jackson.Views;
import com.gorugoru.api.service.AteHistoryService;
import com.gorugoru.api.service.UserService;
import com.gorugoru.api.vo.Card;
import com.gorugoru.api.vo.NutriCount;

/**
 * JSON REST API Test
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping(path = "/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(TestController.class);

	@Autowired
	UserService userService;

	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	AteHistoryService ateHistoryService;

	@RequestMapping(path = "/view/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> view(HttpServletRequest request, ModelMap model, @PathVariable("id") String id, Principal principal)
			throws JsonProcessingException {
		
		if("me".equalsIgnoreCase(id)){
			id = principal.getName();
		}
		
		logger.info("view() id: " + id);

		User user = userService.getUserById(id);
		if (user == null) {
			return new ResponseEntity<String>(JsonResults.RESULT_FAIL_NOT_EXISTS, HttpStatus.BAD_REQUEST);
		}

		String json = mapper.writerWithView(Views.DEF.class).writeValueAsString(user);

		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	// @RequestMapping(path = "/save/{name}/{age}", method = RequestMethod.GET)
	// public ResponseEntity<?> save(HttpServletRequest request, ModelMap model,
	// @PathVariable("name") String name,
	// @PathVariable("age") int age) {
	// logger.info("save() {} {}", name, age);
	//
	// Calendar cal = new GregorianCalendar();
	// int thisYear = cal.get(Calendar.YEAR);
	// cal.set(thisYear - age + 1, 0, 1);
	// String date = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	//
	// User user = userService.registUser(AuthProvider.NONE, "", "id", "pass",
	// "name", date, "email", "phone", "profile", "cardNumber");
	//
	// return new ResponseEntity<String>("OK "+user.getSeq(), HttpStatus.OK);
	// }

	@ResponseBody
	@RequestMapping(path = "/save/cardnumber", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<?> registCardNumber(HttpServletRequest request, @RequestBody Card card)
			throws JsonProcessingException {
		logger.info("registCardNumber()");

		User user = userService.getUserById(card.getId());

		if (user == null)
			return new ResponseEntity<String>(JsonResults.RESULT_FAIL_NOT_EXISTS, HttpStatus.BAD_REQUEST);
		else {
			user = userService.registCardNumber(user, card.getCardNumber());

			return new ResponseEntity<String>(JsonResults.RESULT_OK_SUCCESS, HttpStatus.OK);
		}
	}
	
	/**
	 * 나의 상태
	 * @param request
	 * @param id
	 * @param date
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(path = "/status/{id}/{year}/{month}", method = RequestMethod.GET)
	public ResponseEntity<?> userStatus(HttpServletRequest request, @PathVariable("id") String id,
			@PathVariable("year") String year, @PathVariable("month") String month) throws JsonProcessingException {
		logger.info("userStatus() id: " + id);

		User user = userService.getUserById(id);
		if (user == null) {
			return new ResponseEntity<String>(JsonResults.RESULT_FAIL_NOT_EXISTS, HttpStatus.BAD_REQUEST);
		}
		
		List<AteHistory> ateList = ateHistoryService.getAteHistoryList(year, month);
		
		NutriCount nutriCount = new NutriCount();
		for(int i=0;i<ateList.size();i++){
			if(ateList.get(i).getMainNutri() == Nutri.Carbohydrate) nutriCount.setCarbo(nutriCount.getCarbo()+1);
			else if(ateList.get(i).getMainNutri() == Nutri.Protein) nutriCount.setProtein(nutriCount.getProtein()+1);
			else if(ateList.get(i).getMainNutri() == Nutri.Fat) nutriCount.setFat(nutriCount.getFat()+1);
		}
		
		String graph = mapper.writerWithView(Views.DEF.class).writeValueAsString(nutriCount);
		
		String json = mapper.writerWithView(Views.DEF.class).writeValueAsString(ateList);

		return new ResponseEntity<String>("{\"nutri_count\":"+graph+",\"ate_list\":"+json+"}", HttpStatus.OK);
	}

	// @RequestMapping(path = "/list", method = RequestMethod.GET)
	// public ResponseEntity<?> list(HttpServletRequest request, ModelMap model)
	// throws JsonProcessingException {
	// logger.info("list()");
	//
	// String json = userService.getUserListJSON(Views.DEF.class);
	//
	// return new ResponseEntity<String>(json, HttpStatus.OK);
	// }

}
