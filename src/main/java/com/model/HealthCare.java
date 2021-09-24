package com.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
public class HealthCare extends Person implements Serializable {

	
	@Id
	@Column(name="NATIONAL_ID")
	private String nationalId;
	@Column(name ="EMAIL")
	private String email;
	@Column(name ="TITLE")
	private String title;
	@Column(name ="DESCRIPTION")
	private String Description;
	
	@Column(name="ONLINE")
	private boolean online;
	
	@Transient
	private List<ChatQuestion>chatQuestion;

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getNationalId() {
		return nationalId;
	}

	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public List<ChatQuestion> getChatQuestion() {
		return chatQuestion;
	}

	public void setChatQuestion(List<ChatQuestion> chatQuestion) {
		this.chatQuestion = chatQuestion;
	}


	
}
