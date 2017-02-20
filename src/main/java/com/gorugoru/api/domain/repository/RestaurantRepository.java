package com.gorugoru.api.domain.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.gorugoru.api.domain.model.Restaurant;

@Transactional
//removed @RepositoryRestResource(path = "restaurant")
public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {

}
