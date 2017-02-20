package com.gorugoru.api.domain.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gorugoru.api.domain.model.Restaurant;

@Transactional
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
	
	public abstract List<Restaurant> findByDongAndCategoryOrderByNameAsc(String dong, String cate);
	public abstract List<Restaurant> findByDongAndCategoryOrderByNameDesc(String dong, String cate);
	public abstract List<Restaurant> findByCategoryOrderByNameAsc(String cate);
	public abstract List<Restaurant> findByCategoryOrderByNameDesc(String cate);

}
