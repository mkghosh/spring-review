package com.mithun.db.postgre.service;

import java.util.List;

import com.mithun.db.postgre.model.ActorInfo;

public interface ActorService {
	
	ActorInfo findById(int id);
	
	List<ActorInfo> findAll();

}
