package com.gorugoru.api.domain.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gorugoru.api.domain.model.AteHistory;

public interface AteHistoryRepository extends JpaRepository<AteHistory, Long> {

	public abstract List<AteHistory> findAllByEatDateBetween(Date s, Date e);

}
