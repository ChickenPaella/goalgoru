package com.gorugoru.api.domain.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gorugoru.api.domain.model.Restaurant;
import com.gorugoru.api.domain.model.User;

@Transactional
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
	
	public abstract List<Restaurant> findByLocationSidoAndLocationSigugunAndLocationDongAndCategoryOrderByNameAsc(
			@Param("sido") String sido, @Param("sigugun") String sigugun, @Param("dong") String dong, @Param("category") String category);
	
	//public abstract List<Restaurant> findByDongAndCategoryOrderByNameAsc(String dong, String cate);
	//public abstract List<Restaurant> findByDongAndCategoryOrderByNameDesc(String dong, String cate);
	public abstract List<Restaurant> findByCategoryOrderByNameAsc(String cate);
	public abstract List<Restaurant> findByCategoryOrderByNameDesc(String cate);

}
