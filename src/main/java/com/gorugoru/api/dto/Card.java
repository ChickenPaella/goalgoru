package com.gorugoru.api.dto;

public class Card {
	
	private String id;
	private String cardNumber;
	
	public Card() {
		
	}
	
	public Card(String id, String cardNumber) {
		super();
		this.id = id;
		this.cardNumber = cardNumber;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	@Override
	public String toString() {
		return "Card [id=" + id + ", cardNumber=" + cardNumber + "]";
	}	
}
