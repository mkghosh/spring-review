package com.mithun.db.postgre.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mithun.db.postgre.model.ActorInfo;

public class ActorRowMapper implements RowMapper<ActorInfo> {

	@Override
	public ActorInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		ActorInfo actor = new ActorInfo();
		actor.setActorId(rs.getInt("actor_id"));
		actor.setFirstName(rs.getString("first_name"));
		actor.setLastName(rs.getString("last_name"));
		actor.setFilmInfo(rs.getString("film_info"));
		return actor;
	}

}
