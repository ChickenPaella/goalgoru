package com.gorugoru.api.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gorugoru.api.domain.model.AteHistory;
import com.gorugoru.api.domain.repository.AteHistoryRepository;

@Service
public class AteHistoryService {
	
	private static final Logger logger = LoggerFactory.getLogger(AteHistoryService.class);
	
	@Autowired
	AteHistoryRepository historyRepository;
	
	public AteHistory insertAteHistory(AteHistory ateHistory) {
		logger.info("insertAteHistory() !!!");
		ateHistory = historyRepository.save(ateHistory);
		return ateHistory;
	}
	
	public List<AteHistory> getAteHistoryList() {
		List<AteHistory> list = historyRepository.findAll();
		return list;
	}
}
