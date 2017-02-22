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
	@Temporal(TemporalType.TIMESTAMP)
	@JsonView(Views.MORE.class)
	private Date modified;

	@Column(nullable = false, insertable = true, updatable = false, columnDefinition = "DATETIME DEFAULT '1000-01-01 00:00:00'")
	@JsonView(Views.MORE.class)
	private Date created;

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
		return "RestaurantCategory [seq=" + seq + ", name=" + name + ", modified=" + modified + ", created=" + created + "]";
	}
}
