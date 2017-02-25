package com.gorugoru.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gorugoru.api.component.NutriMiner;
import com.gorugoru.api.domain.model.FoodNutri;
import com.gorugoru.api.domain.repository.FoodNutriRepository;

@Service
public class FoodNutriService {
	
	@Autowired
	FoodNutriRepository foodNutriRepository;
	
	public FoodNutri insertFoodNutri(FoodNutri foodNutri){
		foodNutri = foodNutriRepository.save(foodNutri);
		return foodNutri;
	}
	
	public FoodNutri getFoodNutriByName(String name){
		FoodNutri foodNutri = foodNutriRepository.findOneByName(name);
		
		if(foodNutri == null){
			//파싱
			NutriMiner mnc = NutriMiner.getInstance();
			List<FoodNutri> parsedNutriList = mnc.searchAndMine(name, 1);//1개만
			
			if(parsedNutriList != null && parsedNutriList.size() > 0){
				if(parsedNutriList.get(0).getName().equals(name)){
					//정확히 일치할 경우에만 입력
					foodNutri = insertFoodNutri(parsedNutriList.get(0));
				}
			}
		}
		
		return foodNutri;
	}
	
}
