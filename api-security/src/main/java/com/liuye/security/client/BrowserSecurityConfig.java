package com.liuye.security.client;


import com.liuye.security.core.authentication.FormAuthenticationConfig;
import com.liuye.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.liuye.security.core.authorize.AuthorizeConfigManager;
import com.liuye.security.core.properties.SecurityProperties;
import com.liuye.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @program: baseframe
 * @description
 * @author: liuhui
 * @create: 2019-05-19 13:09
 **/
@Component
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private FormAuthenticationConfig formAuthenticationConfig;

    @Autowired
    private UserDetailsService rbacUserDetailService;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        formAuthenticationConfig.configure(http);

        if (securityProperties.getClientProperties().getSession().getUnlimitConcurrentSession()!=null &&
                securityProperties.getClientProperties().getSession().getUnlimitConcurrentSession()) {
            http
            .apply(validateCodeSecurityConfig)
            .and()
            .apply(smsCodeAuthenticationSecurityConfig)
            .and()
                //记住我配置，如果想在'记住我'登录时记录日志，可以注册一个InteractiveAuthenticationSuccessEvent事件的监听器
            .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getClientProperties().getRememberMeSeconds())
                .userDetailsService(rbacUserDetailService)
                .and()
            .headers().frameOptions().disable()
                .and()
            .authorizeRequests()
                .antMatchers("/authentication/require",securityProperties.getClientProperties().getSignInPage(), "/sso").permitAll()
                .and()
            .logout()
                //不配做默认也是"/logout"
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID")
                .and()
            .csrf().disable();
        } else {
            http
            .apply(validateCodeSecurityConfig)
            .and()
            .apply(smsCodeAuthenticationSecurityConfig)
            .and()
                //记住我配置，如果想在'记住我'登录时记录日志，可以注册一个InteractiveAuthenticationSuccessEvent事件的监听器
            .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getClientProperties().getRememberMeSeconds())
                .userDetailsService(rbacUserDetailService)
                .and()
            .headers().frameOptions().disable()
                .and()
            .sessionManagement()
                //.invalidSessionStrategy(invalidSessionStrategy)
                .maximumSessions(securityProperties.getClientProperties().getSession().getMaximumSessions())
                .maxSessionsPreventsLogin(securityProperties.getClientProperties().getSession().isMaxSessionsPreventsLogin())
                //.expiredSessionStrategy(sessionInformationExpiredStrategy)
                .and()
                .and()
            .authorizeRequests()
                .antMatchers("/authentication/require",securityProperties.getClientProperties().getSignInPage(), "/sso").permitAll()
                .and()
            .logout()
                //不配做默认也是"/logout"
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID")
                .and()
            .csrf().disable();
        }

        authorizeConfigManager.config(http.authorizeRequests());
    }

    /**
     * 记住我功能的token存取器配置
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
//		tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }
}
