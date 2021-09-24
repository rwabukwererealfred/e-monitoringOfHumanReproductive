package com.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.servlet.http.HttpSession;

import com.dao.ChatQuestionDao;
import com.dao.ReplyQuestionDao;
import com.dao.UserDao;
import com.model.ChatQuestion;
import com.model.ChatQuestion.Category;
import com.model.CommonQuestion;
import com.model.HealthCare;
import com.model.ReplyQuestion;
import com.model.User;
import com.model.YoungAdult;


@SuppressWarnings("serial")
public class QuestionServiceImpl extends Validation implements QuestionService , Serializable {
	
	@Override
	public void sendPublicQuestion(YoungAdult youngAdult, ChatQuestion chatQuestion, CommonQuestion question) {
		
		try {
		chatQuestion.setCreatedDate(new Date());
		chatQuestion.setCategory(Category.PUBLIC);
		chatQuestion.setCommonQuestion(question);
		chatQuestion.setYoungAdult(youngAdult);
		chatQuestion.setRead(false);
		new ChatQuestionDao().save(chatQuestion);
		successMessage("SUCCESS", "WELL SUCCESSFULL SENT");
		}catch(Exception e) {
			e.printStackTrace();
			errorMessage("ERROR", e.getMessage());
		}
	}

	@Override
	public void sentPrivateQuestion(YoungAdult youngAdult, ChatQuestion chatQuestion, CommonQuestion question,
			HealthCare healthCare) {
		try {
			chatQuestion.setCreatedDate(new Date());
			chatQuestion.setCategory(Category.PRIVATE);
			chatQuestion.setCommonQuestion(question);
			chatQuestion.setYoungAdult(youngAdult);
			chatQuestion.setHealthCare(healthCare);
			chatQuestion.setRead(false);
			new ChatQuestionDao().save(chatQuestion);
			successMessage("SUCCESS", "WELL SUCCESSFULL SENT");
			}catch(Exception e) {
				e.printStackTrace();
				errorMessage("ERROR", e.getMessage());
			}
		
	}

	@Override
	public List<ChatQuestion> chatPublicQuestionList() {
		
		return new ChatQuestionDao().getAll("FROM ChatQuestion where category ='PUBLIC' order by createdDate DESC");
	}

	@Override
	public List<ChatQuestion> chatPrivateQuestion(String nationalId) {
		return new ChatQuestionDao().getAll("FROM ChatQuestion where category ='PRIVATE' order by createdDate DESC").stream()
				.filter(i->i.getHealthCare().getNationalId().equals(nationalId)).collect(Collectors.toList());
	}
	
	@Override
	public List<ReplyQuestion> replyQuestions(int id) {
		
		return new ReplyQuestionDao().getAll("from ReplyQuestion where chatQuestion =('"+id+"') order by createdDate DESC");
	}

	@Override
	public void createReplyQuestion(ReplyQuestion replyQuestion, ChatQuestion chatQuestion) {
		try {
			HttpSession s = Util.getSession();
			String username = s.getAttribute("username").toString();
			User user = new UserDao().getAll("From User").stream().filter(i->i.getUsername().equals(username)).findAny().get();
			
			replyQuestion.setRead(false);
			replyQuestion.setCreatedDate(new Date());	
			replyQuestion.setUser(user);
			replyQuestion.setChatQuestion(chatQuestion);
			new ReplyQuestionDao().save(replyQuestion);
		
			
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage("ERROR", e.getMessage());
		}
	}

	@Override
	public List<ChatQuestion> myChatQuestionList(int id, Category category) {
		
		return new ChatQuestionDao().getAll("From ChatQuestion where category =('"+category+"') and youngAdult =('"+id+"')");
	}

	@Override
	public List<ChatQuestion> getAll() {
		
		return new ChatQuestionDao().getAll("From ChatQuestion");
	}

	@Override
	public List<ReplyQuestion> findAllReply() {
		
		return new ReplyQuestionDao().getAll("From ReplyQuestion");
	}

	@Override
	public void updateChatQuestion(ChatQuestion chat) {
		chat.setRead(true);
		new ChatQuestionDao().update(chat);
		
	}

	@Override
	public void updateReplyQuestion(ReplyQuestion reply) {
		reply.setRead(true);
		new ReplyQuestionDao().update(reply);
	}

	@Override
	public List<ReplyQuestion> privateReplyQuestion(String username) {
		
		User user = new UserDao().getAll("from User where healthCare is not null").stream()
				.filter(i -> i.getUsername().equals(username)).findAny().get();
		
		return new ReplyQuestionDao().getAll("from ReplyQuestion where read =('"+false+"') order by createdDate Desc").stream().
				filter(i->i.getChatQuestion().getHealthCare()!=null && i.getChatQuestion().getHealthCare().getNationalId().equals(user.getHealthCare().getNationalId())
				&& !i.getUser().getUsername().equals(username)).collect(Collectors.toList());
	}
	
}
