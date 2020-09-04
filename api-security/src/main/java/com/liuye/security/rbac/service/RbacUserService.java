package com.liuye.security.rbac.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface RbacUserService {

    UserDetails findUser(String username) throws UsernameNotFoundException;

    UserDetails findUserByPhoneNumber(String phoneNumber) throws UsernameNotFoundException;



}
