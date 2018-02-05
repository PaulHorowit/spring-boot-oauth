package com.wbda.oauth2.service;

import com.wbda.oauth2.entity.CustomUserDetail;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailImpl implements CustomUserDetails {

    @Override
    public UserDetails loadUserByUsername(String name, String password) throws UsernameNotFoundException {

        CustomUserDetail user = new CustomUserDetail();
        user.setUserId(1);
        user.setUsername(name);
        user.setPassword("$2a$10$HoscWDPnBpJUyqrT3UQWJOpGVhAfOWQRxJ8iNjmgSfE2S913zxoZC");
        List<GrantedAuthority> grantedAuthorityList =  new ArrayList<>();
        grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
        user.setGrantedAuthorityList(grantedAuthorityList);

        return user;
    }
}
