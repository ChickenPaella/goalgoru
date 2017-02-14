package com.gorugoru.api;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.net.URI;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
public class RestTemplateTests {
	
	private static final Logger logger = LoggerFactory.getLogger(RestTemplateTests.class);

    @Value("${local.server.port}")
    int port;

    @Autowired
	ObjectMapper mapper;
    
    @Autowired
    TestRestTemplate restTemplate;
    
    private String baseUrl;

    @Before
    public void setUp() {
    	logger.info("setUp() port: "+port);
        baseUrl = "http://localhost:" +  String.valueOf(port);
    }
    
    @Test
    public void test() throws Exception {
        URI uri = URI.create(baseUrl+ "/api/");
        String responseString = restTemplate.getForObject(uri, String.class);

        logger.info(responseString);
        
        //assert result
        String result = "hello, world";
        String jsonString = mapper.writeValueAsString(result);
        assertThat(responseString, is(equalTo(jsonString)));
    }
}
