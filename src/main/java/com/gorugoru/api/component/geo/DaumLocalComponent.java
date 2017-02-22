package com.gorugoru.api.component.geo;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gorugoru.api.dto.Address;
import com.gorugoru.api.dto.Location;

@Component
public class DaumLocalComponent {
	
	private static final Logger logger = LoggerFactory.getLogger(DaumLocalComponent.class);
	
	@Autowired
	ObjectMapper mapper;
	
	final DaumLocalClient localClient;
	
	public DaumLocalComponent(){
		localClient = new DaumLocalClient("4021d7cd43b6525e1fadf9c3eaba231b");
	}
	
	public Location addr2coord(String address){
		/*{"channel":{"totalCount":"1","link":"http:\/\/developers.daum.net\/services","result":"1","generator":"Daum Open API",
		"pageCount":"1","lastBuildDate":"","item":[{"mountain":"","mainAddress":"100","point_wx":"491068","point_wy":"1105870",
			"isNewAddress":"N","buildingAddress":"","title":"\uc11c\uc6b8 \uad00\uc545\uad6c \ubd09\ucc9c\ub3d9 100",
			"placeName":"Not avaliable","zipcode":"151050","newAddress":"","localName_2":"\uad00\uc545\uad6c",
			"localName_3":"\ubd09\ucc9c\ub3d9","localName_1":"\uc11c\uc6b8","lat":37.4805662584,"point_x":126.9596058074,
			"lng":126.9596058074,"zone_no":"","subAddress":"0","id":"J134803381","point_y":37.4805662584}],
		"title":"Search Daum Open API","description":"Daum Open API search result"}}
		*/
		
		String json = localClient.addr2coord(address);
		
		logger.info(json);
		
		Coord coord = null;
		try {
			coord = mapper.readValue(json, Coord.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(coord != null && coord.getChannel().getItem().size() > 0){
			Location loc = new Location();
			logger.info(coord.getChannel().getItem().get(0).getTitle());
			loc.setSido(coord.getChannel().getItem().get(0).getLocalName_1());
			loc.setSigugun(coord.getChannel().getItem().get(0).getLocalName_2());
			loc.setDong(coord.getChannel().getItem().get(0).getLocalName_3());
			//loc.setEtc((String) coord.getChannel().getItem().get(0).getLocalName_4());
			loc.setLatitude(coord.getChannel().getItem().get(0).getLat());
			loc.setLongitude(coord.getChannel().getItem().get(0).getLng());
			
			return loc;
		}
		
		return null;
	}

	public Address coord2addr(String latitude, String longitude) {
		//{"type":"H","code":"1121057","name":"행운동","fullName":"서울특별시 관악구 행운동","regionId":"I10040315","name0":"대한민국","code1":"11","name1":"서울특별시","code2":"11210","name2":"관악구","code3":"1121057","name3":"행운동","x":126.9570641644803,"y":37.480648254931666}
		
		String json = localClient.coord2addr(latitude, longitude);
		
		Map<String, Object> map = simpleJsonParse(json);
		
		if(map != null){
			Address addr = new Address();
			addr.setSido((String) map.get("name1"));
			addr.setSigugun((String) map.get("name2"));
			addr.setDong((String) map.get("name3"));
			addr.setEtc((String) map.get("name4"));
			
			return addr;
		}
		
		return null;
	}
	
	private Map<String, Object> simpleJsonParse(String json){
		Map<String, Object> map = null;
		
		try {
			map = mapper.readValue(json, new TypeReference<Map<String, String>>(){});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
}