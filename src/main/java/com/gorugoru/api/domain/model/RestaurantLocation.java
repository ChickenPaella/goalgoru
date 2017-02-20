package com.gorugoru.api.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.gorugoru.api.jackson.Views;
import com.gorugoru.util.AddressUtil;

@Entity
@Table(name = "restaurant_location")
public class RestaurantLocation implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@JsonView(Views.MORE.class)
	private long seq;
	
	@OneToOne
	@MapsId
	@JoinColumn(name = "restaurant_seq")
	@JsonIgnore
	private Restaurant restaurant;
	
	@Column
	@NotNull
	@JsonView(Views.DEF.class)
	private String sido;
	
	@Column(nullable = true)
	@JsonView(Views.DEF.class)
	private String gugun;
	
	@Column(nullable = true)
	@JsonView(Views.DEF.class)
	private String dongeup;
	
	@Column
	@NotNull
	@JsonView(Views.DEF.class)
	private String street;
	
	@Column(nullable = true)
	@JsonView(Views.DEF.class)
	private String etc;
	
	@Column(nullable = false, columnDefinition = "DECIMAL(10, 8)")
	@NotNull
	@JsonView(Views.DEF.class)
	private double latitude;
	
	@Column(nullable = false, columnDefinition = "DECIMAL(11, 8)")
	@NotNull
	@JsonView(Views.DEF.class)
	private double longitude;
	
	public RestaurantLocation(){
	}

	public RestaurantLocation(String sido, String gugun, String dongeup, String street, String etc) {
		super();
		this.sido = sido;
		this.gugun = gugun;
		this.dongeup = dongeup;
		this.street = street;
		this.etc = etc;
	}

	public RestaurantLocation(String sido, String gugun, String dongeup, String street, String etc, double latitude,
			double longitude) {
		super();
		this.sido = sido;
		this.gugun = gugun;
		this.dongeup = dongeup;
		this.street = street;
		this.etc = etc;
		this.latitude = latitude;
		this.longitude = longitude;
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

	public String getSido() {
		return sido;
	}

	public void setSido(String sido) {
		this.sido = sido;
	}

	public String getGugun() {
		return gugun;
	}

	public void setGugun(String gugun) {
		this.gugun = gugun;
	}

	public String getDongeup() {
		return dongeup;
	}

	public void setDongeup(String dongeup) {
		this.dongeup = dongeup;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getEtc() {
		return etc;
	}

	public void setEtc(String etc) {
		this.etc = etc;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "RestaurantLocation [seq=" + seq + ", restaurant=" + restaurant + ", sido=" + sido + ", gugun=" + gugun
				+ ", dongeup=" + dongeup + ", street=" + street + ", etc=" + etc + ", latitude=" + latitude
				+ ", longitude=" + longitude + "]";
	}

	public String toAddressString() {
		return AddressUtil.merge(sido, gugun, dongeup, street, etc);
	}
	
}
