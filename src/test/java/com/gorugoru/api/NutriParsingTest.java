package com.gorugoru.api;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
public class NutriParsingTest {
	
	private static final Logger logger = LoggerFactory.getLogger(NutriParsingTest.class);

    @Value("${local.server.port}")
    int port;
    
    @Before
    public void setUp() {
    	logger.info("setUp() port: "+port);
    }
    
    @Test
    public void parse() throws Exception {
    	OkHttpClient client = new OkHttpClient.Builder().build();
    	
    	Request request = new Request.Builder().url("https://www.fatsecret.kr").get().build();
    	
    	try (Response response = client.newCall(request).execute()) {
    		String html = response.body().string();
    		logger.info(html);
    		Document doc = Jsoup.parse(html);
	    }
    	
        //assertThat(responseString, is(equalTo(jsonString)));
    }
}
