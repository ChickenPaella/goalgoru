package com.gorugoru.api.vo;

import com.fasterxml.jackson.annotation.JsonView;
import com.gorugoru.api.jackson.Views;

public class NutriCount {
	@JsonView(Views.DEF.class) private int carbo;
	@JsonView(Views.DEF.class) private int protein;
	@JsonView(Views.DEF.class) private int fat;
	
	public NutriCount(){
		carbo = 0;
		protein = 0;
		fat = 0;
	}

	public int getCarbo() {
		return carbo;
	}

	public void setCarbo(int carbo) {
		this.carbo = carbo;
	}

	public int getProtein() {
		return protein;
	}

	public void setProtein(int protein) {
		this.protein = protein;
	}

	public int getFat() {
		return fat;
	}

	public void setFat(int fat) {
		this.fat = fat;
	}
	
}
