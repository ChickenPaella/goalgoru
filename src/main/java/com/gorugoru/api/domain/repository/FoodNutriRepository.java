package com.gorugoru.api.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gorugoru.api.domain.model.FoodNutri;

@Repository
public interface FoodNutriRepository extends CrudRepository<FoodNutri, Long> {
	
	public abstract FoodNutri findOneByName(String name);
	
}