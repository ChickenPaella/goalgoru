package com.gorugoru.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 관리자 페이지
 * @author Administrator
 *
 */
@Controller
public class AdminController {
	
	@RequestMapping(path = "/admin", method = RequestMethod.GET)
	public String home(){
		return "admin/home";
	}
}