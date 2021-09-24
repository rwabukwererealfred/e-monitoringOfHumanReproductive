package com.controller;



import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.model.CommonQuestion;
import com.model.HealthCare;
import com.service.RegistrationService;
import com.service.RegistrationServiceImpl;



@Named @SessionScoped
public class RegistrationController implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private RegistrationService registrationService = new RegistrationServiceImpl();
	private int id;
	private CommonQuestion category = new CommonQuestion();
	private CommonQuestion description = new CommonQuestion();
	private HealthCare healthCare = new HealthCare();
	private String questionType = "";
	private String classData = "active";
	
	
	
	public void saveCommonQuestionCategory() {
		registrationService.registerCommonQuestionCategory(category);
		category = new CommonQuestion();
	}
	
	
	public void saveCommonQuestionDescription() {
		registrationService.registerCommonQuestionExplanation(description, id);
		description = new CommonQuestion();
		id = 0;
	}
	
	public void saveHealthCare() {
		registrationService.registerHealthCare(healthCare);
		healthCare = new HealthCare();
	}

	

	public CommonQuestion getCategory() {
		return category;
	}

	public void setCategory(CommonQuestion category) {
		this.category = category;
	}

	public CommonQuestion getDescription() {
		return description;
	}

	public void setDescription(CommonQuestion description) {
		this.description = description;
	}



	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public HealthCare getHealthCare() {
		return healthCare;
	}


	public void setHealthCare(HealthCare healthCare) {
		this.healthCare = healthCare;
	}


	public String getQuestionType() {
		return questionType;
	}


	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}


	public String getClassData() {
		return classData;
	}


	public void setClassData(String classData) {
		this.classData = classData;
	}

	
	
}
