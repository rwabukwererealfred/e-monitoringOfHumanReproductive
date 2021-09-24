package com.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class ReplyQuestion implements Serializable {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="ID")
	private int id;
	@Column(name="MESSAGE" , length = 750)
	private String message;
	@Column(name="FILE")
	private String file;
	@Column(name="CREATED_DATE")
	private Date createdDate;
	
	@Column(name="READD")
	private Boolean read;
	
	@ManyToOne
	@JoinColumn(name="USER")
	private User user; 
	
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="CHAT_QUESTION")
	private ChatQuestion chatQuestion;
	
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
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public ChatQuestion getChatQuestion() {
		return chatQuestion;
	}
	public void setChatQuestion(ChatQuestion chatQuestion) {
		this.chatQuestion = chatQuestion;
	}
	public Boolean getRead() {
		return read;
	}
	public void setRead(Boolean read) {
		this.read = read;
	}
	
	
}
