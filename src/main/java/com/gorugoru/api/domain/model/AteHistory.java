package com.gorugoru.api.domain.model;

import java.io.Serializable;
import java.sql.Date;

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
	private String id;
	
	@Column(nullable = false, insertable = false, updatable = false, columnDefinition = "DATETIME DEFAULT NOW()")
	@NotNull
	@JsonView(Views.DEF.class)
	private Date eatDate;
	
	@Column(nullable = true, insertable = false, updatable = true, columnDefinition = "DATETIME")
	@JsonView(Views.DEF.class)
	private Date receiptDate;
	
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
	
	@Column
	@NotNull
	@JsonView(Views.DEF.class)
	private Nutri mainNutri;
	
	@Column(nullable = false)
	@JsonView(Views.DEF.class)
	private boolean isAte;
	
	public AteHistory() {
		
	}

	public AteHistory(String id, Date eatDate, Date receiptDate, String receiptNumber, String restaurantName, String foodName, Nutri mainNutri) {
		super();
		this.id = id;
		this.eatDate = eatDate;
		this.receiptDate = receiptDate;
		this.receiptNumber = receiptNumber;
		this.restaurantName = restaurantName;
		this.foodName = foodName;
		this.mainNutri = mainNutri;
	}

	public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getEatDate() {
		return eatDate;
	}

	public void setEatDate(Date eatDate) {
		this.eatDate = eatDate;
	}

	public Date getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
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
	
	public Nutri getMainNutri() {
		return mainNutri;
	}

	public void setMainNutri(Nutri mainNutri) {
		this.mainNutri = mainNutri;
	}

	public boolean isAte() {
		return isAte;
	}

	public void setAte(boolean isAte) {
		this.isAte = isAte;
	}

	@Override
	public String toString() {
		return "AteHistory [seq=" + seq + ", id=" + id + ", eatDate=" + eatDate + ", receiptDate=" + receiptDate
				+ ", receiptNumber=" + receiptNumber + ", restaurantName=" + restaurantName + ", foodName=" + foodName
				+ ", mainNutri=" + mainNutri + ", isAte=" + isAte + "]";
	}
	
}
