package com.gorugoru.api.domain.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.gorugoru.api.domain.model.Food;

@Transactional
public interface FoodRepository extends CrudRepository<Food, Long> {
	
	public abstract Food findOneByName(String name);
}