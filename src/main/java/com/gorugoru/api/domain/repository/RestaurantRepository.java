package com.gorugoru.api.domain.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.gorugoru.api.domain.model.Restaurant;

@Transactional
@RepositoryRestResource(path = "restaurant")
public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {

}
