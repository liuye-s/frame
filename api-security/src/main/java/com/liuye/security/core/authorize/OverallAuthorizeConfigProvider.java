package com.liuye.security.core.authorize;


import com.liuye.security.core.properties.SecurityConstants;
import com.liuye.security.core.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @program: frame
 * @description 核心模块的授权配置提供器，安全模块涉及的url的授权配置在这里。
 * @author: liuhui
 * @create: 2019-03-31 15:24
 **/
@Component
@Order(Integer.MIN_VALUE)
public class OverallAuthorizeConfigProvider implements AuthorizeConfigProvider{

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {


        config.antMatchers(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE,
                SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
                securityProperties.getClientProperties().getSignInPage(),
                securityProperties.getClientProperties().getSession().getSessionInvalidUrl()).permitAll();
        if(securityProperties.getClientProperties().getPermitUrl() != null){
            List<String> lis = Arrays.asList(securityProperties.getClientProperties().getPermitUrl().split(","));
            if(lis.size() > 0){
                for(String s : lis){
                    config.antMatchers(s).permitAll();
                }
            }

        }


        if (StringUtils.isNotBlank(securityProperties.getClientProperties().getSignOutUrl())) {
            config.antMatchers(securityProperties.getClientProperties().getSignOutUrl()).permitAll();
        }

        return false;
    }
}
