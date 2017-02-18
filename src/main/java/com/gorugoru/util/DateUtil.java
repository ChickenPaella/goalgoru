package com.gorugoru.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
	private static final Calendar cal = new GregorianCalendar();
	private static final SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	public static Date parseDate(String dateString){
		Date date = null;
		if(dateString != null){
			try {
				date = df.parse(dateString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}
	
	public static String ageToBirthYear(int age){
		cal.setTime(new Date());
		int thisYear = cal.get(Calendar.YEAR);
		return String.valueOf(thisYear - age + 1);
	}
}
