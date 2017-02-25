package com.gorugoru.api.domain.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Nutri {
	Carbohydrate("탄수화물"),
	Protein("단백질"),
	Fat("지방");
	
	private String value;

	private Nutri(String value) {
		this.value = value;
	}
	
	@JsonValue
	public String value(){
		return value;
    }
}
