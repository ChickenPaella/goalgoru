package com.gorugoru.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gorugoru.api.domain.model.Restaurant;
import com.gorugoru.api.domain.model.RestaurantLocation;

public class FileUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	public static List<Restaurant> loadRsntDBCSV(File file){
		List<Restaurant> list = new ArrayList<Restaurant>();
		
		String line = "";
		final String tsvSplitBy = "\t";
		
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {

			while ((line = br.readLine()) != null) {
				
				logger.info(line);
				
				line = line.replaceAll("\"", "");
				
				// use comma as separator
				String[] cols = line.split(tsvSplitBy, 12);
				logger.info(""+cols.length);
				for(int i=0;i<cols.length;i++){
					logger.info(""+cols[i]);
				}
				
				String dong = cols[0];
				String name = cols[1];
				String cate = cols[2];
				String address = cols[3];
				String phone = cols[4];
				String sido = cols[5];
				String gugun = cols[6];
				String dongeup = cols[7];
				String street = cols[8];
				String etc = cols[9];
				String lat = cols[10];//(cols.length > 10) ? cols[10] : "";
				String lnt = cols[11];//(cols.length > 11) ? cols[11] : "";
				
				double lat2 = ("".equals(lat)) ? 0d : Double.parseDouble(lat);
				double lnt2 = ("".equals(lnt)) ? 0d : Double.parseDouble(lnt);
				
				RestaurantLocation location = new RestaurantLocation(sido, gugun, dongeup, street, etc, lat2, lnt2);
				Restaurant rsnt = new Restaurant(dong, name, cate, address, phone, location);
				location.setRestaurant(rsnt);
				list.add(rsnt);

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
	}

}
