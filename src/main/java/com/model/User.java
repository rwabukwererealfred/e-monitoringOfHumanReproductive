package com.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
public class User implements Serializable {

	@Id
	@Column(name="USERNAME")
	private String username;
	@Column(name="PASSWORD")
	private String password;
	
	@OneToOne
	@JoinColumn(name="HEALTH_CARE")
	private HealthCare healthCare;
	
	@OneToOne
	@JoinColumn(name="YOUNG_ADULT")
	private YoungAdult youngAdult;
	
	@Column(name="ACTIVE")
	private Boolean active;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public HealthCare getHealthCare() {
		return healthCare;
	}
	public void setHealthCare(HealthCare healthCare) {
		this.healthCare = healthCare;
	}
	public YoungAdult getYoungAdult() {
		return youngAdult;
	}
	public void setYoungAdult(YoungAdult youngAdult) {
		this.youngAdult = youngAdult;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	
	
	
}
