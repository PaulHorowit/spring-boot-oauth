package com.wbda.oauth2.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CustomUserDetails {
    UserDetails loadUserByUsername(String username, String password) throws UsernameNotFoundException;
}


