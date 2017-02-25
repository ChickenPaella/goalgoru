package com.gorugoru.api.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.gorugoru.api.jackson.Views;

@Entity
@Table(name = "restaurant_food")
public class RestaurantFood implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@JsonView(Views.DEF.class)
	private long seq;
	
	@ManyToOne
	@JoinColumn(name = "restaurant_seq", nullable = false)
	@JsonIgnore
	private Restaurant restaurant;
	
	@Column
	@NotNull
	@JsonView(Views.DEF.class)
	private String name;
	
	@Column(nullable = true)
	@JsonView(Views.DEF.class)
	private String description;
	
	@Column(nullable = true)
	@JsonView(Views.MORE.class)
	private int price;
	
	@Column(nullable = true)
	@JsonView(Views.DEF.class)
	private String nutri;
	
	public RestaurantFood(){
	}
	
	

	public RestaurantFood(Restaurant restaurant, String name, String description, int price, String nutri) {
		super();
		this.restaurant = restaurant;
		this.name = name;
		this.description = description;
		this.price = price;
		this.nutri = nutri;
	}



	public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getNutri() {
		return nutri;
	}

	public void setNutri(String nutri) {
		this.nutri = nutri;
	}
	
}
