package com.gorugoru.api.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@Table(name = "restaurant_category")
public class RestaurantCategory implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@JsonView(Views.DEF.class)
	private long seq;
	
	@Column
	@NotNull
	@JsonView(Views.DEF.class)
	private String name;
	
	@Column(nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	@JsonView(Views.MORE.class)
	private LocalDateTime modified;

	@Column(nullable = false, insertable = false, updatable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	@JsonView(Views.MORE.class)
	private LocalDateTime created;

	public RestaurantCategory() {}
	
	public RestaurantCategory(String name) {
		super();
		this.name = name;
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
	
	public LocalDateTime getModified() {
		return modified;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	@Override
	public String toString() {
		return "RestaurantCategory [seq=" + seq + ", name=" + name + ", modified=" + modified + ", created=" + created + "]";
	}
}
