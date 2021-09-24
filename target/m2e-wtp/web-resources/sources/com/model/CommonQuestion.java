package com.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@SuppressWarnings("serial")
@Entity
public class CommonQuestion implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private int id;

	@Column(name="MESSAGE", length = 750)
	@NotBlank(message="message does not be empty")
	private String message;
	
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name ="QUESTION")
	private CommonQuestion question;

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

	public CommonQuestion getQuestion() {
		return question;
	}

	public void setQuestion(CommonQuestion question) {
		this.question = question;
	}
	
	
}
