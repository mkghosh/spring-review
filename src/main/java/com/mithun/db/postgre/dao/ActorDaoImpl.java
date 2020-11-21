package com.mithun.db.postgre.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mithun.db.postgre.mappers.ActorRowMapper;
import com.mithun.db.postgre.model.ActorInfo;

@Repository
public class ActorDaoImpl implements ActorDao {

	@Autowired
	@Qualifier("postgresJdbcTemplate")
	private JdbcTemplate template;

	@Override
	@Transactional("postgreTransactionManager")
	public ActorInfo findById(int actorId) {
		List<ActorInfo> actor = template.query("select * from actor_info where actor_id =" + actorId, new ActorRowMapper());
		return actor.get(0);
	}

	@Override
	@Transactional("postgreTransactionManager")
	public List<ActorInfo> findAll() {
		return template.query("select * from public.actor_info", new ActorRowMapper());
	}

}
