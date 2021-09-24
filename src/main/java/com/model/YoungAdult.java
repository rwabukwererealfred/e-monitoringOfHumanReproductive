package com.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
public class YoungAdult extends Person implements Serializable {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	
	@Transient
	private List<ChatQuestion>chatQuestion;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<ChatQuestion> getChatQuestion() {
		return chatQuestion;
	}

	public void setChatQuestion(List<ChatQuestion> chatQuestion) {
		this.chatQuestion = chatQuestion;
	}

	

	
	
	
}
