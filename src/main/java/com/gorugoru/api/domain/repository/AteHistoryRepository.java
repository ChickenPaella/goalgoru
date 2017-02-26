package com.gorugoru.api.domain.repository;

import java.util.List;

import javax.transaction.Transactional;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.gorugoru.api.domain.model.AteHistory;

@Transactional
public interface AteHistoryRepository extends JpaRepository<AteHistory, Long> {
	
	public abstract AteHistory findOneByIdAndEatDate(@Param("id") String id, @Param("eatDate") Date eateDate);
	
	public abstract List<AteHistory> findById(@Param("id") String id);

	public abstract List<AteHistory> findAllByEatDateBetween(Date s, Date e);

}
