package com.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import com.model.ChatQuestion;
import com.model.ChatQuestion.Category;
import com.model.CommonQuestion;
import com.model.HealthCare;
import com.model.ReplyQuestion;
import com.model.User;
import com.service.AuthenticationService;
import com.service.AuthenticationServiceImpl;
import com.service.QuestionService;
import com.service.QuestionServiceImpl;
import com.service.RegistrationService;
import com.service.RegistrationServiceImpl;


@Named
@RequestScoped
public class ListController {

	private RegistrationService registrationService = new RegistrationServiceImpl();

	private QuestionService questionService = new QuestionServiceImpl();

	private AuthenticationService auth = new AuthenticationServiceImpl();
	
	private String search ="";
	

	private List<CommonQuestion>commonQuestions= registrationService.categories();
	
	public void searchQuestion() {
		List<CommonQuestion>list = new ArrayList<CommonQuestion>();
		if(search.equals("")) {
			commonQuestions= registrationService.categories();
			System.out.println("out com "+commonQuestions.size());
		}else {
			for(CommonQuestion c: registrationService.categories()) {
				if(c.getMessage().contains(search)) {
					list.add(c);
				}
			}
			System.out.println("out "+list.size());
			commonQuestions = list;
		}
	}

	public List<CommonQuestion> categories() {
		
		return registrationService.categories();
	}

	public List<HealthCare> healthCareList() {
		return registrationService.healthCareList();
	}

	public List<CommonQuestion> descriptionList(int id) {
		return registrationService.questionDescriptions(id);
	}

	public List<ChatQuestion> privateChatQuestionList(String usern) {

		User user = auth.userList("from User where healthCare is not null").stream()
				.filter(i -> i.getUsername().equals(usern)).findAny().get();
		return questionService.chatPrivateQuestion(user.getHealthCare().getNationalId());
	}

	public List<ChatQuestion> MyPrivateChatQuestionList(String usern) {

		User user = auth.userList("from User where youngAdult is not null").stream()
				.filter(i -> i.getUsername().equals(usern)).findAny().get();
		return questionService.myChatQuestionList(user.getYoungAdult().getId(), Category.PRIVATE);
	}

	public List<ChatQuestion> MyPublicChatQuestionList(String usern) {

		User user = auth.userList("from User where youngAdult is not null").stream()
				.filter(i -> i.getUsername().equals(usern)).findAny().get();
		return questionService.myChatQuestionList(user.getYoungAdult().getId(), Category.PUBLIC);
	}

	public List<ChatQuestion> publicQuestionList() {
		return questionService.chatPublicQuestionList();
	}

	public List<ReplyQuestion> replyQuestionList(int id) {
		return questionService.replyQuestions(id);
	}
	
	public List<ReplyQuestion>findAllRepliedChat(String username){
		
		return questionService.findAllReply().stream().filter(i->i.getUser().getUsername().equals(username)).collect(Collectors.toList());
	}
    public List<ReplyQuestion>findAllRepliedChat(){
		
		return questionService.findAllReply();
	}
	
	public List<User>userYoungAdultList(){
		return auth.userList("From User where youngAdult is not null");
	}
	public List<User>userHealthCareList(){
		return auth.userList("From User where healthCare is not null");
	}

	public List<CommonQuestion> getCommonQuestions() {
		return commonQuestions;
	}

	public void setCommonQuestions(List<CommonQuestion> commonQuestions) {
		this.commonQuestions = commonQuestions;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
	

}
