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
@Table(name = "food")
public class Food implements Serializable{
 
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@JsonView(Views.DEF.class)
	private long seq;
	
	@Column
	@NotNull
	@JsonView(Views.DEF.class)
	private String name;
	
	@Column
	@NotNull
	@JsonView(Views.DEF.class)
	private int calorie;
	
	@Column
	@NotNull
	@JsonView(Views.DEF.class)
	private Nutri mainNutri;
	
	@Column
	@NotNull
	@JsonView(Views.DEF.class)
	private String nurition;//json string
	
	public Food(){
		
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

	public int getCalorie() {
		return calorie;
	}

	public void setCalorie(int calorie) {
		this.calorie = calorie;
	}

	public Nutri getMainNutri() {
		return mainNutri;
	}

	public void setMainNutri(Nutri mainNutri) {
		this.mainNutri = mainNutri;
	}

	public String getNurition() {
		return nurition;
	}

	public void setNurition(String nurition) {
		this.nurition = nurition;
	}

}
