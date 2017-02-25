package com.gorugoru.api.component;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriComponentsBuilder;

import com.gorugoru.api.domain.model.FoodNutri;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NutriMiner {
	
	private static final Logger logger = LoggerFactory.getLogger(NutriMiner.class);
	
	private final OkHttpClient client;
	
	private NutriMiner(){
		client = new OkHttpClient.Builder().build();
	}
	
	private static class HOLDER {
		private static final NutriMiner INSTANCE = new NutriMiner();
	}
	
	public static NutriMiner getInstance(){
		return HOLDER.INSTANCE;
	}
	
	public List<FoodNutri> searchAndMine(String keyword){
		return searchAndMine(keyword, 0);
	}
	
	public List<FoodNutri> searchAndMine(String keyword, int limit){
		final String url = UriComponentsBuilder.newInstance().scheme("https").host("www.fatsecret.kr")
				.path("/칼로리-영양소/search")
				.queryParam("q", keyword)
				.build()
				.encode()
				.toUriString();
		
		Request request = new Request.Builder()
    			.url(url)
    			.get()
    			.build();
		
		List<FoodNutri> result = new ArrayList<FoodNutri>();
		
		try (Response response = client.newCall(request).execute()) {
			String html = response.body().string();
    		//logger.info(html);
    		
    		Document doc = Jsoup.parse(html);
    		Elements searchResultA = doc.select("body a.prominent");
    		if(searchResultA.isEmpty()){
    			//검색결과없음
    			return null;
    		}else{
    			for (Element a : searchResultA) {
    				String path = a.attr("href");
    				if(!"".equals(path)){
    					logger.info(path);
    					FoodNutri fn = mineUrl("https://www.fatsecret.kr".concat(path));
    					if(fn == null) continue;
    					result.add(fn);
    					if(limit > 0 && limit == result.size()) break;
    				}	
    			}
    		}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		logger.info("mining succeed count: "+result.size());
		
		return result;
	}
	
	/**
	 * https://www.fatsecret.kr/칼로리-영양소/일반명/
	 * 일반명만 가능함..
	 * @param encodedUrl
	 * @return
	 */
	public FoodNutri mineUrl(String encodedUrl){
		
		FoodNutri fn = null;
		String decodedUrl = null;
		try {
			decodedUrl = URLDecoder.decode(encodedUrl, "utf-8");
			
			logger.info("mineUrl: "+decodedUrl);
			
			String[] nnn = decodedUrl.split("/");
			if(!"일반명".equals(nnn[nnn.length-2])){
				return null;
			}
			
			fn = new FoodNutri();
			fn.setName(nnn[nnn.length-1]);
			fn.setOriginalUrl(decodedUrl);
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
			return null;
		}
		
    	Request request = new Request.Builder()
    			.url(encodedUrl)
    			.get()
    			.build();
    	
    	try (Response response = client.newCall(request).execute()) {
    		String html = response.body().string();
    		//logger.info(html);
    		Document doc = Jsoup.parse(html);
    		Element factPanel = doc.select("body .centerContent .factPanel").first();
    		//영양정보 테이블
    		Elements nutpanel_rows = factPanel.select(".nutpanel > table > tbody > tr");
    		
    		fn.setServingSize(nutpanel_rows.eq(1).select("td").eq(0).text().replace("서빙 사이즈:", "").trim());
    		//assertThat("칼로리", is(equalTo(nutpanel_rows.eq(5).select("td").eq(0).text())));
    		fn.setCalorie(Integer.parseInt(nutpanel_rows.eq(5).select("td").eq(1).text().replace("kcal", "").trim()));
    		//assertThat("탄수화물", is(equalTo(nutpanel_rows.eq(6).select("td").eq(0).text())));
    		fn.setCarbo(Float.parseFloat(nutpanel_rows.eq(6).select("td").eq(1).text().replace("g", "").trim()));
    		int row_idx = 7;
    		if("설탕당".equals(nutpanel_rows.eq(7).select("td").eq(1).text())){//optional
    			//설탕당row가 있음
    			row_idx++;
    		}
    		assertThat("단백질", is(equalTo(nutpanel_rows.eq(row_idx).select("td").eq(0).text())));
    		fn.setProtein(Float.parseFloat(nutpanel_rows.eq(row_idx).select("td").eq(1).text().replace("g", "").trim()));
    		row_idx++;
    		assertThat("지방", is(equalTo(nutpanel_rows.eq(row_idx).select("td").eq(0).text())));
    		fn.setFat(Float.parseFloat(nutpanel_rows.eq(row_idx).select("td").eq(1).text().replace("g", "").trim()));
    		row_idx+=4;//+1 +3줄 더
    		assertThat("콜레스테롤", is(equalTo(nutpanel_rows.eq(row_idx).select("td").eq(0).text())));
    		fn.setCholes(Float.parseFloat(nutpanel_rows.eq(row_idx).select("td").eq(1).text().replace("mg", "").trim()));
    		row_idx++;
    		if("식이섬유".equals(nutpanel_rows.eq(row_idx).select("td").eq(0).text())){//optional
    			fn.setFiber(Float.parseFloat(nutpanel_rows.eq(row_idx).select("td").eq(1).text().replace("g", "").trim()));
        		row_idx++;
    		}
    		assertThat("나트륨", is(equalTo(nutpanel_rows.eq(row_idx).select("td").eq(0).text())));
    		fn.setNatrium(Float.parseFloat(nutpanel_rows.eq(row_idx).select("td").eq(1).text().replace("mg", "").trim()));
    		row_idx++;
    		assertThat("칼륨", is(equalTo(nutpanel_rows.eq(row_idx).select("td").eq(0).text())));
    		fn.setKalium(Float.parseFloat(nutpanel_rows.eq(row_idx).select("td").eq(1).text().replace("mg", "").trim()));
    		
    		//칼로리 분석
    		Element fact = factPanel.select(".cfp_breakdown_container").first();
    		String factString = fact.select(".dot.carbs_dot").parents().eq(0).text();// 탄수화물 (21%)  지방 (55%)  단백질 (24%)
    		
    		int s;
			s = factString.indexOf("탄수화물")+"탄수화물 (".length();
			fn.setCarboPercent(Integer.parseInt(factString.substring(s, s + 2).replace("%", "")));
			s = factString.indexOf("단백질")+"단백질 (".length();
			fn.setProteinPercent(Integer.parseInt(factString.substring(s, s + 2).replace("%", "")));
			s = factString.indexOf("지방")+"지방 (".length();
			fn.setFatPercent(Integer.parseInt(factString.substring(s, s + 2).replace("%", "")));
			
			return fn;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    	
    	return null;
	}
}
