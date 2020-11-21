package com.mithun.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mithun.db.postgre.model.ActorInfo;
import com.mithun.db.postgre.service.ActorService;

@RestController
public class ActorController {

	@Autowired
	private ActorService repo;

	@GetMapping("/actor")
	public List<ActorInfo> getAllNotes() {
		return repo.findAll();
	}

	// Get the actor details by
	// ID
	@GetMapping("/actor/{id}")
	public ActorInfo getActorInfoById(@PathVariable(value = "id") int id) {
		return repo.findById(id);
	}
}
