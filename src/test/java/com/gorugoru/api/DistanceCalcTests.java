package com.gorugoru.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.gorugoru.util.GeoUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
public class DistanceCalcTests {
	
	private static final Logger logger = LoggerFactory.getLogger(DistanceCalcTests.class);
	
	@Test
    public void calc() throws Exception {
		double lat1 = 37.496078;
		double lng1 = 126.953734;
		double lat2 = 37.481260;
		double lng2 = 126.952673;
		//구글맵에서 거리 1.65키로
		
		double dis = GeoUtil.getDistanceFromLatLng(lat1, lng1, lat2, lng2);
		logger.info("distance: "+Math.floor(dis * 1000)+"m");//1.6503435432058966
    	
        //assertThat(responseString, is(equalTo(jsonString)));
    }

}
