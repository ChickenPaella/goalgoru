package com.gorugoru.api.domain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
	
	@Column(nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonView(Views.DEF.class)
	private Date modified;

	@Column(nullable = false, insertable = false, updatable = false, columnDefinition = "DATETIME DEFAULT NOW()")
	@JsonView(Views.DEF.class)
	private Date created;
	
	public Restaurant() {}
	
	public Restaurant(String dong, String name, String category, String address, String phone) {
		super();
		this.dong = dong;
		this.name = name;
		this.category = category;
		this.address = address;
		this.phone = phone;
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

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Override
	public String toString() {
		return "Restaurant [seq=" + seq + ", dong=" + dong + ", name=" + name + ", category=" + category + ", address="
				+ address + ", phone=" + phone + ", modified=" + modified + ", created=" + created + "]";
	}
}
