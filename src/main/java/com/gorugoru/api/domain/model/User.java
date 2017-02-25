package com.gorugoru.api.domain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonView;
import com.gorugoru.api.jackson.Views;

/**
 * 유저 엔티티
 * @author Administrator
 *
 */
@Entity
@Table(name = "user", uniqueConstraints={@UniqueConstraint(columnNames = {"authProvider", "authUID"})})
@Proxy(lazy = false)
public class User implements Serializable{
 
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(Views.DEF.class)
	private long seq;
	
	@Column
	@NotNull
	@JsonView(Views.ALL.class)
	private String authProvider;

	@Column(nullable = true)
	@JsonView(Views.ALL.class)
	private String authUID;

	@Column(unique = true)
	@NotNull
	@JsonView(Views.DEF.class)
	private String id;

	@Column(nullable = true)
	@JsonView(Views.ALL.class)
	private String pass;

	@Column
	@NotNull
	@JsonView(Views.DEF.class)
	private String name;
	
	@Column(nullable = true)
	@Temporal(TemporalType.DATE)
	@JsonView(Views.DEF.class)
	private Date birthDate;

	@Column(nullable = true)
	@JsonView(Views.DEF.class)
	private String email;

	@Column(nullable = true)
	@JsonView(Views.DEF.class)
	private String phone;

	@Column
	@NotNull
	@JsonView(Views.DEF.class)
	private String profileImage;
	
	@Column(nullable = true)
	@JsonView(Views.DEF.class)
	private String cardNumber;
	
	@OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER)//for camp LAZY)
	@JoinColumn(name = "user_point_seq")
	@NotNull
	@JsonView(Views.DEF.class)
	private UserPoint point;
	
	@Column(nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonView(Views.DEF.class)
	private Date modified;

	@Column(nullable = false, insertable = true, updatable = false, columnDefinition = "DATETIME DEFAULT '1000-01-01 00:00:00'")
	@JsonView(Views.DEF.class)
	private Date created;

	public User() {
	}
	
	public User(String authProvider, String authUID, String id, String pass, String name, Date birthDate, String email,
			String phone, String profileImage, String cardNumber) {
		super();
		this.authProvider = authProvider;
		this.authUID = authUID;
		this.id = id;
		this.pass = pass;
		this.name = name;
		this.birthDate = birthDate;
		this.email = email;
		this.phone = phone;
		this.profileImage = profileImage;
		this.cardNumber = cardNumber;
		this.point = new UserPoint();
	}
 
	public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}

	public String getAuthProvider() {
		return authProvider;
	}

	public void setAuthProvider(String authProvider) {
		this.authProvider = authProvider;
	}

	public String getAuthUID() {
		return authUID;
	}

	public void setAuthUID(String authUID) {
		this.authUID = authUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
	
	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public UserPoint getPoint() {
		return point;
	}

	public void setPoint(UserPoint point) {
		this.point = point;
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
		return "User [seq=" + seq + ", authProvider=" + authProvider + ", authUID=" + authUID + ", id=" + id + ", pass="
				+ pass + ", name=" + name + ", birthDate=" + birthDate + ", email=" + email + ", phone=" + phone
				+ ", profileImage=" + profileImage + ", cardNumber" + cardNumber + ", point=" + point + ", modified=" + modified
				+ ", created=" + created + "]";
	}
	
}
