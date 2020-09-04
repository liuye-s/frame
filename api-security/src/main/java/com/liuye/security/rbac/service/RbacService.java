package com.liuye.security.rbac.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: epp
 * @description
 * @author: liuhui
 * @create: 2019-04-01 09:16
 **/

public interface RbacService {

    boolean hasPermission(HttpServletRequest request, Authentication authentication);

}
