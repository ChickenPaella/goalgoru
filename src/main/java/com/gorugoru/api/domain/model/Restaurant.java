package com.gorugoru.api.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonView;
import com.gorugoru.api.jackson.Views;

@Entity
@Table(name = "restaurant")
@Proxy(lazy = false)
public class Restaurant implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@JsonView(Views.DEF.class)
	private long seq;
	
	@Column
	@NotNull
	@JsonView(Views.DEF.class)
	private String dong;

	@Column
	@NotNull
	@JsonView(Views.DEF.class)
	private String name;
	
	@Column
	@NotNull
	@JsonView(Views.DEF.class)
	private String category;
	
	@Column
	@NotNull
	@JsonView(Views.DEF.class)
	private String address;
	
	@Column
	@NotNull
	@JsonView(Views.DEF.class)
	private String phone;
	
	@Column
	@JsonView(Views.DEF.class)
	private String profileImage;
	
	@Column
	@JsonView(Views.DEF.class)
	private String distance;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "restaurant", optional = true, fetch = FetchType.EAGER)//for camp LAZY)
	@NotNull
	@JsonView(Views.DEF.class)
	private RestaurantLocation location;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant", fetch = FetchType.EAGER)//for camp LAZY) //EAGER not working
	@JsonView(Views.MORE.class)
	private List<RestaurantFood> foods;
	
	@Column(nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	@JsonView(Views.MORE.class)
	private LocalDateTime modified;

	@Column(nullable = false, insertable = false, updatable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	@JsonView(Views.MORE.class)
	private LocalDateTime created;
	
	public Restaurant() {}
	
	public Restaurant(String dong, String name, String category, String address, String phone, String profileImage, String distance) {
		super();
		this.dong = dong;
		this.name = name;
		this.category = category;
		this.address = address;
		this.phone = phone;
		this.profileImage = profileImage;
		this.distance = distance;
	}
	
	public Restaurant(String dong, String name, String category, String address, String phone, String profileImage, String distance,
			RestaurantLocation location) {
		super();
		this.dong = dong;
		this.name = name;
		this.category = category;
		this.address = address;
		this.phone = phone;
		this.profileImage = profileImage;
		this.distance = distance;
		this.location = location;
	}

	public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}

	public String getDong() {
		return dong;
	}

	public void setDong(String dong) {
		this.dong = dong;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public RestaurantLocation getLocation() {
		return location;
	}
	
	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public void setLocation(RestaurantLocation location) {
		this.location = location;
	}

	public List<RestaurantFood> getFoods() {
		return foods;
	}

	public void setFoods(List<RestaurantFood> foods) {
		this.foods = foods;
	}

	public LocalDateTime getModified() {
		return modified;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	@Override
	public String toString() {
		return "Restaurant [seq=" + seq + ", dong=" + dong + ", name=" + name + ", category=" + category + ", address="
				+ address + ", phone=" + phone + ", profileImage=" + profileImage + ", distance=" + distance
				+ ", location=" + location + ", foods=" + foods + ", modified=" + modified + ", created=" + created
				+ "]";
	}
}
