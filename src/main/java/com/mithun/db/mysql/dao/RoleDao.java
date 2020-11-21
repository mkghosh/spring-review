package com.mithun.db.mysql.dao;

import com.mithun.db.mysql.entity.UserRole;

public interface RoleDao {

	public UserRole findRoleByName(String theRoleName);
	
}
