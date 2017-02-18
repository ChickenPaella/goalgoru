package com.gorugoru.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gorugoru.api.component.NutriMiner;
import com.gorugoru.api.domain.model.FoodNutri;
import com.gorugoru.api.service.FoodNutriService;

/**
 * 관리자 페이지
 * @author Administrator
 *
 */
@Controller
@RequestMapping(path = "/admin")
public class AdminController {
	
	@Autowired
	FoodNutriService foodNutriService;
	
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String home(){
		return "admin/home";
	}
	
	@ResponseBody
	@RequestMapping(path = "/parse_nutri", method = RequestMethod.GET)
	public int dfasf(){
		//insert food nutri to database
		NutriMiner mnc = NutriMiner.getInstance();
		List<FoodNutri> ss = mnc.searchAndMine("된장");
		
		int count = 0;
		for (int i = 0; i < ss.size(); i++) {
			FoodNutri result = foodNutriService.insertFoodNutri(ss.get(i));
			if (result.getSeq() > 0) {
				count++;
			}
		}
		
		return count;
	}
}