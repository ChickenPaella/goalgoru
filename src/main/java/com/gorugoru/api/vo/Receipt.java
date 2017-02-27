package com.gorugoru.api.vo;

import java.time.LocalDateTime;
import java.util.Date;

public class Receipt {
	
	private String restaurantName;
	private String foodName;
	private LocalDateTime receiptDate;
	private String receiptNumber;
	
	public Receipt() {
		
	}
	
	public Receipt(String restaurantName, String foodName, LocalDateTime receiptDate, String receiptNumber) {
		super();
		this.restaurantName = restaurantName;
		this.foodName = foodName;
		this.receiptDate = receiptDate;
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

	public LocalDateTime getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(LocalDateTime receiptDate) {
		this.receiptDate = receiptDate;
	}

	public String getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	@Override
	public String toString() {
		return "Receipt [restaurantName=" + restaurantName + ", foodName=" + foodName + ", receiptDate=" + receiptDate
				+ ", receiptNumber=" + receiptNumber + "]";
	}
	
}
