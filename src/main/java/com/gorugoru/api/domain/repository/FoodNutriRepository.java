package com.gorugoru.api.domain.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.gorugoru.api.domain.model.FoodNutri;

@Transactional
//removed @RepositoryRestResource(path = "food_nutri")
public interface FoodNutriRepository extends CrudRepository<FoodNutri, Long> {
	
	public abstract FoodNutri findOneByName(String name);
	
}