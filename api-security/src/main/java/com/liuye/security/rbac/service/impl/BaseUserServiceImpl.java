package com.liuye.security.rbac.service.impl;

import com.liuye.security.rbac.service.RbacUserService;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @program: frame
 * @description
 * @author: liuhui
 * @create: 2020-08-31 16:44
 **/
@Service("baseUserServiceImpl")
public class BaseUserServiceImpl implements RbacUserService {



    @Override
    public UserDetails findUser(String username) throws UsernameNotFoundException {

        return new User(username,"e10adc3949ba59abbe56e057f20f883e", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
    }

    @Override
    public UserDetails findUserByPhoneNumber(String phoneNumber) throws UsernameNotFoundException {

        return new User(phoneNumber,"e10adc3949ba59abbe56e057f20f883e", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));


    }
}
