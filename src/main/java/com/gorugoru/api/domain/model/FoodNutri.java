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
	private float car;//g
	
	@Column
	@NotNull
	private int carPercent;//%
	
	@Column
	@NotNull
	private float pro;
	
	@Column
	@NotNull
	private int proPercent;
	
	@Column
	@NotNull
	private float fat;
	
	@Column
	@NotNull
	private int fatPercent;
	
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
	public float getCar() {
		return car;
	}
	public void setCar(float car) {
		this.car = car;
	}
	public int getCarPercent() {
		return carPercent;
	}
	public void setCarPercent(int carPercent) {
		this.carPercent = carPercent;
	}
	public float getPro() {
		return pro;
	}
	public void setPro(float pro) {
		this.pro = pro;
	}
	public int getProPercent() {
		return proPercent;
	}
	public void setProPercent(int proPercent) {
		this.proPercent = proPercent;
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
	public String getOriginalUrl() {
		return originalUrl;
	}
	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}
}
