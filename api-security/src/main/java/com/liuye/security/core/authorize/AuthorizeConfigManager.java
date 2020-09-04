package com.liuye.security.core.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * @program: frame
 * @description 授权信息管理器 用于收集系统中所有 AuthorizeConfigProvider 并加载其配置
 * @author: liuhui
 * @create: 2019-03-31 15:14
 **/
public interface AuthorizeConfigManager {
    /**
     * @param config
     */
    void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);

}
