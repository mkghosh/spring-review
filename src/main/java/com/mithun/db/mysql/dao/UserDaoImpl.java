package com.mithun.db.mysql.dao;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mithun.db.mysql.entity.RestUser;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	@Qualifier("entityManager")
	private EntityManager entityManager;

	@Override
	public RestUser findByUserName(String theUserName) {
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		// now retrieve/read from database using username
		Query<RestUser> theQuery = currentSession.createQuery("from RestUser where userName=:uName", RestUser.class);
		theQuery.setParameter("uName", theUserName);
		RestUser theUser = null;
		try {
			theUser = theQuery.getSingleResult();
		} catch (Exception e) {
			theUser = null;
		}

		return theUser;
	}

	@Override
	public void save(RestUser theUser) {
		// get current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		// create the user ... finally LOL
		currentSession.saveOrUpdate(theUser);
	}

}
