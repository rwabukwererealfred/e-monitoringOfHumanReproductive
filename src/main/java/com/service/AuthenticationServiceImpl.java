package com.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


import com.dao.HealthCareDao;
import com.dao.UserDao;
import com.dao.YoungAdultDao;
import com.model.HealthCare;
import com.model.User;
import com.model.YoungAdult;


public class AuthenticationServiceImpl extends Validation implements AuthenticationService {

	
	private UserDao userDao = new UserDao();
	
	
	private HealthCareDao healthDao = new HealthCareDao();
	
	
	private YoungAdultDao youngAdultDao = new YoungAdultDao();
	
	

	@Override
	public void login(User user) {
		HttpSession s = Util.getSession();
		try {

			Optional<User> username = userDao.getAll("from User").stream().filter(i -> i.getUsername().equals(user.getUsername()))
					.findAny();
			if (username.isPresent()) {
				if(username.get().getActive()== true) {
				if (username.get().getPassword().equals(user.getPassword())) {
					
					s.setAttribute("username", username.get().getUsername());
					if(username.get().getHealthCare() != null) {
						username.get().getHealthCare().setOnline(true);
						new HealthCareDao().update(username.get().getHealthCare());
						FacesContext.getCurrentInstance().getExternalContext().redirect("CommonQuestionList.xhtml");
					
					}
					else if(username.get().getYoungAdult() != null) {
						System.out.println("account user: "+username.get().getYoungAdult().getFirstName());
						FacesContext.getCurrentInstance().getExternalContext().redirect("CommonQuestForm.xhtml");
					
					}else {
						
						FacesContext.getCurrentInstance().getExternalContext().redirect("Dashboard.xhtml");
					}
					
				}else {
					errorMessage("error", "USERNAME AND PASSWORD IS INCORRECT");
				}
				}else {
					errorMessage("ERROR", "YOUR ACCOUNT IS BLOCK PLEASE CALL ADMIN TO UNBLOCK");
				}
			}else {
				errorMessage("ERROR", "USER DOES NOT EXIT");
			}
		} catch (Exception e) {
			 e.printStackTrace();
			 errorMessage("ERROR", e.getMessage());
		}
	}

	@Override
	public void createAccount(User user, String id, String Description) {
		Optional<User> username = userDao.getAll("From User").stream().filter(i -> i.getUsername().equals(user.getUsername()))
				.findAny();
		if(username.isPresent() == false) {
			Optional<HealthCare>healthCare = healthDao.getAll("From HealthCare").stream().filter(i->i.getNationalId().equals(id)).findAny();
			if(healthCare.isPresent()) {
				Optional<User> us = userDao.getAll("From User where healthCare is not null").stream().filter(i -> i.getHealthCare().getNationalId().equals(id))
						.findAny();
				if(us.isPresent() == false) {
				user.setActive(true);
				healthCare.get().setDescription(Description);
				healthDao.update(healthCare.get());
				user.setHealthCare(healthCare.get());
				userDao.save(user);
				successMessage("SUCCESS", "WELL SUCCESSFULL CREATED");
				}else {
					errorMessage("ERROR", "YOU HAVE ARLEADY HAVING ACCOUNT");
				}
			}else {
				errorMessage("ERROR", "YOU ARE NOT REGISTERED PLEASE CALL ADMIN TO REGISTER");
			}
			
		}else {
			errorMessage("ERROR", "USERNAME IS ARLEADY EXIST PLEASE TRY ANOTHER USERNAME");
		}
	}

	@Override
	public void createAccountAsAdult(User user, YoungAdult youngAdult) {
		Optional<User> username = userDao.getAll("From User").stream().filter(i -> i.getUsername().equals(user.getUsername()))
				.findAny();
		if(username.isPresent() == false) {
				if(validatePhone(youngAdult.getPhoneNumber())) {
					youngAdult.setDateOfBirth(new Date());
					youngAdult.setCreatedDate(LocalDate.now());
					youngAdultDao.save(youngAdult);
					user.setYoungAdult(youngAdult);
					user.setActive(true);
					userDao.save(user);
					successMessage("SUCCESS", "WELL SUCCESSFULL CREATED");
				}else {
					errorMessage("ERROR", "PHONE NUMBER MUST HAVE 10 DIGIT, PLEASE CORRECT IT");
				}
			
		}else {
			errorMessage("ERROR", "USERNAME IS ARLEADY EXIST PLEASE TRY ANOTHER USERNAME");
		}
		
		
	}

	@Override
	public List<User> userList(String query) {
		
		return new UserDao().getAll(query);
	}

	@Override
	public void updateHealth(HealthCare healthCare) {
		healthDao.update(healthCare);
		
	}

	@Override
	public void updateUser(User user) {
			new UserDao().update(user);
		
	}

}
