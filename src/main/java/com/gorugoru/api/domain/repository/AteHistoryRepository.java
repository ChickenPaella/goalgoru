package com.gorugoru.api.domain.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.gorugoru.api.domain.model.AteHistory;

@Transactional
public interface AteHistoryRepository extends JpaRepository<AteHistory, Long> {
	public abstract List<AteHistory> findById(@Param("id") String id);
}
