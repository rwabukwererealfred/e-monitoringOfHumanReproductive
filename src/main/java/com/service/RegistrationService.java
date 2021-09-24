package com.service;

import java.util.List;

import com.model.CommonQuestion;
import com.model.HealthCare;
import com.model.YoungAdult;

public interface RegistrationService {

	public void registerHealthCare(HealthCare healthCare);
	public void registerCommonQuestionCategory(CommonQuestion commonQuestion);
	public void registerCommonQuestionExplanation(CommonQuestion commonQuestion, int id);
	public List<CommonQuestion>categories();
	public List<CommonQuestion>questionDescriptions(int id);
	public List<HealthCare>healthCareList();
	public List<YoungAdult>youngAdultList();
	
}
