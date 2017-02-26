package com.gorugoru.api.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
	
	public List<AteHistory> getAteHistoryList(String year, String month) {
		int y = Integer.parseInt(year);
		int m = Integer.parseInt(month)-1;
		Calendar cal = new GregorianCalendar();
		cal.set(y, m, 1);
		
		Date s = cal.getTime();
				
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(y, m, lastDay);
		
		Date e = cal.getTime();
		
		List<AteHistory> list = historyRepository.findAllByEatDateBetween(s, e);
		return list;
	}
}
