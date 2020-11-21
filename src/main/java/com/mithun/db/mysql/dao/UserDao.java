package com.mithun.db.mysql.dao;

import com.mithun.db.mysql.entity.RestUser;

public interface UserDao {

    RestUser findByUserName(String userName);
    
    void save(RestUser restUser);
    
}
