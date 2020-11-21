package com.mithun.db.postgre.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mithun.db.postgre.dao.ActorDao;
import com.mithun.db.postgre.model.ActorInfo;

@Service
public class ActorServiceImpl implements ActorService {
	
	@Autowired
	private ActorDao actorDao;
	
	@Override
	@Transactional("postgreTransactionManager")
	public ActorInfo findById(int id) {
		// TODO Auto-generated method stub
		return actorDao.findById(id);
	}

	@Override
	@Transactional("postgreTransactionManager")
	public List<ActorInfo> findAll() {
		// TODO Auto-generated method stub
		return actorDao.findAll();
	}

}
