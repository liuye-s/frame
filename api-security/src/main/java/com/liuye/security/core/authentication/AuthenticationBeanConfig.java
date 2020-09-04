package com.liuye.security.core.authentication;

import com.liuye.common.util.crypto.MyPasswordEncoder;
import com.liuye.security.client.session.DefaultExpiredSessionStrategy;
import com.liuye.security.client.session.DefaultInvalidSessionStrategy;
import com.liuye.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * @program: frame
 * @description
 * @author: liuhui
 * @create: 2020-08-31 14:51
 **/
@Configuration
public class AuthenticationBeanConfig {
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 默认密码处理器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {

//		return new BCryptPasswordEncoder(); //BCryptPasswordEncoder
        //自定义MD5 加密方式
        return new MyPasswordEncoder();
    }

    /**
     * session失效时的处理策略配置
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
    public InvalidSessionStrategy invalidSessionStrategy(){
        return new DefaultInvalidSessionStrategy(securityProperties);
    }

    /**
     * 并发登录导致前一个session失效时的处理策略配置
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy(){
        return new DefaultExpiredSessionStrategy(securityProperties);
    }

}
