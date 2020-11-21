package com.mithun.db.postgre.dao;

import java.util.List;

import com.mithun.db.postgre.model.ActorInfo;

public interface ActorDao {
	ActorInfo findById(int id);
	
	List<ActorInfo> findAll();
}
