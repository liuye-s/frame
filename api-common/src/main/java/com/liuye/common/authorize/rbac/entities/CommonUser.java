package com.liuye.common.authorize.rbac.entities;

import org.springframework.security.core.userdetails.UserDetails;


public interface CommonUser extends UserDetails {
    


    Long getCommonUserId();





}
