package com.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.model.ChatQuestion;
import com.model.ReplyQuestion;
import com.model.User;
import com.service.AuthenticationService;
import com.service.AuthenticationServiceImpl;
import com.service.QuestionService;
import com.service.QuestionServiceImpl;
import com.service.Util;

@Named @RequestScoped
public class RequestController {

	@Inject @Push
	private PushContext pushed;
	
	private QuestionService questionService = new QuestionServiceImpl();
	private AuthenticationService auth = new AuthenticationServiceImpl();
	
	private ChatQuestion chatQuestionResponse = new ChatQuestion();
	private ReplyQuestion reply = new ReplyQuestion();
	
	public List<ReplyQuestion> replyQuestionList() {
		String id = Util.getSession().getAttribute("chat").toString();
		int ids = Integer.valueOf(id);
		return questionService.replyQuestions(ids);
	}
	
	public void createReplyQuestion() {
		String id = Util.getSession().getAttribute("chat").toString();
		int ids = Integer.valueOf(id);
		ChatQuestion c = questionService.getAll().stream().filter(i->i.getId()  ==ids).findAny().get();
		try {
			if (!reply.getMessage().equals("")) {
				questionService.createReplyQuestion(reply, c);
				System.out.println("new woere----------------------------------------------");
				reply = new ReplyQuestion();
				System.out.println("werere----------------------------------------------");
				pushed.send("replyOnline");
				System.out.println("newed----------------------------------------------");
				
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
	
	public List<ChatQuestion>unreadPrivateMessage(String username){
		
		User user = auth.userList("from User where healthCare is not null").stream()
				.filter(i -> i.getUsername().equals(username)).findAny().get();
		
		return questionService.chatPrivateQuestion(user.getHealthCare().getNationalId()).stream().filter(i->i.getRead()== false).collect(Collectors.toList());
	}
     public List<ChatQuestion>unreadPublicMessage(){
		return questionService.chatPublicQuestionList().stream().filter(i->i.getRead()== false).collect(Collectors.toList());
	}
    
    public List<ReplyQuestion>privateReplyList(String username){
    	return questionService.privateReplyQuestion(username);
    }
   
	public ChatQuestion getChatQuestionResponse() {
		return chatQuestionResponse;
	}

	public void setChatQuestionResponse(ChatQuestion chatQuestionResponse) {
		this.chatQuestionResponse = chatQuestionResponse;
	}

	public ReplyQuestion getReply() {
		return reply;
	}

	public void setReply(ReplyQuestion reply) {
		this.reply = reply;
	}
	
}
