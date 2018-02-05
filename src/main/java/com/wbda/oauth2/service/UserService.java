package com.wbda.oauth2.service;

import com.wbda.oauth2.dao.TUserMapper;
import com.wbda.oauth2.entity.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserService {

    @Autowired
    private TUserMapper userMapper;

    TUser getUserByName(String name) {
        return  userMapper.getUserByName(name);
    }

}
