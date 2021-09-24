package com.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class ChatQuestion implements Serializable {

	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private int id;
	@Column(name="MESSAGE", length = 750)
	private String message;
	@Column(name="FILE")
	private String file;
	@Enumerated(EnumType.STRING)
	@Column(name="CATEGORY")
	private Category category;
	
	@Column(name="CREATE_DATE")
	private Date createdDate;
	
	@Column(name="READD")
	private Boolean read;
	
	
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="YOUNG_ADULT")
	private YoungAdult youngAdult;
	
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="HEALTH_CARE")
	private HealthCare healthCare;
	
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="COMMON_QUESTION")
	private CommonQuestion commonQuestion;
	
	public static enum Category{
		PRIVATE, PUBLIC
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public YoungAdult getYoungAdult() {
		return youngAdult;
	}

	public void setYoungAdult(YoungAdult youngAdult) {
		this.youngAdult = youngAdult;
	}

	public HealthCare getHealthCare() {
		return healthCare;
	}

	public void setHealthCare(HealthCare healthCare) {
		this.healthCare = healthCare;
	}

	public CommonQuestion getCommonQuestion() {
		return commonQuestion;
	}

	public void setCommonQuestion(CommonQuestion commonQuestion) {
		this.commonQuestion = commonQuestion;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Boolean getRead() {
		return read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

	
	
	
}
