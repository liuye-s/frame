package com.liuye.security.core;

import com.liuye.security.core.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @program: epp
 * @description 配置类，使SecurityProperties 配置生效
 * @author: liuhui
 * @create: 2019-03-30 15:16
 **/
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {
}
