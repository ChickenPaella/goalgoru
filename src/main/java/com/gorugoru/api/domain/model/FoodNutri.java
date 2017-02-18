package com.gorugoru.api.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 음식 칼로리 영양소 crawled by https://www.fatsecret.kr
 * @author Administrator
 *
 */

@Entity
@Table(name = "food_nutri")
public class FoodNutri implements Serializable{
 
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long seq;
	
	@Column
	@NotNull
	private String name;
	
	@Column
	@NotNull
	private String servingSize;
	
	@Column
	@NotNull
	private int calorie;//kcal
	
	@Column
	@NotNull
	private float carbo;//탄수화물 g
	
	@Column
	@NotNull
	private int carboPercent;//%
	
	@Column
	@NotNull
	private float protein;//단백질 g
	
	@Column
	@NotNull
	private int proteinPercent;//%
	
	@Column
	@NotNull
	private float fat;//지방 g
	
	@Column
	@NotNull
	private int fatPercent;//%
	
	@Column
	@NotNull
	private float choles;//콜레스테롤 mg
	
	@Column
	@NotNull
	private float fiber;//g
	
	@Column
	@NotNull
	private float natrium;//나트륨 mg
	
	@Column
	@NotNull
	private float kalium;//칼륨 mg
	
	@Column
	@NotNull
	private String originalUrl;
	
	public FoodNutri(){
	}

	public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getServingSize() {
		return servingSize;
	}

	public void setServingSize(String servingSize) {
		this.servingSize = servingSize;
	}

	public int getCalorie() {
		return calorie;
	}

	public void setCalorie(int calorie) {
		this.calorie = calorie;
	}

	public float getCarbo() {
		return carbo;
	}

	public void setCarbo(float carbo) {
		this.carbo = carbo;
	}

	public int getCarboPercent() {
		return carboPercent;
	}

	public void setCarboPercent(int carboPercent) {
		this.carboPercent = carboPercent;
	}

	public float getProtein() {
		return protein;
	}

	public void setProtein(float protein) {
		this.protein = protein;
	}

	public int getProteinPercent() {
		return proteinPercent;
	}

	public void setProteinPercent(int proteinPercent) {
		this.proteinPercent = proteinPercent;
	}

	public float getFat() {
		return fat;
	}

	public void setFat(float fat) {
		this.fat = fat;
	}

	public int getFatPercent() {
		return fatPercent;
	}

	public void setFatPercent(int fatPercent) {
		this.fatPercent = fatPercent;
	}

	public float getCholes() {
		return choles;
	}

	public void setCholes(float choles) {
		this.choles = choles;
	}

	public float getFiber() {
		return fiber;
	}

	public void setFiber(float fiber) {
		this.fiber = fiber;
	}

	public float getNatrium() {
		return natrium;
	}

	public void setNatrium(float natrium) {
		this.natrium = natrium;
	}

	public float getKalium() {
		return kalium;
	}

	public void setKalium(float kalium) {
		this.kalium = kalium;
	}

	public String getOriginalUrl() {
		return originalUrl;
	}

	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}
	
}
