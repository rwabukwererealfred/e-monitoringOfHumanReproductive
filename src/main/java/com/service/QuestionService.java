package com.service;

import java.util.List;

import com.model.ChatQuestion;
import com.model.ChatQuestion.Category;
import com.model.CommonQuestion;
import com.model.HealthCare;
import com.model.ReplyQuestion;
import com.model.YoungAdult;

public interface QuestionService {

	public void sendPublicQuestion(YoungAdult youngAdult, ChatQuestion chatQuestion, CommonQuestion question);
	public void sentPrivateQuestion(YoungAdult youngAdult, ChatQuestion chatQuestion, CommonQuestion question, HealthCare healthCare);
	public List<ChatQuestion>chatPublicQuestionList();
	public List<ChatQuestion>chatPrivateQuestion(String nationalId);
	public List<ReplyQuestion>replyQuestions(int id);
	public void createReplyQuestion(ReplyQuestion replyQuestion, ChatQuestion chatQuestion);
	public List<ChatQuestion>myChatQuestionList(int id, Category category);
	public List<ChatQuestion>getAll();
	public List<ReplyQuestion>findAllReply();
	public void updateChatQuestion(ChatQuestion chat);
	public void updateReplyQuestion(ReplyQuestion reply);
	public List<ReplyQuestion>privateReplyQuestion(String username);
	
}
