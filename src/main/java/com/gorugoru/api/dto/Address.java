package com.gorugoru.api.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonView;
import com.gorugoru.api.jackson.Views;

public class Address implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonView(Views.DEF.class) protected String sido;
	@JsonView(Views.DEF.class) protected String sigugun;
	@JsonView(Views.DEF.class) protected String dong;
	@JsonView(Views.DEF.class) protected String bunji;
	@JsonView(Views.DEF.class) protected String etc;
	
	public Address(){
	}
	
	public String getSido() {
		return sido;
	}
	
	public void setSido(String sido) {
		this.sido = sido;
	}
	
	public String getSigugun() {
		return sigugun;
	}
	
	public void setSigugun(String sigugun) {
		this.sigugun = sigugun;
	}
	
	public String getDong() {
		return dong;
	}
	
	public void setDong(String dong) {
		this.dong = dong;
	}
	
	public String getBunji() {
		return bunji;
	}

	public void setBunji(String bunji) {
		this.bunji = bunji;
	}

	public String getEtc() {
		return etc;
	}

	public void setEtc(String etc) {
		this.etc = etc;
	}

	@Override
	public String toString() {
		return "Address [sido=" + sido + ", sigugun=" + sigugun + ", dong=" + dong + ", bunji=" + bunji + ", etc=" + etc
				+ "]";
	}
	
}
