package com.gorugoru.api.domain.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gorugoru.api.domain.model.RestaurantCategory;

@Transactional
public interface RestaurantCategoryRepository extends JpaRepository<RestaurantCategory, Long> {

}