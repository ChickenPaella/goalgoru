package com.gorugoru.api;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.net.URLDecoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gorugoru.api.domain.model.FoodNutri;
import com.gorugoru.api.service.FoodNutriService;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
public class NutriParsingTest {
	
	private static final Logger logger = LoggerFactory.getLogger(NutriParsingTest.class);

    @Value("${local.server.port}")
    int port;
    
    @Autowired
    FoodNutriService foodNutriService;
    
    @Before
    public void setUp() {
    	logger.info("setUp() port: "+port);
    }
    
    @Test
    public void parse() throws Exception {
    	String url = "https://www.fatsecret.kr/%EC%B9%BC%EB%A1%9C%EB%A6%AC-%EC%98%81%EC%96%91%EC%86%8C/%EC%9D%BC%EB%B0%98%EB%AA%85/%EA%B9%80%EC%B9%98%EC%B0%8C%EA%B0%9C";
    	
    	//okhttp3
		OkHttpClient client = new OkHttpClient.Builder().authenticator(new Authenticator() {
			@Override
			public Request authenticate(Route route, Response response) throws IOException {
				if (response.request().header("Authorization") != null) {
			      return null; // Give up, we've already failed to authenticate.
			    }
				String credential = Credentials.basic("id","pass");
					    return response.request().newBuilder()
					        .header("Authorization", credential)
					        .build();
			}
		}).build();
    	
    	Request request = new Request.Builder()
    			.url(url)
    			.get()
    			.build();
    	
    	try (Response response = client.newCall(request).execute()) {
    		String html = response.body().string();
    		//logger.info(html);
    		Document doc = Jsoup.parse(html);
    		Element factPanel = doc.select("body .centerContent .factPanel").first();
    		//영양정보 테이블
    		Elements nutpanel_rows = factPanel.select(".nutpanel > table > tbody > tr");
    		
    		FoodNutri fn = new FoodNutri();
    		fn.setServingSize(nutpanel_rows.eq(1).select("td").eq(0).text().replace("서빙 사이즈:", "").trim());
    		assertThat("칼로리", is(equalTo(nutpanel_rows.eq(5).select("td").eq(0).text())));
    		fn.setCalorie(Integer.parseInt(nutpanel_rows.eq(5).select("td").eq(1).text().replace("kcal", "").trim()));
    		assertThat("탄수화물", is(equalTo(nutpanel_rows.eq(6).select("td").eq(0).text())));
    		fn.setCarbo(Float.parseFloat(nutpanel_rows.eq(6).select("td").eq(1).text().replace("g", "").trim()));
    		assertThat("단백질", is(equalTo(nutpanel_rows.eq(8).select("td").eq(0).text())));
    		fn.setProtein(Float.parseFloat(nutpanel_rows.eq(8).select("td").eq(1).text().replace("g", "").trim()));
    		assertThat("지방", is(equalTo(nutpanel_rows.eq(9).select("td").eq(0).text())));
    		fn.setFat(Float.parseFloat(nutpanel_rows.eq(9).select("td").eq(1).text().replace("g", "").trim()));
    		assertThat("콜레스테롤", is(equalTo(nutpanel_rows.eq(13).select("td").eq(0).text())));
    		fn.setCholes(Float.parseFloat(nutpanel_rows.eq(13).select("td").eq(1).text().replace("mg", "").trim()));
    		assertThat("식이섬유", is(equalTo(nutpanel_rows.eq(14).select("td").eq(0).text())));
    		fn.setFiber(Float.parseFloat(nutpanel_rows.eq(14).select("td").eq(1).text().replace("g", "").trim()));
    		assertThat("나트륨", is(equalTo(nutpanel_rows.eq(15).select("td").eq(0).text())));
    		fn.setNatrium(Float.parseFloat(nutpanel_rows.eq(15).select("td").eq(1).text().replace("mg", "").trim()));
    		assertThat("칼륨", is(equalTo(nutpanel_rows.eq(16).select("td").eq(0).text())));
    		fn.setKalium(Float.parseFloat(nutpanel_rows.eq(16).select("td").eq(1).text().replace("mg", "").trim()));
    		
    		//칼로리 분석
    		Element fact = factPanel.select(".cfp_breakdown_container").first();
    		String factString = fact.select(".dot.carbs_dot").parents().eq(0).text();// 탄수화물 (21%)  지방 (55%)  단백질 (24%)
    		
    		int s;
			s = factString.indexOf("탄수화물")+"탄수화물 (".length();
			fn.setCarboPercent(Integer.parseInt(factString.substring(s, s + 2)));
			s = factString.indexOf("단백질")+"단백질 (".length();
			fn.setProteinPercent(Integer.parseInt(factString.substring(s, s + 2)));
			s = factString.indexOf("지방")+"지방 (".length();
			fn.setFatPercent(Integer.parseInt(factString.substring(s, s + 2)));
			
			String[] nnn = url.split("/");
			fn.setName(URLDecoder.decode(nnn[nnn.length-1], "utf-8"));
			fn.setOriginalUrl(url);

			foodNutriService.insertFoodNutri(fn);
	    }
    	
        //assertThat(responseString, is(equalTo(jsonString)));
    }
}
