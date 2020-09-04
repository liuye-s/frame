package com.liuye.security.rbac.service.impl;

import com.liuye.security.rbac.service.RbacService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


/**
 * @program: frame
 * @description
 * @author: liuhui
 * @create: 2019-04-01 09:17
 **/
@Component("rbacService")
public class RbacServiceImpl implements RbacService {


    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        boolean hasPermission = true;

        //这里查询菜单 写判断逻辑

        System.out.println("------ " + (hasPermission ? "允许" : "拒绝") + " 访问地址: " + request.getRequestURI());
        return hasPermission;
    }



}
