package com.dao;



import org.hibernate.Session;

import com.genericdao.GenericDao;
import com.genericdao.SessionManager;
import com.model.CommonQuestion;

public class CommonQuestionDao extends GenericDao<CommonQuestion> {

	public CommonQuestion getOne(int id){
		Session session = SessionManager.getSession();
		session.beginTransaction();
		CommonQuestion l = (CommonQuestion)session.get(CommonQuestion.class, id);
		session.close();
		return l;
	}
}
