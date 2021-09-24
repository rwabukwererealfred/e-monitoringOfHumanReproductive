package com.service;

import java.util.List;

import com.model.HealthCare;
import com.model.User;
import com.model.YoungAdult;

public interface AuthenticationService {

	public void login(User user);
	public void createAccount(User user, String id, String Description);
	public void createAccountAsAdult(User user, YoungAdult youngAdult);
	public List<User>userList(String query);
	public void updateHealth(HealthCare healthCare);
	public void updateUser(User user);
	
}
