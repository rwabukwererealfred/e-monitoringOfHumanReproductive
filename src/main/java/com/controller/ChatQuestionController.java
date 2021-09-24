package com.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import com.model.ChatQuestion;
import com.model.CommonQuestion;
import com.model.HealthCare;
import com.model.ReplyQuestion;
import com.model.User;
import com.service.AuthenticationService;
import com.service.AuthenticationServiceImpl;
import com.service.QuestionService;
import com.service.QuestionServiceImpl;
import com.service.Util;
import com.service.Validation;

@Named("chatQuestionController")
@SessionScoped
public class ChatQuestionController implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userRole = "";

	private ChatQuestion chatQuestion = new ChatQuestion();

	@Inject @Push
	private PushContext push;

	private AuthenticationService auth = new AuthenticationServiceImpl();
	private QuestionService questionService = new QuestionServiceImpl();
	private CommonQuestion commonQuestion = new CommonQuestion();
	private String otherQuestion = "";
	private HealthCare healthCare = new HealthCare();
	private ChatQuestion chatQuestionResponse = new ChatQuestion();
	private ReplyQuestion reply = new ReplyQuestion();
	
	
	

	private UploadedFile file, file2, file3;

	public void chatInPrivateForm(ChatQuestion question) throws Exception {
		chatQuestionResponse = question;
		HttpSession s = Util.getSession();
		s.setAttribute("chat",chatQuestionResponse.getId());
		String username = s.getAttribute("username").toString();
		questionService.updateChatQuestion(question);
		questionService.findAllReply().stream().filter(i->i.getChatQuestion().getId() == question.getId() && !i.getUser().getUsername().equals(username)).
		forEach(i->{
			questionService.updateReplyQuestion(i);
		});
		push.send("updateChatQuestion");
		push.send("updatetopbar");
		FacesContext.getCurrentInstance().getExternalContext().redirect("ChatMessage.xhtml");
	}

	public List<ReplyQuestion> replyQuestionList(int id) {
		return questionService.replyQuestions(id);
	}
	
	public void createReplyQuestion() {
		try {
			if (!reply.getMessage().equals("")) {
				questionService.createReplyQuestion(reply, chatQuestionResponse);
				replyQuestionList(chatQuestionResponse.getId()).add(0,reply);
				push.send("replyOnline");
				reply = new ReplyQuestion();
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
	public void sendFile() {
		try {
			if (!file3.getFileName().equals("")) {
					String fileName = UUID.randomUUID().toString().substring(0, 4) + file3.getFileName();
					reply.setMessage(null);					reply.setFile(fileName);
					copyFile(fileName, file3.getInputStream());
				questionService.createReplyQuestion(reply, chatQuestionResponse);
				reply = new ReplyQuestion();
				push.send("replyOnlinefile");
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		reflesh();
	}
	
	public void reflesh() {
		push.send("replyOnlinefile");
	}

	public void sentPubliQuestion() {
		try {
			HttpSession s = Util.getSession();
			String username = s.getAttribute("username").toString();
			User us = auth.userList("From User where youngAdult is not null").stream()
					.filter(i -> i.getUsername().equals(username)).findAny().get();

			if (file.getFileName() !=null) {
				String ui = UUID.randomUUID().toString().substring(0, 5);
				String fileName = ui + file.getFileName();
				chatQuestion.setFile(fileName);
				copyFile(fileName, file.getInputStream());
			}
			if (commonQuestion.getMessage() == null) {
				
				questionService.sendPublicQuestion(us.getYoungAdult(), chatQuestion, null);
			} else {
				
				questionService.sendPublicQuestion(us.getYoungAdult(), chatQuestion, commonQuestion);
			}
		
			push.send("publicQuestion");
			chatQuestion = new ChatQuestion();

		} catch (Exception e) {
			e.printStackTrace();
			new Validation().errorMessage("ERROR", e.getMessage());
		}

	}

	public void sentPrivateQuestion() {
		try {
			HttpSession s = Util.getSession();
			String username = s.getAttribute("username").toString();
			User us = auth.userList("From User where youngAdult is not null").stream()
					.filter(i -> i.getUsername().equals(username)).findAny().get();
			if (file2.getFileName() !=null) {
				String ui = UUID.randomUUID().toString().substring(0, 5);
				String fileName = ui + file2.getFileName();
				chatQuestion.setFile(fileName);
				copyFile(fileName, file2.getInputStream());
			} else {
				System.out.println("file is empty----------------------------------------");
			}

			if (commonQuestion.getMessage() == null) {
				questionService.sentPrivateQuestion(us.getYoungAdult(), chatQuestion, null, healthCare);
			} else {
				questionService.sentPrivateQuestion(us.getYoungAdult(), chatQuestion, commonQuestion, healthCare);
			}
			push.send("privateQuestion");
			chatQuestion = new ChatQuestion();

		} catch (Exception e) {
			e.printStackTrace();
			new Validation().errorMessage("ERROR", e.getMessage());
		}
	}

	public void ChooseQuestionForm(CommonQuestion question) throws Exception {
		this.commonQuestion = question;
		this.otherQuestion = "";
		FacesContext.getCurrentInstance().getExternalContext().redirect("ChoosePrivateOrPublicQuestion.xhtml");
	}

	public void ChooseOtherQuestionForm() throws Exception {
		this.commonQuestion = new CommonQuestion();
		this.otherQuestion = "Other";
		FacesContext.getCurrentInstance().getExternalContext().redirect("ChoosePrivateOrPublicQuestion.xhtml");
	}

	public void privateQuestionForm(HealthCare healthCare) throws Exception {
		this.healthCare = healthCare;
		FacesContext.getCurrentInstance().getExternalContext().redirect("privateAskQuestionForm.xhtml");
	}

	public void copyFile(final String fileName, final InputStream in) {

		try {

			String destination = "E:\\projectImage\\";
			OutputStream out = new FileOutputStream(new File(destination + fileName));

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			in.close();
			out.flush();
			out.close();

			System.out.println("New file created!");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void readFile() {

		if (Desktop.isDesktopSupported()) {
			try {
				
				File myFile = new File("E:\\projectImage\\" + chatQuestionResponse.getFile());
				Desktop.getDesktop().open(myFile);
			} catch (IOException ex) {

				new Validation().errorMessage("ERROR", ex.getMessage());
			}
		}
	}

	public void readReplyFile(ReplyQuestion chatQuestion) {

		if (Desktop.isDesktopSupported()) {
			try {
				File myFile = new File("E:\\projectImage\\" + chatQuestion.getFile());
				Desktop.getDesktop().open(myFile);
			} catch (IOException ex) {

				new Validation().errorMessage("ERROR", ex.getMessage());
			}
		}
	}

	public List<ChatQuestion> publicChatQuestion() {
		return questionService.chatPublicQuestionList();
	}

	public ChatQuestion getChatQuestion() {
		return chatQuestion;
	}

	public void setChatQuestion(ChatQuestion chatQuestion) {
		this.chatQuestion = chatQuestion;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public CommonQuestion getCommonQuestion() {
		return commonQuestion;
	}

	public void setCommonQuestion(CommonQuestion commonQuestion) {
		this.commonQuestion = commonQuestion;
	}

	public String getOtherQuestion() {
		return otherQuestion;
	}

	public void setOtherQuestion(String otherQuestion) {
		this.otherQuestion = otherQuestion;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public UploadedFile getFile2() {
		return file2;
	}

	public void setFile2(UploadedFile file2) {
		this.file2 = file2;
	}

	public HealthCare getHealthCare() {
		return healthCare;
	}

	public void setHealthCare(HealthCare healthCare) {
		this.healthCare = healthCare;
	}

	public ChatQuestion getChatQuestionResponse() {
		return chatQuestionResponse;
	}

	public void setChatQuestionResponse(ChatQuestion chatQuestionResponse) {
		this.chatQuestionResponse = chatQuestionResponse;
	}

	public UploadedFile getFile3() {
		return file3;
	}

	public void setFile3(UploadedFile file3) {
		this.file3 = file3;
	}

	public ReplyQuestion getReply() {
		return reply;
	}

	public void setReply(ReplyQuestion reply) {
		this.reply = reply;
	}

}
