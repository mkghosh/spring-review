package com.mithun.db.mysql.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.mithun.db.mysql.entity.RestUser;
import com.mithun.db.mysql.model.ApplicationUser;

public interface UserService extends UserDetailsService {

    RestUser findByUserName(String userName);

    void save(ApplicationUser appUser);
}
