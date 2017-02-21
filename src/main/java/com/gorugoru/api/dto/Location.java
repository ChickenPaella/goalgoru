package com.gorugoru.api.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.gorugoru.api.jackson.Views;

public class Location extends Address {

	private static final long serialVersionUID = 1L;
	
	@JsonView(Views.DEF.class) private String latitude;
	@JsonView(Views.DEF.class) private String longitude;
	
	public Location(){
		super();
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "Location [latitude=" + latitude + ", longitude=" + longitude + ", sido=" + sido + ", gugun=" + sigugun
				+ ", dongeup=" + dong + ", etc=" + etc + "]";
	}
	
}
