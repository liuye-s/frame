package com.liuye.security.rbac.authorize;

import com.liuye.security.core.authorize.AuthorizeConfigProvider;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @program: frame
 * @description
 * @author: liuhui
 * @create: 2019-03-31 15:11
 **/
@Component
@Order(Integer.MAX_VALUE)
public class RbacAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config
                .antMatchers(HttpMethod.GET, "/fonts/**").permitAll()
                .antMatchers(HttpMethod.GET,
                        "/**/*.html",
                        "/me",
                        "/resource").authenticated()
                .anyRequest()
                    .access("@rbacService.hasPermission(request, authentication)");

        return true;
    }
}
