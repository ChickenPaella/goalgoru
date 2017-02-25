package com.gorugoru.api.domain.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gorugoru.api.domain.model.RestaurantLocation;

@Transactional
public interface RestaurantLocationRepository extends JpaRepository<RestaurantLocation, Long> {
	
	@Query("select t from RestaurantLocation t where t.sigugun = :sigugun")
	public abstract List<RestaurantLocation> findBySigugun(@Param("sigugun") String sigugun);

}