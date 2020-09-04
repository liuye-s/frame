package com.liuye.common.authorize.core.util;

import com.liuye.common.authorize.rbac.entities.CommonUser;
import org.springframework.security.core.Authentication;

public class AuthUtil {

    public static CommonUser getUserInfo(Authentication authentication){
        if (authentication==null)
            return null;

        CommonUser commonUser = (CommonUser) authentication.getPrincipal();
        return commonUser;
    }
}
