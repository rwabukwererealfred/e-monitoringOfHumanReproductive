package com.controller;

import java.io.Serializable;
import java.util.Optional;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.inject.Named;
import com.model.HealthCare;
import com.model.User;
import com.model.YoungAdult;
import com.service.AuthenticationService;
import com.service.AuthenticationServiceImpl;
import com.service.RegistrationServiceImpl;
import com.service.Validation;

@Named("authenticationController")
@SessionScoped
public class AuthenticationController implements Serializable {

	private static final long serialVersionUID = 1L;

	private AuthenticationService auth = new AuthenticationServiceImpl();
	
	
	private User user = new User();
	private String nationalId = "";
	private String repassword = ""; private String repasswords = ""; private String description ="";
	
	private YoungAdult youngAdult = new YoungAdult();
	private User userLogin = new User();
	private User userAuth = new User();
	
	@Inject @Push
	private PushContext push;
	
	public AuthenticationController() {
		for(HealthCare c: new RegistrationServiceImpl().healthCareList()) {
		c.setOnline(false);
		auth.updateHealth(c);
		}
	}
	
	public void login () {
		Optional<User> user = auth.userList("From User").stream().filter(i->i.getUsername().equals(userLogin.getUsername())).findAny();
		if(user.isPresent()) {
		userAuth = user.get();
		
		auth.login(userLogin);
		push.send("isLogin");
		}
	}
	
	public void logout(String username)throws Exception {
		Optional<User>user = auth.userList("From User").stream().filter(i->i.getUsername().equals(username)).findAny();
		
		if(user.get().getHealthCare() != null) {
			HealthCare h = new RegistrationServiceImpl().healthCareList().stream().filter(i->i.getNationalId().equals(user.get().getHealthCare().getNationalId())).findAny().get();
			h.setOnline(false);
			auth.updateHealth(h);
		}
		
		userLogin = new User();
		push.send("isLogin");
		FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
	}
	
	public void createAccountAsYoungAdult() {
		if(repassword.matches(user.getPassword())) {
		auth.createAccountAsAdult(user, youngAdult);
		user =new User();
		youngAdult = new YoungAdult();
		}else {
			new Validation().errorMessage("ERROR", "please re-type the same password");
		}
	}
	
	public void createAccountAsHealthCare() {
		if(repasswords.matches(user.getPassword())) {
		auth.createAccount(user, nationalId, description);
		user = new User();
		nationalId = "";
		description = "";
		}else {
			new Validation().errorMessage("ERROR", "please re-type the same password");
		}
	}
	
	public void updateUser(User user) {
		User us = auth.userList("From User").stream().filter(i->i.getUsername().equals(user.getUsername())).findAny().get();
		if(us.getActive() == false) {
		us.setActive(true);
		auth.updateUser(us);
		}else {
			us.setActive(false);
			auth.updateUser(us);
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getNationalId() {
		return nationalId;
	}

	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}

	public YoungAdult getYoungAdult() {
		return youngAdult;
	}

	public void setYoungAdult(YoungAdult youngAdult) {
		this.youngAdult = youngAdult;
	}

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public User getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(User userLogin) {
		this.userLogin = userLogin;
	}

	public String getRepasswords() {
		return repasswords;
	}

	public void setRepasswords(String repasswords) {
		this.repasswords = repasswords;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUserAuth() {
		return userAuth;
	}

	public void setUserAuth(User userAuth) {
		this.userAuth = userAuth;
	}

	
}
