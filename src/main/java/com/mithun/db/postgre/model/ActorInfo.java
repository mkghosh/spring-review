package com.mithun.db.postgre.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class ActorInfo {
	//actor_id, first_name, last_name, film_info
	@Id
	private int actorId;
	
	@Column
	private String firstName;
	
	@Column
	private String lastName;
	
	@Column
	private String filmInfo;
}
