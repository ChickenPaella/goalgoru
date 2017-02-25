package com.gorugoru.api.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;
import com.gorugoru.api.jackson.Views;

@Entity
@Table(name = "ate_history")
public class AteHistory implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(Views.DEF.class)
	private long seq;
	
	@Column
	@NotNull
	@JsonView(Views.DEF.class)
	private String userId;
	
	@Column
	@NotNull
	@JsonView(Views.DEF.class)
	private String date;
	
	@Column
	@NotNull
	@JsonView(Views.DEF.class)
	private String receiptNumber;
	
	@Column
	@NotNull
	@JsonView(Views.DEF.class)
	private String restaurantName;
	
	@Column
	@NotNull
	@JsonView(Views.DEF.class)
	private String foodName;
	
	public AteHistory() {
		
	}

	public AteHistory(String userId, String date, String receiptNumber, String restaurantName, String foodName) {
		super();
		this.userId = userId;
		this.date = date;
		this.receiptNumber = receiptNumber;
		this.restaurantName = restaurantName;
		this.foodName = foodName;
	}

	public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	@Override
	public String toString() {
		return "AteHistory [seq=" + seq + ", date=" + date + ", receiptNumber=" + receiptNumber + ", restaurantName="
				+ restaurantName + ", foodName=" + foodName + "]";
	}
}
