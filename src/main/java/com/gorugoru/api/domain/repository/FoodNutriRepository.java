package com.gorugoru.api.domain.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.gorugoru.api.domain.model.FoodNutri;

@Transactional
//removed @RepositoryRestResource(path = "food_nutri")
public interface FoodNutriRepository extends CrudRepository<FoodNutri, Long> {
	
}