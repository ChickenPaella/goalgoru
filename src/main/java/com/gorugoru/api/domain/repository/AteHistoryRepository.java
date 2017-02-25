package com.gorugoru.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gorugoru.api.domain.model.AteHistory;

public interface AteHistoryRepository extends JpaRepository<AteHistory, Long> {

}
