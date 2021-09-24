package com.service;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import com.dao.CommonQuestionDao;
import com.dao.HealthCareDao;
import com.dao.YoungAdultDao;
import com.model.CommonQuestion;
import com.model.HealthCare;
import com.model.YoungAdult;


@Stateless
@SuppressWarnings("serial")
public class RegistrationServiceImpl extends Validation implements RegistrationService, Serializable {
	
	
	private CommonQuestionDao commonQuestionDao = new CommonQuestionDao();
	
	
	private HealthCareDao healthCareDao = new HealthCareDao();
	
	private YoungAdultDao youngDao = new YoungAdultDao();

	@Override
	public void registerHealthCare(HealthCare healthCare) {
		Optional<HealthCare>health = healthCareDao.getAll("From HealthCare").stream().filter(i->i.getNationalId().equals(healthCare.getNationalId())).findAny();
		if(health.isPresent() == false) {
		String date = new SimpleDateFormat("dd/MM/yyyy").format(healthCare.getDateOfBirth());
			if (validateId(healthCare.getNationalId(),date )) {
				if(validatePhone(healthCare.getPhoneNumber())) {
					healthCare.setOnline(false);
					healthCare.setCreatedDate(LocalDate.now());
					healthCareDao.save(healthCare);
					successMessage("SUCCESS", "WELL SUCCESSFULL SAVED");
				}else {
					errorMessage("ERROR", "PHONE IS INCORECT");
				}
			}else {
				errorMessage("ERROR", "NATIONAL ID AND DATE OF BIRTH DOES NOT MATCH");
			}
		}else {
			errorMessage("ERROR", "NATIONAL ID IS EXIST");
		}
		
	}

	@Override
	public void registerCommonQuestionCategory(CommonQuestion commonQuestion) {
		commonQuestionDao.save(commonQuestion);
		successMessage("SUCCESS", "WELL SUCCESSFULL SAVED");
	}

	@Override
	public void registerCommonQuestionExplanation(CommonQuestion commonQuestion, int id) {
		
		try {
			if(id ==0) {
				errorMessage("ERROR", "PLEASE SELECT QUESTION CATEGORY");
			}else {
			CommonQuestion c = commonQuestionDao.getOne(id);
			commonQuestion.setQuestion(c);
			commonQuestionDao.save(commonQuestion);
			successMessage("SUCCESS", "WELL SUCCESSFULL SAVED");
			}
		}
		
		 catch (Exception e) {
			e.printStackTrace();
			errorMessage("ERROR", e.getMessage());
		
	}

	}

	@Override
	public List<CommonQuestion> categories() {
		return commonQuestionDao.getAll("From CommonQuestion where question is null").stream().filter(i->i.getQuestion() == null).collect(Collectors.toList());
	}

	@Override
	public List<CommonQuestion> questionDescriptions(int id) {
		
		return commonQuestionDao.getAll("From CommonQuestion where question is not null").stream().filter(i->i.getQuestion().getId() == id).collect(Collectors.toList());
	}

	@Override
	public List<HealthCare> healthCareList() {
		return healthCareDao.getAll("From HealthCare");
	}

	@Override
	public List<YoungAdult> youngAdultList() {
		
		return youngDao.getAll("From YoungAdult");
	}
}
