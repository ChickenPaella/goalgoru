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
@Table(name = "restaurant")
public class Restaurant implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@JsonView(Views.DEF.class)
	private long id;
	
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
	
	public Restaurant() {}
	
	public Restaurant(String dong, String name, String category, String address, String phone) {
		this.dong = dong;
		this.name = name;
		this.category = category;
		this.address = address;
		this.phone = phone;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "Restaurant [ id = " + id + ", dong = " + dong + ", name = " + name + ", category = " + category + ", address = "
				+ address + ", phone = " + phone + "]";
	}
}
