package com.wbda.oauth2.service;

import com.wbda.oauth2.entity.CustomUserDetail;
import com.wbda.oauth2.entity.TUser;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        logger.error("user:" + name + "loadUserByUsername");

        TUser u = userService.getUserByName(name);
        if (u == null) {
            throw  new UsernameNotFoundException("userName:" + name + "no find!");
        }

        CustomUserDetail user = new CustomUserDetail();
        user.setUserId(1);
        user.setUsername(name);
        user.setPassword("");
        List<GrantedAuthority> grantedAuthorityList =  new ArrayList<>();
        grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
        user.setGrantedAuthorityList(grantedAuthorityList);

        return user;
    }
}
