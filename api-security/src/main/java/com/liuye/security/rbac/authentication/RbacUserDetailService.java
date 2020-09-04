package com.liuye.security.rbac.authentication;


import com.liuye.common.util.beans.SpringContextUtil;
import com.liuye.security.core.properties.SecurityConstants;
import com.liuye.security.core.properties.SecurityProperties;
import com.liuye.security.rbac.entities.BaseUsers;
import com.liuye.security.rbac.service.RbacUserService;
import com.liuye.security.rbac.service.impl.BaseUserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: frame
 * @description
 * @author: liuhui
 * @create: 2019-05-19 15:01
 **/
@Component
@Transactional
public class RbacUserDetailService  implements UserDetailsService {

    @Autowired
    private SecurityProperties securityProperties;

    private RbacUserService rbacUserService;

    public RbacUserDetailService(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
        try {
//            this.rbacUserService = SpringContextUtil.getBean( BaseUserServiceImpl.class);
            this.rbacUserService = new BaseUserServiceImpl();
        } catch (Exception ex) {
            this.rbacUserService = SpringContextUtil.getBean(SecurityConstants.DEFAULT_RBAC_USER_SERVICE, RbacUserService.class);
        }

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return rbacUserService.findUser(username);
    }

    public UserDetails findUserByPhoneNumber(String phoneNumber) throws UsernameNotFoundException {
        return rbacUserService.findUserByPhoneNumber(phoneNumber);
    }
//    private Logger logger = LoggerFactory.getLogger(getClass());
//
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        BaseUsers testUsers = new BaseUsers();
//        testUsers.setUsername("admin");
//        testUsers.setPassword("e10adc3949ba59abbe56e057f20f883e");
//
//        return testUsers;
//    }
//
//    public UserDetails findUserByPhoneNumber(String phoneNumber) throws UsernameNotFoundException {
//        BaseUsers testUsers = new BaseUsers();
//        testUsers.setUsername("admin");
//        testUsers.setPassword("e10adc3949ba59abbe56e057f20f883e");
//
//        return testUsers;
//    }
}
