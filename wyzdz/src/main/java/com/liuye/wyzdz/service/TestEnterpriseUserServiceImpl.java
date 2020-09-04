package com.liuye.wyzdz.service;


import com.liuye.security.rbac.service.RbacUserService;
import com.liuye.wyzdz.entities.TestUsers;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * @program: wyzdz
 * @description
 * @author: liuhui
 * @create: 2020-05-19 21:38
 **/
@Service
public class TestEnterpriseUserServiceImpl implements RbacUserService {
    @Override
    public UserDetails findUser(String username) throws UsernameNotFoundException {

        TestUsers testUsers = new TestUsers();
        testUsers.setUsername("admin1");
        testUsers.setPassword("e10adc3949ba59abbe56e057f20f883e");

        return testUsers;
    }

    @Override
    public UserDetails findUserByPhoneNumber(String phoneNumber) throws UsernameNotFoundException {
        return null;
    }


}
