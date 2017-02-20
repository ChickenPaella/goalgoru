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

/**
 * 유저 포인트 엔티티
 * 별, 뱃지
 * @author Administrator
 *
 */
@Entity
@Table(name = "user_point")
public class UserPoint implements Serializable{
	 
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(Views.MORE.class)
	private long seq;
	
	@Column
	@NotNull
	@JsonView(Views.DEF.class)
	private int star;
	
	@Column
	@NotNull
	@JsonView(Views.DEF.class)
	private int badgeCarbo;
	
	@Column
	@NotNull
	@JsonView(Views.DEF.class)
	private int badgeProtein;
	
	@Column
	@NotNull
	@JsonView(Views.DEF.class)
	private int badgeFat;
	
	public UserPoint(){
	}

	public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public int getBadgeCarbo() {
		return badgeCarbo;
	}

	public void setBadgeCarbo(int badgeCarbo) {
		this.badgeCarbo = badgeCarbo;
	}

	public int getBadgeProtein() {
		return badgeProtein;
	}

	public void setBadgeProtein(int badgeProtein) {
		this.badgeProtein = badgeProtein;
	}

	public int getBadgeFat() {
		return badgeFat;
	}

	public void setBadgeFat(int badgeFat) {
		this.badgeFat = badgeFat;
	}
}
