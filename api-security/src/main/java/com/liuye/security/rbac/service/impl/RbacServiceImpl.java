package com.liuye.security.rbac.service.impl;

import com.liuye.common.authorize.rbac.entities.CommonUser;
import com.liuye.security.core.properties.SecurityProperties;
import com.liuye.security.rbac.service.RbacService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;


/**
 * @program: frame
 * @description
 * @author: liuhui
 * @create: 2019-04-01 09:17
 **/
@Component("rbacService")
public class RbacServiceImpl implements RbacService {
    private AntPathMatcher antPathMatcher = new AntPathMatcher();


    @Autowired
    private SecurityProperties securityProperties;


    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        boolean hasPermission = false;
        if (securityProperties.getClientProperties().getPermitUrl() != null) {
            List<String> permitUrlList = Arrays.asList(securityProperties.getClientProperties().getPermitUrl().split(","));
            if (permitUrlList!=null && permitUrlList.size()>0) {
                String uri = request.getRequestURI();
                for (String permitUrl : permitUrlList) {
                    if (antPathMatcher.match("/" + permitUrl.replace(".", "*"), uri)) {
                        hasPermission = true;
                        break;
                    }
                }
            }
        }

        if (!hasPermission) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CommonUser) {
                //如果用户名是admin，就永远返回true
                if (StringUtils.equals(((CommonUser) principal).getUsername(), "admin")||
                        StringUtils.equals(((CommonUser) principal).getUsername(), "admin1")) {
                    hasPermission = true;
                }
            }
        }



        //这里集合菜单控制权限


        System.out.println("------ " + (hasPermission ? "允许" : "拒绝") + " 访问地址: " + request.getRequestURI());
        return hasPermission;
    }





}
